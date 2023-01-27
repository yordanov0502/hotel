package bg.tu_varna.sit.hotel.presentation.controllers.receptionist;

import bg.tu_varna.sit.hotel.business.*;
import bg.tu_varna.sit.hotel.common.AlertManager;
import bg.tu_varna.sit.hotel.common.Constants;
import bg.tu_varna.sit.hotel.common.UserSession;
import bg.tu_varna.sit.hotel.common.ViewManager;
import bg.tu_varna.sit.hotel.data.entities.Reservation;
import bg.tu_varna.sit.hotel.presentation.models.CustomerModel;
import bg.tu_varna.sit.hotel.presentation.models.HotelModel;
import bg.tu_varna.sit.hotel.presentation.models.ReservationModel;
import bg.tu_varna.sit.hotel.presentation.models.RoomModel;
import bg.tu_varna.sit.hotel.presentation.models.custom.ReservationRowModel;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.shape.Circle;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Minutes;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

public class ReceptionistReservationsController {
    private static final Logger log = Logger.getLogger(ReceptionistReservationsController.class);
    private final CustomerService customerService = CustomerService.getInstance();
    private final UserService userService = UserService.getInstance();
    private final ServiceService serviceService = ServiceService.getInstance();
    private final RoomService roomService = RoomService.getInstance();
    private final ReservationService reservationService = ReservationService.getInstance();
    private HotelModel hotelModel;

    @FXML
    private Circle notificationCircle;
    @FXML
    private Label notificationLabel;

    @FXML
    private TextField searchField;
    @FXML
    private Button searchButton;
    @FXML
    private Button clearSearchButton;
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
    private TableColumn<ReservationRowModel,String> resCustomerColumn;
    @FXML
    private TableColumn actionColumn;

