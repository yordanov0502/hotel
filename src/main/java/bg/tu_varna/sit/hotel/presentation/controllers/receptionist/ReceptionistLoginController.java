package bg.tu_varna.sit.hotel.presentation.controllers.receptionist;

import bg.tu_varna.sit.hotel.application.Main;
import bg.tu_varna.sit.hotel.business.UserService;
import bg.tu_varna.sit.hotel.common.AlertManager;
import bg.tu_varna.sit.hotel.common.UserSession;
import bg.tu_varna.sit.hotel.common.ViewManager;
import bg.tu_varna.sit.hotel.common.Constants;
import bg.tu_varna.sit.hotel.presentation.controllers.manager.ManagerLoginController;
import bg.tu_varna.sit.hotel.presentation.models.UserModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ResourceBundle;

public class ReceptionistLoginController {
    private static final Logger log = Logger.getLogger(ReceptionistLoginController.class);
    private final UserService userService = UserService.getInstance();

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField receptionistUsernameField;
    @FXML
    private PasswordField receptionistPasswordField;
    @FXML
    private Button receptionistLoginButton;
    @FXML
    private Button receptionistBackButton;
    @FXML
    private Button closeWindowButton;


    public void backToWelcomePage(ActionEvent actionEvent) throws IOException {
        ViewManager.changeView(Constants.View.WELCOME_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Hotel Management System", 800, 500);
    }

    public void receptionist_Login() throws IOException {
        if(userService.validateLoginFields(new String[] {receptionistUsernameField.getText(), receptionistPasswordField.getText()}) && userService.authenticateUser(receptionistUsernameField.getText(), receptionistPasswordField.getText(),"рецепционист"))
        {
            UserSession.user=userService.getUserByUsername(receptionistUsernameField.getText());//Got user by username successfully

            //updates last login column of user every time he logs in
            if(userService.updateUser(new UserModel(UserSession.user.getId(),UserSession.user.getFirstName(),UserSession.user.getLastName(),UserSession.user.getPhone(), UserSession.user.getUsername(),UserSession.user.getEmail(),UserSession.user.getPassword(),UserSession.user.getHash(),UserSession.user.getRole(),UserSession.user.getCreatedAt(),new Timestamp(System.currentTimeMillis()), UserSession.user.getStatus(),UserSession.user.getHotels())))
            {
                UserSession.user=null;
                UserSession.user=userService.getUserByUsername(receptionistUsernameField.getText());//Got user by username successfully
                log.info("Receptionist \""+UserSession.user.getUsername()+"\" successfully logged in.");
                AlertManager.showAlert(Alert.AlertType.INFORMATION,"Информация","✅ Извършихте успешен вход в системата.");
                ViewManager.changeView(Constants.View.RECEPTIONIST_MAIN_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Receptionist Main", 800, 500);
            }
            else
            {
                log.error("Receptionist \""+UserSession.user.getUsername()+"\" failed to log in.");
                AlertManager.showAlert(Alert.AlertType.ERROR, "Грешка", "❌ Възникна грешка. Моля опитайте отново.");
                UserSession.user=null;
            }
        }
    }

    public void initialize() {

        anchorPane.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER){
                receptionistLoginButton.fire();
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