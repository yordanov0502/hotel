package bg.tu_varna.sit.hotel.presentation.controllers.owner;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.util.ArrayList;
import java.util.List;

public class HotelRoomsInformation {

    private static TabPane tabPane = new TabPane();
    //private static List<Tab> tabList = new ArrayList<>();

    public static TabPane getTabPane() {
        return tabPane;
    }

    public static void setTabPane(TabPane tabPane, OwnerHotelRoomsInformationController ownerHotelRoomsInformationController) {
        HotelRoomsInformation.tabPane = tabPane;
    }

   // public static List<Tab> getTabList() {
   //     return tabList;
   // }

   // public static void setTabList(List<Tab> tabList, OwnerHotelRoomsInformationController ownerHotelRoomsInformationController) {
    //    HotelRoomsInformation.tabList = tabList;
   // }
}