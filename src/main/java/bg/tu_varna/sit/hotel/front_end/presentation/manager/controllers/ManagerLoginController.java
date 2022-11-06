package bg.tu_varna.sit.hotel.front_end.presentation.manager.controllers;

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

public class ManagerLoginController implements Initializable {

    @FXML
    public TextField managerEmailField;
    @FXML
    public PasswordField managerPasswordField;
    @FXML
    public Button managerEnterButton;
    @FXML
    public Button managerBackButton;
    @FXML
    public Button closeWindowButton;

    @FXML
    public void backToWelcomePage(ActionEvent actionEvent) throws IOException {
        CommonTask.pageNavigation(Constants.View.WELCOME_VIEW,Main.stage,this.getClass(),"Hotel Management System", 800, 500);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        closeWindowButton.setOnMouseClicked(event -> System.exit(0));
    }
}
