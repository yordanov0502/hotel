package bg.tu_varna.sit.hotel.front_end.presentation.application.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class WelcomeViewController implements Initializable {

    @FXML
    public Button adminLoginButton;
    @FXML
    public Button ownerLoginButton;
    @FXML
    public Button managerLoginButton;
    @FXML
    public Button receptionistLoginButton;
    @FXML
    public Button closeWindowButton;

    @FXML
    void admin_Login(ActionEvent event) throws IOException {
       // CommonTask.pageNavigation("zadmin/Login/AdminLogin.fxml", Main.stage,this.getClass(),"Admin Login", 600, 400);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        closeWindowButton.setOnMouseClicked(event -> System.exit(0));
    }
}