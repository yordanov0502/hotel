package bg.tu_varna.sit.hotel.presentation.controllers.manager;

import bg.tu_varna.sit.hotel.business.UserService;
import bg.tu_varna.sit.hotel.common.*;
import bg.tu_varna.sit.hotel.presentation.models.UserModel;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class ManagerEditInfoController {
    private final UserService userService = UserService.getInstance();

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField managerNameField;
    @FXML
    private TextField managerSurnameField;
    @FXML
    private TextField managerEGNField;
    @FXML
    private TextField managerPhoneField;
    @FXML
    private TextField managerUsernameField;
    @FXML
    private TextField managerEmailField;
    @FXML
    private TextField managerPasswordField;
    @FXML
    private Button managerEditInfoButton;
    @FXML
    private Button closeButton;


    public void editManagerInfo() {
        if(userService.validateFields(new String[] {managerNameField.getText(), managerSurnameField.getText(), managerEGNField.getText(), managerPhoneField.getText(), managerUsernameField.getText(), managerEmailField.getText(), managerPasswordField.getText()})
                && userService.checkForCorrectPersonalDataUpdate(new String[] {managerNameField.getText(), managerSurnameField.getText(), managerPhoneField.getText(), managerUsernameField.getText(), managerEmailField.getText(), managerPasswordField.getText()}))
        {
            if(userService.updateUser(new UserModel(managerEGNField.getText(), managerNameField.getText(), managerSurnameField.getText(), managerPhoneField.getText(), managerUsernameField.getText(), managerEmailField.getText(), managerPasswordField.getText(), Hasher.SHA512.hash(managerPasswordField.getText()), UserSession.user.getRole(), UserSession.user.getCreatedAt(),UserSession.user.getLastLogin(), "редактиран",UserSession.user.getHotels())))
            {
                AlertManager.showAlert(Alert.AlertType.INFORMATION,"Информация","✅ Извършихте успешно актуализиране на данни.");
                UserSession.user=null;
                UserSession.user=userService.getUserById(managerEGNField.getText());//zadavame sushtiq user za tekusht user session, no s update-nati danni
                ViewManager.closeDialogBox();
            }
            else {AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","❌ Актуализирането на данни е неуспешно.");}
        }
    }


    public void closeEditInfoPage(){
        ViewManager.closeDialogBox();
    }

    public void showCustomerInfo(){
        managerNameField.setText(UserSession.user.getFirstName());
        managerSurnameField.setText(UserSession.user.getLastName());
        managerEGNField.setText(UserSession.user.getId());
        managerPhoneField.setText(UserSession.user.getPhone());
        managerUsernameField.setText(UserSession.user.getUsername());
        managerEmailField.setText(UserSession.user.getEmail());
        managerPasswordField.setText(UserSession.user.getPassword());
    }

    public void initialize()
    {
        if(UserSession.user!=null)
        {
            showCustomerInfo();

            anchorPane.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
                if(keyEvent.getCode() == KeyCode.ENTER){
                    managerEditInfoButton.fire();
                    keyEvent.consume();
                }
            });
        }
    }
}