package bg.tu_varna.sit.hotel.presentation.controllers.admin;

import bg.tu_varna.sit.hotel.business.UserService;
import bg.tu_varna.sit.hotel.common.AlertManager;
import bg.tu_varna.sit.hotel.common.Constants;
import bg.tu_varna.sit.hotel.common.UserSession;
import bg.tu_varna.sit.hotel.common.ViewManager;
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

public class AdminsNewRegistrationsInfoController {
    private static final Logger log = Logger.getLogger(AdminMainController.class);
    private final UserService userService = UserService.getInstance();

    @FXML
    public TextField searchField;
    @FXML
    public Button searchButton;
    @FXML
    public Button clearSearchButton;
    @FXML
    public TableView<UserModel> adminsTable;
    @FXML
    public TableColumn<UserModel,String> egnColumn;
    @FXML
    public TableColumn<UserModel, String> nameColumn;
    @FXML
    public TableColumn<UserModel,String> surnameColumn;
    @FXML
    public TableColumn<UserModel, String> phoneColumn;
    @FXML
    public TableColumn<UserModel,String> usernameColumn;
    @FXML
    public TableColumn<UserModel, String> emailColumn;
    @FXML
    public TableColumn<UserModel,String> statusColumn;
    @FXML
    public TableColumn actionColumn;

