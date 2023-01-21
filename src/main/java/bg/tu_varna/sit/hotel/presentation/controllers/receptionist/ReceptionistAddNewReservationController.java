package bg.tu_varna.sit.hotel.presentation.controllers.receptionist;

import bg.tu_varna.sit.hotel.business.CustomerService;
import bg.tu_varna.sit.hotel.business.HotelService;
import bg.tu_varna.sit.hotel.business.UserService;
import bg.tu_varna.sit.hotel.common.AlertManager;
import bg.tu_varna.sit.hotel.common.Constants;
import bg.tu_varna.sit.hotel.common.UserSession;
import bg.tu_varna.sit.hotel.common.ViewManager;
import bg.tu_varna.sit.hotel.data.entities.Customer;
import bg.tu_varna.sit.hotel.presentation.models.CustomerModel;
import bg.tu_varna.sit.hotel.presentation.models.HotelModel;
import bg.tu_varna.sit.hotel.presentation.models.UserModel;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.shape.Circle;
import javafx.util.Callback;
import org.apache.log4j.Logger;

import java.io.IOException;

public class ReceptionistAddNewReservationController {
    private static final Logger log = Logger.getLogger(ReceptionistAddNewReservationController.class);
    private final CustomerService customerService = CustomerService.getInstance();
    private final UserService userService = UserService.getInstance();

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

    public void initialize()
    {
        //notificationCircle.setVisible(true);
        //notificationLabel.setText("7");
        //notificationLabel.setVisible(true);

        if(UserSession.user!=null)
        {
            customersTable.getColumns().forEach(column -> column.setReorderable(false));//prevents custom reordering of columns in order to avoid icon bugs
            customersTable.getColumns().forEach(column -> column.setSortable(false));//prevents custom sorting of columns in order to avoid icon bugs

            Label label = new Label("Няма информация за клиенти.");
            label.setStyle("-fx-text-fill: black;"+"-fx-background-color: white;"+"-fx-font-size: 20;");
            customersTable.setPlaceholder(label); //shows text when there are no owners in the database

            //https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/cell/PropertyValueFactory.html
            egnColumn.setCellValueFactory(new PropertyValueFactory<CustomerModel,String>("egn"));
            nameColumn.setCellValueFactory(new PropertyValueFactory<CustomerModel,String>("firstName"));
            surnameColumn.setCellValueFactory(new PropertyValueFactory<CustomerModel,String>("lastName"));

            egnColumn.setStyle("-fx-alignment:center");
            nameColumn.setStyle("-fx-alignment:center");
            surnameColumn.setStyle("-fx-alignment:center");
            actionColumn.setStyle("-fx-alignment:center");

            HotelModel hotelModel = userService.getUserById(UserSession.user.getId()).getHotels().get(0).toModel();//here I am sure that the user(receptionist) has only 1 hotel
            if(customerService.getAllCustomersOfHotel(hotelModel)!=null)
            {
                customersTable.setItems(customerService.getAllCustomersOfHotel(hotelModel));// Inserts all customers of hotel in TableView
                createActionButtons();//insert dynamically created action buttons in every row of TableView
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
            ///////////////////////////////////////////
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

        if(customerService.getAllCustomersOfHotel(userService.getUserById(UserSession.user.getId()).getHotels().get(0).toModel()).size()>1 && customersTable.getItems().size()!=1)
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
                HotelModel hotelModel = userService.getUserById(UserSession.user.getId()).getHotels().get(0).toModel();

                if(customerService.isEgnExists(searchField.getText(),hotelModel))
                {
                    customersTable.getItems().clear();
                    customersTable.getItems().add(customerService.getCustomerByEgn(searchField.getText(),hotelModel));
                }
                else
                {
                    AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","Клиент с ЕГН: "+searchField.getText()+" не съществува в базата данни на хотел \""+hotelModel.getName()+"\".");
                    searchField.setText("");
                }
            }
        }
    }


    public void clearSearch() throws IOException {
        searchField.setText("");
        if(customerService.getAllCustomersOfHotel(userService.getUserById(UserSession.user.getId()).getHotels().get(0).toModel()).size()>1 && customersTable.getItems().size()==1)
        {
            ViewManager.closeDialogBox();
            ViewManager.changeView(Constants.View.RECEPTIONIST_ADD_NEW_RESERVATION_VIEW,ViewManager.getPrimaryStage(),this.getClass(),"Receptionist Add New Reservation",800,500);
        }
    }

}