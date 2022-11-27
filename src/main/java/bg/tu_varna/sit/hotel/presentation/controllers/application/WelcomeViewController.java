package bg.tu_varna.sit.hotel.presentation.controllers.application;

import bg.tu_varna.sit.hotel.application.Main;
import bg.tu_varna.sit.hotel.common.ViewManager;
import bg.tu_varna.sit.hotel.common.Constants;
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
        ViewManager.changeView(Constants.View.ADMIN_LOGIN_VIEW, Main.stage,this.getClass(),"Admin Login", 800, 500);
    }

    @FXML
    void owner_Login(ActionEvent event) throws IOException {
        ViewManager.changeView(Constants.View.OWNER_LOGIN_VIEW, Main.stage,this.getClass(),"Owner Login", 800, 500);
    }

    @FXML
    void manager_Login(ActionEvent event) throws IOException {
        ViewManager.changeView(Constants.View.MANAGER_LOGIN_VIEW, Main.stage,this.getClass(),"Manager Login", 800, 500);
    }

    @FXML
    void receptionist_Login(ActionEvent event) throws IOException {
        ViewManager.changeView(Constants.View.RECEPTIONIST_LOGIN_VIEW, Main.stage,this.getClass(),"Receptionist Login", 800, 500);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        closeWindowButton.setOnMouseClicked(event -> System.exit(0));
    }

}