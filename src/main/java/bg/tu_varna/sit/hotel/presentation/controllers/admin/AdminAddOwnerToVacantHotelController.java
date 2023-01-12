package bg.tu_varna.sit.hotel.presentation.controllers.admin;

import bg.tu_varna.sit.hotel.business.HotelService;
import bg.tu_varna.sit.hotel.business.UserService;
import bg.tu_varna.sit.hotel.common.*;
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
import javafx.util.Callback;
import org.apache.log4j.Logger;

import java.io.IOException;

public class AdminAddOwnerToVacantHotelController {
    private static final Logger log = Logger.getLogger(AdminMainController.class);
    private final UserService userService = UserService.getInstance();
    private final HotelService hotelService = HotelService.getInstance();

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField searchField;
    @FXML
    private Button searchButton;
    @FXML
    private Button clearSearchButton;
    @FXML
    private TableView<UserModel> ownersTable;
    @FXML
    private TableColumn<UserModel,String> egnColumn;
    @FXML
    private TableColumn<UserModel, String> nameColumn;
    @FXML
    private TableColumn<UserModel,String> surnameColumn;
    @FXML
    private TableColumn actionColumn;
    @FXML
    private Button addOwnerToHotelButton;
    @FXML
    private ComboBox<String> comboBox;