    public void showReceptionistMainView() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.RECEPTIONIST_MAIN_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Receptionist Main", 800, 500);
    }

    public void addNewCustomer() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.RECEPTIONIST_ADD_NEW_CUSTOMER_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Receptionist Add New Customer", 800, 500);
    }

    public void addNewReservation() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.RECEPTIONIST_ADD_NEW_RESERVATION_VIEW,ViewManager.getPrimaryStage(),this.getClass(),"Receptionist Add New Reservation",800,500);
    }

    public void addNewService() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.RECEPTIONIST_ADD_NEW_SERVICE_VIEW,ViewManager.getPrimaryStage(),this.getClass(),"Receptionist Add New Service",800,500);
    }

    public void showHotelInfo() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.RECEPTIONIST_HOTEL_INFO_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Receptionist Hotel Info", 800, 500);
    }








    public void initialize(){

        //notificationCircle.setVisible(true);
        //notificationLabel.setText("7");
        //notificationLabel.setVisible(true);

        if(UserSession.user!=null)
        {
            hotelModel = userService.getUserById(UserSession.user.getId()).getHotels().get(0).toModel();

            reservationService.refreshUncompletedReservationsStatus(hotelModel);

            reservationsTable.getColumns().forEach(column -> column.setReorderable(false));//prevents custom reordering of columns in order to avoid icon bugs
            reservationsTable.getColumns().forEach(column -> column.setSortable(false));//prevents custom sorting of columns in order to avoid icon bugs

            Label label = new Label("Няма информация за резервации.");
            label.setStyle("-fx-text-fill: black;"+"-fx-background-color: white;"+"-fx-font-size: 20;");
            reservationsTable.setPlaceholder(label); //shows text when there are no customer in the database


            //https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/cell/PropertyValueFactory.html
            resNumberColumn.setCellValueFactory(new PropertyValueFactory<ReservationRowModel,String>("number"));
            resTypeColumn.setCellValueFactory(new PropertyValueFactory<ReservationRowModel,String>("type"));
            resStatusColumn.setCellValueFactory(new PropertyValueFactory<ReservationRowModel,String>("status"));
            resRoomsColumn.setCellValueFactory(new PropertyValueFactory<ReservationRowModel,String>("rooms"));
            resCustomerColumn.setCellValueFactory(new PropertyValueFactory<ReservationRowModel,String>("customerFullName"));


            resNumberColumn.setStyle("-fx-alignment:center");
            resTypeColumn.setStyle("-fx-alignment:center");
            resStatusColumn.setStyle("-fx-alignment:center");
            resRoomsColumn.setStyle("-fx-alignment:center");
            resCustomerColumn.setStyle("-fx-alignment:center");
            actionColumn.setStyle("-fx-alignment:center");


            //In this observable list I get all uncompleted  reservations of hotel and if there aren't any
            //then NULL value is assigned to the collection.
            //This is why I check the collection if it is NULL in order to avoid NullPointerException when filling the table view
            List<Reservation> uncompletedReservationsOfHotel = reservationService.getAllUncompletedReservationsOfHotel(this.hotelModel);
            if(uncompletedReservationsOfHotel!=null)
            {
                ObservableList<ReservationRowModel> uncompletedAssembledReservations = assembleWholeReservations(uncompletedReservationsOfHotel);
                reservationsTable.setItems(uncompletedAssembledReservations);// Inserts all uncompleted assembled reservations of hotel in TableView
                createActionButtons();//insert dynamically created action buttons in every row of TableView

                if(reservationsTable.getItems().size()==1)
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
        }



    }


    private void createActionButtons() {
        Callback<TableColumn<ReservationRowModel, String>, TableCell<ReservationRowModel, String>> cellCallback =
                new Callback<>() {
                    @Override
                    public TableCell<ReservationRowModel, String> call(TableColumn<ReservationRowModel, String> param) {

                        TableCell<ReservationRowModel, String> cell = new TableCell<ReservationRowModel, String>() {

                            FontAwesomeIconView infoIcon = new FontAwesomeIconView(FontAwesomeIcon.INFO_CIRCLE);
                            FontAwesomeIconView annulationIcon = new FontAwesomeIconView(FontAwesomeIcon.TIMES_CIRCLE);
                            FontAwesomeIconView completeIcon = new FontAwesomeIconView(FontAwesomeIcon.CHECK_CIRCLE);


                            public HBox hBox = new HBox(20,infoIcon, annulationIcon, completeIcon);

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
                                    annulationIcon.setStyle("-glyph-size:20px; -fx-font-family: FontAwesome;");
                                    completeIcon.setStyle("-glyph-size:20px; -fx-font-family: FontAwesome;");

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

                                    annulationIcon.setOnMouseEntered((MouseEvent event) -> {
                                        Tooltip tooltip = new Tooltip("анулирай");
                                        Tooltip.install(annulationIcon,tooltip);
                                    });

                                    annulationIcon.setOnMouseClicked((MouseEvent event) -> {

                                        ReservationRowModel reservationRowModel = getTableView().getItems().get(getIndex());
                                        try
                                        {
                                            annulateReservation(reservationRowModel);//annulates(deletes) assembled reservation(and all its parts) from the TableView
                                        }
                                        catch (IOException e)
                                        {
                                            e.printStackTrace();
                                        }
                                    });



                                    completeIcon.setOnMouseEntered((MouseEvent event) -> {
                                        Tooltip tooltip = new Tooltip("завърши");
                                        Tooltip.install(completeIcon,tooltip);
                                    });

                                    completeIcon.setOnMouseClicked((MouseEvent event) -> {

                                        ReservationRowModel reservationRowModel = getTableView().getItems().get(getIndex());
                                        try
                                        {
                                            completeReservation(reservationRowModel);//completes assembled reservation(and all its parts) and removes it from the TableView - updates the status and all parts connected with the reservation
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

    private void showReservationInfo(ReservationRowModel reservationRowModel) throws IOException {
        UserSession.selectedReservationRowModel=reservationRowModel;
        ViewManager.openDialogBox(Constants.View.RECEPTIONIST_RESERVATION_INFO_VIEW,ViewManager.getSecondaryStage(),this.getClass(),"Receptionist Reservation Info",652,352);
    }

    private void annulateReservation(ReservationRowModel reservationRowModel) throws IOException {

        reservationService.refreshUncompletedReservationsStatus(hotelModel);

        //Information for one reservation(unit)
        ReservationModel reservationModel = reservationService.getReservationWithNumber(reservationRowModel.getNumber(),hotelModel);
        CustomerModel customerModel = customerService.getCustomerByEgn(reservationModel.getCustomer().getEgn(),hotelModel);


        if(reservationModel.getStatus().equals("незапочнала"))
        {
            //List of all reservation units(parts of the assembled reservation)
            List<Reservation> reservationUnits = reservationService.getAllUncompletedReservationsOfHotelWithNumber(reservationRowModel.getNumber(),hotelModel);

            Timestamp today = new Timestamp(System.currentTimeMillis());
            //if final date for free annulation has passed
            if(reservationModel.getFinalAnnulationDate().equals(reservationModel.getStartDate()) || Minutes.minutesBetween(new DateTime(today.getTime()),new DateTime(reservationModel.getFinalAnnulationDate().getTime())).getMinutes()<0)
            {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("Потвърждение");
                alert.initStyle(StageStyle.UNDECORATED);
                alert.setContentText("Датата за безплатно анулиране на резеревацията е изтекла!\nПотвърждавате ли анулирането?\n\n(Ако потвърдите се съгласявате, че сте получили от клиента "+customerModel.getFirstName()+" "+customerModel.getLastName()+" сумата от "+reservationModel.getTotalPrice()+"лв. за цялата резервация)");
                alert.setX(ViewManager.getPrimaryStage().getX()+220);
                alert.setY(ViewManager.getPrimaryStage().getY()+180);
                alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                ButtonType yesButton = new ButtonType("Да", ButtonBar.ButtonData.YES);
                ButtonType noButton = new ButtonType("Не", ButtonBar.ButtonData.NO);
                alert.getButtonTypes().setAll(yesButton, noButton);
                Optional<ButtonType> answer = alert.showAndWait();

                boolean successfulDelete=true;
                if(answer.isPresent() && answer.get()==yesButton)
                {
                    for(Reservation reservation: reservationUnits)
                    {
                        if(!reservationService.deleteReservation(reservation.toModel()))
                        {
                            successfulDelete=false;
                            break;
                        }
                    }

                    if(successfulDelete)
                    {
                        AlertManager.showAlert(Alert.AlertType.INFORMATION,"Информация","✅ Резервацията беше анулирана успешно.");
                        ViewManager.closeDialogBox();
                        ViewManager.changeView(Constants.View.RECEPTIONIST_RESERVATIONS_VIEW,ViewManager.getPrimaryStage(),this.getClass(),"Receptionist Uncompleted Reservations",800,500);
                    }
                    else
                    {
                        AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","Неуспешно анулиране на резервация.");
                    }
                }
            }
            else //if final date for free annulation has NOT passed (this means that annulation for free is possible)
            {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("Потвърждение");
                alert.initStyle(StageStyle.UNDECORATED);
                alert.setContentText("Анулирането на резеревацията е безплатно. Потвърждавате ли анулирането?");
                alert.setX(ViewManager.getPrimaryStage().getX()+220);
                alert.setY(ViewManager.getPrimaryStage().getY()+180);
                alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                ButtonType yesButton = new ButtonType("Да", ButtonBar.ButtonData.YES);
                ButtonType noButton = new ButtonType("Не", ButtonBar.ButtonData.NO);
                alert.getButtonTypes().setAll(yesButton, noButton);
                Optional<ButtonType> answer = alert.showAndWait();

                boolean successfulDelete=true;
                if(answer.isPresent() && answer.get()==yesButton)
                {
                    for(Reservation reservation: reservationUnits)
                    {
                        if(!reservationService.deleteReservation(reservation.toModel()))
                        {
                            successfulDelete=false;
                            break;
                        }
                    }

                    if(successfulDelete)
                    {
                        AlertManager.showAlert(Alert.AlertType.INFORMATION,"Информация","✅ Резервацията беше анулирана успешно.");
                        ViewManager.closeDialogBox();
                        ViewManager.changeView(Constants.View.RECEPTIONIST_RESERVATIONS_VIEW,ViewManager.getPrimaryStage(),this.getClass(),"Receptionist Uncompleted Reservations",800,500);
                    }
                    else
                    {
                        AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","Неуспешно анулиране на резервация.");
                    }
                }
            }
        }

        else
        {
            AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","Не можете да анулирате резервация, която вече е започнала или е изтекла.");
        }

    }

    private void completeReservation(ReservationRowModel reservationRowModel) throws IOException {

        reservationService.refreshUncompletedReservationsStatus(hotelModel);

        //Information for one reservation(unit)
        ReservationModel reservationModel = reservationService.getReservationWithNumber(reservationRowModel.getNumber(),hotelModel);

        if(reservationModel.getStatus().equals("незапочнала"))
        {
            AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","Не можете да завършите резервация, която все още не е започнала.");
        }
        else
        {
            UserSession.selectedReservationRowModel=reservationRowModel;
            ViewManager.closeDialogBox();
            ViewManager.openDialogBox(Constants.View.RECEPTIONIST_COMPLETE_RESERVATION_VIEW,ViewManager.getSecondaryStage(),this.getClass(),"Receptionist Complete Reservation",400,270);
        }
    }

    //This method is used to assemble several reservations with same number as one reservation
    //(because for example if I reserve 3 rooms they will be stored in the database as 3 separate reservations with same number)
    private ObservableList<ReservationRowModel> assembleWholeReservations(List<Reservation> uncompletedReservationsOfHotel) {

        Map<Long,ReservationRowModel> tempReservationsMap = new LinkedHashMap<>();
        Integer roomCounter=1;
        List<Reservation> tempReservationsList;//this list is used to store reservations with same number

        for(Reservation reservation : uncompletedReservationsOfHotel)
        {
            if(!tempReservationsMap.containsKey(reservation.getNumber()))
            {
                tempReservationsList = reservationService.getAllUncompletedReservationsOfHotelWithNumber(reservation.getNumber(),this.hotelModel);
                for(int i=0;i<tempReservationsList.size();i++)
                {
                    if(i==tempReservationsList.size()-1)
                    {
                        tempReservationsMap.put(tempReservationsList.get(i).getNumber(),tempReservationsList.get(i).toReservationRowModel(roomCounter));
                    }
                    else
                    {
                        roomCounter++;
                    }
                }
                tempReservationsList.clear();
                roomCounter=1;
            }
        }


        return FXCollections.observableList(
                new LinkedList<>(tempReservationsMap.values()).stream().map(rrm -> new ReservationRowModel(
                        rrm.getNumber(),
                        rrm.getType(),
                        rrm.getStatus(),
                        rrm.getRooms(),
                        rrm.getCustomerFullName()
                )).collect(Collectors.toList())
        );
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void searchReservationsByCustomerEgn() {
/*
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
        }*/
    }


    public void clearSearch() {/*
        searchField.setText("");
        ViewManager.closeDialogBox();
        List<CustomerModel> customersList = customerService.getAllCustomersOfHotel(this.hotelModel);
        if(customersList.size()>1 && customersTable.getItems().size()==1)
        {
            searchField.setDisable(false);
            searchButton.setDisable(false);
            customersTable.getItems().clear();
            customersTable.setItems(FXCollections.observableList(customersList));
        }*/
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public void logout() throws IOException {
        ViewManager.closeDialogBox();
        if(UserSession.user!=null)
        {
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

}