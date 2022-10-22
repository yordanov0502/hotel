package bg.tu_varna.sit.hotel.front_end.presentation.application.controllers;

import bg.tu_varna.sit.hotel.back_end.application.Welcome;
import bg.tu_varna.sit.hotel.back_end.common.CommonTask;
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
        CommonTask.pageNavigation("/bg/tu_varna/sit/hotel/front_end/presentation/admin/pages/AdminLogin.fxml", Welcome.stage,this.getClass(),"Admin Login", 800, 500);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        closeWindowButton.setOnMouseClicked(event -> System.exit(0));
    }

   // public void backToWelcomeScreen(ActionEvent event) throws IOException {
     //  CommonTask.pageNavigation("/bg/tu_varna/sit/hotel/front_end/presentation/application/pages/WelcomeView.fxml", Welcome.stage,this.getClass(),"Hotel Management System", 800, 500);
   // }
}