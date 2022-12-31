package bg.tu_varna.sit.hotel.presentation.controllers.admin;

import bg.tu_varna.sit.hotel.business.UserService;
import bg.tu_varna.sit.hotel.common.*;
import bg.tu_varna.sit.hotel.presentation.models.UserModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;

public class AdminRegistrationController {
    private static final Logger log = Logger.getLogger(AdminRegistrationController.class);
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
    public PasswordField adminPasswordField;
    @FXML
    public Button adminRegistrationButton;
    @FXML
    public Button adminBackButton;
    @FXML
    public Button closeWindowButton;

    @FXML
    public void registerAdmin(ActionEvent event) throws IOException {
        if(userService.validateFields(new String[] {adminNameField.getText(), adminSurnameField.getText(), adminEGNField.getText(), adminPhoneField.getText(), adminUsernameField.getText(), adminEmailField.getText(), adminPasswordField.getText()})
                && !userService.checkForExistingData(new String[] {adminEGNField.getText(), adminPhoneField.getText(), adminUsernameField.getText(),adminEmailField.getText()}))
        {


            if(userService.getAllByRole("администратор")==null)//grants direct access to the system only for the first admin registration
            {
                if(userService.addUser(new UserModel(adminEGNField.getText(),adminNameField.getText(),adminSurnameField.getText(),adminPhoneField.getText(), adminUsernameField.getText(), adminEmailField.getText(), adminPasswordField.getText(),Hasher.SHA512.hash(adminPasswordField.getText()),"администратор",new Timestamp(System.currentTimeMillis()),new Timestamp(System.currentTimeMillis()),"потвърден",new ArrayList<>())))
                {
                    log.info("Successful admin registration.");
                    AlertManager.showAlert(Alert.AlertType.INFORMATION,"Информация","✅ Извършихте успешна регистрация.");
                    UserSession.user=userService.getUserById(adminEGNField.getText());
                    ViewManager.changeView(Constants.View.ADMIN_MAIN_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Admin Main", 800, 500);
                }
                else
                {
                    log.error("Unsuccessful admin registration.");
                    AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","❌ Регистрацията ви е неуспешна.");
                }
            }
            else
            {
                if(userService.addUser(new UserModel(adminEGNField.getText(),adminNameField.getText(),adminSurnameField.getText(),adminPhoneField.getText(), adminUsernameField.getText(), adminEmailField.getText(), adminPasswordField.getText(),Hasher.SHA512.hash(adminPasswordField.getText()),"администратор",new Timestamp(System.currentTimeMillis()),new Timestamp(System.currentTimeMillis()),"непотвърден",new ArrayList<>())))
                {
                    log.info("Successful admin registration.");
                    AlertManager.showAlert(Alert.AlertType.INFORMATION,"Информация","✅ Извършихте успешна регистрация, но трябва да бъде потвърдена от някой от администраторите, за да получите достъп до системата.");
                    ViewManager.changeView(Constants.View.ADMIN_REGISTRATION_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Admin Registration", 800, 500);
                }
                else
                {
                    log.error("Unsuccessful admin registration.");
                    AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","❌ Регистрацията ви е неуспешна.");
                }
            }
        }
    }

    @FXML
    public void backToAdminLoginPage(ActionEvent event) throws IOException {
        ViewManager.changeView(Constants.View.ADMIN_LOGIN_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Admin Login", 800, 500);
    }


    public void initialize()
    {
        anchorPane.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER){
                adminRegistrationButton.fire();
                keyEvent.consume();
            }
            if(keyEvent.getCode() == KeyCode.ESCAPE){
                System.exit(0);
                keyEvent.consume();
            }
        });

        closeWindowButton.setOnMouseClicked(event -> System.exit(0));
    }
}