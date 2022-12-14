package bg.tu_varna.sit.hotel.presentation.controllers.owner;

import bg.tu_varna.sit.hotel.business.HotelService;
import bg.tu_varna.sit.hotel.business.UserService;
import bg.tu_varna.sit.hotel.common.*;
import bg.tu_varna.sit.hotel.presentation.controllers.admin.AdminMainController;
import bg.tu_varna.sit.hotel.presentation.models.UserModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;

public class OwnerAddNewManagerToVacantHotelController {
    private static final Logger log = Logger.getLogger(AdminMainController.class);
    private final UserService userService = UserService.getInstance();
    private final HotelService hotelService = HotelService.getInstance();

    @FXML
    public AnchorPane anchorPane;
    @FXML
    public TextField managerNameField;
    @FXML
    public TextField managerSurnameField;
    @FXML
    public TextField managerEGNField;
    @FXML
    public TextField managerPhoneField;
    @FXML
    public TextField managerUsernameField;
    @FXML
    public TextField managerEmailField;
    @FXML
    public PasswordField managerPasswordField;
    @FXML
    public Button addNewManagerButton;
    @FXML
    public ComboBox comboBox;

    @FXML
    public void showOwnerMainView() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.OWNER_MAIN_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Owner Main", 800, 500);
    }

    @FXML
    public void addHotelAndManager() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.OWNER_ADD_HOTEL_AND_MANAGER_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Owner Add Hotel And Manager", 800, 500);
    }

    @FXML
    public void addNewManagerToVacantHotel() throws IOException {

        if (userService.validateFields(new String[]{managerNameField.getText(), managerSurnameField.getText(), managerEGNField.getText(), managerPhoneField.getText(), managerUsernameField.getText(), managerEmailField.getText(), managerPasswordField.getText()})
                && !userService.checkForExistingData(new String[]{managerEGNField.getText(), managerPhoneField.getText(), managerUsernameField.getText(), managerEmailField.getText()}))
        {
            if(comboBox.getValue()!=null)
            {
                UserModel userModel = new UserModel(managerEGNField.getText(), managerNameField.getText(), managerSurnameField.getText(), managerPhoneField.getText(), managerUsernameField.getText(), managerEmailField.getText(), managerPasswordField.getText(), Hasher.SHA512.hash(managerPasswordField.getText()), "????????????????", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), "??????????????????", new ArrayList<>());
                if (userService.addUser(userModel))
                {
                    log.info("New manager has been added successfully.");
                    if(userService.addHotel(userModel,hotelService.getHotelByName(comboBox.getValue().toString())))
                    {
                        if(hotelService.getAllHotelsWithoutManager()!=null)
                        {
                            ViewManager.closeDialogBox();
                            ViewManager.changeView(Constants.View.OWNER_ADD_NEW_MANAGER_TO_VACANT_HOTEL_VIEW,ViewManager.getPrimaryStage(),this.getClass(),"Owner Add New Manager To Vacant Hotel",800,500);
                        }
                        else
                        {
                            ViewManager.closeDialogBox();
                            ViewManager.changeView(Constants.View.OWNER_ADD_HOTEL_AND_MANAGER_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Owner Add Hotel And Manager", 800, 500);
                        }
                    }
                    else
                    {
                        userService.deleteUser(userModel);
                        ViewManager.closeDialogBox();
                        ViewManager.changeView(Constants.View.OWNER_ADD_NEW_MANAGER_TO_VACANT_HOTEL_VIEW,ViewManager.getPrimaryStage(),this.getClass(),"Owner Add New Manager To Vacant Hotel",800,500);
                        AlertManager.showAlert(Alert.AlertType.ERROR, "????????????", "??? ?????????????????? ???????????????? ???? ?????? ???????????????? ?????? ?????????? \""+hotelService.getHotelByName(comboBox.getValue().toString()).getName()+"\".");
                    }
                }
                else
                {
                    log.error("Manager has not been added successfully.");
                    AlertManager.showAlert(Alert.AlertType.ERROR, "????????????", "??? ?????????????????? ???????????????? ???? ?????? ????????????????.");
                    ViewManager.closeDialogBox();
                    ViewManager.changeView(Constants.View.OWNER_ADD_NEW_MANAGER_TO_VACANT_HOTEL_VIEW,ViewManager.getPrimaryStage(),this.getClass(),"Owner Add New Manager To Vacant Hotel",800,500);
                }
            }
            else
            {
                AlertManager.showAlert(Alert.AlertType.ERROR, "????????????", "??? ???????? ???????????????? ??????????.");
            }
        }
    }

    @FXML
    public void backToOwnerAddHotelAndManager() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.OWNER_ADD_HOTEL_AND_MANAGER_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Owner Add Hotel And Manager", 800, 500);
    }

    @FXML
    public void logout() throws IOException {
        ViewManager.closeDialogBox();
        if(UserSession.user!=null)
        {
            log.info("Owner \""+UserSession.user.getUsername()+"\" successfully logged out.");
            UserSession.user=null;//pri logout dannite za nastoqshta user sesiq se iztrivat, za da ne sa nali4ni otvun
        }
        ViewManager.changeView(Constants.View.OWNER_LOGIN_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Owner Login", 800, 500);
    }

    @FXML
    public void showAccountInformation() throws IOException {
        if(UserSession.user==null)
        {
            AlertManager.showAlert(Alert.AlertType.ERROR, "????????????", "???????? ???????????????? ?????????? ???? ????????????????????.");
        }
        else
        {
            ViewManager.closeDialogBox();
            ViewManager.openDialogBox(Constants.View.OWNER_INFO_VIEW, null,this.getClass(),"Owner Info", 652, 352);
        }
    }

    public void initialize()
    {
        comboBox.setItems(hotelService.getAllHotelNamesWithoutManager());

        anchorPane.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER){
                addNewManagerButton.fire();
                keyEvent.consume();
            }
        });
    }
}