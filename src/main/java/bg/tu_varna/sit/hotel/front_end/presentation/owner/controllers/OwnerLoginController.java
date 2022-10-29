package bg.tu_varna.sit.hotel.front_end.presentation.owner.controllers;

import bg.tu_varna.sit.hotel.back_end.application.Main;
import bg.tu_varna.sit.hotel.back_end.common.CommonTask;
import bg.tu_varna.sit.hotel.back_end.common.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class OwnerLoginController implements Initializable {

    @FXML
    public TextField ownerEmailField;
    @FXML
    public PasswordField ownerPasswordField;
    @FXML
    public Button ownerEnterButton;
    @FXML
    public Button ownerBackButton;
    @FXML
    public Button closeWindowButton;

    @FXML
    public void backToWelcomePage(ActionEvent actionEvent) throws IOException {
        CommonTask.pageNavigation(Constants.View.WELCOME_VIEW, Main.stage,this.getClass(),"Hotel Management System", 800, 500);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        closeWindowButton.setOnMouseClicked(event -> System.exit(0));
    }
}
