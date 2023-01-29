package bg.tu_varna.sit.hotel.presentation.controllers.manager;

import bg.tu_varna.sit.hotel.business.ReservationService;
import bg.tu_varna.sit.hotel.business.UserService;
import bg.tu_varna.sit.hotel.common.AlertManager;
import bg.tu_varna.sit.hotel.common.Constants;
import bg.tu_varna.sit.hotel.common.UserSession;
import bg.tu_varna.sit.hotel.common.ViewManager;
import bg.tu_varna.sit.hotel.data.entities.Reservation;
import bg.tu_varna.sit.hotel.data.entities.User;
import bg.tu_varna.sit.hotel.data.repositories.implementations.ReservationRepositoryImpl;
import bg.tu_varna.sit.hotel.presentation.models.HotelModel;
import bg.tu_varna.sit.hotel.presentation.models.UserModel;
import bg.tu_varna.sit.hotel.presentation.models.custom.ReservationRowModel;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.util.Callback;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ManagerReceptionistsReservationsController {
    private static final Logger log = Logger.getLogger(ManagerReceptionistsReservationsController.class);
    private final UserService userService = UserService.getInstance();
    private final ReservationService reservationService = ReservationService.getInstance();
    private HotelModel hotelModel;
    private List<String> receptionistsNames;
    private List<User> allUsers;


    @FXML
    private Label timeLabel;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;

    @FXML
    private TableView<ReservationRowModel> reservationsTable;
    @FXML
    private TableColumn<ReservationRowModel,String> resNumberColumn;
    @FXML
    private TableColumn<ReservationRowModel, String> resTypeColumn;
    @FXML
    private TableColumn<ReservationRowModel,String> resStatusColumn;
    @FXML
    private TableColumn<ReservationRowModel,String> resRoomsColumn;
    @FXML
    private TableColumn actionColumn;

    @FXML
    private Label nameLabel;
    @FXML
    private Label surnameLabel;
    @FXML
    private Label egnLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label emailLabel;

    @FXML
    private ComboBox<String> receptionistsComboBox;
    @FXML
    private Button reservationsQueryButton;




    public void showManagerMainView() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.MANAGER_MAIN_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Manager Main", 800, 500);
    }

    public void addNewReceptionist() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.MANAGER_ADD_NEW_RECEPTIONIST_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Manager Add New Receptionist", 800, 500);
    }

    public void showRegistrationsInfo() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.MANAGER_REGISTRATIONS_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Manager Registrations Info", 800, 500);
    }

    public void showRoomRatings() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.MANAGER_ROOMS_RATINGS_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Manager Room Ratings", 800, 500);
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

            reservationsTable.getColumns().forEach(column -> column.setReorderable(false));//prevents custom reordering of columns in order to avoid icon bugs
            reservationsTable.getColumns().forEach(column -> column.setSortable(false));//prevents custom sorting of columns in order to avoid icon bugs

            Label label = new Label("Няма информация за резервации.");
            label.setStyle("-fx-text-fill: black;"+"-fx-background-color: white;"+"-fx-font-size: 20;");
            reservationsTable.setPlaceholder(label); //shows text when there are no customer in the database

            resNumberColumn.setCellValueFactory(new PropertyValueFactory<ReservationRowModel,String>("number"));
            resTypeColumn.setCellValueFactory(new PropertyValueFactory<ReservationRowModel,String>("type"));
            resStatusColumn.setCellValueFactory(new PropertyValueFactory<ReservationRowModel,String>("status"));
            resRoomsColumn.setCellValueFactory(new PropertyValueFactory<ReservationRowModel,String>("rooms"));

            resNumberColumn.setStyle("-fx-alignment:center");
            resTypeColumn.setStyle("-fx-alignment:center");
            resStatusColumn.setStyle("-fx-alignment:center");
            resRoomsColumn.setStyle("-fx-alignment:center");
            actionColumn.setStyle("-fx-alignment:center");


            receptionistsComboBox.valueProperty().addListener(new ChangeListener<String>() {
                @Override
                public void changed(ObservableValue ov, String t, String t1) {
                    if(reservationsTable.getItems()!=null)
                    {
                        reservationsTable.getItems().clear();
                    }
                    reservationsQueryButton.setDisable(startDatePicker.getValue() == null || endDatePicker.getValue() == null);
                    nameLabel.setText("");
                    surnameLabel.setText("");
                    egnLabel.setText("");
                    phoneLabel.setText("");
                    usernameLabel.setText("");
                    emailLabel.setText("");
                }
            });

            reservationsQueryButton.setDisable(startDatePicker.getValue() == null || endDatePicker.getValue() == null);

            if(hotelModel.getUsers()!=null && hotelModel.getUsers().size()>0)
            {
                allUsers = hotelModel.getUsers();
                receptionistsNames = new LinkedList<>();

                for(User currUser: allUsers)
                {
                    if(currUser.getRole().equals("рецепционист"))
                    {
                        String fullName = currUser.getFirstName()+" "+currUser.getLastName();
                        receptionistsNames.add(fullName);
                    }
                }

                if(receptionistsNames!=null && receptionistsNames.size()>0)
                {
                    receptionistsComboBox.getItems().addAll(receptionistsNames);
                }
                else
                {
                    receptionistsComboBox.setDisable(true);
                }
            }
        }
    }



    private void createActionButtons() {
        Callback<TableColumn<ReservationRowModel, String>, TableCell<ReservationRowModel, String>> cellCallback =
                new Callback<>() {
                    @Override
                    public TableCell<ReservationRowModel, String> call(TableColumn<ReservationRowModel, String> param) {

                        TableCell<ReservationRowModel, String> cell = new TableCell<ReservationRowModel, String>() {

                            FontAwesomeIconView infoIcon = new FontAwesomeIconView(FontAwesomeIcon.INFO_CIRCLE);

                            public HBox hBox = new HBox(infoIcon);

                            @Override
                            protected void updateItem(String item, boolean empty) {
                                super.updateItem(item, empty);
                                if (empty)
                                {
                                    setGraphic(null);
                                    setText(null);
                                }
                                else
                                {
                                    infoIcon.setStyle("-glyph-size:20px; -fx-font-family: FontAwesome;");

                                    infoIcon.setOnMouseEntered((MouseEvent event) -> {
                                        Tooltip tooltip = new Tooltip("подробна информация");
                                        Tooltip.install(infoIcon,tooltip);
                                    });

                                    infoIcon.setOnMouseClicked((MouseEvent event) -> {

                                        ReservationRowModel reservationRowModel = getTableView().getItems().get(getIndex());
                                        try
                                        {
                                            showReservationInfo(reservationRowModel);
                                        } catch (IOException e)
                                        {
                                            e.printStackTrace();
                                        }
                                    });

                                    hBox.setStyle("-fx-alignment:center");
                                    setGraphic(hBox);
                                }
                            }
                        };

                        return cell;
                    }
                };
        actionColumn.setCellFactory(cellCallback);
    }

    private void showReservationInfo(ReservationRowModel reservationRowModel) throws IOException {
        UserSession.selectedReservationRowModel=reservationRowModel;
        ViewManager.openDialogBox(Constants.View.MANAGER_RESERVATION_INFO_VIEW,ViewManager.getSecondaryStage(),this.getClass(),"Manager Reservation Info",652,352);
    }


    public void showReservationsQuery(){

        if(validateDates())
        {
            if(receptionistsComboBox.getValue() != null)
            {
                String startDateHour = startDatePicker.getValue()+" 00:00:00.000000000";
                Timestamp startDate = Timestamp.valueOf(startDateHour);

                String endDateHour = endDatePicker.getValue()+" 23:59:59.000000000";
                Timestamp endDate = Timestamp.valueOf(endDateHour);

                if(receptionistsNames!=null && receptionistsNames.size()>0 && receptionistsComboBox.getValue()!=null)
                {
                    UserModel receptionist = new UserModel();
                    for(User currUser: allUsers)
                    {
                        String fullName = currUser.getFirstName()+" "+currUser.getLastName();
                        if(receptionistsComboBox.getValue().equals(fullName))
                        {
                            receptionist=currUser.toModel();
                        }
                    }

                    if(receptionist.getFirstName().length()>2)
                    {





                        List<Reservation> allReservationsOfReceptionist = reservationService.getAllReservationsOfReceptionist(receptionist,hotelModel);


                        if(allReservationsOfReceptionist!=null)
                        {
                            List<Reservation> reservationsOfReceptionistForPeriod = new LinkedList<>();

                            for(Reservation currReservation: allReservationsOfReceptionist)
                            {
                                if(currReservation.getCreatedAt().after(startDate) && currReservation.getCreatedAt().before(endDate))
                                {
                                    reservationsOfReceptionistForPeriod.add(currReservation);
                                }
                            }



                            if(reservationsOfReceptionistForPeriod.size()>0)
                            {

                                Map<Long,ReservationRowModel> tempReservationsMap = new LinkedHashMap<>();

                                for(Reservation currReservation: reservationsOfReceptionistForPeriod)
                                {
                                     if(!tempReservationsMap.containsKey(currReservation.getNumber()))
                                     {
                                         tempReservationsMap.put(currReservation.getNumber(), currReservation.toReservationRowModel((reservationService.getCountOfReservation(currReservation.getNumber(),hotelModel))));
                                     }
                                }

                                reservationsTable.setItems(
                                        FXCollections.observableList(
                                        new LinkedList<>(tempReservationsMap.values()).stream().map(rrm -> new ReservationRowModel(
                                                rrm.getNumber(),
                                                rrm.getType(),
                                                rrm.getStatus(),
                                                rrm.getRooms(),
                                                rrm.getCustomerFullName()
                                        )).collect(Collectors.toList())));// Inserts all  assembled reservations of receptionist in TableView

                                createActionButtons();//insert dynamically created action buttons in every row of TableView


                                nameLabel.setText(receptionist.getFirstName());
                                surnameLabel.setText(receptionist.getLastName());
                                egnLabel.setText(receptionist.getId());
                                phoneLabel.setText(receptionist.getPhone());
                                usernameLabel.setText(receptionist.getUsername());
                                emailLabel.setText(receptionist.getEmail());
                            }

                        }
                    }
                }
            }

            else
            {
                AlertManager.showAlert(Alert.AlertType.ERROR, "Грешка", "❌ Моля изберете рецепционист.");
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
        if(reservationsTable.getItems()!=null)
        {
            reservationsTable.getItems().clear();
        }
        reservationsQueryButton.setDisable(startDatePicker.getValue() == null || endDatePicker.getValue() == null);
        nameLabel.setText("");
        surnameLabel.setText("");
        egnLabel.setText("");
        phoneLabel.setText("");
        usernameLabel.setText("");
        emailLabel.setText("");
    }
    public void endDatePickerChanged()
    {
        if(reservationsTable.getItems()!=null)
        {
            reservationsTable.getItems().clear();
        }
        reservationsQueryButton.setDisable(startDatePicker.getValue() == null || endDatePicker.getValue() == null);
        nameLabel.setText("");
        surnameLabel.setText("");
        egnLabel.setText("");
        phoneLabel.setText("");
        usernameLabel.setText("");
        emailLabel.setText("");
    }

}