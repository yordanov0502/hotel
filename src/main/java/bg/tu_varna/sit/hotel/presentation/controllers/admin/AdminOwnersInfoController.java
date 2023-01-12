package bg.tu_varna.sit.hotel.presentation.controllers.admin;

import bg.tu_varna.sit.hotel.business.UserService;
import bg.tu_varna.sit.hotel.common.*;
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

public class AdminOwnersInfoController {
    private static final Logger log = Logger.getLogger(AdminMainController.class);
    private final UserService userService = UserService.getInstance();

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
    private TableColumn<UserModel, String> phoneColumn;
    @FXML
    private TableColumn<UserModel,String> usernameColumn;
    @FXML
    private TableColumn<UserModel, String> emailColumn;
    @FXML
    private TableColumn<UserModel,String> statusColumn;
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

    public void initialize() {

        if(UserSession.user!=null)
        {
            ownersTable.getColumns().forEach(column -> column.setReorderable(false));//prevents custom reordering of columns in order to avoid icon bugs
            ownersTable.getColumns().forEach(column -> column.setSortable(false));//prevents custom sorting of columns in order to avoid icon bugs

            Label label = new Label("Няма информация за собственици.");
            label.setStyle("-fx-text-fill: black;"+"-fx-background-color: white;"+"-fx-font-size: 20;");
            ownersTable.setPlaceholder(label); //shows text when there are no owners in the database

            //https://docs.oracle.com/javase/8/javafx/api/javafx/scene/control/cell/PropertyValueFactory.html
            egnColumn.setCellValueFactory(new PropertyValueFactory<UserModel,String>("id"));
            nameColumn.setCellValueFactory(new PropertyValueFactory<UserModel,String>("firstName"));
            surnameColumn.setCellValueFactory(new PropertyValueFactory<UserModel,String>("lastName"));
            phoneColumn.setCellValueFactory(new PropertyValueFactory<UserModel,String>("phone"));
            usernameColumn.setCellValueFactory(new PropertyValueFactory<UserModel,String>("username"));
            emailColumn.setCellValueFactory(new PropertyValueFactory<UserModel,String>("email"));
            statusColumn.setCellValueFactory(new PropertyValueFactory<UserModel,String>("status"));

            egnColumn.setStyle("-fx-alignment:center");
            nameColumn.setStyle("-fx-alignment:center");
            surnameColumn.setStyle("-fx-alignment:center");
            phoneColumn.setStyle("-fx-alignment:center");
            usernameColumn.setStyle("-fx-alignment:center");
            emailColumn.setStyle("-fx-alignment:center");
            statusColumn.setStyle("-fx-alignment:center");
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
        else
        {
            searchField.setDisable(true);
            searchButton.setDisable(true);
            clearSearchButton.setDisable(true);
        }
    }

    //https://github.com/Rakib-Hasan-455/Hotel-Management-System-JavaFx/blob/master/src/sample/zadmin/AdminPages/AdminEmployeeInfo.java
    private void createActionButtons() {
        Callback<TableColumn<UserModel, String>, TableCell<UserModel, String>> cellCallback =
                new Callback<>() {
                    @Override
                    public TableCell<UserModel, String> call(TableColumn<UserModel, String> param) {

                        TableCell<UserModel, String> cell = new TableCell<UserModel, String>() {

                            FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TRASH);
                            FontAwesomeIconView editIcon = new FontAwesomeIconView(FontAwesomeIcon.EDIT);

                            public HBox hBox = new HBox(25, editIcon, deleteIcon);

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
                                    editIcon.setStyle("-glyph-size:15px;");
                                    deleteIcon.setStyle("-glyph-size:15px;");


                                    editIcon.setOnMouseEntered((MouseEvent event) -> {
                                        Tooltip tooltip = new Tooltip("редактирай");
                                        Tooltip.install(editIcon,tooltip);
                                    });

                                    editIcon.setOnMouseClicked((MouseEvent event) -> {

                                        UserModel userModel = getTableView().getItems().get(getIndex());
                                        try
                                        {
                                            editRow(userModel);//edits row in TableView
                                        }
                                        catch (IOException e)
                                        {
                                            e.printStackTrace();
                                        }
                                    });



                                    deleteIcon.setOnMouseEntered((MouseEvent event) -> {
                                        Tooltip tooltip = new Tooltip("изтрий");
                                        Tooltip.install(deleteIcon,tooltip);
                                    });

                                    deleteIcon.setOnMouseClicked((MouseEvent event) -> {

                                        UserModel userModel = getTableView().getItems().get(getIndex());

                                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                        alert.setHeaderText("Потвърждение");
                                        alert.initStyle(StageStyle.UNDECORATED);
                                        alert.setContentText("Наистина ли искате да изтриете данните за "+userModel.getRole()+"\n \""+userModel.getFirstName()+" "+userModel.getLastName()+"\" от системата ?");
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
                                                deleteRow(userModel);//deletes row from TableView
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
        actionColumn.setCellFactory(cellCallback);
    }

    private void editRow(UserModel userModel) throws IOException {

        UserSession.selectedUser=userModel;
        ViewManager.openDialogBox(Constants.View.USER_EDIT_INFO_VIEW,ViewManager.getSecondaryStage(),this.getClass(),"User Edit Info",652,352);
    }

    private void deleteRow(UserModel userModel) throws IOException {

        //this loop will have more than 1 iteration because the user is owner (one owner can own many hotels, but every hotel has only one owner)
        if(!userModel.getHotels().isEmpty())
        {
            for(HotelModel h :userService.getAllHotelsOfUser(userModel))
            {
                userService.removeHotel(userModel,h,ViewManager.getPrimaryStage().getTitle());//removes user from certain hotel's list and vice versa, and updates hotel's hasOwner and hasManager attributes if the user is owner or manager of a hotel, but his account in the system remains
            }
        }

        if(userService.deleteUser(userModel))
          {
              log.info("Information for owner \""+userModel.getFirstName()+" "+userModel.getLastName()+"\" has been successfully deleted.");
              AlertManager.showAlert(Alert.AlertType.INFORMATION,"Информация","✅ Успешно изтрихте данни за "+userModel.getRole()+" \""+userModel.getFirstName()+" "+userModel.getLastName()+"\".");
              ViewManager.changeView(Constants.View.ADMIN_OWNERS_INFO_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Admin Owners Info", 800, 500);
          }
          else
          {
              log.info("Information for owner \""+userModel.getFirstName()+" "+userModel.getLastName()+"\" has NOT been deleted.");
              AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","❌ Изтриването на данни е неуспешно.");
          }
    }


    public void searchOwnerById() {

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
            ViewManager.changeView(Constants.View.ADMIN_OWNERS_INFO_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Admin Owners Info", 800, 500);
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