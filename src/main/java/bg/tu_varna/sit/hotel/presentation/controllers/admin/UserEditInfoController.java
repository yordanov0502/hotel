package bg.tu_varna.sit.hotel.presentation.controllers.admin;

import bg.tu_varna.sit.hotel.business.UserService;
import bg.tu_varna.sit.hotel.common.*;
import bg.tu_varna.sit.hotel.presentation.models.HotelModel;
import bg.tu_varna.sit.hotel.presentation.models.UserModel;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import org.apache.log4j.Logger;

import java.io.IOException;

public class UserEditInfoController {
    private static final Logger log = Logger.getLogger(UserEditInfoController.class);
    private final UserService userService = UserService.getInstance();
    private UserModel selectedUser;

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

    @FXML
    public void editUserInfo() throws IOException {
        if(userService.validateFields(new String[] {userNameField.getText(), userSurnameField.getText(), userEGNField.getText(), userPhoneField.getText(), userUsernameField.getText(), userEmailField.getText(), userPasswordField.getText()})
                && userService.checkForCorrectDataUpdate(new String[] {userNameField.getText(), userSurnameField.getText(), userPhoneField.getText(), userUsernameField.getText(), userEmailField.getText(), userPasswordField.getText()},selectedUser))
        {
            if(userService.updateUser(new UserModel(userEGNField.getText(),userNameField.getText(), userSurnameField.getText(), userPhoneField.getText(), userUsernameField.getText(), userEmailField.getText(), userPasswordField.getText(), Hasher.SHA512.hash(userPasswordField.getText()),selectedUser.getRole(), selectedUser.getCreatedAt(),selectedUser.getLastLogin(), "редактиран",selectedUser.getHotels())))
            {
                AlertManager.showAlert(Alert.AlertType.INFORMATION,"Информация","✅ Извършихте успешно актуализиране на данните за "+selectedUser.getRole()+" \""+selectedUser.getFirstName()+" "+selectedUser.getLastName()+"\" .");
                closeEditInfoPage();
                refreshView(selectedUser.getRole());//loads specific page again(refreshes), in order to show the new changes
///////////////////////////////////////////////////////////////////////////////////////
          //      if(UserSession.user.getId().equals(selectedUser.getId()))
          //      {
          //          System.out.println(UserSession.user);
           //         UserSession.user=null;
            //        System.out.println(UserSession.user);
             //       UserSession.user=userService.getUserById(selectedUser.getId());
            //    }
////////////////////////////////////////////////////////////////////////////////////////
            }
            else
            {AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","❌ Актуализирането на данни е неуспешно.");}
        }
    }

    @FXML
    public void closeEditInfoPage(){
        ViewManager.closeDialogBox();
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
            selectedUser=UserSession.selectedUser;
            UserSession.selectedUser=null;
            showCustomerInfo();

        anchorPane.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER){
                userEditInfoButton.fire();
                keyEvent.consume();
            }
        });
    }

    private void refreshView(String role) throws IOException {
        //Determines and refreshes the page, in which an update has been recently made
        if(ViewManager.getPrimaryStage().getTitle().equals("Admin Hotels Info"))
        {
            ViewManager.changeView(Constants.View.ADMIN_HOTELS_INFO_VIEW,ViewManager.getPrimaryStage(),this.getClass(),"Admin Hotels Info",800,500);
        }
        else if(ViewManager.getPrimaryStage().getTitle().equals("Admin Owners Info")||ViewManager.getPrimaryStage().getTitle().equals("Admin Managers Info")||ViewManager.getPrimaryStage().getTitle().equals("Admin Receptionists Info"))
        {
            switch (role)
            {
                case "собственик": ViewManager.changeView(Constants.View.ADMIN_OWNERS_INFO_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Admin Owners Info", 800, 500);break;
                case "мениджър": ViewManager.changeView(Constants.View.ADMIN_MANAGERS_INFO_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Admin Managers Info", 800, 500);break;
                case "рецепционист": ViewManager.changeView(Constants.View.ADMIN_RECEPTIONISTS_INFO_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Admin Receptionists Info", 800, 500);break;
                default:break;
            }
        }
    }

}