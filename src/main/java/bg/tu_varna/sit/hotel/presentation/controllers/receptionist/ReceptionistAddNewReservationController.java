package bg.tu_varna.sit.hotel.presentation.controllers.receptionist;

import bg.tu_varna.sit.hotel.business.*;
import bg.tu_varna.sit.hotel.common.AlertManager;
import bg.tu_varna.sit.hotel.common.Constants;
import bg.tu_varna.sit.hotel.common.UserSession;
import bg.tu_varna.sit.hotel.common.ViewManager;
import bg.tu_varna.sit.hotel.presentation.models.*;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.shape.Circle;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import org.apache.log4j.Logger;
import org.controlsfx.control.CheckComboBox;
import org.joda.time.DateTime;
import org.joda.time.Days;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ReceptionistAddNewReservationController {
    private static final Logger log = Logger.getLogger(ReceptionistAddNewReservationController.class);
    private final CustomerService customerService = CustomerService.getInstance();
    private final UserService userService = UserService.getInstance();
    private final ServiceService serviceService = ServiceService.getInstance();
    private final RoomService roomService = RoomService.getInstance();
    private final ReservationService reservationService = ReservationService.getInstance();
    private HotelModel hotelModel;
    private boolean isRoomSearchConducted=false;


    @FXML
    private Circle notificationCircle;
    @FXML
    private Label notificationLabel;


    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField searchField;
    @FXML
    private Button searchButton;
    @FXML
    private Button clearSearchButton;
    @FXML
    private TableView<CustomerModel> customersTable;
    @FXML
    private TableColumn<CustomerModel,String> egnColumn;
    @FXML
    private TableColumn<CustomerModel, String> nameColumn;
    @FXML
    private TableColumn<CustomerModel,String> surnameColumn;
    @FXML
    private TableColumn actionColumn;


    @FXML
    private DatePicker startDatePicker;
    @FXML
    private Spinner<Integer> startTimeSpinner;
    @FXML
    private DatePicker endDatePicker;
    @FXML
    private Spinner<Integer> endTimeSpinner;
    @FXML
    private TextField daysAnnulationField;
    @FXML
    private Button searchRoomsButton;

    @FXML
    private CheckComboBox<String> checkComboBox;

    @FXML
    private TableView<RoomModel> freeRoomsTable;
    @FXML
    private TableColumn<RoomModel,String> numberColumn;
    @FXML
    private TableColumn<RoomModel, String> typeColumn;
    @FXML
    private TableColumn<RoomModel,String> bedsColumn;
    @FXML
    private TableColumn<RoomModel,String> areaColumn;
    @FXML
    private TableColumn<RoomModel,String> priceColumn;

    @FXML
    private Button reserveButton;
    @FXML
    private Label timeLabel;


    public void showReceptionistMainView() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.RECEPTIONIST_MAIN_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Receptionist Main", 800, 500);
    }

    public void addNewCustomer() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.RECEPTIONIST_ADD_NEW_CUSTOMER_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Receptionist Add New Customer", 800, 500);
    }

    public void addNewService() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.RECEPTIONIST_ADD_NEW_SERVICE_VIEW,ViewManager.getPrimaryStage(),this.getClass(),"Receptionist Add New Service",800,500);
    }

    public void showReservations() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.RECEPTIONIST_RESERVATIONS_VIEW,ViewManager.getPrimaryStage(),this.getClass(),"Receptionist Uncompleted Reservations",800,500);
    }

    public void showHotelInfo() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.RECEPTIONIST_HOTEL_INFO_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Receptionist Hotel Info", 800, 500);
    }



    public void logout() throws IOException {
        ViewManager.closeDialogBox();
        if(UserSession.user!=null)
        {
            ReservationService.processNotificationSucceeded = true;
            ReservationService.uncompletedNotifiedReservations = 0;

            log.info("Receptionist \""+UserSession.user.getUsername()+"\" successfully logged out.");
            UserSession.user=null;//pri logout dannite za nastoqshta user sesiq se iztrivat, za da ne sa nali4ni otvun
        }
        ViewManager.changeView(Constants.View.RECEPTIONIST_LOGIN_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Receptionist Login", 800, 500);
    }


    public void showAccountInformation() throws IOException {
        if(UserSession.user==null)
        {
            AlertManager.showAlert(Alert.AlertType.ERROR, "Грешка", "Няма заредени данни за рецепционист.");
        }
        else
        {
            ViewManager.closeDialogBox();
            ViewManager.openDialogBox(Constants.View.RECEPTIONIST_INFO_VIEW, null,this.getClass(),"Receptionist Info", 652, 352);
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

            reservationService.refreshUncompletedReservationsStatus(hotelModel);

            if(ReservationService.processNotificationSucceeded)
            {
                reservationService.processNotifications(UserSession.user.getHotels().get(0).toModel(),notificationCircle,notificationLabel);
            }

            customersTable.getColumns().forEach(column -> column.setReorderable(false));//prevents custom reordering of columns in order to avoid icon bugs
            customersTable.getColumns().forEach(column -> column.setSortable(false));//prevents custom sorting of columns in order to avoid icon bugs
            freeRoomsTable.getColumns().forEach(column -> column.setReorderable(false));//prevents custom reordering of columns in order to avoid icon bugs
            freeRoomsTable.getColumns().forEach(column -> column.setSortable(false));//prevents custom sorting of columns in order to avoid icon bugs


            Label label = new Label("Няма информация за клиенти.");
            label.setStyle("-fx-text-fill: black;"+"-fx-background-color: white;"+"-fx-font-size: 20;");
            customersTable.setPlaceholder(label); //shows text when there are no customer in the database

            Label label1 = new Label("Няма информация за свободни стаи.");
            label1.setStyle("-fx-text-fill: black;"+"-fx-background-color: white;"+"-fx-font-size: 20;");
            freeRoomsTable.setPlaceholder(label1); //shows text when there are no free rooms in the database

            //https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/cell/PropertyValueFactory.html
            egnColumn.setCellValueFactory(new PropertyValueFactory<CustomerModel,String>("egn"));
            nameColumn.setCellValueFactory(new PropertyValueFactory<CustomerModel,String>("firstName"));
            surnameColumn.setCellValueFactory(new PropertyValueFactory<CustomerModel,String>("lastName"));

            egnColumn.setStyle("-fx-alignment:center");
            nameColumn.setStyle("-fx-alignment:center");
            surnameColumn.setStyle("-fx-alignment:center");
            actionColumn.setStyle("-fx-alignment:center");

            numberColumn.setCellValueFactory(new PropertyValueFactory<RoomModel,String>("number"));
            typeColumn.setCellValueFactory(new PropertyValueFactory<RoomModel,String>("type"));
            bedsColumn.setCellValueFactory(new PropertyValueFactory<RoomModel,String>("beds"));
            areaColumn.setCellValueFactory(new PropertyValueFactory<RoomModel,String>("size"));
            priceColumn.setCellValueFactory(new PropertyValueFactory<RoomModel,String>("price"));

            numberColumn.setStyle("-fx-alignment:center");
            typeColumn.setStyle("-fx-alignment:center");
            bedsColumn.setStyle("-fx-alignment:center");
            areaColumn.setStyle("-fx-alignment:center");
            priceColumn.setStyle("-fx-alignment:center");

            //////////////////modification for multiple row selection on free rooms table///////////////////////
            freeRoomsTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

            freeRoomsTable.setRowFactory(tableView2 -> {
                final TableRow<RoomModel> row = new TableRow<>();

                row.addEventFilter(MouseEvent.MOUSE_PRESSED, event -> {
                    final int index = row.getIndex();

                    if (index >= 0 && index < freeRoomsTable.getItems().size()) {
                        if (freeRoomsTable.getSelectionModel().isSelected(index))
                            freeRoomsTable.getSelectionModel().clearSelection(index);
                        else
                            freeRoomsTable.getSelectionModel().select(index);

                        event.consume();
                    }
                });
                return row;
            });
            ////////////////////////////////////////////////////////////////////////////////////////////////////

            SpinnerValueFactory<Integer> startHoursFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,23);
            SpinnerValueFactory<Integer> endHoursFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1,23);
            startTimeSpinner.setValueFactory(startHoursFactory);
            endTimeSpinner.setValueFactory(endHoursFactory);

            startTimeSpinner.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
                if(freeRoomsTable.getItems()!=null)
                {
                    freeRoomsTable.getItems().clear();
                }
            });
            endTimeSpinner.getEditor().textProperty().addListener((obs, oldValue, newValue) -> {
                if(freeRoomsTable.getItems()!=null)
                {
                    freeRoomsTable.getItems().clear();
                }
            });


            //In this list I get all service names of hotel for current season(or for all year if specified)
            //It is possible that no service names are found so value could be null
            //That's why I check it in the following if statement
            List<String> allServicesNamesOfHotelForCurrentSeason = serviceService.getAllServicesNamesOfHotelForCurrentSeason(this.hotelModel,getCurrentSeason());
            if(allServicesNamesOfHotelForCurrentSeason!=null)
            {
                    //checkComboBox is filled with all names of services provided by the hotel for current season
                    checkComboBox.getItems().addAll(allServicesNamesOfHotelForCurrentSeason);
            }
            else
            {
                checkComboBox.setDisable(true);
            }

            //In this observable list I get all customers of hotel and if there are no customers NULL value is assigned to the collection
            //This is why I check the collection if it is NULL in order to avoid NullPointerException when filling the table view
            ObservableList<CustomerModel> customersOfHotel = customerService.getAllCustomersOfHotel(this.hotelModel);
            if(customersOfHotel!=null)
            {
                customersTable.setItems(customersOfHotel);// Inserts all customers of hotel in TableView
                createActionButtons();//insert dynamically created action buttons in every row of TableView

                if(customersTable.getItems().size()==1)
                {
                    searchButton.setDisable(true);
                    clearSearchButton.setDisable(true);
                    searchField.setDisable(true);
                }
            }
            else
            {
                searchField.setDisable(true);
                searchButton.setDisable(true);
                clearSearchButton.setDisable(true);
            }
        }
        else
        {
            searchField.setDisable(true);
            searchButton.setDisable(true);
            clearSearchButton.setDisable(true);
            startDatePicker.setDisable(true);
            startTimeSpinner.setDisable(true);
            endDatePicker.setDisable(true);
            endTimeSpinner.setDisable(true);
            daysAnnulationField.setDisable(true);
            searchRoomsButton.setDisable(true);
            checkComboBox.setDisable(true);
            reserveButton.setDisable(true);
        }
    }

    private String getCurrentSeason(){
        switch (LocalDateTime.now().getMonth())
        {
            case JANUARY:return Constants.seasons[0];
            case FEBRUARY:return Constants.seasons[1];
            case MARCH:return Constants.seasons[2];
            case APRIL:return Constants.seasons[3];
            case MAY:return Constants.seasons[4];
            case JUNE:return Constants.seasons[5];
            case JULY:return Constants.seasons[6];
            case AUGUST:return Constants.seasons[7];
            case SEPTEMBER:return Constants.seasons[8];
            case OCTOBER:return Constants.seasons[9];
            case NOVEMBER:return Constants.seasons[10];
            case DECEMBER:return Constants.seasons[11];
            default: return Constants.seasons[12];
        }
    }

    private void createActionButtons() {
        Callback<TableColumn<CustomerModel, String>, TableCell<CustomerModel, String>> cellCallback =
                new Callback<>() {
                    @Override
                    public TableCell<CustomerModel, String> call(TableColumn<CustomerModel, String> param) {

                        TableCell<CustomerModel, String> cell = new TableCell<CustomerModel, String>() {

                            FontAwesomeIconView infoIcon = new FontAwesomeIconView(FontAwesomeIcon.ADDRESS_CARD_ALT);
                            FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.EDIT);

                            public HBox hBox = new HBox(20, infoIcon, editIcon);

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
                                    editIcon.setStyle("-glyph-size:15px; -fx-font-family: FontAwesome;");


                                    infoIcon.setOnMouseEntered((MouseEvent event) -> {
                                        Tooltip tooltip = new Tooltip("подробна информация");
                                        Tooltip.install(infoIcon,tooltip);
                                    });

                                    infoIcon.setOnMouseClicked((MouseEvent event) -> {

                                        CustomerModel customerModel = getTableView().getItems().get(getIndex());
                                        try
                                        {
                                            showInformation(customerModel);//shows information for selected customer from the TableView
                                        }
                                        catch (IOException e)
                                        {
                                            e.printStackTrace();
                                        }
                                    });



                                    editIcon.setOnMouseEntered((MouseEvent event) -> {
                                        Tooltip tooltip = new Tooltip("редактирай");
                                        Tooltip.install(editIcon,tooltip);
                                    });

                                    editIcon.setOnMouseClicked((MouseEvent event) -> {

                                        CustomerModel customerModel = getTableView().getItems().get(getIndex());
                                        try
                                        {
                                            editRow(customerModel);//edits row in TableView
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


    private void showInformation(CustomerModel customerModel) throws IOException {

        UserSession.selectedCustomer=customerModel;
        ViewManager.openDialogBox(Constants.View.RECEPTIONIST_CUSTOMER_INFO_VIEW,ViewManager.getSecondaryStage(),this.getClass(),"Receptionist Customer Info",652,352);
    }

    private void editRow(CustomerModel customerModel) throws IOException {

        UserSession.selectedCustomer=customerModel;
        ViewManager.openDialogBox(Constants.View.CUSTOMER_EDIT_INFO_VIEW,ViewManager.getSecondaryStage(),this.getClass(),"Customer Edit Info",652,352);
    }

    public void searchCustomerByEgn() {

        if(customerService.getAllCustomersOfHotel(this.hotelModel).size()>1 && customersTable.getItems().size()!=1)
        {
            if(searchField.getText().equals(""))
            {
                AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","Моля въведете ЕГН в полето за търсене.");
            }
            else if(!customerService.validateEgn(searchField.getText()))
            {
                AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","ЕГН-то трябва да съдържа 10 цифри [0-9] и да бъде валидно.");
                searchField.setText("");
            }
            else
            {
                if(customerService.isEgnExists(searchField.getText(),this.hotelModel))
                {
                    customersTable.getItems().clear();
                    customersTable.getItems().add(customerService.getCustomerByEgn(searchField.getText(),this.hotelModel));
                    searchButton.setDisable(true);
                    clearSearchButton.setDisable(false);
                    searchField.setDisable(true);
                }
                else
                {
                    AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","Клиент с ЕГН: "+searchField.getText()+" не съществува в базата данни на хотел \""+this.hotelModel.getName()+"\".");
                    searchField.setText("");
                }
            }
        }
    }


    public void clearSearch() {
        searchField.setText("");
        ViewManager.closeDialogBox();
        List<CustomerModel> customersList = customerService.getAllCustomersOfHotel(this.hotelModel);
        if(customersList.size()>1 && customersTable.getItems().size()==1)
        {
            searchField.setDisable(false);
            searchButton.setDisable(false);
            customersTable.getItems().clear();
            customersTable.setItems(FXCollections.observableList(customersList));
        }
    }

    private boolean validateDates(){
        if(startDatePicker.getValue()==null && endDatePicker.getValue()==null)
        {
            AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","Моля изберете начална и крайна дата на резервация.");
            return false;
        }
        else if(startDatePicker.getValue()==null)
        {
            AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","Моля изберете начална дата на резервация.");
            return false;
        }
        else if(endDatePicker.getValue()==null)
        {
            AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","Моля изберете крайна дата на резервация.");
            return false;
        }
        else if(startDatePicker.getValue().equals(endDatePicker.getValue()))
        {
            AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","Началната и крайната дата на резервация не могат да съвпадат.");
            return false;
        }
        else if(startDatePicker.getValue().compareTo(endDatePicker.getValue()) > 0)
        {
            AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","Началната дата на резервация не може да е след крайната дата.");
            return false;
        }
        else {return true;}//everything is OK with the dates
    }

    public void searchFreeRooms(){
        if(validateDates())
        {
            isRoomSearchConducted=true;

            String startDateHour = startDatePicker.getValue()+" "+startTimeSpinner.getValue()+":00:00.000";
            Timestamp reservationStart = Timestamp.valueOf(startDateHour);

            String endDateHour = endDatePicker.getValue()+" "+endTimeSpinner.getValue()+":00:00.000";
            Timestamp reservationEnd = Timestamp.valueOf(endDateHour);

            ObservableList<RoomModel> freeRooms = reservationService.getAllFreeRooms(this.hotelModel,reservationStart,reservationEnd);
            if(freeRooms!=null && freeRoomsTable.getItems()!=null)
            {
                freeRoomsTable.getItems().clear();
                freeRoomsTable.setItems(freeRooms);
            }
            else if(freeRooms!=null)
            {
                freeRoomsTable.setItems(freeRooms);
            }
        }
    }

    public void createReservation() throws IOException {

        ObservableList<RoomModel> selectedRooms = freeRoomsTable.getSelectionModel().getSelectedItems();
        ObservableList<String> selectedServices = checkComboBox.getCheckModel().getCheckedItems();
        CustomerModel customerModel = customersTable.getSelectionModel().getSelectedItem();

        if(customerModel!=null)//proverka dali ima izbran klient
        {
            if(isRoomSearchConducted)
            {
                if(validateNumberOfDaysForFreeAnnulation())
                {
                    if(freeRoomsTable.getItems()!=null && freeRoomsTable.getItems().size()>0)//ako ima svobodni stai
                    {
                        if(selectedRooms!=null && selectedRooms.size()>0)//proverka za izbrani stai (1 ili pove4e)
                        {

                            if(customerModel.getRating().equals("слаб"))
                            {
                                AlertManager.showAlert(Alert.AlertType.WARNING,"Внимание❕","Рисков клиент.");
                            }

                            ////////////////////////////////////for reservation////////////////////////////////////////////
                            String startDateHour = startDatePicker.getValue()+" "+startTimeSpinner.getValue()+":00:00.000";
                            Timestamp reservationStart = Timestamp.valueOf(startDateHour);

                            String endDateHour = endDatePicker.getValue()+" "+endTimeSpinner.getValue()+":00:00.000";
                            Timestamp reservationEnd = Timestamp.valueOf(endDateHour);
                            ///////////////////////////////////////////////////////////////////////////////////////////////

                            Calendar cal = Calendar.getInstance();
                            cal.setTimeInMillis(reservationStart.getTime());
                            cal.add(Calendar.DAY_OF_MONTH, -(Integer.parseInt(daysAnnulationField.getText())));
                            Timestamp finalDateOfFreeAnnulation = new Timestamp(cal.getTime().getTime());

                            ////////////////////////////////////for total price////////////////////////////////////////////
                            String startDateHour1 = startDatePicker.getValue()+" 00:00:00.000";
                            Timestamp reservationStart1 = Timestamp.valueOf(startDateHour1);

                            String endDateHour1 = endDatePicker.getValue()+" 00:00:00.000";
                            Timestamp reservationEnd1 = Timestamp.valueOf(endDateHour1);
                            ///////////////////////////////////////////////////////////////////////////////////////////////

                            //Calculating the total price of Reservation(determined by number of rooms and the days reserved for each room X price of each room)
                            long totalPrice=0L;
                            for(RoomModel roomModel: selectedRooms)
                            {
                                totalPrice += (long) (Days.daysBetween(new DateTime(reservationStart1.getTime()),new DateTime(reservationEnd1.getTime())).getDays()) * roomModel.getPrice();
                            }



                            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                            alert.setHeaderText("Потвърждение");
                            alert.initStyle(StageStyle.UNDECORATED);
                            alert.setContentText("Общата цена за плащане е "+totalPrice+"лв. Потвърждавате ли, че желаете да създадете резервация?");
                            alert.setX(ViewManager.getPrimaryStage().getX()+220);
                            alert.setY(ViewManager.getPrimaryStage().getY()+180);
                            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                            ButtonType yesButton = new ButtonType("Да", ButtonBar.ButtonData.YES);
                            ButtonType noButton = new ButtonType("Не", ButtonBar.ButtonData.NO);
                            alert.getButtonTypes().setAll(yesButton, noButton);
                            Optional<ButtonType> answer = alert.showAndWait();

                            if(answer.isPresent() && answer.get()==yesButton)
                            {
                                String serviceList="";
                                if(selectedServices!=null && selectedServices.size()>0)
                                {
                                    for(int i=0;i<selectedServices.size();i++)
                                    {
                                        if(i==0){serviceList=selectedServices.get(i);}
                                        else if(i<selectedServices.size()-1){serviceList=serviceList+","+selectedServices.get(i);}
                                        else {serviceList=serviceList+","+selectedServices.get(i);}
                                    }
                                }
                                else
                                {
                                    serviceList=".";
                                }

                                String reservationType;
                                if(selectedRooms.size()==1) {reservationType="малка";}
                                else if(selectedRooms.size()>1 && selectedRooms.size()<6) {reservationType="средна";}
                                else {reservationType="голяма";}


                                Long reservationNumber = reservationService.getLastReservationNumberOfHotel(this.hotelModel)+1;
                                boolean flag = false;

                                Timestamp currentTime = new Timestamp(System.currentTimeMillis());

                                for(int i=0;i<selectedRooms.size();i++)
                                {
                                    flag = reservationService.addReservation(new ReservationModel(1L, reservationNumber, reservationType, "незапочнала", currentTime, reservationStart, reservationEnd, this.hotelModel, userService.getUserById(UserSession.user.getId()), customerModel, customerModel.getRating(),selectedRooms.get(i), selectedRooms.get(i).getNightsOccupied(), selectedRooms.get(i).getRating(), serviceList, false,finalDateOfFreeAnnulation,totalPrice));
                                    if(!flag)
                                    {
                                        if(reservationService.getReservationWithNumber(reservationNumber,this.hotelModel)!=null)
                                        {
                                            for(int j=0;j<=i;j++)
                                            {
                                                if(reservationService.getReservationWithNumber(reservationNumber,this.hotelModel)!=null)
                                                {
                                                    reservationService.deleteReservation(reservationService.getReservationWithNumber(reservationNumber,this.hotelModel));
                                                }
                                            }
                                        }
                                        break;
                                    }
                                }

                                if(flag)
                                {
                                    AlertManager.showAlert(Alert.AlertType.INFORMATION,"Информация","✅ Успешно създаване на резервация.");
                                    ViewManager.closeDialogBox();
                                    ViewManager.changeView(Constants.View.RECEPTIONIST_ADD_NEW_RESERVATION_VIEW,ViewManager.getPrimaryStage(),this.getClass(),"Receptionist Add New Reservation",800,500);
                                }
                                else
                                {
                                    AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","Неуспешно създаване на резервация.");
                                }
                            }


                        }
                        else
                        {
                            AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","Моля изберете стая/и за резервацията.");
                        }
                    }
                    else
                    {
                        AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","За периода от време, който сте избрали няма свободни стаи.");
                    }
                }
            }
          else
          {
              AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","Моля потърсете свободни стаи.");
          }
        }
        else
        {
            AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","Моля изберете клиент за резервацията.");
        }

    }

    private boolean validateNumberOfDaysForFreeAnnulation(){
        if(daysAnnulationField.getText().equals(""))
        {
            AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","Моля задайте брой дни [0-9] преди начална дата, до които резервацията ще може да бъде анулирана безплатно.");
            return false;
        }
        else if(!reservationService.validateDaysAnnulationField(daysAnnulationField.getText()))
        {
            AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","Броят дни преди начална дата на резервация, който определея до кога е възможно безплатно анулиране на резервация може да бъде между 0 и 9.");
            return false;
        }
        else {return true;}//everything is OK
    }

    public void startDatePickerChanged()
    {
        isRoomSearchConducted = false;
        daysAnnulationField.setText("");
        if(freeRoomsTable.getItems()!=null)
        {
            freeRoomsTable.getItems().clear();
        }
    }
    public void endDatePickerChanged()
    {
        isRoomSearchConducted = false;
        daysAnnulationField.setText("");
        if(freeRoomsTable.getItems()!=null)
        {
            freeRoomsTable.getItems().clear();
        }
    }



}