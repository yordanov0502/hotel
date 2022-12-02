package bg.tu_varna.sit.hotel.presentation.controllers.admin;

import bg.tu_varna.sit.hotel.application.Main;
import bg.tu_varna.sit.hotel.business.UserService;
import bg.tu_varna.sit.hotel.common.AlertManager;
import bg.tu_varna.sit.hotel.common.ViewManager;
import bg.tu_varna.sit.hotel.common.Constants;
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
            ViewManager.changeView(Constants.View.ADMIN_MAIN_VIEW, Main.stage,this.getClass(),"Admin Main", 800, 500);
        }
    }

    @FXML
    public void backToWelcomePage(ActionEvent actionEvent) throws IOException {
        ViewManager.changeView(Constants.View.WELCOME_VIEW, Main.stage,this.getClass(),"Hotel Management System", 800, 500);
    }

    @FXML
    public void admin_Registration(ActionEvent actionEvent) throws IOException {
        ViewManager.changeView(Constants.View.ADMIN_REGISTRATION_VIEW, Main.stage,this.getClass(),"Admin Registration", 800, 500);
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