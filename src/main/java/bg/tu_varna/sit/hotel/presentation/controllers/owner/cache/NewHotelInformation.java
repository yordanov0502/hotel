package bg.tu_varna.sit.hotel.presentation.controllers.owner.cache;

import bg.tu_varna.sit.hotel.common.AlertManager;
import bg.tu_varna.sit.hotel.presentation.controllers.owner.*;
import bg.tu_varna.sit.hotel.presentation.models.*;
import javafx.scene.control.Alert;
import javafx.scene.control.TabPane;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class NewHotelInformation {
   private static UserModel hotelManagerInformation;
   private static HotelModel hotelMajorInformation;
   private static RoomsInformation hotelRoomsInformation;
   private static List<ServiceModel> hotelServicesInformation;
   private static boolean refreshed = false;

   private NewHotelInformation(){}

   public static UserModel getHotelManagerInformation() {return hotelManagerInformation;}

   public static HotelModel getHotelMajorInformation() {
      return hotelMajorInformation;
   }

   public static RoomsInformation getHotelRoomsInformation() {
      return hotelRoomsInformation;
   }

   public static List<ServiceModel> getHotelServicesInformation() {
      return hotelServicesInformation;
   }

   public static boolean isRefreshed() {return refreshed;}

   public static void makeRefreshedFalse(MajorOwnerController majorOwnerController) {
      if(refreshed){refreshed=false;}
   }

   public static void deletePreviousCachedInformation(OwnerAddNewHotelAndNewManagerController ownerAddNewHotelAndNewManagerController)
   {
      NewHotelInformation.hotelManagerInformation = null;
      NewHotelInformation.hotelMajorInformation = null;
      NewHotelInformation.hotelRoomsInformation = null;
      NewHotelInformation.hotelServicesInformation = null;
   }

   public static void transferNewManagerInformation(UserModel userModel,OwnerHotelManagerAddController ownerHotelManagerAddController)
   {
      NewHotelInformation.hotelManagerInformation =  userModel;
      NewHotelInformation.refreshed =true;
   }

   public static void transferNewHotelInformation(HotelModel hotelModel, OwnerHotelMajorInformationController ownerHotelMajorInformationController){
      NewHotelInformation.hotelMajorInformation = hotelModel;
      NewHotelInformation.refreshed =true;
   }

   public static void transferNewRoomsInformation(RoomsInformation hotelRoomsInformation,OwnerHotelRoomsInformationController ownerHotelRoomsInformationController) {
      NewHotelInformation.hotelRoomsInformation = hotelRoomsInformation;
      NewHotelInformation.refreshed =true;
   }

   public static void transferNewServicesInformation(List<ServiceModel> hotelServicesInformation,OwnerHotelServicesInformationController ownerHotelServicesInformationController) {
      NewHotelInformation.hotelServicesInformation = hotelServicesInformation;
      NewHotelInformation.refreshed =true;
   }

}