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



public class AdminLoginController  implements Initializable{

    @FXML
    public TextField adminEmailField;
    @FXML
    public PasswordField adminPasswordField;
    @FXML
    public Button adminEnterButton;
    @FXML
    public Button adminBackButton;
    @FXML
    public Button adminRegisterButton;
    @FXML
    public Button closeWindowButton;


    @FXML
    public void admin_Registration(ActionEvent actionEvent) throws IOException {
        CommonTask.pageNavigation(Constants.View.ADMIN_REGISTRATION_VIEW, Main.stage,this.getClass(),"Admin Registration", 800, 500);
    }

    @FXML
    public void backToWelcomePage(ActionEvent actionEvent) throws IOException {
        CommonTask.pageNavigation(Constants.View.WELCOME_VIEW, Main.stage,this.getClass(),"Hotel Management System", 800, 500);
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        closeWindowButton.setOnMouseClicked(event -> System.exit(0));
    }


}