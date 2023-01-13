package bg.tu_varna.sit.hotel.presentation.controllers.owner.cache;

import bg.tu_varna.sit.hotel.common.AlertManager;
import bg.tu_varna.sit.hotel.presentation.controllers.owner.OwnerAddNewHotelAndNewManagerController;
import bg.tu_varna.sit.hotel.presentation.controllers.owner.OwnerHotelRoomsInformationController;
import bg.tu_varna.sit.hotel.presentation.models.*;
import javafx.scene.control.Alert;
import javafx.scene.control.TabPane;

import java.util.HashMap;
import java.util.Map;

public final class NewHotelInformation {
   private static UserModel hotelManagerInformation;
   private static HotelModel hotelMajorInformation;
   private static RoomsInformation hotelRoomsInformation;
   private static ServiceModel hotelServicesInformation;
   private static boolean refreshed = false;

   private NewHotelInformation(){}

   public static UserModel getHotelManagerInformation() {return hotelManagerInformation;}

   public static HotelModel getHotelMajorInformation() {
      return hotelMajorInformation;
   }

   public static RoomsInformation getHotelRoomsInformation() {
      return hotelRoomsInformation;
   }

   public static ServiceModel getHotelServicesInformation() {
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

   public static void transferHotelInformation(EntityModel entityModel, NewHotelInfoProvider specificController)
   {
      if(entityModel instanceof UserModel)
      {
         NewHotelInformation.hotelManagerInformation = (UserModel) entityModel;
         NewHotelInformation.refreshed =true;
      }
      else if(entityModel instanceof HotelModel)
      {
         NewHotelInformation.hotelMajorInformation = (HotelModel) entityModel;
         NewHotelInformation.refreshed =true;
      }
      //this check will be added to separate method(overloaded because for providing rooms information we use specific class which is not part of the model type classes as the other)
      //else if(entityModel instanceof RoomModel)
      //{
      //   NewHotelInformation.hotelRoomsInformation = (RoomModel) entityModel;
      //   checkBoxesStates.put(3,true);
      //   NewHotelInformation.refreshed =true;
      //}
      else if(entityModel instanceof ServiceModel)
      {
         NewHotelInformation.hotelServicesInformation = (ServiceModel) entityModel;
         NewHotelInformation.refreshed =true;
      }
      else
      {
         AlertManager.showAlert(Alert.AlertType.ERROR, "Грешка", "❌ Грешка. Може да предавате информация само за мениджър, хотел, стаи и допълнителни услуги.");
      }
   }

   public static void transferHotelInformation(RoomsInformation hotelRoomsInformation) {
         NewHotelInformation.hotelRoomsInformation = hotelRoomsInformation;
         NewHotelInformation.refreshed =true;
   }


}