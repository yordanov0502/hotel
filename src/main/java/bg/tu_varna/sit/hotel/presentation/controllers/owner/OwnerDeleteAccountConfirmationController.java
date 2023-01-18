package bg.tu_varna.sit.hotel.presentation.controllers.owner;

import bg.tu_varna.sit.hotel.business.HotelService;
import bg.tu_varna.sit.hotel.business.UserService;
import bg.tu_varna.sit.hotel.common.*;
import bg.tu_varna.sit.hotel.data.entities.Hotel;
import bg.tu_varna.sit.hotel.presentation.models.HotelModel;
import bg.tu_varna.sit.hotel.presentation.models.UserModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.stage.StageStyle;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class OwnerDeleteAccountConfirmationController {
    private final UserService userService = UserService.getInstance();
    private final HotelService hotelService = HotelService.getInstance();

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField ownerUsernameField;
    @FXML
    private TextField ownerPasswordField;
    @FXML
    private Button confirmRemovalButton;
    @FXML
    private Button denyRemovalButton;


    public void confirmRemoval(){

        if(userService.validateLoginFields(new String[] {ownerUsernameField.getText(), ownerPasswordField.getText()}))
        {
            if(UserSession.user.getUsername().equals(ownerUsernameField.getText()) && UserSession.user.getHash().equals(Hasher.SHA512.hash(ownerPasswordField.getText())))
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
                        List<HotelModel> hotelsOfOwner = new ArrayList<>();

                        if(!userService.getUserById(UserSession.user.getId()).getHotels().isEmpty())
                        {
                            hotelsOfOwner = userService.getAllHotelsOfUser(userService.getUserById(UserSession.user.getId()));
                        }

                        if(userService.deleteUser(userService.getUserById(UserSession.user.getId())))
                        {
                            if(!hotelsOfOwner.isEmpty())
                            {
                                for(HotelModel h: hotelsOfOwner)
                                {
                                    h.setHasOwner(false);//if the user(owner) had hotels, I set each of them free(hasOwner=false)
                                    hotelService.updateHotel(h);
                                }
                            }

                            AlertManager.showAlert(Alert.AlertType.INFORMATION, "Информация", "✅ Вие изтрихте акаунтът си успешно от системата.");
                            UserSession.user=null;
                            ViewManager.closeDialogBox();
                            ViewManager.changeView(Constants.View.OWNER_LOGIN_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Owner Login", 800, 500);
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