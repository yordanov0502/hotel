package bg.tu_varna.sit.hotel.presentation.controllers.receptionist;

import bg.tu_varna.sit.hotel.business.UserService;
import bg.tu_varna.sit.hotel.common.AlertManager;
import bg.tu_varna.sit.hotel.common.Hasher;
import bg.tu_varna.sit.hotel.common.UserSession;
import bg.tu_varna.sit.hotel.common.ViewManager;
import bg.tu_varna.sit.hotel.presentation.models.UserModel;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

public class ReceptionistEditInfoController {
    private final UserService userService = UserService.getInstance();

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField receptionistNameField;
    @FXML
    private TextField receptionistSurnameField;
    @FXML
    private TextField receptionistEGNField;
    @FXML
    private TextField receptionistPhoneField;
    @FXML
    private TextField receptionistUsernameField;
    @FXML
    private TextField receptionistEmailField;
    @FXML
    private TextField receptionistPasswordField;
    @FXML
    private Button receptionistEditInfoButton;
    @FXML
    private Button closeButton;


    public void editReceptionistInfo() {
        if(userService.validateFields(new String[] {receptionistNameField.getText(), receptionistSurnameField.getText(), receptionistEGNField.getText(), receptionistPhoneField.getText(), receptionistUsernameField.getText(), receptionistEmailField.getText(), receptionistPasswordField.getText()})
                && userService.checkForCorrectPersonalDataUpdate(new String[] {receptionistNameField.getText(), receptionistSurnameField.getText(), receptionistPhoneField.getText(), receptionistUsernameField.getText(), receptionistEmailField.getText(), receptionistPasswordField.getText()}))
        {
            if(userService.updateUser(new UserModel(receptionistEGNField.getText(), receptionistNameField.getText(), receptionistSurnameField.getText(), receptionistPhoneField.getText(), receptionistUsernameField.getText(), receptionistEmailField.getText(), receptionistPasswordField.getText(), Hasher.SHA512.hash(receptionistPasswordField.getText()), UserSession.user.getRole(), UserSession.user.getCreatedAt(),UserSession.user.getLastLogin(), "редактиран",UserSession.user.getHotels())))
            {
                AlertManager.showAlert(Alert.AlertType.INFORMATION,"Информация","✅ Извършихте успешно актуализиране на данни.");
                UserSession.user=null;
                UserSession.user=userService.getUserById(receptionistEGNField.getText());//zadavame sushtiq user za tekusht user session, no s update-nati danni
                ViewManager.closeDialogBox();
            }
            else {AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","❌ Актуализирането на данни е неуспешно.");}
        }
    }


    public void closeEditInfoPage(){
        ViewManager.closeDialogBox();
    }

    public void showCustomerInfo(){
        receptionistNameField.setText(UserSession.user.getFirstName());
        receptionistSurnameField.setText(UserSession.user.getLastName());
        receptionistEGNField.setText(UserSession.user.getId());
        receptionistPhoneField.setText(UserSession.user.getPhone());
        receptionistUsernameField.setText(UserSession.user.getUsername());
        receptionistEmailField.setText(UserSession.user.getEmail());
        receptionistPasswordField.setText(UserSession.user.getPassword());
    }

    public void initialize()
    {
        if(UserSession.user!=null)
        {
            showCustomerInfo();

            anchorPane.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
                if(keyEvent.getCode() == KeyCode.ENTER){
                    receptionistEditInfoButton.fire();
                    keyEvent.consume();
                }
            });
        }
    }
}