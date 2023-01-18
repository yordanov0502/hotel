package bg.tu_varna.sit.hotel.presentation.controllers.admin;

import bg.tu_varna.sit.hotel.business.HotelService;
import bg.tu_varna.sit.hotel.business.UserService;
import bg.tu_varna.sit.hotel.common.AlertManager;
import bg.tu_varna.sit.hotel.common.Constants;
import bg.tu_varna.sit.hotel.common.UserSession;
import bg.tu_varna.sit.hotel.common.ViewManager;
import bg.tu_varna.sit.hotel.presentation.models.HotelModel;
import bg.tu_varna.sit.hotel.presentation.models.UserModel;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Optional;

public class AdminHotelsInfoController {
    private static final Logger log = Logger.getLogger(AdminHotelsInfoController.class);
    private final HotelService hotelService = HotelService.getInstance();

    @FXML
    private TextField searchField;
    @FXML
    private Button searchButton;
    @FXML
    private Button clearSearchButton;
    @FXML
    private TableView<HotelModel> hotelsTable;
    @FXML
    private TableColumn<HotelModel, String> nameColumn;
    @FXML
    private TableColumn<HotelModel,String> addressColumn;
    @FXML
    private TableColumn<HotelModel, String> establishedAtColumn;
    @FXML
    private TableColumn<HotelModel,String> starsColumn;
    @FXML
    private TableColumn actionColumn;


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


    public void showNewlyRegisteredAdmins() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.ADMINS_NEW_REGISTRATIONS_INFO, ViewManager.getPrimaryStage(),this.getClass(),"Admins New Registrations Info", 800, 500);
    }

    public void initialize() {

        if(UserSession.user!=null)
        {
            hotelsTable.getColumns().forEach(column -> column.setReorderable(false));//prevents custom reordering of columns in order to avoid icon bugs
            hotelsTable.getColumns().forEach(column -> column.setSortable(false));//prevents custom sorting of columns in order to avoid icon bugs

            Label label = new Label("Няма информация за хотели.");
            label.setStyle("-fx-text-fill: black;" + "-fx-background-color: white;" + "-fx-font-size: 20;");
            hotelsTable.setPlaceholder(label); //shows text when there are no hotels in the database

            //https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/cell/PropertyValueFactory.html
            nameColumn.setCellValueFactory(new PropertyValueFactory<HotelModel, String>("name"));
            addressColumn.setCellValueFactory(new PropertyValueFactory<HotelModel, String>("address"));
            establishedAtColumn.setCellValueFactory(new PropertyValueFactory<HotelModel, String>("established_at"));
            starsColumn.setCellValueFactory(new PropertyValueFactory<HotelModel, String>("stars"));

            nameColumn.setStyle("-fx-alignment:center");
            addressColumn.setStyle("-fx-alignment:center");
            establishedAtColumn.setStyle("-fx-alignment:center");
            starsColumn.setStyle("-fx-alignment:center");
            actionColumn.setStyle("-fx-alignment:center");

            if(hotelService.getAllHotels()!=null)
            {
                hotelsTable.setItems(hotelService.getAllHotels());// Inserts all hotels in TableView
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
        }
    }

    //https://github.com/Rakib-Hasan-455/Hotel-Management-System-JavaFx/blob/master/src/sample/zadmin/AdminPages/AdminEmployeeInfo.java
    private void createActionButtons() {
        Callback<TableColumn<HotelModel, String>, TableCell<HotelModel, String>> cellCallback =
                new Callback<>() {
                    @Override
                    public TableCell<HotelModel, String> call(TableColumn<HotelModel, String> param) {

                        TableCell<HotelModel, String> cell = new TableCell<HotelModel, String>() {

                            FontAwesomeIconView usersInfoIcon = new FontAwesomeIconView(FontAwesomeIcon.ADDRESS_BOOK_ALT);

                            public HBox hBox = new HBox(usersInfoIcon);

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
                                    usersInfoIcon.setStyle("-glyph-size:15px; -fx-font-family: FontAwesome;");


                                    usersInfoIcon.setOnMouseEntered((MouseEvent event) -> {
                                        Tooltip tooltip = new Tooltip("списък");
                                        Tooltip.install(usersInfoIcon,tooltip);
                                    });

                                    usersInfoIcon.setOnMouseClicked((MouseEvent event) -> {

                                        HotelModel hotelModel = getTableView().getItems().get(getIndex());
                                        try
                                        {
                                            showUsersInfo(hotelModel);
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

    private void showUsersInfo(HotelModel hotelModel) throws IOException{

        UserSession.selectedHotel=hotelModel;
        ViewManager.openDialogBox(Constants.View.ADMIN_HOTEL_USERS_INFO_VIEW,ViewManager.getSecondaryStage(),this.getClass(),"Hotel Users Info",750,450);
    }


    public void searchHotelByName(){

        if(hotelService.getAllHotels().size()>1 && hotelsTable.getItems().size()!=1)
        {
            if(searchField.getText().equals(""))
            {
                AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","Моля въведете име на хотел в полето за търсене.");
            }
            else if(!hotelService.validateName(searchField.getText()))
            {
                AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","Името на хотела трябва да съдържа от 3 до 61 символа като трябва да започва с главна буква на кирилица или на латиница, последвана от малки букви на кирилица или на латиница като може да съдържа цифрите [0-9], интервали (\" \") както и символите (_) и (.) и (,).");
                searchField.setText("");
            }
            else
            {
                if(hotelService.isNameExists(searchField.getText()))
                {
                    hotelsTable.getItems().clear();
                    hotelsTable.getItems().add(hotelService.getHotelByName(searchField.getText()));
                }
                else
                {
                    AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","Хотел с име: \""+searchField.getText()+"\" не съществува в системата.");
                    searchField.setText("");
                }
            }
        }
    }


    public void clearSearch() throws IOException {
        searchField.setText("");
        if(hotelService.getAllHotels().size()>1 && hotelsTable.getItems().size()==1)
        {
            ViewManager.closeDialogBox();
            ViewManager.changeView(Constants.View.ADMIN_HOTELS_INFO_VIEW,ViewManager.getPrimaryStage(),this.getClass(),"Admin Hotels Info",800,500);
        }
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