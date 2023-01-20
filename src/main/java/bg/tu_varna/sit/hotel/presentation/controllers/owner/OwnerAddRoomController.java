package bg.tu_varna.sit.hotel.presentation.controllers.owner;

import bg.tu_varna.sit.hotel.business.HotelService;
import bg.tu_varna.sit.hotel.business.RoomService;
import bg.tu_varna.sit.hotel.common.AlertManager;
import bg.tu_varna.sit.hotel.common.Constants;
import bg.tu_varna.sit.hotel.common.UserSession;
import bg.tu_varna.sit.hotel.common.ViewManager;
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

public class OwnerAddRoomController {
    private static final Logger log = Logger.getLogger(OwnerAddRoomController.class);
    private final RoomService roomService = RoomService.getInstance();

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
    private Button addRoomButton;

    private static HotelModel selectedHotel;

    public void addRoom() throws IOException {
        if(roomService.validateRoomInfoFields(numberField.getText(), typeField.getText(), areaField.getText(), priceField.getText(),selectedHotel.getName()))
        {
            if(roomService.addRoom(new RoomModel(1L,Integer.parseInt(numberField.getText()), HotelService.getInstance().getHotelByName(selectedHotel.getName()), Integer.parseInt(priceField.getText()), typeField.getText(), Integer.parseInt(areaField.getText()),0D,0,false)))
            {
                AlertManager.showAlert(Alert.AlertType.INFORMATION,"Информация","✅ Извършихте успешно добавяне на стая.");
                ViewManager.closeDialogBox();
                ViewManager.changeView(Constants.View.OWNER_HOTELS_INFO_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Owner Hotels Info", 800, 500);
            }
            else
            {AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","❌ Добавянето на нова стая е неуспешно.");}
        }
    }

    public void backToRoomsInfoPage() throws IOException {
        UserSession.selectedHotel=selectedHotel;
        ViewManager.closeDialogBox();
        ViewManager.openDialogBox(Constants.View.OWNER_HOTELS_INFO_ROOMS_INFORMATION_VIEW,ViewManager.getSecondaryStage(),this.getClass(),"Owner Hotels Info Rooms Information",750,450);
    }

    public void initialize()
    {
        anchorPane.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ENTER){
                addRoomButton.fire();
                keyEvent.consume();
            }
        });
    }

    public static void setSelectedHotel(HotelModel selectedHotel,OwnerHotelsInfoRoomsInformationController ownerHotelsInfoRoomsInformationController) {
        OwnerAddRoomController.selectedHotel = selectedHotel;
    }
}