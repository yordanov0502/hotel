package bg.tu_varna.sit.hotel.front_end.presentation.admin.controllers;

import bg.tu_varna.sit.hotel.back_end.application.Main;
import bg.tu_varna.sit.hotel.back_end.common.CommonTask;
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
        CommonTask.pageNavigation("/bg/tu_varna/sit/hotel/front_end/presentation/admin/pages/AdminLogin.fxml", Main.stage,this.getClass(),"Admin Login", 800, 500);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        closeWindowButton.setOnMouseClicked(event -> System.exit(0));
    }
}
