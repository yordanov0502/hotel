package bg.tu_varna.sit.hotel.presentation.controllers.manager;

import bg.tu_varna.sit.hotel.business.*;
import bg.tu_varna.sit.hotel.common.AlertManager;
import bg.tu_varna.sit.hotel.common.Constants;
import bg.tu_varna.sit.hotel.common.UserSession;
import bg.tu_varna.sit.hotel.common.ViewManager;
import bg.tu_varna.sit.hotel.data.entities.Reservation;
import bg.tu_varna.sit.hotel.presentation.models.HotelModel;
import bg.tu_varna.sit.hotel.presentation.models.RoomModel;
import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Days;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ManagerRoomRatingsController {
    private static final Logger log = Logger.getLogger(ManagerRoomRatingsController.class);
    private final UserService userService = UserService.getInstance();
    private final ServiceService serviceService = ServiceService.getInstance();
    private final RoomService roomService = RoomService.getInstance();
    private final ReservationService reservationService = ReservationService.getInstance();
    private HotelModel hotelModel;

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


    public void showManagerMainView() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.MANAGER_MAIN_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Manager Main", 800, 500);
    }

    public void addNewReceptionist() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.MANAGER_ADD_NEW_RECEPTIONIST_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Manager Add New Receptionist", 800, 500);
    }

    public void showCustomersQuery() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.MANAGER_CUSTOMERS_INFO_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Manager Customers Info", 800, 500);
    }

    public void showReceptionistsReservationsInfo() throws IOException{
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.MANAGER_RECEPTIONISTS_RESERVATIONS_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Manager Receptionists Reservations Info", 800, 500);
    }

    public void showRegistrationsInfo() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.MANAGER_REGISTRATIONS_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Manager Registrations Info", 800, 500);
    }

    public void showHotelInfo() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.MANAGER_HOTEL_INFO_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Manager Hotel Info", 800, 500);
    }



    public void logout() throws IOException {
        ViewManager.closeDialogBox();
        if(UserSession.user!=null)
        {
            log.info("Manager \""+UserSession.user.getUsername()+"\" successfully logged out.");
            UserSession.user=null;//pri logout dannite za nastoqshta user sesiq se iztrivat, za da ne sa nali4ni otvun
        }
        ViewManager.changeView(Constants.View.MANAGER_LOGIN_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Manager Login", 800, 500);
    }


    public void showAccountInformation() throws IOException {
        if(UserSession.user==null)
        {
            AlertManager.showAlert(Alert.AlertType.ERROR, "Грешка", "Няма заредени данни за мениджър.");
        }
        else
        {
            ViewManager.closeDialogBox();
            ViewManager.openDialogBox(Constants.View.MANAGER_INFO_VIEW, null,this.getClass(),"Manager Info", 652, 352);
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
            hotelModel = userService.getUserById(UserSession.user.getId()).getHotels().get(0).toModel();


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

        if(validateDates())
        {
            String startDateHour = startDatePicker.getValue()+" 00:00:00.000000000";
            Timestamp startDate = Timestamp.valueOf(startDateHour);

            String endDateHour = endDatePicker.getValue()+" 23:59:59.000000000";
            Timestamp endDate = Timestamp.valueOf(endDateHour);


            List<RoomModel> allRooms = roomService.getAllHotelRooms(hotelModel);//all rooms of hotel



            List<Reservation> reservationsForPeriod = reservationService.getReservationsForPeriod(hotelModel,startDate,endDate);//all reservations between start date and end date


            Map<Integer,RoomModel> reservedRooms = new HashMap();//list to store all rooms which have been reserved for specific period (start date - end date)



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
                    //2nd casee
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

                        if(!reservedRooms.containsKey(tempRoom.getNumber()))
                        {
                            reservedRooms.put(tempRoom.getNumber(),tempRoom);
                        }
                        else
                        {
                            int oldNightsOccupied = reservedRooms.get(tempRoom.getNumber()).getNightsOccupied();
                            reservedRooms.get(tempRoom.getNumber()).setNightsOccupied(oldNightsOccupied+tempRoom.getNightsOccupied());
                        }

                    }



                }




                //list of all rooms which have been reserved during or around the period with calculated rating
                List<RoomModel> roomsWithCalculatedRatingForPeriod = roomService.calculateRoomRatingsForPeriod(new ArrayList<>(reservedRooms.values()),hotelModel);



                //if there are rooms which has never been reserved or doesn't match the criteria for period
                if(allRooms.size()>roomsWithCalculatedRatingForPeriod.size())
                {

                    for(RoomModel currRoom: allRooms)
                    {
                        if(!reservedRooms.containsKey(currRoom.getNumber()))
                        {
                            currRoom.setRating(1);
                            currRoom.setNightsOccupied(0);
                            roomsWithCalculatedRatingForPeriod.add(currRoom);
                            if(allRooms.size()==roomsWithCalculatedRatingForPeriod.size()){break;}
                        }
                    }

                    Collections.sort(roomsWithCalculatedRatingForPeriod);
                    roomsTable.setItems(FXCollections.observableArrayList(roomsWithCalculatedRatingForPeriod));
                }

                else
                {
                    Collections.sort(roomsWithCalculatedRatingForPeriod);
                    roomsTable.setItems(FXCollections.observableArrayList(roomsWithCalculatedRatingForPeriod));
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
        roomQueryButton.setDisable(startDatePicker.getValue() == null || endDatePicker.getValue() == null);
    }
    public void endDatePickerChanged()
    {
        if(roomsTable.getItems()!=null)
        {
            roomsTable.getItems().clear();
        }
        roomQueryButton.setDisable(startDatePicker.getValue() == null || endDatePicker.getValue() == null);
    }

}