package bg.tu_varna.sit.hotel.presentation.controllers.admin;

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

public class AdminEditInfoController {

    private final UserService userService = UserService.getInstance();

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

    @FXML
    public void editAdminInfo() throws IOException {
        if(userService.validateFields(new String[] {adminNameField.getText(), adminSurnameField.getText(), adminEGNField.getText(), adminPhoneField.getText(), adminUsernameField.getText(), adminEmailField.getText(), adminPasswordField.getText()})
                && userService.checkForCorrectPersonalDataUpdate(new String[] {adminNameField.getText(),adminSurnameField.getText(),adminPhoneField.getText(), adminUsernameField.getText(),adminEmailField.getText(),adminPasswordField.getText()}))
        {
            if(userService.updateUser(new UserModel(adminEGNField.getText(),adminNameField.getText(),adminSurnameField.getText(),adminPhoneField.getText(), adminUsernameField.getText(), adminEmailField.getText(), adminPasswordField.getText(), Hasher.SHA512.hash(adminPasswordField.getText()),UserSession.user.getRole(), UserSession.user.getCreatedAt(),UserSession.user.getLastLogin(), "редактиран",UserSession.user.getHotels())))
            {
                AlertManager.showAlert(Alert.AlertType.INFORMATION,"Информация","✅ Извършихте успешно актуализиране на данни.");
                UserSession.user=null;
                UserSession.user=userService.getUserById(adminEGNField.getText());//zadavame sushtiq user za tekusht user session, no s update-nati danni
                ViewManager.closeDialogBox();
////////////////////////////////////////////////////////////////////////////////////////////////////////////
          //      if(ViewManager.getPrimaryStage().getTitle().equals("Admin Hotels Info"))
          //      {
           //         ViewManager.changeView(Constants.View.ADMIN_HOTELS_INFO_VIEW,ViewManager.getPrimaryStage(),this.getClass(),"Admin Hotels Info",800,500);

         //       }
////////////////////////////////////////////////////////////////////////////////////////////////////////////
            }
            else {AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","❌ Актуализирането на данни е неуспешно.");}
        }
    }

    @FXML
    public void closeEditInfoPage(){
        ViewManager.closeDialogBox();
    }

    public void showCustomerInfo(){
        adminNameField.setText(UserSession.user.getFirstName());
        adminSurnameField.setText(UserSession.user.getLastName());
        adminEGNField.setText(UserSession.user.getId());
        adminPhoneField.setText(UserSession.user.getPhone());
        adminUsernameField.setText(UserSession.user.getUsername());
        adminEmailField.setText(UserSession.user.getEmail());
        adminPasswordField.setText(UserSession.user.getPassword());
    }

    public void initialize()
    {
        if(UserSession.user!=null)
        {
            showCustomerInfo();

            anchorPane.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
                if(keyEvent.getCode() == KeyCode.ENTER){
                    adminEditInfoButton.fire();
                    keyEvent.consume();
                }
            });
        }
    }

}