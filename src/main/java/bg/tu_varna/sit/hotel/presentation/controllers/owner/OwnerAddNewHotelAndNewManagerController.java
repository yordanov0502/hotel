package bg.tu_varna.sit.hotel.presentation.controllers.owner;

import bg.tu_varna.sit.hotel.business.HotelService;
import bg.tu_varna.sit.hotel.common.AlertManager;
import bg.tu_varna.sit.hotel.common.Constants;
import bg.tu_varna.sit.hotel.common.UserSession;
import bg.tu_varna.sit.hotel.common.ViewManager;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.*;

public class OwnerAddNewHotelAndNewManagerController {
    private static final Logger log = Logger.getLogger(OwnerAddNewHotelAndNewManagerController.class);
    private final HotelService hotelService = HotelService.getInstance();

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button addNewHotelAndNewManagerButton;
    @FXML
    private CheckBox managerCheckBox;
    @FXML
    private CheckBox hotelCheckBox;
    @FXML
    private CheckBox roomsCheckBox;
    @FXML
    private CheckBox servicesCheckBox;



    public void showOwnerMainView() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.OWNER_MAIN_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Owner Main", 800, 500);
    }


    public void addHotelAndManager() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.OWNER_ADD_HOTEL_AND_MANAGER_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Owner Add Hotel And Manager", 800, 500);
    }






    public void addHotelManagerInformation() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.openDialogBox(Constants.View.OWNER_HOTEL_MANAGER_ADD_VIEW, null,this.getClass(),"Owner Hotel Manager Add", 652, 352);
    }


    public void addMajorInformation() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.openDialogBox(Constants.View.OWNER_HOTEL_MAJOR_INFORMATION_VIEW, null,this.getClass(),"Owner Hotel Major Information", 652, 352);
    }


    public void addRoomsInformation() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.openDialogBox(Constants.View.OWNER_HOTEL_ROOMS_INFORMATION_VIEW, null,this.getClass(),"Owner Hotel Rooms Information", 750, 450);
    }


    public void addAdditionalServicesInformation() throws IOException {
        //ViewManager.closeDialogBox();
        //ViewManager.openDialogBox(Constants.View.OWNER_INFO_VIEW, null,this.getClass(),"Owner Info", 652, 352);
    }


    public void addNewHotelAndNewManager(){

    }


    public void backToOwnerAddHotelAndManager() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.OWNER_ADD_HOTEL_AND_MANAGER_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Owner Add Hotel And Manager", 800, 500);
    }


    public void logout() throws IOException {
        ViewManager.closeDialogBox();
        if(UserSession.user!=null)
        {
            log.info("Owner \""+UserSession.user.getUsername()+"\" successfully logged out.");
            UserSession.user=null;//pri logout dannite za nastoqshta user sesiq se iztrivat, za da ne sa nali4ni otvun
        }
        ViewManager.changeView(Constants.View.OWNER_LOGIN_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Owner Login", 800, 500);
    }


    public void showAccountInformation() throws IOException {
        if(UserSession.user==null)
        {
            AlertManager.showAlert(Alert.AlertType.ERROR, "Грешка", "Няма заредени данни за собственик.");
        }
        else
        {
            ViewManager.closeDialogBox();
            ViewManager.openDialogBox(Constants.View.OWNER_INFO_VIEW, null,this.getClass(),"Owner Info", 652, 352);
        }
    }

    public void initialize()
    {
        if(NewHotelInformation.isRefreshed())
        {
            if(NewHotelInformation.getCheckBoxesStates().get(1)){managerCheckBox.setSelected(true);}
            if(NewHotelInformation.getCheckBoxesStates().get(2)){hotelCheckBox.setSelected(true);}
            if(NewHotelInformation.getCheckBoxesStates().get(3)){roomsCheckBox.setSelected(true);}
            if(NewHotelInformation.getCheckBoxesStates().get(4)){servicesCheckBox.setSelected(true);}
        }
        else
        {
            NewHotelInformation.deletePreviousCachedInformation(this);
        }

        //if all 3 mandatory of total 4 vital informations for hotel(and manager) are provided
        //then data for a new hotel with manager can be inserted in the database.(the button to do this is enabled)
        if(managerCheckBox.isSelected() && hotelCheckBox.isSelected() && roomsCheckBox.isSelected())
        {
            addNewHotelAndNewManagerButton.setDisable(false);
        }

    }

}