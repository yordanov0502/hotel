package bg.tu_varna.sit.hotel.presentation.controllers.manager;

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

public class ManagerDeleteAccountConfirmationController {
    private final UserService userService = UserService.getInstance();
    private final HotelService hotelService = HotelService.getInstance();

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField managerUsernameField;
    @FXML
    private TextField managerPasswordField;
    @FXML
    private Button confirmRemovalButton;
    @FXML
    private Button denyRemovalButton;


    public void confirmRemoval(){

        if(userService.validateLoginFields(new String[] {managerUsernameField.getText(), managerPasswordField.getText()}))
        {
            if(UserSession.user.getUsername().equals(managerUsernameField.getText()) && UserSession.user.getHash().equals(Hasher.SHA512.hash(managerPasswordField.getText())))
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
                        List<HotelModel> hotelsOfManager = new ArrayList<>();//the manager has only 1 hotel, so the list could contain max 1 HotelModel

                        if(!userService.getUserById(UserSession.user.getId()).getHotels().isEmpty())
                        {
                            hotelsOfManager = userService.getAllHotelsOfUser(userService.getUserById(UserSession.user.getId()));
                        }

                        if(userService.deleteUser(userService.getUserById(UserSession.user.getId())))
                        {
                            if(!hotelsOfManager.isEmpty())
                            {
                                for(HotelModel h: hotelsOfManager)
                                {
                                    h.setHasManager(false);//if the user(manager) had hotels, I set each of them free(hasManager=false)
                                    hotelService.updateHotel(h);
                                }
                            }

                            AlertManager.showAlert(Alert.AlertType.INFORMATION, "Информация", "✅ Вие изтрихте акаунтът си успешно от системата.");
                            UserSession.user=null;
                            ViewManager.closeDialogBox();
                            ViewManager.changeView(Constants.View.MANAGER_LOGIN_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Manager Login", 800, 500);
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