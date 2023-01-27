package bg.tu_varna.sit.hotel.presentation.controllers.manager;

import bg.tu_varna.sit.hotel.application.Main;
import bg.tu_varna.sit.hotel.business.UserService;
import bg.tu_varna.sit.hotel.common.AlertManager;
import bg.tu_varna.sit.hotel.common.UserSession;
import bg.tu_varna.sit.hotel.common.ViewManager;
import bg.tu_varna.sit.hotel.common.Constants;
import bg.tu_varna.sit.hotel.presentation.controllers.owner.OwnerLoginController;
import bg.tu_varna.sit.hotel.presentation.models.UserModel;
import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ManagerLoginController {
    private static final Logger log = Logger.getLogger(ManagerLoginController.class);
    private final UserService userService = UserService.getInstance();

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField managerUsernameField;
    @FXML
    private PasswordField managerPasswordField;
    @FXML
    private Button managerLoginButton;
    @FXML
    private Button managerBackButton;
    @FXML
    private Button closeWindowButton;
    @FXML
    private Label timeLabel;


    @FXML
    public void backToWelcomePage(ActionEvent actionEvent) throws IOException {
        ViewManager.changeView(Constants.View.WELCOME_VIEW,ViewManager.getPrimaryStage(),this.getClass(),"Hotel Management System", 800, 500);
    }

    public void manager_Login() throws IOException {
        if(userService.validateLoginFields(new String[] {managerUsernameField.getText(), managerPasswordField.getText()}) && userService.authenticateUser(managerUsernameField.getText(), managerPasswordField.getText(),"мениджър"))
        {
            UserSession.user=userService.getUserByUsername(managerUsernameField.getText());//Got user by username successfully

            //updates last login column of user every time he logs in
            if(userService.updateUser(new UserModel(UserSession.user.getId(),UserSession.user.getFirstName(),UserSession.user.getLastName(),UserSession.user.getPhone(), UserSession.user.getUsername(),UserSession.user.getEmail(),UserSession.user.getPassword(),UserSession.user.getHash(),UserSession.user.getRole(),UserSession.user.getCreatedAt(),new Timestamp(System.currentTimeMillis()), UserSession.user.getStatus(),UserSession.user.getHotels())))
            {
                UserSession.user=null;
                UserSession.user=userService.getUserByUsername(managerUsernameField.getText());//Got user by username successfully
                log.info("Manager \""+UserSession.user.getUsername()+"\" successfully logged in.");
                AlertManager.showAlert(Alert.AlertType.INFORMATION,"Информация","✅ Извършихте успешен вход в системата.");
                ViewManager.changeView(Constants.View.MANAGER_MAIN_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Manager Main", 800, 500);
            }
            else
            {
                log.error("Manager \""+UserSession.user.getUsername()+"\" failed to log in.");
                AlertManager.showAlert(Alert.AlertType.ERROR, "Грешка", "❌ Възникна грешка. Моля опитайте отново.");
                UserSession.user=null;
            }
        }
    }

    public void initialize() {

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                timeLabel.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            }
        };
        timer.start();

        anchorPane.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER){
                managerLoginButton.fire();
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
