package bg.tu_varna.sit.hotel.presentation.controllers.owner;

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

public class OwnerEditInfoController {
    private final UserService userService = UserService.getInstance();

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField ownerNameField;
    @FXML
    private TextField ownerSurnameField;
    @FXML
    private TextField ownerEGNField;
    @FXML
    private TextField ownerPhoneField;
    @FXML
    private TextField ownerUsernameField;
    @FXML
    private TextField ownerEmailField;
    @FXML
    private TextField ownerPasswordField;
    @FXML
    private Button ownerEditInfoButton;
    @FXML
    private Button closeButton;


    public void editOwnerInfo() throws IOException {
        if(userService.validateFields(new String[] {ownerNameField.getText(), ownerSurnameField.getText(), ownerEGNField.getText(), ownerPhoneField.getText(), ownerUsernameField.getText(), ownerEmailField.getText(), ownerPasswordField.getText()})
                && userService.checkForCorrectPersonalDataUpdate(new String[] {ownerNameField.getText(), ownerSurnameField.getText(), ownerPhoneField.getText(), ownerUsernameField.getText(), ownerEmailField.getText(), ownerPasswordField.getText()}))
        {
            if(userService.updateUser(new UserModel(ownerEGNField.getText(), ownerNameField.getText(), ownerSurnameField.getText(), ownerPhoneField.getText(), ownerUsernameField.getText(), ownerEmailField.getText(), ownerPasswordField.getText(), Hasher.SHA512.hash(ownerPasswordField.getText()), UserSession.user.getRole(), UserSession.user.getCreatedAt(),UserSession.user.getLastLogin(), "редактиран",UserSession.user.getHotels())))
            {
                AlertManager.showAlert(Alert.AlertType.INFORMATION,"Информация","✅ Извършихте успешно актуализиране на данни.");
                UserSession.user=null;
                UserSession.user=userService.getUserById(ownerEGNField.getText());//zadavame sushtiq user za tekusht user session, no s update-nati danni
                ViewManager.closeDialogBox();
                if(ViewManager.getPrimaryStage().getTitle().equals("Owner Hotels Info"))
                {
                    ViewManager.changeView(Constants.View.OWNER_HOTELS_INFO_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Owner Hotels Info", 800, 500);
                }
            }
            else {AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","❌ Актуализирането на данни е неуспешно.");}
        }
    }


    public void closeEditInfoPage(){
        ViewManager.closeDialogBox();
    }

    public void showCustomerInfo(){
        ownerNameField.setText(UserSession.user.getFirstName());
        ownerSurnameField.setText(UserSession.user.getLastName());
        ownerEGNField.setText(UserSession.user.getId());
        ownerPhoneField.setText(UserSession.user.getPhone());
        ownerUsernameField.setText(UserSession.user.getUsername());
        ownerEmailField.setText(UserSession.user.getEmail());
        ownerPasswordField.setText(UserSession.user.getPassword());
    }

    public void initialize()
    {
        if(UserSession.user!=null)
        {
            showCustomerInfo();

            anchorPane.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
                if(keyEvent.getCode() == KeyCode.ENTER){
                    ownerEditInfoButton.fire();
                    keyEvent.consume();
                }
            });
        }
    }

}