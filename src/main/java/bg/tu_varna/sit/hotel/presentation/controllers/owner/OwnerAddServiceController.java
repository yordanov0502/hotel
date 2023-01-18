package bg.tu_varna.sit.hotel.presentation.controllers.owner;

import bg.tu_varna.sit.hotel.business.HotelService;
import bg.tu_varna.sit.hotel.business.ServiceService;
import bg.tu_varna.sit.hotel.common.AlertManager;
import bg.tu_varna.sit.hotel.common.Constants;
import bg.tu_varna.sit.hotel.common.UserSession;
import bg.tu_varna.sit.hotel.common.ViewManager;
import bg.tu_varna.sit.hotel.presentation.controllers.owner.cache.NewHotelInformation;
import bg.tu_varna.sit.hotel.presentation.models.HotelModel;
import bg.tu_varna.sit.hotel.presentation.models.RoomModel;
import bg.tu_varna.sit.hotel.presentation.models.ServiceModel;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class OwnerAddServiceController {
    private static final Logger log = Logger.getLogger(OwnerAddServiceController.class);
    private final ServiceService serviceService = ServiceService.getInstance();

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button addServiceButton;
    @FXML
    private TextField serviceTextField;
    @FXML
    private ComboBox<String> seasonsComboBox;

    private static HotelModel selectedHotel;

    public void addService() throws IOException {
        if(serviceService.validateServiceField(serviceTextField.getText()) && checkForSelectedSeason() && checkForExistingHotelService(serviceTextField.getText()))
        {

            if(serviceService.addService(new ServiceModel(1L,serviceTextField.getText(),seasonsComboBox.getValue(),0,HotelService.getInstance().getHotelByName(selectedHotel.getName()))))
            {
                AlertManager.showAlert(Alert.AlertType.INFORMATION,"Информация","✅ Извършихте успешно добавяне на услуга.");
                ViewManager.closeDialogBox();
                UserSession.selectedHotel=selectedHotel;
                ViewManager.openDialogBox(Constants.View.OWNER_HOTELS_INFO_SERVICES_INFORMATION_VIEW,ViewManager.getSecondaryStage(),this.getClass(),"Owner Hotels Info Services Information",750,450);
            }
            else
            {
                AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","❌ Добавянето на нова услуга е неуспешно.");
            }
        }
    }

    public void backToServicesInfoPage() throws IOException {
        UserSession.selectedHotel=selectedHotel;
        ViewManager.closeDialogBox();
        ViewManager.openDialogBox(Constants.View.OWNER_HOTELS_INFO_SERVICES_INFORMATION_VIEW,ViewManager.getSecondaryStage(),this.getClass(),"Owner Hotels Info Services Information",750,450);
    }

    public void initialize()
    {
        seasonsComboBox.setItems(FXCollections.observableArrayList("пролет", "лято", "есен", "зима" , "цяла година"));

        anchorPane.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER){
                addServiceButton.fire();
                keyEvent.consume();
            }
        });
    }

    public static void setSelectedHotel(HotelModel selectedHotel,OwnerHotelsInfoServicesInformationController ownerHotelsInfoServicesInformationController) {
        OwnerAddServiceController.selectedHotel = selectedHotel;
    }

    private boolean checkForExistingHotelService(String serviceName){
        if(selectedHotel != null && ServiceService.getInstance().getAllServicesOfHotel(HotelService.getInstance().getHotelByName(selectedHotel.getName()))!=null)
        {
            for(ServiceModel service : ServiceService.getInstance().getAllServicesOfHotel(HotelService.getInstance().getHotelByName(selectedHotel.getName())))
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

}