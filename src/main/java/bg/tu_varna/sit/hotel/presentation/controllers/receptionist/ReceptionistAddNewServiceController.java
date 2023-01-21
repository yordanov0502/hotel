package bg.tu_varna.sit.hotel.presentation.controllers.receptionist;

import bg.tu_varna.sit.hotel.business.HotelService;
import bg.tu_varna.sit.hotel.business.ServiceService;
import bg.tu_varna.sit.hotel.business.UserService;
import bg.tu_varna.sit.hotel.common.AlertManager;
import bg.tu_varna.sit.hotel.common.Constants;
import bg.tu_varna.sit.hotel.common.UserSession;
import bg.tu_varna.sit.hotel.common.ViewManager;
import bg.tu_varna.sit.hotel.presentation.models.HotelModel;
import bg.tu_varna.sit.hotel.presentation.models.ServiceModel;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import org.apache.log4j.Logger;

import java.io.IOException;

public class ReceptionistAddNewServiceController {
    private static final Logger log = Logger.getLogger(ReceptionistAddNewServiceController.class);
    private final ServiceService serviceService = ServiceService.getInstance();
    private final HotelService hotelService = HotelService.getInstance();
    private final UserService userService = UserService.getInstance();

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button addNewServiceButton;
    @FXML
    private TextField serviceTextField;
    @FXML
    private ComboBox<String> seasonsComboBox;


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
        HotelModel hotelModel = hotelService.getHotelByName(userService.getUserById(UserSession.user.getId()).getHotels().get(0).getName());
        if(ServiceService.getInstance().getAllServicesOfHotel(hotelModel)!=null)
        {
            for(ServiceModel service : serviceService.getAllServicesOfHotel(hotelModel))
            {
                if(service.getType().equals(serviceName))
                {
                    AlertManager.showAlert(Alert.AlertType.ERROR, "Грешка", "❌ Хотелска услуга \""+serviceName+"\" вече съществува.");
                    return false;
                }
            }
        }
        return true;
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
        seasonsComboBox.setItems(FXCollections.observableArrayList("пролет", "лято", "есен", "зима" , "цяла година"));

        if(UserSession.user==null)
        {
            serviceTextField.setDisable(true);
            seasonsComboBox.setDisable(true);
            addNewServiceButton.setDisable(true);
        }
    }

}