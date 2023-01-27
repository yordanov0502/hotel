package bg.tu_varna.sit.hotel.presentation.controllers.receptionist;

import bg.tu_varna.sit.hotel.business.ReservationService;
import bg.tu_varna.sit.hotel.business.UserService;
import bg.tu_varna.sit.hotel.common.AlertManager;
import bg.tu_varna.sit.hotel.common.Constants;
import bg.tu_varna.sit.hotel.common.UserSession;
import bg.tu_varna.sit.hotel.common.ViewManager;
import bg.tu_varna.sit.hotel.presentation.models.HotelModel;
import bg.tu_varna.sit.hotel.presentation.models.UserModel;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import org.apache.log4j.Logger;
import org.controlsfx.control.Rating;

import java.io.IOException;

public class ReceptionistHotelInfoController {
    private static final Logger log = Logger.getLogger(ReceptionistHotelInfoController.class);
    private final ReservationService reservationService = ReservationService.getInstance();

    @FXML
    private Label hotelNameLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private Label establishedAtLabel;
    @FXML
    private Rating hotelRating;




    public void showReceptionistMainView() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.RECEPTIONIST_MAIN_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Receptionist Main", 800, 500);
    }

    public void addNewCustomer() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.RECEPTIONIST_ADD_NEW_CUSTOMER_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Receptionist Add New Customer", 800, 500);
    }

    public void addNewReservation() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.RECEPTIONIST_ADD_NEW_RESERVATION_VIEW,ViewManager.getPrimaryStage(),this.getClass(),"Receptionist Add New Reservation",800,500);
    }

    public void addNewService() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.RECEPTIONIST_ADD_NEW_SERVICE_VIEW,ViewManager.getPrimaryStage(),this.getClass(),"Receptionist Add New Service",800,500);
    }

    public void showReservations() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.RECEPTIONIST_RESERVATIONS_VIEW,ViewManager.getPrimaryStage(),this.getClass(),"Receptionist Uncompleted Reservations",800,500);
    }


    public void logout() throws IOException {
        ViewManager.closeDialogBox();
        if(UserSession.user!=null)
        {
            log.info("Receptionist \""+UserSession.user.getUsername()+"\" successfully logged out.");
            UserSession.user=null;//pri logout dannite za nastoqshta user sesiq se iztrivat, za da ne sa nali4ni otvun
        }
        ViewManager.changeView(Constants.View.RECEPTIONIST_LOGIN_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Receptionist Login", 800, 500);
    }


    public void showAccountInformation() throws IOException {
        if(UserSession.user==null)
        {
            AlertManager.showAlert(Alert.AlertType.ERROR, "Грешка", "Няма заредени данни за рецепционист.");
        }
        else
        {
            ViewManager.closeDialogBox();
            ViewManager.openDialogBox(Constants.View.RECEPTIONIST_INFO_VIEW, null,this.getClass(),"Receptionist Info", 652, 352);
        }
    }


    public void initialize()
    {
        if(UserSession.user!=null)
        {
            UserModel receptionist = UserService.getInstance().getUserById(UserSession.user.getId());
            HotelModel hotelModel = receptionist.getHotels().get(0).toModel();//Receptionist works only for 1 hotel

            hotelNameLabel.setText(hotelModel.getName());
            addressLabel.setText(hotelModel.getAddress());
            establishedAtLabel.setText(hotelModel.getEstablished_at().toString());
            hotelRating.setRating(hotelModel.getStars());

            reservationService.refreshUncompletedReservationsStatus(hotelModel);
        }
       else
        {
            hotelNameLabel.setDisable(true);
            addressLabel.setDisable(true);
            establishedAtLabel.setDisable(true);
        }
    }

}