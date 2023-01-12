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
import java.util.Objects;


public class AdminLoginController{
    private static final Logger log = Logger.getLogger(AdminLoginController.class);
    private final UserService userService = UserService.getInstance();

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField adminUsernameField;
    @FXML
    private PasswordField adminPasswordField;
    @FXML
    private Button adminLoginButton;
    @FXML
    private Button adminBackButton;
    @FXML
    private Button adminRegisterButton;
    @FXML
    private Button closeWindowButton;


    public void admin_Login() throws IOException {
        if(userService.validateLoginFields(new String[] {adminUsernameField.getText(), adminPasswordField.getText()}) && userService.authenticateUser(adminUsernameField.getText(), adminPasswordField.getText(),"администратор"))
        {
            UserSession.user=userService.getUserByUsername(adminUsernameField.getText());//Got user by username successfully

            if(Objects.equals(UserSession.user.getStatus(), "непотвърден"))
            {
                log.error("Admin \""+UserSession.user.getUsername()+"\" failed to log in, because their registration has not been approved by any of the admins yet.");
                AlertManager.showAlert(Alert.AlertType.INFORMATION,"Информация","ⓘ Достъпът до системата не е възможен, защото регистрацията ви все още не е потвърдена от администраторите.");
                UserSession.user=null;
                ViewManager.changeView(Constants.View.ADMIN_LOGIN_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Admin Login", 800, 500);
            }
            else if(Objects.equals(UserSession.user.getStatus(), "отхвърлен"))
            {
                log.error("Admin \""+UserSession.user.getUsername()+"\" failed to log in, because their registration has been rejected and their data will be deleted.");
                if(userService.deleteUser(UserSession.user))
                {
                    log.info("Information for rejected admin \""+UserSession.user.getFirstName()+" "+UserSession.user.getLastName()+"\" has been successfully deleted.");
                }
                else
                {
                    log.info("Information for rejected admin \""+UserSession.user.getFirstName()+" "+UserSession.user.getLastName()+"\" has NOT been deleted.");
                }
                AlertManager.showAlert(Alert.AlertType.INFORMATION,"Информация","ⓘ Достъпът до системата не е възможен, защото регистрацията ви e била отхвърлена.");
                UserSession.user=null;
                ViewManager.changeView(Constants.View.ADMIN_LOGIN_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Admin Login", 800, 500);
            }
            else
            {
                //updates last login column of user every time he logs in
                if(userService.updateUser(new UserModel(UserSession.user.getId(),UserSession.user.getFirstName(),UserSession.user.getLastName(),UserSession.user.getPhone(), UserSession.user.getUsername(),UserSession.user.getEmail(),UserSession.user.getPassword(),UserSession.user.getHash(),UserSession.user.getRole(),UserSession.user.getCreatedAt(),new Timestamp(System.currentTimeMillis()), "редактиран",UserSession.user.getHotels())))
                {
                    UserSession.user=null;
                    UserSession.user=userService.getUserByUsername(adminUsernameField.getText());//Got user by username successfully
                    log.info("Admin \""+UserSession.user.getUsername()+"\" successfully logged in.");
                    AlertManager.showAlert(Alert.AlertType.INFORMATION,"Информация","✅ Извършихте успешен вход в системата.");
                    ViewManager.changeView(Constants.View.ADMIN_MAIN_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Admin Main", 800, 500);
                }
                else
                {
                    log.error("Admin \""+UserSession.user.getUsername()+"\" failed to log in.");
                    AlertManager.showAlert(Alert.AlertType.ERROR, "Грешка", "❌ Възникна грешка. Моля опитайте отново.");
                    UserSession.user=null;
                }
            }
        }
    }


    public void backToWelcomePage(ActionEvent actionEvent) throws IOException {
        ViewManager.changeView(Constants.View.WELCOME_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Hotel Management System", 800, 500);
    }


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