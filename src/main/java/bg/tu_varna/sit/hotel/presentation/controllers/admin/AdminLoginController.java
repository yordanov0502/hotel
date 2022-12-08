package bg.tu_varna.sit.hotel.presentation.controllers.admin;

import bg.tu_varna.sit.hotel.application.Main;
import bg.tu_varna.sit.hotel.business.UserService;
import bg.tu_varna.sit.hotel.common.*;
import bg.tu_varna.sit.hotel.presentation.models.UserModel;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.Timestamp;


public class AdminLoginController{
    private static final Logger log = Logger.getLogger(AdminLoginController.class);
    public final UserService userService = UserService.getInstance();

    @FXML
    public AnchorPane anchorPane;
    @FXML
    public TextField adminUsernameField;
    @FXML
    public PasswordField adminPasswordField;
    @FXML
    public Button adminLoginButton;
    @FXML
    public Button adminBackButton;
    @FXML
    public Button adminRegisterButton;
    @FXML
    public Button closeWindowButton;

    @FXML
    public void admin_Login() throws IOException {
        if(userService.validateLoginFields(new String[] {adminUsernameField.getText(), adminPasswordField.getText()}) && userService.authenticateUser(adminUsernameField.getText(), adminPasswordField.getText(),"Администратор"))
        {
            UserSession.setUser(userService.getUserByUsername(adminUsernameField.getText()));//Got user by username successfully
            //updates last login column of user every time he logs in
            if(userService.updateUser(new UserModel(UserSession.getUser().getId(),UserSession.getUser().getFirstName(),UserSession.getUser().getLastName(),UserSession.getUser().getPhone(), UserSession.getUser().getUsername(),UserSession.getUser().getEmail(),UserSession.getUser().getPassword(),UserSession.getUser().getHash(),UserSession.getUser().getRole(),UserSession.getUser().getCreatedAt(),new Timestamp(System.currentTimeMillis()), UserSession.getUser().getStatus())))
            {
                UserSession.setUser(null);
                UserSession.setUser(userService.getUserByUsername(adminUsernameField.getText()));//Got user by username successfully
                log.info("Admin \""+UserSession.getUser().getUsername()+"\" successfully logged in.");
                ViewManager.changeView(Constants.View.ADMIN_MAIN_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Admin Main", 800, 500);
            }
            else
            {
                log.error("Admin \""+UserSession.getUser().getUsername()+"\" failed to log in.");
                AlertManager.showAlert(Alert.AlertType.ERROR, "Грешка", "Възникна грешка. Моля опитайте отново.");
            }
        }
    }

    @FXML
    public void backToWelcomePage(ActionEvent actionEvent) throws IOException {
        ViewManager.changeView(Constants.View.WELCOME_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Hotel Management System", 800, 500);
    }

    @FXML
    public void admin_Registration(ActionEvent actionEvent) throws IOException {
        ViewManager.changeView(Constants.View.ADMIN_REGISTRATION_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Admin Registration", 800, 500);
    }

    public void initialize()
    {
        anchorPane.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER){
                adminLoginButton.fire();
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