package bg.tu_varna.sit.hotel.presentation.controllers.owner;

import bg.tu_varna.sit.hotel.common.AlertManager;
import bg.tu_varna.sit.hotel.presentation.models.*;
import javafx.scene.control.Alert;

import java.util.HashMap;
import java.util.Map;

public final class NewHotelInformation {
   private static UserModel hotelManagerInformation;
   private static HotelModel hotelMajorInformation;
   private static RoomModel hotelRoomsInformation;
   private static ServiceModel hotelServicesInformation;
   private static boolean refreshed = false;
   private static final Map<Integer, Boolean> checkBoxesStates = new HashMap<>() {{put(1, false);put(2, false);put(3, false);put(4, false);}};


   private NewHotelInformation(){}

   public static UserModel getHotelManagerInformation() {return hotelManagerInformation;}

   public static HotelModel getHotelMajorInformation() {
      return hotelMajorInformation;
   }

   public static RoomModel getHotelRoomsInformation() {
      return hotelRoomsInformation;
   }

   public static ServiceModel getHotelServicesInformation() {
      return hotelServicesInformation;
   }

   public static boolean isRefreshed() {return refreshed;}

   public static Map<Integer, Boolean> getCheckBoxesStates() {return checkBoxesStates;}

   public static void makeRefreshedFalse(MajorOwnerController majorOwnerController) {
      if(refreshed){refreshed=false;}
   }

   public static void deletePreviousCachedInformation(OwnerAddNewHotelAndNewManagerController ownerAddNewHotelAndNewManagerController)
   {
      NewHotelInformation.hotelManagerInformation = null;
      NewHotelInformation.hotelMajorInformation = null;
      NewHotelInformation.hotelRoomsInformation = null;
      NewHotelInformation.hotelServicesInformation = null;

      for(int i=1;i<=4;i++)
      {    //if a checkBox was selected then it will be made unselected
         if(checkBoxesStates.get(i)) {checkBoxesStates.put(i,false);}
      }
   }

   public static void transferHotelInformation(EntityModel entityModel, NewHotelInfoProvider specificController)
   {
      if(entityModel instanceof UserModel)
      {
         NewHotelInformation.hotelManagerInformation = (UserModel) entityModel;
         checkBoxesStates.put(1,true);
         NewHotelInformation.refreshed =true;
      }
      else if(entityModel instanceof HotelModel)
      {
         NewHotelInformation.hotelMajorInformation = (HotelModel) entityModel;
         checkBoxesStates.put(2,true);
         NewHotelInformation.refreshed =true;
      }
      else if(entityModel instanceof RoomModel)
      {
         NewHotelInformation.hotelRoomsInformation = (RoomModel) entityModel;
         checkBoxesStates.put(3,true);
         NewHotelInformation.refreshed =true;
      }
      else if(entityModel instanceof ServiceModel)
      {
         NewHotelInformation.hotelServicesInformation = (ServiceModel) entityModel;
         checkBoxesStates.put(4,true);
         NewHotelInformation.refreshed =true;
      }
      else
      {
         AlertManager.showAlert(Alert.AlertType.ERROR, "Грешка", "❌ Грешка. Може да предавате информация само за мениджър, хотел, стаи и допълнителни услуги.");
      }
   }
}