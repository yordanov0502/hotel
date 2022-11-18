package bg.tu_varna.sit.hotel.presentation.controllers.admin;

import bg.tu_varna.sit.hotel.application.Main;
import bg.tu_varna.sit.hotel.common.CommonTask;
import bg.tu_varna.sit.hotel.common.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminRegistrationController implements Initializable {

    @FXML
    public TextField adminNameField;
    @FXML
    public TextField adminSurnameField;
    @FXML
    public TextField adminEgnField;
    @FXML
    public TextField adminCountryField;
    @FXML
    public TextField adminPhoneNumberField;
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
    public void backToAdminLoginPage(ActionEvent event) throws IOException{
        CommonTask.pageNavigation(Constants.View.ADMIN_LOGIN_VIEW, Main.stage,this.getClass(),"Admin Login", 800, 500);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        closeWindowButton.setOnMouseClicked(event -> System.exit(0));
    }
}
