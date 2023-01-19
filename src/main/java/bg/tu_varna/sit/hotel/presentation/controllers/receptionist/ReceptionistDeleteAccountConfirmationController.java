package bg.tu_varna.sit.hotel.presentation.controllers.receptionist;

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

public class ReceptionistDeleteAccountConfirmationController {
    private final UserService userService = UserService.getInstance();
    private final HotelService hotelService = HotelService.getInstance();

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField receptionistUsernameField;
    @FXML
    private TextField receptionistPasswordField;
    @FXML
    private Button confirmRemovalButton;
    @FXML
    private Button denyRemovalButton;


    public void confirmRemoval(){

        if(userService.validateLoginFields(new String[] {receptionistUsernameField.getText(), receptionistPasswordField.getText()}))
        {
            if(UserSession.user.getUsername().equals(receptionistUsernameField.getText()) && UserSession.user.getHash().equals(Hasher.SHA512.hash(receptionistPasswordField.getText())))
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
                        if(userService.deleteUser(userService.getUserById(UserSession.user.getId())))
                        {
                            AlertManager.showAlert(Alert.AlertType.INFORMATION, "Информация", "✅ Вие изтрихте акаунтът си успешно от системата.");
                            UserSession.user=null;
                            ViewManager.closeDialogBox();
                            ViewManager.changeView(Constants.View.RECEPTIONIST_LOGIN_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Receptionist Login", 800, 500);
                        }
                        else { AlertManager.showAlert(Alert.AlertType.ERROR, "Грешка", "❌ Неуспешно изтриване на акаунт от системата.");}
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