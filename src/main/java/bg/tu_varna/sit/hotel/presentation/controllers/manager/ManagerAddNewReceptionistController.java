package bg.tu_varna.sit.hotel.presentation.controllers.manager;

import bg.tu_varna.sit.hotel.business.UserService;
import bg.tu_varna.sit.hotel.common.*;
import bg.tu_varna.sit.hotel.presentation.controllers.admin.AdminMainController;
import bg.tu_varna.sit.hotel.presentation.models.UserModel;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ManagerAddNewReceptionistController {
    private static final Logger log = Logger.getLogger(ManagerAddNewReceptionistController.class);
    private final UserService userService = UserService.getInstance();

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField receptionistNameField;
    @FXML
    private TextField receptionistSurnameField;
    @FXML
    private TextField receptionistEGNField;
    @FXML
    private TextField receptionistPhoneField;
    @FXML
    private TextField receptionistUsernameField;
    @FXML
    private TextField receptionistEmailField;
    @FXML
    private PasswordField receptionistPasswordField;
    @FXML
    private Button addNewReceptionistButton;
    @FXML
    private Label timeLabel;



    public void showManagerMainView() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.MANAGER_MAIN_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Manager Main", 800, 500);
    }

    public void showCustomersQuery() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.MANAGER_CUSTOMERS_INFO_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Manager Customers Info", 800, 500);
    }

    public void showRegistrationsInfo() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.MANAGER_REGISTRATIONS_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Manager Registrations Info", 800, 500);
    }

    public void showReceptionistsReservationsInfo() throws IOException{
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.MANAGER_RECEPTIONISTS_RESERVATIONS_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Manager Receptionists Reservations Info", 800, 500);
    }

    public void showRoomRatings() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.MANAGER_ROOMS_RATINGS_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Manager Room Ratings", 800, 500);
    }

    public void showHotelInfo() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.MANAGER_HOTEL_INFO_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Manager Hotel Info", 800, 500);
    }


    public void addNewReceptionist() throws IOException {
        if (userService.validateFields(new String[]{receptionistNameField.getText(), receptionistSurnameField.getText(), receptionistEGNField.getText(), receptionistPhoneField.getText(), receptionistUsernameField.getText(), receptionistEmailField.getText(), receptionistPasswordField.getText()})
                && !userService.checkForExistingUserData(new String[]{receptionistEGNField.getText(), receptionistPhoneField.getText(), receptionistUsernameField.getText(), receptionistEmailField.getText()}))
        {
            if (userService.addUser(new UserModel(receptionistEGNField.getText(), receptionistNameField.getText(), receptionistSurnameField.getText(), receptionistPhoneField.getText(), receptionistUsernameField.getText(), receptionistEmailField.getText(), receptionistPasswordField.getText(), Hasher.SHA512.hash(receptionistPasswordField.getText()), "рецепционист", new Timestamp(System.currentTimeMillis()), new Timestamp(System.currentTimeMillis()), "потвърден", new ArrayList<>())))
            {
                log.info("New receptionist has been added successfully.");
                if(userService.addHotel(userService.getUserById(receptionistEGNField.getText()),userService.getUserById(UserSession.user.getId()).getHotels().get(0).toModel())) //here we add new receptionist to the hotel of manager(every manager works for only 1 hotel)
                {
                    ViewManager.closeDialogBox();
                    ViewManager.changeView(Constants.View.MANAGER_ADD_NEW_RECEPTIONIST_VIEW, ViewManager.getPrimaryStage(), this.getClass(), "Manager Add New Receptionist", 800, 500);
                }
                else
                {
                    userService.deleteUser(userService.getUserById(receptionistEGNField.getText()));
                    ViewManager.closeDialogBox();
                    ViewManager.changeView(Constants.View.MANAGER_ADD_NEW_RECEPTIONIST_VIEW, ViewManager.getPrimaryStage(), this.getClass(), "Manager Add New Receptionist", 800, 500);
                    //AlertManager.showAlert(Alert.AlertType.ERROR, "Грешка", "❌ Неуспешно добавяне на нов мениджър към хотел \""+hotelService.getHotelByName(comboBox.getValue()).getName()+"\".");
                }
            }
            else
            {
                log.error("Receptionist has not been added successfully.");
                AlertManager.showAlert(Alert.AlertType.ERROR, "Грешка", "❌ Неуспешно добавяне на нов рецепционист.");
            }
        }
    }


    public void logout() throws IOException {
        ViewManager.closeDialogBox();
        if(UserSession.user!=null)
        {
            log.info("Manager \""+UserSession.user.getUsername()+"\" successfully logged out.");
            UserSession.user=null;//pri logout dannite za nastoqshta user sesiq se iztrivat, za da ne sa nali4ni otvun
        }
        ViewManager.changeView(Constants.View.MANAGER_LOGIN_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Manager Login", 800, 500);
    }


    public void showAccountInformation() throws IOException {
        if(UserSession.user==null)
        {
            AlertManager.showAlert(Alert.AlertType.ERROR, "Грешка", "Няма заредени данни за мениджър.");
        }
        else
        {
            ViewManager.closeDialogBox();
            ViewManager.openDialogBox(Constants.View.MANAGER_INFO_VIEW, null,this.getClass(),"Manager Info", 652, 352);
        }
    }

    public void initialize()
    {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                timeLabel.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            }
        };
        timer.start();

        if(UserSession.user==null)
        {
            receptionistNameField.setDisable(true);
            receptionistSurnameField.setDisable(true);
            receptionistEGNField.setDisable(true);
            receptionistPhoneField.setDisable(true);
            receptionistPasswordField.setDisable(true);
            receptionistUsernameField.setDisable(true);
            receptionistEmailField.setDisable(true);
            addNewReceptionistButton.setDisable(true);
        }

        anchorPane.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER){
                addNewReceptionistButton.fire();
                keyEvent.consume();
            }
        });
    }


}