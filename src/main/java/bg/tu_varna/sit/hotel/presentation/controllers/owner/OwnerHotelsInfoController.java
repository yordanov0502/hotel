package bg.tu_varna.sit.hotel.presentation.controllers.owner;

import bg.tu_varna.sit.hotel.business.HotelService;
import bg.tu_varna.sit.hotel.business.RoomService;
import bg.tu_varna.sit.hotel.business.UserService;
import bg.tu_varna.sit.hotel.common.AlertManager;
import bg.tu_varna.sit.hotel.common.Constants;
import bg.tu_varna.sit.hotel.common.UserSession;
import bg.tu_varna.sit.hotel.common.ViewManager;
import bg.tu_varna.sit.hotel.data.entities.Hotel;
import bg.tu_varna.sit.hotel.presentation.controllers.owner.cache.MajorOwnerController;
import bg.tu_varna.sit.hotel.presentation.controllers.owner.cache.NewHotelInformation;
import bg.tu_varna.sit.hotel.presentation.models.CustomerModel;
import bg.tu_varna.sit.hotel.presentation.models.HotelModel;
import bg.tu_varna.sit.hotel.presentation.models.UserModel;
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
import javafx.stage.StageStyle;
import javafx.util.Callback;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class OwnerHotelsInfoController implements MajorOwnerController {
    private static final Logger log = Logger.getLogger(OwnerHotelsInfoController.class);
    private final UserService userService = UserService.getInstance();
    private final HotelService hotelService = HotelService.getInstance();
    private final RoomService roomService = RoomService.getInstance();

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
    private TableColumn majorInfoColumn;
    @FXML
    private TableColumn employeesInfoColumn;
    @FXML
    private TableColumn roomsInfoColumn;
    @FXML
    private TableColumn servicesInfoColumn;
    @FXML
    private TableColumn removeOrDeleteColumn;

    public void showOwnerMainView() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.OWNER_MAIN_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Owner Main", 800, 500);
    }

    public void addHotelAndManager() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.OWNER_ADD_HOTEL_AND_MANAGER_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Owner Add Hotel And Manager", 800, 500);
    }



    public void initialize()
    {
        if(UserSession.user!=null)
        {
            NewHotelInformation.makeRefreshedFalse(this);

            hotelsTable.getColumns().forEach(column -> column.setReorderable(false));//prevents custom reordering of columns in order to avoid icon bugs
            hotelsTable.getColumns().forEach(column -> column.setSortable(false));//prevents custom sorting of columns in order to avoid icon bugs

            Label label = new Label("Няма информация за хотели.");
            label.setStyle("-fx-text-fill: black;" + "-fx-background-color: white;" + "-fx-font-size: 20;");
            hotelsTable.setPlaceholder(label); //shows text when there are no hotels in the database

            nameColumn.setCellValueFactory(new PropertyValueFactory<HotelModel, String>("name"));

            nameColumn.setStyle("-fx-alignment:center");
            majorInfoColumn.setStyle("-fx-alignment:center");
            employeesInfoColumn.setStyle("-fx-alignment:center");
            roomsInfoColumn.setStyle("-fx-alignment:center");
            servicesInfoColumn.setStyle("-fx-alignment:center");
            removeOrDeleteColumn.setStyle("-fx-alignment:center");

            if(userService.getAllHotelsOfUser(userService.getUserById(UserSession.user.getId()))!=null)
            {
                hotelsTable.setItems(userService.getAllHotelsOfUser(userService.getUserById(UserSession.user.getId())));// Inserts all hotels of owner in TableView
                createActionButtons();//insert dynamically created action buttons in every row of TableView

                if(hotelsTable.getItems().size()==1)
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
        //notificationCircle.setVisible(true);
        //notificationLabel.setText("7");
        //notificationLabel.setVisible(true);
    }

   private void createActionButtons(){
     createMajorInfoButton();
     createEmployeesInfoButton();
     createRoomsInfoButton();
     createServicesInfoButton();
     createRemoveAndDeleteHotelButtons();
   }

    private void createMajorInfoButton() {
        Callback<TableColumn<HotelModel, String>, TableCell<HotelModel, String>> cellCallback =
                new Callback<>() {
                    @Override
                    public TableCell<HotelModel, String> call(TableColumn<HotelModel, String> param) {

                        TableCell<HotelModel, String> cell = new TableCell<HotelModel, String>() {

                            FontAwesomeIconView hotelMajorInfoIcon = new FontAwesomeIconView(FontAwesomeIcon.INFO_CIRCLE);

                            public HBox hBox = new HBox(25,hotelMajorInfoIcon);

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
                                    hotelMajorInfoIcon.setStyle("-glyph-size:20px; -fx-font-family: FontAwesome;");


                                    hotelMajorInfoIcon.setOnMouseEntered((MouseEvent event) -> {
                                        Tooltip tooltip = new Tooltip("основна информация");
                                        Tooltip.install(hotelMajorInfoIcon,tooltip);
                                    });

                                    hotelMajorInfoIcon.setOnMouseClicked((MouseEvent event) -> {

                                        HotelModel hotelModel = getTableView().getItems().get(getIndex());
                                        try
                                        {
                                            showMajorInfo(hotelModel);
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
        majorInfoColumn.setCellFactory(cellCallback);
    }

    private void showMajorInfo(HotelModel hotelModel) throws IOException{
        UserSession.selectedHotel=hotelModel;
        ViewManager.openDialogBox(Constants.View.OWNER_HOTELS_INFO_MAJOR_INFORMATION_VIEW,ViewManager.getSecondaryStage(),this.getClass(),"Owner Hotels Info Major Information",652,352);
    }



    private void createEmployeesInfoButton() {
        Callback<TableColumn<HotelModel, String>, TableCell<HotelModel, String>> cellCallback =
                new Callback<>() {
                    @Override
                    public TableCell<HotelModel, String> call(TableColumn<HotelModel, String> param) {

                        TableCell<HotelModel, String> cell = new TableCell<HotelModel, String>() {

                            FontAwesomeIconView usersInfoIcon = new FontAwesomeIconView(FontAwesomeIcon.ADDRESS_BOOK_ALT);

                            public HBox hBox = new HBox(25,usersInfoIcon);

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
                                    usersInfoIcon.setStyle("-glyph-size:20px; -fx-font-family: FontAwesome;");


                                    usersInfoIcon.setOnMouseEntered((MouseEvent event) -> {
                                        Tooltip tooltip = new Tooltip("списък");
                                        Tooltip.install(usersInfoIcon,tooltip);
                                    });

                                    usersInfoIcon.setOnMouseClicked((MouseEvent event) -> {

                                        HotelModel hotelModel = getTableView().getItems().get(getIndex());
                                        try
                                        {
                                            showEmployeesInfo(hotelModel);
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
        employeesInfoColumn.setCellFactory(cellCallback);
    }

    private void showEmployeesInfo(HotelModel hotelModel) throws IOException{
        UserSession.selectedHotel=hotelModel;
        ViewManager.openDialogBox(Constants.View.OWNER_HOTELS_INFO_EMPLOYEES_INFORMATION_VIEW,ViewManager.getSecondaryStage(),this.getClass(),"Owner Hotels Info Employees Information",750,450);
    }


    private void createRoomsInfoButton() {
        Callback<TableColumn<HotelModel, String>, TableCell<HotelModel, String>> cellCallback =
                new Callback<>() {
                    @Override
                    public TableCell<HotelModel, String> call(TableColumn<HotelModel, String> param) {

                        TableCell<HotelModel, String> cell = new TableCell<HotelModel, String>() {

                            FontAwesomeIconView hotelRoomsInfoIcon = new FontAwesomeIconView(FontAwesomeIcon.MICROCHIP);

                            public HBox hBox = new HBox(25,hotelRoomsInfoIcon);

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
                                    hotelRoomsInfoIcon.setStyle("-glyph-size:20px; -fx-font-family: FontAwesome;");


                                    hotelRoomsInfoIcon.setOnMouseEntered((MouseEvent event) -> {
                                        Tooltip tooltip = new Tooltip("списък");
                                        Tooltip.install(hotelRoomsInfoIcon,tooltip);
                                    });

                                    hotelRoomsInfoIcon.setOnMouseClicked((MouseEvent event) -> {

                                        HotelModel hotelModel = getTableView().getItems().get(getIndex());
                                        try
                                        {
                                            showRoomsInfo(hotelModel);
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
        roomsInfoColumn.setCellFactory(cellCallback);
    }

    private void showRoomsInfo(HotelModel hotelModel) throws IOException{
        UserSession.selectedHotel=hotelModel;
        ViewManager.openDialogBox(Constants.View.OWNER_HOTELS_INFO_ROOMS_INFORMATION_VIEW,ViewManager.getSecondaryStage(),this.getClass(),"Owner Hotels Info Rooms Information",750,450);
    }


    private void createServicesInfoButton() {
        Callback<TableColumn<HotelModel, String>, TableCell<HotelModel, String>> cellCallback =
                new Callback<>() {
                    @Override
                    public TableCell<HotelModel, String> call(TableColumn<HotelModel, String> param) {

                        TableCell<HotelModel, String> cell = new TableCell<HotelModel, String>() {

                            FontAwesomeIconView hotelRoomsInfoIcon = new FontAwesomeIconView(FontAwesomeIcon.FILE_TEXT);

                            public HBox hBox = new HBox(25,hotelRoomsInfoIcon);

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
                                    hotelRoomsInfoIcon.setStyle("-glyph-size:20px; -fx-font-family: FontAwesome;");


                                    hotelRoomsInfoIcon.setOnMouseEntered((MouseEvent event) -> {
                                        Tooltip tooltip = new Tooltip("списък");
                                        Tooltip.install(hotelRoomsInfoIcon,tooltip);
                                    });

                                    hotelRoomsInfoIcon.setOnMouseClicked((MouseEvent event) -> {

                                        HotelModel hotelModel = getTableView().getItems().get(getIndex());
                                        try
                                        {
                                            showServicesInfo(hotelModel);
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
        servicesInfoColumn.setCellFactory(cellCallback);
    }

    private void showServicesInfo(HotelModel hotelModel) throws IOException{
        UserSession.selectedHotel=hotelModel;
        ViewManager.openDialogBox(Constants.View.OWNER_HOTELS_INFO_SERVICES_INFORMATION_VIEW,ViewManager.getSecondaryStage(),this.getClass(),"Owner Hotels Info Services Information",750,450);
    }


    private void createRemoveAndDeleteHotelButtons() {
        Callback<TableColumn<HotelModel, String>, TableCell<HotelModel, String>> cellCallback =
                new Callback<>() {
                    @Override
                    public TableCell<HotelModel, String> call(TableColumn<HotelModel, String> param) {

                        TableCell<HotelModel, String> cell = new TableCell<HotelModel, String>() {

                            FontAwesomeIconView removeIcon = new FontAwesomeIconView(FontAwesomeIcon.TIMES_CIRCLE);
                            FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);


                            public HBox hBox = new HBox(25, removeIcon, deleteIcon);

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
                                    removeIcon.setStyle("-glyph-size:20px; -fx-font-family: FontAwesome;");
                                    deleteIcon.setStyle("-glyph-size:20px; -fx-font-family: FontAwesome;");


                                    removeIcon.setOnMouseEntered((MouseEvent event) -> {
                                        Tooltip tooltip = new Tooltip("премахни");
                                        Tooltip.install(removeIcon,tooltip);
                                    });

                                    removeIcon.setOnMouseClicked((MouseEvent event) -> {

                                        HotelModel hotelModel = getTableView().getItems().get(getIndex());

                                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                        alert.setHeaderText("Потвърждение");
                                        alert.initStyle(StageStyle.UNDECORATED);
                                        alert.setContentText("Наистина ли искате да премахнете хотел \""+hotelModel.getName()+"\" от списъкът с вашите хотели?");
                                        alert.setX(ViewManager.getPrimaryStage().getX()+220);
                                        alert.setY(ViewManager.getPrimaryStage().getY()+180);
                                        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                                        ButtonType yesButton = new ButtonType("Да", ButtonBar.ButtonData.YES);
                                        ButtonType noButton = new ButtonType("Не", ButtonBar.ButtonData.NO);
                                        alert.getButtonTypes().setAll(yesButton, noButton);
                                        Optional<ButtonType> answer = alert.showAndWait();

                                        if(answer.isPresent() && answer.get()==yesButton)
                                        {
                                            try
                                            {
                                                removeHotel(hotelModel);//removes hotel from the owner's list of hotels
                                            }
                                            catch (IOException e)
                                            {
                                                e.printStackTrace();
                                            }
                                        }
                                    });



                                    deleteIcon.setOnMouseEntered((MouseEvent event) -> {
                                        Tooltip tooltip = new Tooltip("изтрий");
                                        Tooltip.install(deleteIcon,tooltip);
                                    });

                                    deleteIcon.setOnMouseClicked((MouseEvent event) -> {

                                        HotelModel hotelModel = getTableView().getItems().get(getIndex());

                                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                        alert.setHeaderText("Потвърждение");
                                        alert.initStyle(StageStyle.UNDECORATED);
                                        alert.setContentText("Наистина ли искате да изтриете данните за хотел \""+hotelModel.getName()+"\" и неговите служители?(без собственика)");
                                        alert.setX(ViewManager.getPrimaryStage().getX()+220);
                                        alert.setY(ViewManager.getPrimaryStage().getY()+180);
                                        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                                        ButtonType yesButton = new ButtonType("Да", ButtonBar.ButtonData.YES);
                                        ButtonType noButton = new ButtonType("Не", ButtonBar.ButtonData.NO);
                                        alert.getButtonTypes().setAll(yesButton, noButton);
                                        Optional<ButtonType> answer = alert.showAndWait();

                                        if(answer.isPresent() && answer.get()==yesButton)
                                        {
                                            try
                                            {
                                                deleteHotel(hotelModel);//deletes hotel and its employees from the database (without the owner)
                                            }
                                            catch (IOException e)
                                            {
                                                e.printStackTrace();
                                            }
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
        removeOrDeleteColumn.setCellFactory(cellCallback);
    }

    private void removeHotel(HotelModel hotelModel) throws IOException {//here the owner removes a hotel from his list, but does not delete it from the system
            UserModel hotelOwner = userService.getUserById(UserSession.user.getId());

            if(userService.removeHotel(hotelOwner,hotelModel,ViewManager.getPrimaryStage().getTitle()))//removes user(owner) from certain hotel's list and vice versa, but his account in the system remains,just in case(if) he is owner of other hotels
            {
                AlertManager.showAlert(Alert.AlertType.INFORMATION,"Информация","✅ "+hotelOwner.getFirstName()+" "+hotelOwner.getLastName()+" вече не е "+hotelOwner.getRole()+" на хотел \""+hotelModel.getName()+"\".");
                ViewManager.closeDialogBox();
                ViewManager.changeView(Constants.View.OWNER_HOTELS_INFO_VIEW,ViewManager.getPrimaryStage(),this.getClass(),"Owner Hotels Info",800,500);
            }
            else
            {
                AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","❌ Операцията по премахване на \""+hotelOwner.getFirstName()+" "+hotelOwner.getLastName()+"\" като "+hotelOwner.getRole()+" на хотел \""+hotelModel.getName()+"\" е неуспешна.");
            }
    }


    private void deleteHotel(HotelModel hotelModel) throws IOException{

        List<UserModel> hotelEmployees = HotelService.getInstance().getAllHotelUsers(hotelModel);
        for(UserModel userModel: hotelEmployees)
        {
            if(!userModel.getRole().equals("собственик"))
            {
                UserService.getInstance().deleteUser(userModel);//delete all users associated with the hotel without the owner just in case he has other hotels
            }
            else //removes the association between the hotel and the owner so no child records are available of the hotel which is about to be deleted
            {
                userService.removeHotel(userModel,hotelModel,ViewManager.getPrimaryStage().getTitle());
            }
        }

        if(hotelService.deleteHotel(hotelModel))//delete the hotel)
        {
            AlertManager.showAlert(Alert.AlertType.INFORMATION,"Информация","✅ Успешно изтрихте данните за хотел \""+hotelModel.getName()+"\" и всички негови служители.(без собственика)");
        }
        else
        {
            AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","❌ Операцията по изтриване на данните на хотел \""+hotelModel.getName()+"\" е неуспешна.");
        }
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.OWNER_HOTELS_INFO_VIEW,ViewManager.getPrimaryStage(),this.getClass(),"Owner Hotels Info",800,500);
    }

    public void searchHotelByName(){

        UserModel owner = userService.getUserById(UserSession.user.getId());

        if(owner.getHotels().size()>1 && hotelsTable.getItems().size()!=1)
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
                if(doesOwnerHaveHotelWithName(owner,searchField.getText()))
                {
                    hotelsTable.getItems().clear();
                    hotelsTable.getItems().add(hotelService.getHotelByName(searchField.getText()));
                    searchButton.setDisable(true);
                    clearSearchButton.setDisable(false);
                    searchField.setDisable(true);
                }
                else
                {
                    AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","Хотел с име: \""+searchField.getText()+"\" не съществува в системата.");
                    searchField.setText("");
                }
            }
        }
    }


    public void clearSearch() {

        searchField.setText("");
        ViewManager.closeDialogBox();
        List<HotelModel> hotelListOfOwner = userService.getAllHotelsOfUser(userService.getUserById(UserSession.user.getId()));
        if(hotelListOfOwner.size()>1 && hotelsTable.getItems().size()==1)
        {
            searchField.setDisable(false);
            searchButton.setDisable(false);
            hotelsTable.getItems().clear();
            hotelsTable.setItems(FXCollections.observableList(hotelListOfOwner));
        }
    }

    private boolean doesOwnerHaveHotelWithName(UserModel owner,String hotelName)
    {
        for(Hotel hotel : userService.getUserById(owner.getId()).getHotels())
        {
            if(hotel.getName().equals(hotelName))
            {
                return true;
            }
        }
        return false;
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


}