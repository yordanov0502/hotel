package bg.tu_varna.sit.hotel.presentation.controllers.admin;

import bg.tu_varna.sit.hotel.common.Constants;
import bg.tu_varna.sit.hotel.common.UserSession;
import bg.tu_varna.sit.hotel.common.ViewManager;
import bg.tu_varna.sit.hotel.presentation.models.UserModel;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class AdminUserInfoController {

    private UserModel selectedUser;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Label nameLabel;
    @FXML
    private Label surnameLabel;
    @FXML
    private Label egnLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label usernameLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label passwordLabel;


    public void closeInfoPage(){
        ViewManager.closeDialogBox();
    }

    public void initialize()
    {
        selectedUser=UserSession.selectedUser;
        UserSession.selectedUser=null;

        nameLabel.setText(selectedUser.getFirstName());
        surnameLabel.setText(selectedUser.getLastName());
        egnLabel.setText(selectedUser.getId());
        phoneLabel.setText(selectedUser.getPhone());
        usernameLabel.setText(selectedUser.getUsername());
        emailLabel.setText(selectedUser.getEmail());
        passwordLabel.setText(selectedUser.getPassword());
    }
}