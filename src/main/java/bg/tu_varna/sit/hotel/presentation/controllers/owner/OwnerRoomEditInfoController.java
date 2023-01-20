package bg.tu_varna.sit.hotel.presentation.controllers.owner;

import bg.tu_varna.sit.hotel.business.RoomService;
import bg.tu_varna.sit.hotel.common.*;
import bg.tu_varna.sit.hotel.presentation.models.HotelModel;
import bg.tu_varna.sit.hotel.presentation.models.RoomModel;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import org.apache.log4j.Logger;

import java.io.IOException;

public class OwnerRoomEditInfoController {
    private static final Logger log = Logger.getLogger(OwnerRoomEditInfoController.class);
    private final RoomService roomService = RoomService.getInstance();
    private RoomModel selectedRoom;

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private TextField numberField;
    @FXML
    private TextField typeField;
    @FXML
    private TextField areaField;
    @FXML
    private TextField priceField;
    @FXML
    private TextField ratingField;
    @FXML
    private TextField daysOccupiedField;
    @FXML
    private Button editRoomInfoButton;

    private static HotelModel selectedHotel;

    public void editRoomInfo() throws IOException {
        if(roomService.validateRoomInfoFields(numberField.getText(), typeField.getText(), areaField.getText(), priceField.getText(),selectedHotel.getName(),selectedRoom))
        {
            if(roomService.updateRoom(new RoomModel(selectedRoom.getId(),Integer.parseInt(numberField.getText()), selectedRoom.getHotel(), Integer.parseInt(priceField.getText()), typeField.getText(), Integer.parseInt(areaField.getText()),selectedRoom.getRating(),selectedRoom.getDaysOccupied(),selectedRoom.getIsOccupied())))
            {
                AlertManager.showAlert(Alert.AlertType.INFORMATION,"Информация","✅ Извършихте успешно редактиране на данни за стая.");
                ViewManager.closeDialogBox();
                ViewManager.changeView(Constants.View.OWNER_HOTELS_INFO_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Owner Hotels Info", 800, 500);
            }
            else
            {AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","❌ Редактирането на данни за стая е неуспешно.");}
        }
    }

    public void backToRoomsInfoPage() throws IOException {
        UserSession.selectedHotel=selectedHotel;
        ViewManager.closeDialogBox();
        ViewManager.openDialogBox(Constants.View.OWNER_HOTELS_INFO_ROOMS_INFORMATION_VIEW,ViewManager.getSecondaryStage(),this.getClass(),"Owner Hotels Info Rooms Information",750,450);
    }

    public void initialize()
    {
        selectedRoom= UserSession.selectedRoom;
        UserSession.selectedRoom=null;

        numberField.setText(selectedRoom.getNumber().toString());
        typeField.setText(selectedRoom.getType());
        areaField.setText(selectedRoom.getSize().toString());
        priceField.setText(selectedRoom.getPrice().toString());
        ratingField.setText(selectedRoom.getRating().toString());
        daysOccupiedField.setText(selectedRoom.getDaysOccupied().toString());

        anchorPane.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER){
                editRoomInfoButton.fire();
                keyEvent.consume();
            }
        });
    }

    public static void setSelectedHotel(HotelModel selectedHotel,OwnerHotelsInfoRoomsInformationController ownerHotelsInfoRoomsInformationController) {
        OwnerRoomEditInfoController.selectedHotel = selectedHotel;
    }
}