    @FXML
    public void showAdminMainView() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.ADMIN_MAIN_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Admin Main", 800, 500);
    }

    @FXML
    public void addOwner() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.ADMIN_ADD_OWNER_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Admin Add Owner", 800, 500);
    }

    @FXML
    public void showOwnersInfo() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.ADMIN_OWNERS_INFO_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Admin Owners Info", 800, 500);
    }

    @FXML
    public void showManagersInfo() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.ADMIN_MANAGERS_INFO_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Admin Managers Info", 800, 500);
    }

    @FXML
    public void showReceptionistsInfo() throws IOException{
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.ADMIN_RECEPTIONISTS_INFO_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Admin Receptionists Info", 800, 500);
    }

    @FXML
    public void showHotelsInfo() throws IOException{
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.ADMIN_HOTELS_INFO_VIEW,ViewManager.getPrimaryStage(),this.getClass(),"Admin Hotels Info",800,500);
    }

    public void initialize() {

        if(UserSession.user!=null)
        {
            adminsTable.getColumns().forEach(column -> column.setReorderable(false));//prevents custom reordering of columns in order to avoid icon bugs
            adminsTable.getColumns().forEach(column -> column.setSortable(false));//prevents custom sorting of columns in order to avoid icon bugs

            Label label = new Label("???????? ???????????????????? ???? ???????????????????????????????? ????????????????????????????.");
            label.setStyle("-fx-text-fill: black;"+"-fx-background-color: white;"+"-fx-font-size: 20;");
            adminsTable.setPlaceholder(label); //shows text when there are no newly registered admins in the database

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

            if(userService.getAllNewlyRegisteredAdmins()!=null)
            {
                adminsTable.setItems(userService.getAllNewlyRegisteredAdmins());// Inserts all newly registered admins in TableView
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


                            FontAwesomeIconView infoIcon = new FontAwesomeIconView(FontAwesomeIcon.ADDRESS_CARD_ALT);
                            FontAwesomeIconView approveIcon = new FontAwesomeIconView(FontAwesomeIcon.CHECK_CIRCLE_ALT);
                            FontAwesomeIconView deleteIcon = new FontAwesomeIconView(FontAwesomeIcon.TIMES_CIRCLE_ALT);

                            public HBox hBox = new HBox(17, infoIcon, approveIcon, deleteIcon);

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
                                    infoIcon.setStyle("-glyph-size:17px;");
                                    approveIcon.setStyle("-glyph-size:17px;");
                                    deleteIcon.setStyle("-glyph-size:17px;");


                                    infoIcon.setOnMouseEntered((MouseEvent event) -> {
                                        Tooltip tooltip = new Tooltip("????????????????");
                                        Tooltip.install(infoIcon,tooltip);
                                    });

                                    infoIcon.setOnMouseClicked((MouseEvent event) -> {

                                        UserModel userModel = getTableView().getItems().get(getIndex());
                                        try
                                        {
                                            showInformation(userModel);//shows information for selected admin from the TableView
                                        }
                                        catch (IOException e)
                                        {
                                            e.printStackTrace();
                                        }
                                    });



                                    approveIcon.setOnMouseEntered((MouseEvent event) -> {
                                        Tooltip tooltip = new Tooltip("????????????");
                                        Tooltip.install(approveIcon,tooltip);
                                    });

                                    approveIcon.setOnMouseClicked((MouseEvent event) -> {

                                        UserModel userModel = getTableView().getItems().get(getIndex());

                                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                        alert.setHeaderText("????????????????????????");
                                        alert.initStyle(StageStyle.UNDECORATED);
                                        alert.setContentText("???????????????? ???? ???????????? ???? ???????????????????? ?????????????????????????? ???? "+userModel.getRole()+" \""+userModel.getFirstName()+" "+userModel.getLastName()+"\" ?? ?????????????????? ?");
                                        alert.setX(ViewManager.getPrimaryStage().getX()+220);
                                        alert.setY(ViewManager.getPrimaryStage().getY()+180);
                                        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                                        ButtonType yesButton = new ButtonType("????", ButtonBar.ButtonData.YES);
                                        ButtonType noButton = new ButtonType("????", ButtonBar.ButtonData.NO);
                                        alert.getButtonTypes().setAll(yesButton, noButton);
                                        Optional<ButtonType> answer = alert.showAndWait();

                                        if(answer.isPresent() && answer.get()==yesButton)
                                        {
                                            try
                                            {
                                                approveRegistration(userModel);//approves(updates status) selected admin from the TableView
                                            }
                                            catch (IOException e)
                                            {
                                                e.printStackTrace();
                                            }
                                        }
                                    });



                                    deleteIcon.setOnMouseEntered((MouseEvent event) -> {
                                        Tooltip tooltip = new Tooltip("????????????????");
                                        Tooltip.install(deleteIcon,tooltip);
                                    });

                                    deleteIcon.setOnMouseClicked((MouseEvent event) -> {

                                        UserModel userModel = getTableView().getItems().get(getIndex());

                                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                        alert.setHeaderText("????????????????????????");
                                        alert.initStyle(StageStyle.UNDECORATED);
                                        alert.setContentText("???????????????? ???? ???????????? ???? ???????????????????? ?????????????????????????? ???? "+userModel.getRole()+" \""+userModel.getFirstName()+" "+userModel.getLastName()+"\" ???? ?????????????????? ?");
                                        alert.setX(ViewManager.getPrimaryStage().getX()+220);
                                        alert.setY(ViewManager.getPrimaryStage().getY()+180);
                                        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
                                        ButtonType yesButton = new ButtonType("????", ButtonBar.ButtonData.YES);
                                        ButtonType noButton = new ButtonType("????", ButtonBar.ButtonData.NO);
                                        alert.getButtonTypes().setAll(yesButton, noButton);
                                        Optional<ButtonType> answer = alert.showAndWait();

                                        if(answer.isPresent() && answer.get()==yesButton)
                                        {
                                            try
                                            {
                                                rejectRegistration(userModel);//rejects(updates status) selected admin registration from TableView
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

    private void showInformation(UserModel userModel) throws IOException {

        UserSession.selectedUser=userModel;
        ViewManager.openDialogBox(Constants.View.ADMIN_USER_INFO,ViewManager.getSecondaryStage(),this.getClass(),"Admin User Info",652,352);
    }

    private void approveRegistration(UserModel userModel) throws IOException{

        userModel.setStatus("??????????????????");
        if(userService.updateUser(userModel))
        {
            log.info("Information for admin \""+userModel.getFirstName()+" "+userModel.getLastName()+"\" has been successfully UPDATED(status = \"??????????????????\").");
            AlertManager.showAlert(Alert.AlertType.INFORMATION,"????????????????????","??? ?????????????? ?????????????????????? ?????????????????????????? ???? "+userModel.getRole()+" \""+userModel.getFirstName()+" "+userModel.getLastName()+"\".");
            ViewManager.changeView(Constants.View.ADMINS_NEW_REGISTRATIONS_INFO, ViewManager.getPrimaryStage(),this.getClass(),"Admins New Registrations Info", 800, 500);
        }
        else
        {
            log.info("Information for admin \""+userModel.getFirstName()+" "+userModel.getLastName()+"\" has NOT been UPDATED.");
            AlertManager.showAlert(Alert.AlertType.ERROR,"????????????","??? ???????????????????????????? ???? ?????????????????????????? ???? "+userModel.getRole()+" \""+userModel.getFirstName()+" "+userModel.getLastName()+" ?? ??????????????????.");
        }
    }

    private void rejectRegistration(UserModel userModel) throws IOException {

        userModel.setStatus("??????????????????");
        if(userService.updateUser(userModel))
        {
            log.info("Information for admin \""+userModel.getFirstName()+" "+userModel.getLastName()+"\" has been successfully UPDATED(status = \"??????????????????\").");
            AlertManager.showAlert(Alert.AlertType.INFORMATION,"????????????????????","??? ?????????????? ?????????????????????? ?????????????????????????? ???? "+userModel.getRole()+" \""+userModel.getFirstName()+" "+userModel.getLastName()+"\".");
            ViewManager.changeView(Constants.View.ADMINS_NEW_REGISTRATIONS_INFO, ViewManager.getPrimaryStage(),this.getClass(),"Admins New Registrations Info", 800, 500);
        }
        else
        {
            log.info("Information for admin \""+userModel.getFirstName()+" "+userModel.getLastName()+"\" has NOT been UPDATED.");
            AlertManager.showAlert(Alert.AlertType.ERROR,"????????????","??? ???????????????????????? ???? ?????????????????????????? ???? "+userModel.getRole()+" \""+userModel.getFirstName()+" "+userModel.getLastName()+" ?? ??????????????????.");
        }
    }

    @FXML
    public void searchAdminById() {

        if(userService.getAllNewlyRegisteredAdmins().size()>1 && adminsTable.getItems().size()!=1)
        {
            if(searchField.getText().equals(""))
            {
                AlertManager.showAlert(Alert.AlertType.ERROR,"????????????","???????? ???????????????? ?????? ?? ???????????? ???? ??????????????.");
            }
            else if(!userService.validateId(searchField.getText()))
            {
                AlertManager.showAlert(Alert.AlertType.ERROR,"????????????","??????-???? ???????????? ???? ?????????????? 10 ?????????? [0-9] ?? ???? ???????? ??????????????.");
                searchField.setText("");
            }
            else
            {
                if(userService.getNewlyRegisteredAdminById(searchField.getText())!=null)
                {
                    adminsTable.getItems().clear();
                    adminsTable.getItems().add(userService.getNewlyRegisteredAdminById(searchField.getText()));
                }
                else
                {
                    AlertManager.showAlert(Alert.AlertType.ERROR,"????????????","?????????????????????????????? ?????????????????????????? ?? ??????: "+searchField.getText()+" ???? ???????????????????? ?? ??????????????????.");
                    searchField.setText("");
                }
            }
        }
    }

    @FXML
    public void clearSearch() throws IOException {
        searchField.setText("");
        if(userService.getAllNewlyRegisteredAdmins().size()>1 && adminsTable.getItems().size()==1)
        {
            ViewManager.closeDialogBox();
            ViewManager.changeView(Constants.View.ADMINS_NEW_REGISTRATIONS_INFO, ViewManager.getPrimaryStage(),this.getClass(),"Admins New Registrations Info", 800, 500);
        }
    }

    @FXML
    public void logout() throws IOException {
        ViewManager.closeDialogBox();
        if(UserSession.user!=null)
        {
            log.info("Admin \""+UserSession.user.getUsername()+"\" successfully logged out.");
            UserSession.user=null;//pri logout dannite za nastoqshta user sesiq se iztrivat, za da ne sa nali4ni otvun
        }
        ViewManager.changeView(Constants.View.ADMIN_LOGIN_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Admin Login", 800, 500);
    }

    @FXML
    public void showAccountInformation() throws IOException {
        if(UserSession.user==null)
        {
            AlertManager.showAlert(Alert.AlertType.ERROR, "????????????", "???????? ???????????????? ?????????? ???? ??????????????????????????.");
        }
        else
        {
            ViewManager.closeDialogBox();
            ViewManager.openDialogBox(Constants.View.ADMIN_INFO_VIEW, null,this.getClass(),"Admin Info", 652, 352);
        }
    }
}