    public void showAdminMainView() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.ADMIN_MAIN_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Admin Main", 800, 500);
    }


    public void addOwner() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.ADMIN_ADD_OWNER_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Admin Add Owner", 800, 500);
    }


    public void showOwnersInfo() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.ADMIN_OWNERS_INFO_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Admin Owners Info", 800, 500);
    }


    public void showManagersInfo() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.ADMIN_MANAGERS_INFO_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Admin Managers Info", 800, 500);
    }


    public void showReceptionistsInfo() throws IOException{
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.ADMIN_RECEPTIONISTS_INFO_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Admin Receptionists Info", 800, 500);
    }


    public void showHotelsInfo() throws IOException{
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.ADMIN_HOTELS_INFO_VIEW,ViewManager.getPrimaryStage(),this.getClass(),"Admin Hotels Info",800,500);
    }


    public void showNewlyRegisteredAdmins() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.ADMINS_NEW_REGISTRATIONS_INFO, ViewManager.getPrimaryStage(),this.getClass(),"Admins New Registrations Info", 800, 500);
    }

    public void initialize()
    {
        comboBox.setItems(hotelService.getAllHotelNamesWithoutOwner());

        anchorPane.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER){
                addOwnerToHotelButton.fire();
                keyEvent.consume();
            }
        });


        ownersTable.getColumns().forEach(column -> column.setReorderable(false));//prevents custom reordering of columns in order to avoid icon bugs
        ownersTable.getColumns().forEach(column -> column.setSortable(false));//prevents custom sorting of columns in order to avoid icon bugs

        Label label = new Label("Няма информация за собственици.");
        label.setStyle("-fx-text-fill: black;"+"-fx-background-color: white;"+"-fx-font-size: 20;");
        ownersTable.setPlaceholder(label); //shows text when there are no owners in the database

        //https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/cell/PropertyValueFactory.html
        egnColumn.setCellValueFactory(new PropertyValueFactory<UserModel,String>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<UserModel,String>("firstName"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<UserModel,String>("lastName"));

        egnColumn.setStyle("-fx-alignment:center");
        nameColumn.setStyle("-fx-alignment:center");
        surnameColumn.setStyle("-fx-alignment:center");
        actionColumn.setStyle("-fx-alignment:center");

        if(userService.getAllByRole("собственик")!=null)
        {
            ownersTable.setItems(userService.getAllByRole("собственик"));// Inserts all owners in TableView
            createActionButtons();//insert dynamically created action buttons in every row of TableView
        }
        else
        {
            searchField.setDisable(true);
            searchButton.setDisable(true);
            clearSearchButton.setDisable(true);
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
                                    infoIcon.setStyle("-glyph-size:15px;");


                                    infoIcon.setOnMouseEntered((MouseEvent event) -> {
                                        Tooltip tooltip = new Tooltip("подробно");
                                        Tooltip.install(infoIcon,tooltip);
                                    });

                                    infoIcon.setOnMouseClicked((MouseEvent event) -> {

                                        UserModel userModel = getTableView().getItems().get(getIndex());
                                        try
                                        {
                                            showInformation(userModel);//shows information for selected owner from the TableView
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

    private void showInformation(UserModel userModel) throws IOException {

        UserSession.selectedUser=userModel;
        ViewManager.openDialogBox(Constants.View.ADMIN_USER_INFO,ViewManager.getSecondaryStage(),this.getClass(),"Admin User Info",652,352);
    }


    public void searchOwnerById(){

        if(userService.getAllByRole("собственик").size()>1 && ownersTable.getItems().size()!=1)
        {
            if(searchField.getText().equals(""))
            {
                AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","Моля въведете ЕГН в полето за търсене.");
            }
            else if(!userService.validateId(searchField.getText()))
            {
                AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","ЕГН-то трябва да съдържа 10 цифри [0-9] и да бъде валидно.");
                searchField.setText("");
            }
            else
            {
                if(userService.isIdExists(searchField.getText()) && userService.getUserById(searchField.getText()).getRole().equals("собственик"))
                {
                    ownersTable.getItems().clear();
                    ownersTable.getItems().add(userService.getUserById(searchField.getText()));
                }
                else
                {
                    AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","Собственик с ЕГН: "+searchField.getText()+" не съществува в системата.");
                    searchField.setText("");
                }
            }
        }
    }


    public void clearSearch() throws IOException {
        searchField.setText("");
        if(userService.getAllByRole("собственик").size()>1 && ownersTable.getItems().size()==1)
        {
            ViewManager.closeDialogBox();
            ViewManager.changeView(Constants.View.ADMIN_ADD_OWNER_TO_VACANT_HOTEL_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Admin Add Owner To Vacant Hotel", 800, 500);
        }
    }


    public void addOwnerToVacantHotel() throws IOException {
        UserModel userModel = ownersTable.getSelectionModel().getSelectedItem();
        if(userModel!=null)
        {
            if(comboBox.getValue()!=null)
            {
                if(userService.addHotel(userModel,hotelService.getHotelByName(comboBox.getValue())))
                {
                    if(hotelService.getAllHotelsWithoutOwner()!=null)
                    {
                        ViewManager.closeDialogBox();
                        ViewManager.changeView(Constants.View.ADMIN_ADD_OWNER_TO_VACANT_HOTEL_VIEW, ViewManager.getPrimaryStage(), this.getClass(), "Admin Add Owner To Vacant Hotel", 800, 500);
                    }
                    else
                    {
                        ViewManager.closeDialogBox();
                        ViewManager.changeView(Constants.View.ADMIN_ADD_OWNER_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Admin Add Owner", 800, 500);
                    }
                }
            }
            else
            {
                AlertManager.showAlert(Alert.AlertType.ERROR, "Грешка", "❌ Моля изберете хотел.");
            }
        }
        else
        {
            AlertManager.showAlert(Alert.AlertType.ERROR, "Грешка", "❌ Моля изберете собственик.");
        }
    }




    public void backToAdminAddOwner() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.ADMIN_ADD_OWNER_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Admin Add Owner", 800, 500);
    }


    public void logout() throws IOException {
        ViewManager.closeDialogBox();
        if(UserSession.user!=null)
        {
            log.info("Admin \""+UserSession.user.getUsername()+"\" successfully logged out.");
            UserSession.user=null;//pri logout dannite za nastoqshta user sesiq se iztrivat, za da ne sa nali4ni otvun
        }
        ViewManager.changeView(Constants.View.ADMIN_LOGIN_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Admin Login", 800, 500);
    }


    public void showAccountInformation() throws IOException {
        if(UserSession.user==null)
        {
            AlertManager.showAlert(Alert.AlertType.ERROR, "Грешка", "Няма заредени данни за администратор.");
        }
        else
        {
            ViewManager.closeDialogBox();
            ViewManager.openDialogBox(Constants.View.ADMIN_INFO_VIEW, null,this.getClass(),"Admin Info", 652, 352);
        }
    }

}