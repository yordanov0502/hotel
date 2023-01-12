package bg.tu_varna.sit.hotel.presentation.controllers.owner;

import bg.tu_varna.sit.hotel.business.HotelService;
import bg.tu_varna.sit.hotel.common.AlertManager;
import bg.tu_varna.sit.hotel.common.Constants;
import bg.tu_varna.sit.hotel.common.ViewManager;
import bg.tu_varna.sit.hotel.presentation.models.HotelModel;
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
import java.util.ArrayList;

public class OwnerHotelMajorInformationController implements NewHotelInfoProvider {
    private static final Logger log = Logger.getLogger(OwnerHotelMajorInformationController.class);
    private final HotelService hotelService = HotelService.getInstance();

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField hotelNameField;
    @FXML
    private TextField hotelAddressField;
    @FXML
    private Rating hotelRating;
    @FXML
    private Button addHotelMajorInfoButton;
    @FXML
    private Button closeButton;


    public void addHotelMajorInformation() throws IOException {
        if(hotelService.validateMajorInfoFields(new String[] {hotelNameField.getText(), hotelAddressField.getText()})
                && !hotelService.checkForExistingHotelData(new String[] {hotelNameField.getText(), hotelAddressField.getText()}))
        {
            NewHotelInformation.transferHotelInformation(new HotelModel(1L,hotelNameField.getText(),hotelAddressField.getText(),new Timestamp(System.currentTimeMillis()), (int) hotelRating.getRating(),true,true,new ArrayList<>()/*owner and manager must be added!!!!*/),this);
            log.info("New major data for hotel added.");
            AlertManager.showAlert(Alert.AlertType.INFORMATION,"Информация","✅ Успешно добавихте основна информация за хотел.");
            ViewManager.closeDialogBox();
            ViewManager.changeView(Constants.View.OWNER_ADD_NEW_HOTEL_AND_NEW_MANAGER_VIEW, ViewManager.getPrimaryStage(), this.getClass(), "Owner Add New Hotel And New Manager", 800, 500);
        }
    }


    public void closePage(){
        ViewManager.closeDialogBox();
    }

    public void initialize()
    {
          if(NewHotelInformation.getHotelMajorInformation()!=null)
          {
              hotelNameField.setText(NewHotelInformation.getHotelMajorInformation().getName());
              hotelAddressField.setText(NewHotelInformation.getHotelMajorInformation().getAddress());
              hotelRating.setRating(NewHotelInformation.getHotelMajorInformation().getStars());
          }

        anchorPane.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER){
                addHotelMajorInfoButton.fire();
                keyEvent.consume();
            }
        });
    }
}