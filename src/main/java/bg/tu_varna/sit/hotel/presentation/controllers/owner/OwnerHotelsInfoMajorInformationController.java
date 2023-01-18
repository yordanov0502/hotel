package bg.tu_varna.sit.hotel.presentation.controllers.owner;

import bg.tu_varna.sit.hotel.business.HotelService;
import bg.tu_varna.sit.hotel.business.UserService;
import bg.tu_varna.sit.hotel.common.AlertManager;
import bg.tu_varna.sit.hotel.common.Constants;
import bg.tu_varna.sit.hotel.common.UserSession;
import bg.tu_varna.sit.hotel.common.ViewManager;
import bg.tu_varna.sit.hotel.presentation.models.HotelModel;
import bg.tu_varna.sit.hotel.presentation.models.UserModel;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import org.apache.log4j.Logger;
import org.controlsfx.control.Rating;

import java.io.IOException;
import java.sql.Timestamp;

public class OwnerHotelsInfoMajorInformationController {
    private static final Logger log = Logger.getLogger(OwnerHotelsInfoMajorInformationController.class);
    private final HotelService hotelService = HotelService.getInstance();

    @FXML
    private TextField hotelNameField;
    @FXML
    private TextField hotelAddressField;
    @FXML
    private TextField hotelEstablishedAtField;
    @FXML
    private Rating hotelRating;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button editHotelMajorInfoButton;

    private HotelModel selectedHotel;

    public void initialize(){

        selectedHotel=UserSession.selectedHotel;
        UserSession.selectedHotel=null;

        hotelNameField.setText(selectedHotel.getName());
        hotelAddressField.setText(selectedHotel.getAddress());
        hotelEstablishedAtField.setText(selectedHotel.getEstablished_at().toString());
        hotelRating.setRating(selectedHotel.getStars());

        anchorPane.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER){
                editHotelMajorInfoButton.fire();
                keyEvent.consume();
            }
        });
    }

    public void editHotelMajorInformation() throws IOException {
        if(hotelService.validateMajorInfoFields(new String[] {hotelNameField.getText(), hotelAddressField.getText()})
                && !hotelService.checkForExistingHotelData(new String[] {hotelNameField.getText(), hotelAddressField.getText()},(int) hotelRating.getRating(),selectedHotel))
        {
            if(hotelService.updateHotel(new HotelModel(selectedHotel.getId(),hotelNameField.getText(),hotelAddressField.getText(),selectedHotel.getEstablished_at(),(int) hotelRating.getRating(),selectedHotel.getHasOwner(),selectedHotel.getHasManager(),selectedHotel.getUsers())))
            {
                log.info("Successfully updated major information for hotel.");
                AlertManager.showAlert(Alert.AlertType.INFORMATION,"Информация","✅ Успешно редактирахте основна информация за хотел.");
                ViewManager.closeDialogBox();
                ViewManager.changeView(Constants.View.OWNER_HOTELS_INFO_VIEW, ViewManager.getPrimaryStage(), this.getClass(), "Owner Hotels Info", 800, 500);
            }
           else
           {
               log.info("Error when updating major information for hotel.");
               AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","❌ Редактирането на основни данни за хотел е неуспешно.");
           }
        }
    }

    public void closePage(){
        ViewManager.closeDialogBox();
    }
}