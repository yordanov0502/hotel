package bg.tu_varna.sit.hotel.presentation.controllers.owner;

import bg.tu_varna.sit.hotel.business.UserService;
import bg.tu_varna.sit.hotel.common.*;
import bg.tu_varna.sit.hotel.presentation.controllers.admin.UserEditInfoController;
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

public class OwnerUserEditInfoController {
    private static final Logger log = Logger.getLogger(OwnerUserEditInfoController.class);
    private final UserService userService = UserService.getInstance();
    private UserModel selectedUser;

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField userNameField;
    @FXML
    private TextField userSurnameField;
    @FXML
    private TextField userEGNField;
    @FXML
    private TextField userPhoneField;
    @FXML
    private TextField userUsernameField;
    @FXML
    private TextField userEmailField;
    @FXML
    private TextField userPasswordField;
    @FXML
    private Button userEditInfoButton;
    @FXML
    private Button closeButton;

    private static HotelModel selectedHotel;

    public void editUserInfo() throws IOException {
        if(userService.validateFields(new String[] {userNameField.getText(), userSurnameField.getText(), userEGNField.getText(), userPhoneField.getText(), userUsernameField.getText(), userEmailField.getText(), userPasswordField.getText()})
                && userService.checkForCorrectUserDataUpdate(new String[] {userNameField.getText(), userSurnameField.getText(), userPhoneField.getText(), userUsernameField.getText(), userEmailField.getText(), userPasswordField.getText()},selectedUser))
        {
            if(userService.updateUser(new UserModel(userEGNField.getText(),userNameField.getText(), userSurnameField.getText(), userPhoneField.getText(), userUsernameField.getText(), userEmailField.getText(), userPasswordField.getText(), Hasher.SHA512.hash(userPasswordField.getText()),selectedUser.getRole(), selectedUser.getCreatedAt(),selectedUser.getLastLogin(), "редактиран",selectedUser.getHotels())))
            {
                if(selectedUser.getId().equals(UserSession.user.getId()))
                {
                    UserSession.user=null;
                    UserSession.user=userService.getUserById(selectedUser.getId());
                }
                AlertManager.showAlert(Alert.AlertType.INFORMATION,"Информация","✅ Извършихте успешно редактиране на данните за "+selectedUser.getRole()+" \""+selectedUser.getFirstName()+" "+selectedUser.getLastName()+"\" .");
                ViewManager.closeDialogBox();
                ViewManager.changeView(Constants.View.OWNER_HOTELS_INFO_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Owner Hotels Info", 800, 500);
            }
            else
            {AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","❌ Редактирането на данни е неуспешно.");}
        }
    }

    public static void setSelectedHotel(HotelModel selectedHotel,OwnerHotelsInfoEmployeesInformationController ownerHotelsInfoEmployeesInformationController) {
        OwnerUserEditInfoController.selectedHotel = selectedHotel;
    }

    public void backToEmployeesInfoPage() throws IOException {
        UserSession.selectedHotel=selectedHotel;
        ViewManager.closeDialogBox();
        ViewManager.openDialogBox(Constants.View.OWNER_HOTELS_INFO_EMPLOYEES_INFORMATION_VIEW,ViewManager.getSecondaryStage(),this.getClass(),"Owner Hotels Info Employees Information",750,450);
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
}