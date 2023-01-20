package bg.tu_varna.sit.hotel.business;

import bg.tu_varna.sit.hotel.common.AlertManager;
import bg.tu_varna.sit.hotel.data.entities.Room;
import bg.tu_varna.sit.hotel.data.repositories.implementations.RoomRepositoryImpl;
import bg.tu_varna.sit.hotel.presentation.controllers.owner.cache.RoomsInformation;
import bg.tu_varna.sit.hotel.presentation.models.HotelModel;
import bg.tu_varna.sit.hotel.presentation.models.RoomModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class RoomService {
    private static final Logger log = Logger.getLogger(RoomService.class);
    private final RoomRepositoryImpl roomRepository = RoomRepositoryImpl.getInstance();

    //lazy-loaded singleton pattern
    public static RoomService getInstance() {
        return RoomService.RoomServiceHolder.INSTANCE;
    }

    private static class RoomServiceHolder {
        public static final RoomService INSTANCE = new RoomService();
    }

    public ObservableList<RoomModel> getAllHotelRooms(HotelModel hotelModel) {
        List<Room> rooms = roomRepository.getAllRoomsOfHotel(hotelModel.toEntity());

        if(rooms.isEmpty()){return null;}

        else
        {
            return FXCollections.observableList(
                    rooms.stream().map(r -> new RoomModel(
                            r.getId(),
                            r.getNumber(),
                            new HotelModel(r.getHotel()),
                            r.getPrice(),
                            r.getType(),
                            r.getSize(),
                            r.getRating(),
                            r.getNightsOccupied(),
                            r.getIsOccupied()
                    )).collect(Collectors.toList())
            );
        }
    }

    public boolean addRoom(RoomModel roomModel) {
        return roomRepository.save(roomModel.toEntity());
    }

    public boolean updateRoom(RoomModel roomModel) {
        return roomRepository.update(roomModel.toEntity());
    }

    public boolean deleteRoom(RoomModel roomModel){return roomRepository.delete(roomModel.toEntity());}

    public boolean addRooms(RoomsInformation roomsInformation,HotelModel hotelModel){

        for(int i=0;i<Integer.parseInt(roomsInformation.getFloorsNumber());i++)
        {
            List <CheckBox> checkBoxesOfTabList = new ArrayList<>();
            for(int c=0;c<((Pane)(roomsInformation.getTabPane().getTabs().get(i).getContent())).getChildren().size()-2;c++)//size is minus 2 because we count only the number of each checkboxes per dynamically created Tab and we don't count the icon and the text field
            {
                //Here I am absolutely sure that every children of ((Pane)component) is CheckBox
                checkBoxesOfTabList.add((CheckBox)((Pane)(roomsInformation.getTabPane().getTabs().get(i).getContent())).getChildren().get(c));
            }
            List <CheckBox> selectedCheckBoxes = getSelectedRoomTypesCheckBoxes(checkBoxesOfTabList);

            int selectedCheckBoxesCounter=0;
            for(int j=1;j<=Integer.parseInt(((TextField)(((Pane)(roomsInformation.getTabPane().getTabs().get(i).getContent())).getChildren().get(checkBoxesOfTabList.size()+1))).getText());j++)
            {
                if (selectedCheckBoxesCounter == selectedCheckBoxes.size())
                {
                    selectedCheckBoxesCounter = 0;
                }

                StringBuilder sb = new StringBuilder();
                if (j < 10)
                {
                    sb.append(i+1).append(0).append(j);
                }
                else
                {
                    sb.append(i+1).append(j);
                }
                //florsNumber(i)+0j ako j e 1-9
                //floorsNumber(i)+j ako j>9

                String type = selectedCheckBoxes.get(selectedCheckBoxesCounter).getText();
                int price = 0;//indicator for error in the database
                int area = 0;//indicator for error in the database
                for (int k = 0; k < roomsInformation.getRoomTypeCheckBoxes().size(); k++)
                {
                    if(type.equals(roomsInformation.getRoomTypeCheckBoxes().get(k).getText()))
                    {
                        price=Integer.parseInt(roomsInformation.getRoomTypePrices().get(k).getText());
                        area=Integer.parseInt(roomsInformation.getRoomTypeAreas().get(k).getText());
                        break;
                    }
                }

                if(!addRoom(new RoomModel(1L,Integer.parseInt(String.valueOf(sb)),hotelModel,price,type,area,0D,0,false)))
                {
                    return false;
                }
                selectedCheckBoxesCounter++;
            }
        }
        return true;//everything is OK
    }


    //used when creating new hotel + new manager
    public boolean validateFloorsField(String floorsNumber) {
        String regex = "^[^0]([0-9]{0,3})$";

        Pattern p = Pattern.compile(regex);
        if(floorsNumber == null) {return false;}
        else
        {
            Matcher m = p.matcher(floorsNumber);
            return m.matches();
        }
    }

    //used when creating new hotel + new manager
    public boolean validateFloorsNumber(String floorsNumber)
    {
        if(floorsNumber.equals(""))
        {
            AlertManager.showAlert(Alert.AlertType.ERROR, "Грешка", "Моля въведете брой ежати на хотела.");
            return false;
        }
        else if(!validateFloorsField(floorsNumber))//validates that floors number is an actual number
        {
            AlertManager.showAlert(Alert.AlertType.ERROR, "Грешка", "Етажите на хотел могат да бъдат от 1 до 100.");
            return false;
        }
        else if(Integer.parseInt(floorsNumber)<1 || Integer.parseInt(floorsNumber)>100)//validates that floors number is [1-100]
        {
            AlertManager.showAlert(Alert.AlertType.ERROR, "Грешка", "Етажите на хотел могат да бъдат от 1 до 100.");
            return false;
        }
        else
        {
            return true;//everything is OK
        }
    }

    //used when creating new hotel + new manager
    public boolean validateRoomsNumberDynamicField(String roomsNumber) {
        String regex = "^[^0]([0-9]{0,2})$";

        Pattern p = Pattern.compile(regex);
        if(roomsNumber == null) {return false;}
        else
        {
            Matcher m = p.matcher(roomsNumber);
            return m.matches();
        }
    }


    public boolean validateRoomType(String type) {
        String regex = "^([\\u0430-\\u044F OR a-z]{3,50})$";

        Pattern p = Pattern.compile(regex);
        if(type == null) {return false;}
        else
        {
            Matcher m = p.matcher(type);
            return m.matches();
        }
    }

    //used when creating new hotel + new manager
    public boolean validateRoomsNumberPerFloor(int floor, String roomsNumberPerFloor)
    {
            if (roomsNumberPerFloor.equals(""))
            {
                AlertManager.showAlert(Alert.AlertType.ERROR, "Грешка", "Моля въведете брой стаи за етаж " + floor + " на хотела.");
                return false;
            }
            else if (!validateRoomsNumberDynamicField(roomsNumberPerFloor))//validates that rooms number is an actual number
            {
                AlertManager.showAlert(Alert.AlertType.ERROR, "Грешка", "Стаите на етаж " + floor + " на хотелa могат да бъдат от 1 до 98.");
                return false;
            }
            else if (Integer.parseInt(roomsNumberPerFloor) < 1 || Integer.parseInt(roomsNumberPerFloor) > 98)//validates that rooms number per floor is [1-98]
            {
                AlertManager.showAlert(Alert.AlertType.ERROR, "Грешка", "Стаите на етаж " + floor + " на хотелa могат да бъдат от 1 до 98.");
                return false;
            }
            else
            {
                return true;//everything is OK
            }
    }

    //used when creating new hotel + new manager
    public boolean validateRoomTypesCheckBoxesSelection(int floor, List<CheckBox> roomTypesCheckBoxesList) {
        int counter=0;
        for(CheckBox checkBox :roomTypesCheckBoxesList)
        {
            if(!checkBox.isSelected()){counter++;}//counts all checkboxes which are NOT selected
        }
        if(roomTypesCheckBoxesList.size()==counter)
        {
            AlertManager.showAlert(Alert.AlertType.ERROR, "Грешка", "Моля изберете минимум 1 тип стая за етаж "+floor+" на вашия хотел.");
            return false;//No checkBox for room type was selected
        }
        else
        {
            return true;//everything is OK
        }
    }

    //used when creating new hotel + new manager
    //overloaded
    public boolean validateRoomTypesCheckBoxesSelection(List<CheckBox> roomTypesCheckBoxesList) {
        int counter=0;
        for(CheckBox checkBox :roomTypesCheckBoxesList)
        {
            if(!checkBox.isSelected()){counter++;}//counts all checkboxes which are NOT selected
        }
        if(roomTypesCheckBoxesList.size()==counter)
        {
            AlertManager.showAlert(Alert.AlertType.ERROR, "Грешка", "Моля изберете минимум 1 тип стая за етажите на вашия хотел.");
            return false;//No checkBox for room type was selected
        }
        else
        {
            return true;//everything is OK
        }
    }

    public List<CheckBox> getSelectedRoomTypesCheckBoxes(List<CheckBox> roomTypesCheckBoxesList)
    {
        List<CheckBox> selectedRoomTypesCheckBoxesList = new ArrayList<>();
        for (CheckBox checkBox : roomTypesCheckBoxesList)
        {
            if (checkBox.isSelected())
            {
                selectedRoomTypesCheckBoxesList.add(checkBox);
            }
        }
        return selectedRoomTypesCheckBoxesList;
    }

    public void changeColorOfSelectedRoomTypesCheckBoxes(List<CheckBox> selectedRoomTypesCheckBoxesList)
    {
        for (CheckBox checkBox : selectedRoomTypesCheckBoxesList)
        {
            checkBox.setStyle("-fx-text-fill:  #e68a00;");
        }
    }

    public RoomModel getRoomByNumber(String number,HotelModel hotelModel) {
        Room room = roomRepository.getByNumber(number,hotelModel.toEntity());
        return (room == null) ? null : new RoomModel(room);
    }

    public boolean isRoomExists(String number,HotelModel hotelModel) {
        return getRoomByNumber(number,hotelModel) != null;
    }


    //method used when editing room information
    public boolean validateRoomInfoFields(String number, String type, String area, String price, String hotelName, RoomModel selectedRoom){

        if(number.equals("")||type.equals("")||area.equals("")||price.equals(""))
        {
            AlertManager.showAlert(Alert.AlertType.ERROR, "Грешка", "Моля въведете данни във всички полета.");
            return false;
        }
        else if(!validateRoomNumber(number) || Integer.parseInt(number)<101 || Integer.parseInt(number)>10098)
        {
            AlertManager.showAlert(Alert.AlertType.ERROR, "Грешка", "Номерът на стая може да съдържа от 3 до 5 цифри[101-10098].");
            return false;
        }
        else if(!Objects.equals(selectedRoom.getNumber(), Integer.parseInt(number)) && isRoomExists(number,HotelService.getInstance().getHotelByName(hotelName)))
        {
            AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","Стая с номер: \""+number+"\" вече съществува във вашия хотел.");
            return false;
        }
        else if(!validateRoomType(type))
        {
            AlertManager.showAlert(Alert.AlertType.ERROR, "Грешка", "Типът на една стая трябва да съдържа между 4 и 50 символа като те могат да бъдат само малки букви на кирилица или на латиница.");
            return false;
        }
        else if(!validateRoomAreaField(area) || Integer.parseInt(area)<10 || Integer.parseInt(area)>10000)
        {
            AlertManager.showAlert(Alert.AlertType.ERROR, "Грешка", "Размерът на една стая може да бъде между 10 и 10000 кв.м.");
            return false;
        }
        else if(!validateRoomPriceField(price) || Integer.parseInt(price)<1 || Integer.parseInt(area)>100000)
        {
            AlertManager.showAlert(Alert.AlertType.ERROR, "Грешка", "Цената на една стая може да бъде между 1 и 100000 лв.");
            return false;
        }
        else if(Objects.equals(selectedRoom.getNumber(), Integer.parseInt(number)) && Objects.equals(selectedRoom.getType(), type) && Objects.equals(selectedRoom.getSize(), Integer.parseInt(area)) && Objects.equals(selectedRoom.getPrice(), Integer.parseInt(price)))
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    //method used when creating a new room
    public boolean validateRoomInfoFields(String number, String type, String area, String price, String hotelName){

        if(number.equals("")||type.equals("")||area.equals("")||price.equals(""))
        {
            AlertManager.showAlert(Alert.AlertType.ERROR, "Грешка", "Моля въведете данни във всички полета.");
            return false;
        }
        else if(!validateRoomNumber(number) || Integer.parseInt(number)<101 || Integer.parseInt(number)>10098)
        {
            AlertManager.showAlert(Alert.AlertType.ERROR, "Грешка", "Номерът на стая може да съдържа от 3 до 5 цифри[101-10098].");
            return false;
        }
        else if(isRoomExists(number,HotelService.getInstance().getHotelByName(hotelName)))
        {
            AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","Стая с номер: \""+number+"\" вече съществува във вашия хотел.");
            return false;
        }
        else if(!validateRoomType(type))
        {
            AlertManager.showAlert(Alert.AlertType.ERROR, "Грешка", "Типът на една стая трябва да съдържа между 4 и 50 символа като те могат да бъдат само малки букви на кирилица или на латиница.");
            return false;
        }
        else if(!validateRoomAreaField(area) || Integer.parseInt(area)<10 || Integer.parseInt(area)>10000)
        {
            AlertManager.showAlert(Alert.AlertType.ERROR, "Грешка", "Размерът на една стая може да бъде между 10 и 10000 кв.м.");
            return false;
        }
        else if(!validateRoomPriceField(price) || Integer.parseInt(price)<1 || Integer.parseInt(area)>100000)
        {
            AlertManager.showAlert(Alert.AlertType.ERROR, "Грешка", "Цената на една стая може да бъде между 1 и 100000 лв.");
            return false;
        }
        else
        {
            return true;
        }
    }



    //[101-198]-[10001-10098]
    public boolean validateRoomNumber(String roomNumber)
    {
        String regex = "^[^0]([0-9]{2,5})$";

        Pattern p = Pattern.compile(regex);
        if(roomNumber == null) {return false;}
        else
        {
            Matcher m = p.matcher(roomNumber);
            return m.matches();
        }
    }

    //used when creating new hotel + new manager
    //Room area[10-10000]
    public boolean validateRoomAreaField(String roomAreaNumber) {
        String regex = "^[^0]([0-9]{1,5})$";

        Pattern p = Pattern.compile(regex);
        if(roomAreaNumber == null) {return false;}
        else
        {
            Matcher m = p.matcher(roomAreaNumber);
            return m.matches();
        }
    }

    //used when creating new hotel + new manager
    //Room price[1-100000]
    public boolean validateRoomPriceField(String roomPriceNumber) {
        String regex = "^[^0]([0-9]{0,5})$";

        Pattern p = Pattern.compile(regex);
        if(roomPriceNumber == null) {return false;}
        else
        {
            Matcher m = p.matcher(roomPriceNumber);
            return m.matches();
        }
    }

    //used when creating new hotel + new manager
    public boolean validateRoomTypeAreaAndPriceFields(List<CheckBox> roomTypesCheckBoxesList, List<TextField>roomTypesAreaFieldsList, List<TextField>roomTypesPriceFieldsList) {

        for(int i=0;i< roomTypesCheckBoxesList.size();i++)
        {
            if(roomTypesCheckBoxesList.get(i).isSelected())
            {
                if(roomTypesAreaFieldsList.get(i).getText().equals(""))
                {
                    AlertManager.showAlert(Alert.AlertType.ERROR, "Грешка", "Моля въведете размер на стая от тип \""+roomTypesCheckBoxesList.get(i).getText()+"\".");
                    return false;
                }
                else if (!validateRoomAreaField(roomTypesAreaFieldsList.get(i).getText()))//validates that room size is an actual number
                {
                    AlertManager.showAlert(Alert.AlertType.ERROR, "Грешка", "Размерът на стая от тип \""+roomTypesCheckBoxesList.get(i).getText()+"\" може да бъде от "+roomTypesAreaFieldsList.get(i).getPromptText().substring(5,7)+" до 10000 кв.м.");
                    return false;
                }
                else if (Integer.parseInt(roomTypesAreaFieldsList.get(i).getText())<Integer.parseInt(roomTypesAreaFieldsList.get(i).getPromptText().substring(5,7)) || Integer.parseInt(roomTypesAreaFieldsList.get(i).getText())>10000)//validates that room size is [custom-10000]
                {
                    AlertManager.showAlert(Alert.AlertType.ERROR, "Грешка", "Размерът на стая от тип \""+roomTypesCheckBoxesList.get(i).getText()+"\" може да бъде от "+roomTypesAreaFieldsList.get(i).getPromptText().substring(5,7)+" до 10000 кв.м.");
                    return false;
                }



                if(roomTypesPriceFieldsList.get(i).getText().equals(""))
                {
                    AlertManager.showAlert(Alert.AlertType.ERROR, "Грешка", "Моля въведете цена на стая от тип \""+roomTypesCheckBoxesList.get(i).getText()+"\".");
                    return false;
                }
                else if (!validateRoomPriceField(roomTypesPriceFieldsList.get(i).getText()))//validates that room price is an actual number
                {
                    AlertManager.showAlert(Alert.AlertType.ERROR, "Грешка", "Цената на стая от тип \""+roomTypesCheckBoxesList.get(i).getText()+"\" може да бъде от 1 до 100000 лв.");
                    return false;
                }
                else if (Integer.parseInt(roomTypesPriceFieldsList.get(i).getText())<1 || Integer.parseInt(roomTypesPriceFieldsList.get(i).getText())>100000)//validates that room price is [1-100000]
                {
                    AlertManager.showAlert(Alert.AlertType.ERROR, "Грешка", "Цената на стая от тип \""+roomTypesCheckBoxesList.get(i).getText()+"\" може да бъде от 1 до 100000 лв.");
                    return false;
                }
            }
        }
        return true;//everything is OK
    }

    //used when creating new hotel + new manager
    public boolean validateRoomsInformationFields(String floorsNumber, List<CheckBox> roomTypesCheckBoxesList,List<TextField>roomTypesAreaFieldsList, List<TextField>roomTypesPriceFieldsList){
        return validateFloorsNumber(floorsNumber) && validateRoomTypesCheckBoxesSelection(roomTypesCheckBoxesList) && validateRoomTypeAreaAndPriceFields(roomTypesCheckBoxesList, roomTypesAreaFieldsList, roomTypesPriceFieldsList);
    }

    //used when creating new hotel + new manager(but cached data is loaded in the rooms information controller)
    public boolean validateRoomsInformationFields(List<CheckBox> roomTypesCheckBoxesList,List<TextField>roomTypesAreaFieldsList, List<TextField>roomTypesPriceFieldsList){
        return validateRoomTypesCheckBoxesSelection(roomTypesCheckBoxesList) && validateRoomTypeAreaAndPriceFields(roomTypesCheckBoxesList, roomTypesAreaFieldsList, roomTypesPriceFieldsList);
    }

}