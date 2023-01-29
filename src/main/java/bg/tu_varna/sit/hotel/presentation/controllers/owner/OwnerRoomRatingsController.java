package bg.tu_varna.sit.hotel.presentation.controllers.owner;

import bg.tu_varna.sit.hotel.business.*;
import bg.tu_varna.sit.hotel.common.AlertManager;
import bg.tu_varna.sit.hotel.common.Constants;
import bg.tu_varna.sit.hotel.common.UserSession;
import bg.tu_varna.sit.hotel.common.ViewManager;
import bg.tu_varna.sit.hotel.data.entities.Hotel;
import bg.tu_varna.sit.hotel.data.entities.Reservation;
import bg.tu_varna.sit.hotel.presentation.controllers.manager.ManagerRoomRatingsController;
import bg.tu_varna.sit.hotel.presentation.models.HotelModel;
import bg.tu_varna.sit.hotel.presentation.models.RoomModel;
import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Days;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class OwnerRoomRatingsController {
    private static final Logger log = Logger.getLogger(OwnerRoomRatingsController.class);
    private final UserService userService = UserService.getInstance();
    private final ServiceService serviceService = ServiceService.getInstance();
    private final RoomService roomService = RoomService.getInstance();
    private final ReservationService reservationService = ReservationService.getInstance();
    private final HotelService hotelService = HotelService.getInstance();
    private HotelModel hotelModel;
    private List<Hotel> hotelsOfOwner;

    @FXML
    private TableView<RoomModel> roomsTable;
    @FXML
    private TableColumn<RoomModel,String> numberColumn;
    @FXML
    private TableColumn<RoomModel, String> ratingColumn;
    @FXML
    private TableColumn<RoomModel, String> typeColumn;
    @FXML
    private TableColumn<RoomModel,String> bedsColumn;
    @FXML
    private TableColumn<RoomModel,String> areaColumn;
    @FXML
    private TableColumn<RoomModel,String> priceColumn;
    @FXML
    private TableColumn<RoomModel, String> nightsColumn;

    @FXML
    private Label timeLabel;

    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;

    @FXML
    private Button roomQueryButton;

    @FXML
    private ComboBox<String> hotelsComboBox;


    public void showOwnerMainView() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.OWNER_MAIN_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Owner Main", 800, 500);
    }

    public void addHotelAndManager() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.OWNER_ADD_HOTEL_AND_MANAGER_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Owner Add Hotel And Manager", 800, 500);
    }

    public void showHotelsInfo() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.OWNER_HOTELS_INFO_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Owner Hotels Info", 800, 500);
    }

    public void showReceptionistsReservations() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.OWNER_RECEPTIONISTS_RESERVATIONS_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Owner Receptionists Reservations Info", 800, 500);
    }

    public void showRegistrationsInfo() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.OWNER_REGISTRATIONS_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Owner Registrations Info", 800, 500);
    }


    public void logout() throws IOException {
        ViewManager.closeDialogBox();
        if(UserSession.user!=null)
        {
            log.info("Owner \""+UserSession.user.getUsername()+"\" successfully logged out.");
            UserSession.user=null;//pri logout dannite za nastoqshta user sesiq se iztrivat, za da ne sa nali4ni otvun
        }
        ViewManager.changeView(Constants.View.OWNER_LOGIN_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Owner Login", 800, 500);
    }


    public void showAccountInformation() throws IOException {
        if(UserSession.user==null)
        {
            AlertManager.showAlert(Alert.AlertType.ERROR, "Грешка", "Няма заредени данни за собственик.");
        }
        else
        {
            ViewManager.closeDialogBox();
            ViewManager.openDialogBox(Constants.View.OWNER_INFO_VIEW, null,this.getClass(),"Owner Info", 652, 352);
        }
    }


    public void initialize()
    {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                timeLabel.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            }
        };
        timer.start();

        if(UserSession.user!=null)
        {
            hotelsOfOwner = userService.getUserById(UserSession.user.getId()).getHotels();
            if(hotelsOfOwner!=null && hotelsOfOwner.size()>0)
            {
                hotelsComboBox.getItems().addAll(hotelsOfOwner.stream().map(Hotel::getName).collect(Collectors.toList()));
            }
            else
            {
                hotelsComboBox.setDisable(true);
            }



            hotelsComboBox.valueProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue ov, String t, String t1) {
                    if(roomsTable.getItems()!=null)
                    {
                        roomsTable.getItems().clear();
                    }
                    roomQueryButton.setDisable(startDatePicker.getValue() == null || endDatePicker.getValue() == null);
                }
            });



            roomsTable.getColumns().forEach(column -> column.setReorderable(false));//prevents custom reordering of columns in order to avoid icon bugs
            roomsTable.getColumns().forEach(column -> column.setSortable(false));//prevents custom sorting of columns in order to avoid icon bugs


            Label label = new Label("Няма информация за стаи.");
            label.setStyle("-fx-text-fill: black;"+"-fx-background-color: white;"+"-fx-font-size: 20;");
            roomsTable.setPlaceholder(label); //shows text when there are no rooms in the database

            numberColumn.setCellValueFactory(new PropertyValueFactory<RoomModel,String>("number"));
            ratingColumn.setCellValueFactory(new PropertyValueFactory<RoomModel,String>("rating"));
            typeColumn.setCellValueFactory(new PropertyValueFactory<RoomModel,String>("type"));
            bedsColumn.setCellValueFactory(new PropertyValueFactory<RoomModel,String>("beds"));
            areaColumn.setCellValueFactory(new PropertyValueFactory<RoomModel,String>("size"));
            priceColumn.setCellValueFactory(new PropertyValueFactory<RoomModel,String>("price"));
            nightsColumn.setCellValueFactory(new PropertyValueFactory<RoomModel,String>("nightsOccupied"));

            numberColumn.setStyle("-fx-alignment:center");
            ratingColumn.setStyle("-fx-alignment:center");
            typeColumn.setStyle("-fx-alignment:center");
            bedsColumn.setStyle("-fx-alignment:center");
            areaColumn.setStyle("-fx-alignment:center");
            priceColumn.setStyle("-fx-alignment:center");
            nightsColumn.setStyle("-fx-alignment:center");

            roomQueryButton.setDisable(startDatePicker.getValue() == null || endDatePicker.getValue() == null);
        }

    }

    public void showRoomQuery() {

        if(validateDates() && validateChosenHotel())
        {
            for(Hotel currHotel : hotelsOfOwner)
            {
                if(hotelsComboBox.getValue().equals(currHotel.getName()))
                {
                    hotelModel = hotelService.getHotelByName(hotelsComboBox.getValue());
                    break;
                }
            }

            String startDateHour = startDatePicker.getValue()+" 00:00:00.000000000";
            Timestamp startDate = Timestamp.valueOf(startDateHour);

            String endDateHour = endDatePicker.getValue()+" 23:59:59.000000000";
            Timestamp endDate = Timestamp.valueOf(endDateHour);


            List<RoomModel> allRooms = roomService.getAllHotelRooms(hotelModel);//all rooms of hotel



            List<Reservation> reservationsForPeriod = reservationService.getReservationsForPeriod(hotelModel,startDate,endDate);//all reservations between start date and end date


            List<RoomModel> reservedRooms = new LinkedList<>();//list to store all rooms which have been reserved before specific date



            if(reservationsForPeriod !=null)
            {
                for(Reservation reservation: reservationsForPeriod)
                {
                    RoomModel tempRoom = reservation.getRoom().toModel();



                    //1st case
                    if(reservation.getStartDate().before(startDate) && reservation.getEndDate().after(startDate) && reservation.getEndDate().before(endDate))
                    {
                        int daysAfterStartDate = (Days.daysBetween(new DateTime(startDate.getTime()),new DateTime(reservation.getEndDate().getTime())).getDays());
                        tempRoom.setNightsOccupied(daysAfterStartDate);
                    }
                    //2nd case
                    else if(reservation.getStartDate().after(startDate) && reservation.getStartDate().before(endDate) && reservation.getEndDate().after(startDate) && reservation.getEndDate().before(endDate))
                    {
                        tempRoom.setNightsOccupied(reservation.getNightsOccupied());
                    }
                    //3rd case
                    else if(reservation.getStartDate().after(startDate) && reservation.getStartDate().before(endDate) && reservation.getEndDate().after(startDate) && reservation.getEndDate().after(endDate))
                    {
                        int daysBeforeEndDate = (Days.daysBetween(new DateTime(reservation.getStartDate().getTime()),new DateTime(endDate.getTime())).getDays());
                        tempRoom.setNightsOccupied(daysBeforeEndDate);
                    }
                    //4th case
                    else if(reservation.getStartDate().before(startDate) && reservation.getEndDate().after(endDate))
                    {
                        int daysBetweenPeriod = (Days.daysBetween(new DateTime(startDate.getTime()),new DateTime(endDate.getTime())).getDays());
                        tempRoom.setNightsOccupied(daysBetweenPeriod);
                    }


                    if(!(reservation.getEndDate().before(startDate) || reservation.getStartDate().after(endDate)))
                    {
                        if(!reservedRooms.contains(tempRoom))
                        {
                            reservedRooms.add(tempRoom);
                        }
                        else
                        {
                            for(RoomModel reservedRoom: reservedRooms)
                            {
                                if(reservedRoom.equals(tempRoom))
                                {
                                    int oldNightsOccupied = reservedRoom.getNightsOccupied();
                                    reservedRoom.setNightsOccupied(oldNightsOccupied+tempRoom.getNightsOccupied());
                                    break;
                                }
                            }
                        }
                    }



                }




                List<RoomModel> uniqueRooms = new LinkedList<>();//list to store all rooms which have been reserved before specific date
                for(RoomModel reservedRoom : reservedRooms)
                {
                    if(reservedRoom.getHotel().getId().equals(hotelModel.getId()))
                    {
                        uniqueRooms.add(reservedRoom);
                    }
                }



                //list of all rooms which have been reserved during or around the period
                List<RoomModel> roomsWithCalculatedRatingForPeriod = roomService.calculateRoomRatingsForPeriod(uniqueRooms,hotelModel);



                //if there are rooms which has never been reserved or doesn't match the criteria for period
                boolean brake=false;
                if(allRooms.size()>roomsWithCalculatedRatingForPeriod.size())
                {


                    for(RoomModel currRoom: allRooms)
                    {
                        for(RoomModel roomModel : roomsWithCalculatedRatingForPeriod)
                        {
                            if(!currRoom.getHotel().getName().equals(roomModel.getHotel().getName()))
                            {
                                currRoom.setRating(1);
                                currRoom.setNightsOccupied(0);
                                roomsWithCalculatedRatingForPeriod.add(currRoom);
                                if(allRooms.size()==roomsWithCalculatedRatingForPeriod.size()){break;}
                            }
                        }
                    }


                    Set<RoomModel> finalRooms = new HashSet<>(roomsWithCalculatedRatingForPeriod);
                    List<RoomModel> sortedRooms = new LinkedList<>(finalRooms);

                    Collections.sort(sortedRooms);
                    roomsTable.setItems(FXCollections.observableArrayList(sortedRooms));
                }

                else
                {
                    Set<RoomModel> finalRooms = new HashSet<>(roomsWithCalculatedRatingForPeriod);
                    List<RoomModel> sortedRooms = new LinkedList<>(finalRooms);

                    Collections.sort(sortedRooms);
                    roomsTable.setItems(FXCollections.observableArrayList(sortedRooms));
                }

            }


            else //this will execute if no rooms were reserved(ever)
            {
                for(RoomModel roomModel: allRooms)
                {
                    roomModel.setRating(1);
                    roomModel.setNightsOccupied(0);
                }
                roomsTable.setItems(FXCollections.observableList(allRooms));
            }
        }
    }

    private boolean validateChosenHotel(){
        if(hotelsComboBox.getValue()!=null)
        {
            return true;
        }
        else
        {
            AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","Моля изберете хотел.");
            return false;
        }
    }

    private boolean validateDates(){
        if(startDatePicker.getValue().equals(endDatePicker.getValue()))
        {
            AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","Началната и крайната дата не могат да съвпадат.");
            return false;
        }
        else if(startDatePicker.getValue().compareTo(endDatePicker.getValue()) > 0)
        {
            AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","Началната дата не може да е след крайната дата.");
            return false;
        }
        else {return true;}//everything is OK with the dates
    }

    public void startDatePickerChanged()
    {
        if(roomsTable.getItems()!=null)
        {
            roomsTable.getItems().clear();
        }
        if(hotelsComboBox.getValue()!=null)
        {
            hotelsComboBox.setValue(null);
        }
        roomQueryButton.setDisable(startDatePicker.getValue() == null || endDatePicker.getValue() == null);
    }
    public void endDatePickerChanged()
    {
        if(roomsTable.getItems()!=null)
        {
            roomsTable.getItems().clear();
        }
        if(hotelsComboBox.getValue()!=null)
        {
            hotelsComboBox.setValue(null);
        }
        roomQueryButton.setDisable(startDatePicker.getValue() == null || endDatePicker.getValue() == null);
    }


}