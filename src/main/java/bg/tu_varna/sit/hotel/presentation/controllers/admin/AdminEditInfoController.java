package bg.tu_varna.sit.hotel.presentation.controllers.admin;

import bg.tu_varna.sit.hotel.business.UserService;
import bg.tu_varna.sit.hotel.common.*;
import bg.tu_varna.sit.hotel.presentation.models.UserModel;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class AdminEditInfoController {

    public final UserService userService = UserService.getInstance();

    @FXML
    public AnchorPane anchorPane;
    @FXML
    public TextField adminNameField;
    @FXML
    public TextField adminSurnameField;
    @FXML
    public TextField adminEGNField;
    @FXML
    public TextField adminPhoneField;
    @FXML
    public TextField adminUsernameField;
    @FXML
    public TextField adminEmailField;
    @FXML
    public TextField adminPasswordField;
    @FXML
    public Button adminEditInfoButton;
    @FXML
    public Button closeButton;

    public void editAdminInfo(){
        if(userService.validateFields(new String[] {adminNameField.getText(), adminSurnameField.getText(), adminEGNField.getText(), adminPhoneField.getText(), adminUsernameField.getText(), adminEmailField.getText(), adminPasswordField.getText()})
                && userService.checkForCorrectPersonalDataUpdate(new String[] {adminNameField.getText(),adminSurnameField.getText(),adminPhoneField.getText(), adminUsernameField.getText(),adminEmailField.getText(),adminPasswordField.getText()}))
        {
            if(userService.updateUser(new UserModel(adminEGNField.getText(),adminNameField.getText(),adminSurnameField.getText(),adminPhoneField.getText(), adminUsernameField.getText(), adminEmailField.getText(), adminPasswordField.getText(), Hasher.SHA512.hash(adminPasswordField.getText()),UserSession.getUser().getRole(), UserSession.getUser().getCreatedAt(),UserSession.getUser().getLastLogin(), "редактиран")))
            {
                AlertManager.showAlert(Alert.AlertType.INFORMATION,"Информация","✅ Извършихте успешно актуализиране на данни.");
                UserSession.setUser(null);
                UserSession.setUser(userService.getUserById(adminEGNField.getText()));//zadavame sushtiq user za tekusht user session, no s update-nati danni
                ViewManager.getSecondaryStage().close();
                ViewManager.setSecondaryStage(null);
            }
            else {AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","❌ Актуализирането на данни е неуспешно.");}
        }
    }

    public void closeEditInfoPage(){
        ViewManager.getSecondaryStage().close();
        ViewManager.setSecondaryStage(null);
    }

    public void showCustomerInfo(){
        adminNameField.setText(UserSession.getUser().getFirstName());
        adminSurnameField.setText(UserSession.getUser().getLastName());
        adminEGNField.setText(UserSession.getUser().getId());
        adminPhoneField.setText(UserSession.getUser().getPhone());
        adminUsernameField.setText(UserSession.getUser().getUsername());
        adminEmailField.setText(UserSession.getUser().getEmail());
        adminPasswordField.setText(UserSession.getUser().getPassword());
    }

    public void initialize()
    {
        if(UserSession.getUser()!=null)
        {
            showCustomerInfo();
        }
    }

}