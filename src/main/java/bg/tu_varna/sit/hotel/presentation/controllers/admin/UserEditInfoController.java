package bg.tu_varna.sit.hotel.presentation.controllers.admin;

import bg.tu_varna.sit.hotel.business.UserService;
import bg.tu_varna.sit.hotel.common.*;
import bg.tu_varna.sit.hotel.presentation.models.UserModel;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import org.apache.log4j.Logger;

import java.io.IOException;

public class UserEditInfoController {
    private static final Logger log = Logger.getLogger(UserEditInfoController.class);
    public final UserService userService = UserService.getInstance();
    public static UserModel selectedUser;//represents selected user from AdminOwnersInfoController TableView

    @FXML
    public AnchorPane anchorPane;
    @FXML
    public TextField userNameField;
    @FXML
    public TextField userSurnameField;
    @FXML
    public TextField userEGNField;
    @FXML
    public TextField userPhoneField;
    @FXML
    public TextField userUsernameField;
    @FXML
    public TextField userEmailField;
    @FXML
    public TextField userPasswordField;
    @FXML
    public Button userEditInfoButton;
    @FXML
    public Button closeButton;

    public static void setSelectedUser(UserModel selectedUser) {
        UserEditInfoController.selectedUser = selectedUser;
    }

    public void editUserInfo() throws IOException {
        if(userService.validateFields(new String[] {userNameField.getText(), userSurnameField.getText(), userEGNField.getText(), userPhoneField.getText(), userUsernameField.getText(), userEmailField.getText(), userPasswordField.getText()})
                && userService.checkForCorrectDataUpdate(new String[] {userNameField.getText(), userSurnameField.getText(), userPhoneField.getText(), userUsernameField.getText(), userEmailField.getText(), userPasswordField.getText()},selectedUser))
        {
            if(userService.updateUser(new UserModel(userEGNField.getText(),userNameField.getText(), userSurnameField.getText(), userPhoneField.getText(), userUsernameField.getText(), userEmailField.getText(), userPasswordField.getText(), Hasher.SHA512.hash(userPasswordField.getText()),selectedUser.getRole(), selectedUser.getCreatedAt(),selectedUser.getLastLogin(), "редактиран")))
            {
                AlertManager.showAlert(Alert.AlertType.INFORMATION,"Информация","✅ Извършихте успешно актуализиране на данните за собственик \""+selectedUser.getFirstName()+" "+selectedUser.getLastName()+"\" .");
                ViewManager.getSecondaryStage().close();
                ViewManager.setSecondaryStage(null);
                UserEditInfoController.setSelectedUser(null);
                ViewManager.changeView(Constants.View.ADMIN_OWNERS_INFO_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Admin Owners Info", 800, 500);
            }
            else {AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","❌ Актуализирането на данни е неуспешно.");}
        }
    }

    public void closeEditInfoPage(){
        ViewManager.getSecondaryStage().close();
        ViewManager.setSecondaryStage(null);
    }

    public void showCustomerInfo(){
        userNameField.setText(selectedUser.getFirstName());
        userSurnameField.setText(selectedUser.getLastName());
        userEGNField.setText(selectedUser.getId());
        userPhoneField.setText(selectedUser.getPhone());
        userUsernameField.setText(selectedUser.getUsername());
        userEmailField.setText(selectedUser.getEmail());
        userPasswordField.setText(selectedUser.getPassword());
    }

    public void initialize()
    {
        if(UserSession.getUser()!=null)
        {
            showCustomerInfo();
        }
    }
}