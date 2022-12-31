package bg.tu_varna.sit.hotel.presentation.controllers.admin;

import bg.tu_varna.sit.hotel.common.Constants;
import bg.tu_varna.sit.hotel.common.UserSession;
import bg.tu_varna.sit.hotel.common.ViewManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class AdminInfoController {

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
    public Button adminEditInfoButton;
    @FXML
    public Button closeButton;

    public void closeInfoPage(){
        ViewManager.closeDialogBox();
    }

    public void openEditInfoPage() throws IOException {
            ViewManager.openDialogBox(Constants.View.ADMIN_EDIT_INFO_VIEW,ViewManager.getSecondaryStage(),this.getClass(),"Admin Edit Info",652,352);
    }

    public void showCustomerInfo(){
        nameLabel.setText(UserSession.user.getFirstName());
        surnameLabel.setText(UserSession.user.getLastName());
        egnLabel.setText(UserSession.user.getId());
        phoneLabel.setText(UserSession.user.getPhone());
        usernameLabel.setText(UserSession.user.getUsername());
        emailLabel.setText(UserSession.user.getEmail());
        passwordLabel.setText(UserSession.user.getPassword());
    }

    public void initialize()
    {
        if(UserSession.user!=null)
        {
            showCustomerInfo();
        }
    }

}