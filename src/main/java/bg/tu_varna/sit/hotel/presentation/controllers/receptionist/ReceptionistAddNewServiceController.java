package bg.tu_varna.sit.hotel.presentation.controllers.receptionist;

import bg.tu_varna.sit.hotel.business.HotelService;
import bg.tu_varna.sit.hotel.business.ReservationService;
import bg.tu_varna.sit.hotel.business.ServiceService;
import bg.tu_varna.sit.hotel.business.UserService;
import bg.tu_varna.sit.hotel.common.AlertManager;
import bg.tu_varna.sit.hotel.common.Constants;
import bg.tu_varna.sit.hotel.common.UserSession;
import bg.tu_varna.sit.hotel.common.ViewManager;
import bg.tu_varna.sit.hotel.presentation.models.HotelModel;
import bg.tu_varna.sit.hotel.presentation.models.ServiceModel;
import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Circle;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ReceptionistAddNewServiceController {
    private static final Logger log = Logger.getLogger(ReceptionistAddNewServiceController.class);
    private final ServiceService serviceService = ServiceService.getInstance();
    private final HotelService hotelService = HotelService.getInstance();
    private final UserService userService = UserService.getInstance();
    private final ReservationService reservationService = ReservationService.getInstance();
    private HotelModel hotelModel;

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button addNewServiceButton;
    @FXML
    private TextField serviceTextField;
    @FXML
    private ComboBox<String> seasonsComboBox;

    @FXML
    private Circle notificationCircle;
    @FXML
    private Label notificationLabel;
    @FXML
    private Label timeLabel;


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

    public void showReservations() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.RECEPTIONIST_RESERVATIONS_VIEW,ViewManager.getPrimaryStage(),this.getClass(),"Receptionist Uncompleted Reservations",800,500);
    }

    public void showHotelInfo() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.RECEPTIONIST_HOTEL_INFO_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Receptionist Hotel Info", 800, 500);
    }


    public void addNewService() throws IOException {
        if(serviceService.validateServiceField(serviceTextField.getText()) && checkForSelectedSeason() && checkForExistingHotelService(serviceTextField.getText()))
        {
            if(serviceService.addService(new ServiceModel(1L,serviceTextField.getText(),seasonsComboBox.getValue(),0, hotelService.getHotelByName(userService.getUserById(UserSession.user.getId()).getHotels().get(0).getName()))))
            {
                    AlertManager.showAlert(Alert.AlertType.INFORMATION,"Информация","✅ Извършихте успешно добавяне на услуга.");
                    ViewManager.closeDialogBox();
                    ViewManager.changeView(Constants.View.RECEPTIONIST_ADD_NEW_SERVICE_VIEW,ViewManager.getPrimaryStage(),this.getClass(),"Receptionist Add New Service",800,500);
            }
            else
            {
                    AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","❌ Добавянето на нова услуга е неуспешно.");
            }
        }
    }

    private boolean checkForSelectedSeason(){
        if(seasonsComboBox.getValue()!=null)
        {
            return true;
        }
        else
        {
            AlertManager.showAlert(Alert.AlertType.ERROR, "Грешка", "❌ Моля изберете сезон за хотелската услуга.");
            return false;
        }
    }

    private boolean checkForExistingHotelService(String serviceName){
        if(serviceService.isServiceExists(serviceName,hotelModel))
        {
            AlertManager.showAlert(Alert.AlertType.ERROR, "Грешка", "❌ Хотелска услуга \""+serviceName+"\" вече съществува.");
            return false;
        }
        else
        {
            return true;
        }
    }


    public void logout() throws IOException {
        ViewManager.closeDialogBox();
        if(UserSession.user!=null)
        {
            ReservationService.processNotificationSucceeded = true;
            ReservationService.uncompletedNotifiedReservations = 0;

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
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                timeLabel.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            }
        };
        timer.start();

        if(UserSession.user!=null)
        {
            seasonsComboBox.setItems(FXCollections.observableArrayList("пролет", "лято", "есен", "зима" , "цяла година"));

            anchorPane.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
                if(keyEvent.getCode() == KeyCode.ENTER){
                    addNewServiceButton.fire();
                    keyEvent.consume();
                }
            });

            hotelModel = UserSession.user.getHotels().get(0).toModel();
            reservationService.refreshUncompletedReservationsStatus(hotelModel);

            if(ReservationService.processNotificationSucceeded)
            {
                reservationService.processNotifications(UserSession.user.getHotels().get(0).toModel(),notificationCircle,notificationLabel);
            }
        }
        else
        {
            serviceTextField.setDisable(true);
            seasonsComboBox.setDisable(true);
            addNewServiceButton.setDisable(true);
        }
    }

}