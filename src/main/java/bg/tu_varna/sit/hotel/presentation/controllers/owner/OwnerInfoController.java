package bg.tu_varna.sit.hotel.presentation.controllers.owner;

import bg.tu_varna.sit.hotel.common.Constants;
import bg.tu_varna.sit.hotel.common.UserSession;
import bg.tu_varna.sit.hotel.common.ViewManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.Optional;

public class OwnerInfoController {
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
    public Button ownerEditInfoButton;
    @FXML
    public Button deleteAccountButton;
    @FXML
    public Button closeButton;

    @FXML
    public void openEditInfoPage() throws IOException {
        ViewManager.openDialogBox(Constants.View.OWNER_EDIT_INFO_VIEW,ViewManager.getSecondaryStage(),this.getClass(),"Owner Edit Info",652,352);
    }

    @FXML
    public void deleteAccount(){
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Потвърждение");
        alert.initStyle(StageStyle.UNDECORATED);
        alert.setContentText("Наистина ли искате да изтриете акаунтът си от системата ?");
        alert.setX(ViewManager.getPrimaryStage().getX()+220);
        alert.setY(ViewManager.getPrimaryStage().getY()+180);
        alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
        ButtonType yesButton = new ButtonType("Да", ButtonBar.ButtonData.YES);
        ButtonType noButton = new ButtonType("Не", ButtonBar.ButtonData.NO);
        alert.getButtonTypes().setAll(yesButton, noButton);
        Optional<ButtonType> answer = alert.showAndWait();

        if(answer.isPresent() && answer.get()==yesButton)
        {
            try
            {
                ViewManager.openDialogBox(Constants.View.OWNER_DELETE_ACCOUNT_CONFIRMATION_VIEW,ViewManager.getSecondaryStage(),this.getClass(),"Owner Delete Account Confirmation",652,352);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void closeInfoPage(){
        ViewManager.closeDialogBox();
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