package bg.tu_varna.sit.hotel.presentation.controllers.manager;

import bg.tu_varna.sit.hotel.business.ReservationService;
import bg.tu_varna.sit.hotel.business.UserService;
import bg.tu_varna.sit.hotel.common.AlertManager;
import bg.tu_varna.sit.hotel.common.Constants;
import bg.tu_varna.sit.hotel.common.UserSession;
import bg.tu_varna.sit.hotel.common.ViewManager;
import bg.tu_varna.sit.hotel.presentation.controllers.receptionist.ReceptionistHotelInfoController;
import bg.tu_varna.sit.hotel.presentation.models.HotelModel;
import bg.tu_varna.sit.hotel.presentation.models.UserModel;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import org.apache.log4j.Logger;
import org.controlsfx.control.Rating;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ManagerHotelInfoController {
    private static final Logger log = Logger.getLogger(ManagerHotelInfoController.class);


    @FXML
    private Label hotelNameLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private Label establishedAtLabel;
    @FXML
    private Rating hotelRating;
    @FXML
    private Label timeLabel;




    public void showManagerMainView() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.MANAGER_MAIN_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Manager Main", 800, 500);
    }

    public void addNewReceptionist() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.MANAGER_ADD_NEW_RECEPTIONIST_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Manager Add New Receptionist", 800, 500);
    }

    public void showRoomRatings() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.MANAGER_ROOMS_RATINGS_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Manager Room Ratings", 800, 500);
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

        if(UserSession.user!=null)
        {
            UserModel manager = UserService.getInstance().getUserById(UserSession.user.getId());
            HotelModel hotelModel = manager.getHotels().get(0).toModel();//Manager works only for 1 hotel

            hotelNameLabel.setText(hotelModel.getName());
            addressLabel.setText(hotelModel.getAddress());
            establishedAtLabel.setText(hotelModel.getEstablished_at().toString());
            hotelRating.setRating(hotelModel.getStars());
        }
        else
        {
            hotelNameLabel.setDisable(true);
            addressLabel.setDisable(true);
            establishedAtLabel.setDisable(true);
        }
    }

}