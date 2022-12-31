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
    public AnchorPane anchorPane;
    @FXML
    public Label nameLabel;
    @FXML
    public Label surnameLabel;
    @FXML
    public Label egnLabel;
    @FXML
    public Label phoneLabel;
    @FXML
    public Label usernameLabel;
    @FXML
    public Label emailLabel;
    @FXML
    public Label passwordLabel;

    @FXML
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