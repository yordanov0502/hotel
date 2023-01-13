package bg.tu_varna.sit.hotel.presentation.controllers.owner.cache;

import bg.tu_varna.sit.hotel.presentation.controllers.owner.OwnerHotelRoomsInformationController;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;

import java.util.List;

public class RoomsInformation {

    private TabPane tabPane;
    private List<CheckBox> roomTypeCheckBoxes;
    private List<TextField> roomTypeAreas;
    private List<TextField> roomTypePrices;
    private String floorsNumber;

    public RoomsInformation(OwnerHotelRoomsInformationController ownerHotelRoomsInformationController,TabPane tabPane,List<CheckBox> roomTypeCheckBoxes,List<TextField> roomTypeAreas,List<TextField> roomTypePrices,String floorsNumber)
    {
        this.tabPane = tabPane;
        this.roomTypeCheckBoxes = roomTypeCheckBoxes;
        this.roomTypeAreas = roomTypeAreas;
        this.roomTypePrices = roomTypePrices;
        this.floorsNumber=floorsNumber;
    }

    public RoomsInformation(OwnerHotelRoomsInformationController ownerHotelRoomsInformationController){}

    public TabPane getTabPane() {
        return tabPane;
    }

    public List<CheckBox> getRoomTypeCheckBoxes() {
        return roomTypeCheckBoxes;
    }

    public List<TextField> getRoomTypeAreas() {
        return roomTypeAreas;
    }

    public List<TextField> getRoomTypePrices() {
        return roomTypePrices;
    }

    public String getFloorsNumber() {
        return floorsNumber;
    }
}