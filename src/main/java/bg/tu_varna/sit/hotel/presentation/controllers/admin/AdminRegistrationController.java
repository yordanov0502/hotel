package bg.tu_varna.sit.hotel.presentation.controllers.admin;

import bg.tu_varna.sit.hotel.application.Main;
import bg.tu_varna.sit.hotel.business.UserService;
import bg.tu_varna.sit.hotel.common.Hasher;
import bg.tu_varna.sit.hotel.common.ViewManager;
import bg.tu_varna.sit.hotel.common.Constants;
import bg.tu_varna.sit.hotel.presentation.models.UserModel;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;

public class AdminRegistrationController {

    private static final Logger log = Logger.getLogger(AdminRegistrationController.class);
    public final UserService userService = UserService.getInstance();

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
            if(userService.addUser(new UserModel(adminEGNField.getText(),adminNameField.getText(),adminSurnameField.getText(),adminPhoneField.getText(), adminUsernameField.getText(), adminEmailField.getText(), Hasher.SHA512.hash(adminPasswordField.getText()),"admin",new Timestamp(System.currentTimeMillis()),new Timestamp(System.currentTimeMillis()),"unconfirmed")))
            {
                ViewManager.changeView(Constants.View.ADMIN_MAIN_VIEW, Main.stage,this.getClass(),"Admin Main", 800, 500);
            }
        }
    }

    @FXML
    public void backToAdminLoginPage(ActionEvent event) throws IOException {
        ViewManager.changeView(Constants.View.ADMIN_LOGIN_VIEW, Main.stage,this.getClass(),"Admin Login", 800, 500);
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