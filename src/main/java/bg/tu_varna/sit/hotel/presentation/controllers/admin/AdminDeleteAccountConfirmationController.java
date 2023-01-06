package bg.tu_varna.sit.hotel.presentation.controllers.admin;

import bg.tu_varna.sit.hotel.business.HotelService;
import bg.tu_varna.sit.hotel.business.UserService;
import bg.tu_varna.sit.hotel.common.*;
import bg.tu_varna.sit.hotel.presentation.models.HotelModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class AdminDeleteAccountConfirmationController {
    private final UserService userService = UserService.getInstance();
    private final HotelService hotelService = HotelService.getInstance();

    @FXML
    public AnchorPane anchorPane;
    @FXML
    public TextField adminUsernameField;
    @FXML
    public TextField adminPasswordField;
    @FXML
    public Button confirmRemovalButton;
    @FXML
    public Button denyRemovalButton;

    @FXML
    public void confirmRemoval(){

        if(userService.validateLoginFields(new String[] {adminUsernameField.getText(), adminPasswordField.getText()}))
        {
            if(UserSession.user.getUsername().equals(adminUsernameField.getText()) && UserSession.user.getHash().equals(Hasher.SHA512.hash(adminPasswordField.getText())))
            {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setHeaderText("Потвърждение");
                alert.initStyle(StageStyle.UNDECORATED);
                alert.setContentText("Потвърждавам изтриването на акаунтът ми от системата.");
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
                        if(userService.getAllConfirmedAdmins().size()>1)
                        {
                            if(userService.deleteUser(UserSession.user))
                            {
                                AlertManager.showAlert(Alert.AlertType.INFORMATION, "Информация", "✅ Вие изтрихте акаунтът си успешно от системата.");
                                UserSession.user=null;
                                ViewManager.closeDialogBox();
                                ViewManager.changeView(Constants.View.ADMIN_LOGIN_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Admin Login", 800, 500);
                            }
                            else
                            {AlertManager.showAlert(Alert.AlertType.ERROR, "Грешка", "❌ Неуспешно изтриване на акаунт от системата.");}
                        }
                        else
                        {
                            AlertManager.showAlert(Alert.AlertType.ERROR, "Грешка", "❌ Не можете да изтриете акаунтът си, защото вие сте единствения упълномощен администратор в системата към момента.");
                            ViewManager.closeDialogBox();
                        }
                    }
                    catch (IOException e)
                    {
                        e.printStackTrace();
                    }
                }

            }
            else { AlertManager.showAlert(Alert.AlertType.ERROR, "Грешка", "❌ Грешно потребителско име или парола.");}
        }
    }

    @FXML
    public void denyRemoval(){
        ViewManager.closeDialogBox();
    }

    public void initialize() {

        anchorPane.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER){
                confirmRemovalButton.fire();
                keyEvent.consume();
            }
        });
    }
}