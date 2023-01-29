package bg.tu_varna.sit.hotel.presentation.controllers.owner;

import bg.tu_varna.sit.hotel.business.HotelService;
import bg.tu_varna.sit.hotel.business.UserService;
import bg.tu_varna.sit.hotel.common.AlertManager;
import bg.tu_varna.sit.hotel.common.Constants;
import bg.tu_varna.sit.hotel.common.UserSession;
import bg.tu_varna.sit.hotel.common.ViewManager;
import bg.tu_varna.sit.hotel.data.entities.Hotel;
import bg.tu_varna.sit.hotel.data.entities.User;
import bg.tu_varna.sit.hotel.presentation.models.HotelModel;
import bg.tu_varna.sit.hotel.presentation.models.UserModel;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.AnimationTimer;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
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
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public class OwnerRegistrationsController {
    private static final Logger log = Logger.getLogger(OwnerRegistrationsController.class);
    private final UserService userService = UserService.getInstance();
    private final HotelService hotelService = HotelService.getInstance();
    private HotelModel hotelModel;
    private List<Hotel> hotelsOfOwner;



    @FXML
    private Label timeLabel;
    @FXML
    private TableView<UserModel> receptionistsTable;
    @FXML
    private TableColumn<UserModel,String> egnColumn;
    @FXML
    private TableColumn<UserModel, String> nameColumn;
    @FXML
    private TableColumn<UserModel,String> surnameColumn;
    @FXML
    private TableColumn<UserModel, String> phoneColumn;
    @FXML
    private TableColumn<UserModel,String> usernameColumn;
    @FXML
    private TableColumn<UserModel, String> emailColumn;
    @FXML
    private TableColumn<UserModel,String> statusColumn;
    @FXML
    private TableColumn actionColumn;

    @FXML
    private Button registrationQueryButton;
    @FXML
    private DatePicker startDatePicker;
    @FXML
    private DatePicker endDatePicker;
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

    public void showRoomRatings() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.OWNER_ROOMS_RATINGS_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Owner Room Ratings", 800, 500);
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
                    if(receptionistsTable.getItems()!=null)
                    {
                        receptionistsTable.getItems().clear();
                    }
                    registrationQueryButton.setDisable(startDatePicker.getValue() == null || endDatePicker.getValue() == null);
                }
            });

            receptionistsTable.getColumns().forEach(column -> column.setReorderable(false));//prevents custom reordering of columns in order to avoid icon bugs
            receptionistsTable.getColumns().forEach(column -> column.setSortable(false));//prevents custom sorting of columns in order to avoid icon bugs


            Label label = new Label("Няма информация за служители.");
            label.setStyle("-fx-text-fill: black;"+"-fx-background-color: white;"+"-fx-font-size: 20;");
            receptionistsTable.setPlaceholder(label); //shows text when there are no registrations of receptionists for period

            egnColumn.setCellValueFactory(new PropertyValueFactory<UserModel, String>("id"));
            nameColumn.setCellValueFactory(new PropertyValueFactory<UserModel, String>("firstName"));
            surnameColumn.setCellValueFactory(new PropertyValueFactory<UserModel, String>("lastName"));
            phoneColumn.setCellValueFactory(new PropertyValueFactory<UserModel, String>("phone"));
            usernameColumn.setCellValueFactory(new PropertyValueFactory<UserModel, String>("username"));
            emailColumn.setCellValueFactory(new PropertyValueFactory<UserModel, String>("email"));
            statusColumn.setCellValueFactory(new PropertyValueFactory<UserModel, String>("status"));

            egnColumn.setStyle("-fx-alignment:center");
            nameColumn.setStyle("-fx-alignment:center");
            surnameColumn.setStyle("-fx-alignment:center");
            phoneColumn.setStyle("-fx-alignment:center");
            usernameColumn.setStyle("-fx-alignment:center");
            emailColumn.setStyle("-fx-alignment:center");
            statusColumn.setStyle("-fx-alignment:center");
            actionColumn.setStyle("-fx-alignment:center");

            registrationQueryButton.setDisable(startDatePicker.getValue() == null || endDatePicker.getValue() == null);
        }
    }



    private void createActionButtons() {
        Callback<TableColumn<UserModel, String>, TableCell<UserModel, String>> cellCallback =
                new Callback<>() {
                    @Override
                    public TableCell<UserModel, String> call(TableColumn<UserModel, String> param) {

                        TableCell<UserModel, String> cell = new TableCell<UserModel, String>() {

                            FontAwesomeIconView infoIcon = new FontAwesomeIconView(FontAwesomeIcon.ADDRESS_CARD_ALT);

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

                                    infoIcon.setStyle("-glyph-size:15px; -fx-font-family: FontAwesome;");


                                    infoIcon.setOnMouseEntered((MouseEvent event) -> {
                                        Tooltip tooltip = new Tooltip("формуляр");
                                        Tooltip.install(infoIcon,tooltip);
                                    });

                                    infoIcon.setOnMouseClicked((MouseEvent event) -> {

                                        UserModel userModel = getTableView().getItems().get(getIndex());
                                        try
                                        {
                                            showInfo(userModel);//edits row in TableView
                                        }
                                        catch (IOException e)
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

    private void showInfo(UserModel userModel) throws IOException {

        UserSession.selectedUser=userModel;
        ViewManager.openDialogBox(Constants.View.OWNER_REGISTRATIONS_FORM_VIEW,ViewManager.getSecondaryStage(),this.getClass(),"Owner Registrations Form",652,352);
    }


    public void showRegistrationsQuery(){

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

            List<User> allUsers = hotelModel.getUsers();
            List<UserModel> employees = new LinkedList<>();
            for(User currUser: allUsers)
            {
                if((currUser.getRole().equals("рецепционист") || currUser.getRole().equals("мениджър")) && currUser.getCreatedAt().after(startDate) && currUser.getCreatedAt().before(endDate))
                {
                    employees.add(currUser.toModel());
                }
            }
            if(employees.size()>0)
            {
                receptionistsTable.setItems(FXCollections.observableList(employees));
                createActionButtons();//insert dynamically created action buttons in every row of TableView
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
        if(receptionistsTable.getItems()!=null)
        {
            receptionistsTable.getItems().clear();
        }
        if(hotelsComboBox.getValue()!=null)
        {
            hotelsComboBox.setValue(null);
        }
        registrationQueryButton.setDisable(startDatePicker.getValue() == null || endDatePicker.getValue() == null);
    }
    public void endDatePickerChanged()
    {
        if(receptionistsTable.getItems()!=null)
        {
            receptionistsTable.getItems().clear();
        }
        if(hotelsComboBox.getValue()!=null)
        {
            hotelsComboBox.setValue(null);
        }
        registrationQueryButton.setDisable(startDatePicker.getValue() == null || endDatePicker.getValue() == null);
    }

}