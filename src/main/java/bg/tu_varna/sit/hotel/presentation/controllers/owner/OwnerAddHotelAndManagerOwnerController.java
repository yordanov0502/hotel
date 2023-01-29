package bg.tu_varna.sit.hotel.presentation.controllers.owner;

import bg.tu_varna.sit.hotel.business.HotelService;
import bg.tu_varna.sit.hotel.business.UserService;
import bg.tu_varna.sit.hotel.common.AlertManager;
import bg.tu_varna.sit.hotel.common.Constants;
import bg.tu_varna.sit.hotel.common.UserSession;
import bg.tu_varna.sit.hotel.common.ViewManager;
import bg.tu_varna.sit.hotel.presentation.controllers.owner.cache.MajorOwnerController;
import bg.tu_varna.sit.hotel.presentation.controllers.owner.cache.NewHotelInformation;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import org.apache.log4j.Logger;

import java.io.IOException;

public class OwnerAddHotelAndManagerOwnerController implements MajorOwnerController {
    private static final Logger log = Logger.getLogger(OwnerAddHotelAndManagerOwnerController.class);
    private final HotelService hotelService = HotelService.getInstance();
    private final UserService userService = UserService.getInstance();

    @FXML
    private Button addHotelAndManagerButton1;
    @FXML
    private Button addHotelAndManagerButton2;



    public void showOwnerMainView() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.OWNER_MAIN_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Owner Main", 800, 500);
    }

    public void showHotelsInfo() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.OWNER_HOTELS_INFO_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Owner Hotels Info", 800, 500);
    }

    public void showRoomRatings() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.OWNER_ROOMS_RATINGS_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Owner Room Ratings", 800, 500);
    }


    public void addNewHotelAndNewManager() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.OWNER_ADD_NEW_HOTEL_AND_NEW_MANAGER_VIEW,ViewManager.getPrimaryStage(),this.getClass(),"Owner Add New Hotel And New Manager",800,500);
    }

    public void addNewManagerToVacantHotel() throws IOException {
        if(userService.getAllHotelsOfOwnerWithoutManager(userService.getUserById(UserSession.user.getId()))!=null)
        {
            ViewManager.closeDialogBox();
            ViewManager.changeView(Constants.View.OWNER_ADD_NEW_MANAGER_TO_VACANT_HOTEL_VIEW,ViewManager.getPrimaryStage(),this.getClass(),"Owner Add New Manager To Vacant Hotel",800,500);
        }
        else if(userService.getUserById(UserSession.user.getId()).getHotels().isEmpty())
        {
            AlertManager.showAlert(Alert.AlertType.INFORMATION,"Информация","ⓘ В момента нямате хотели.");
        }
        else
        {
            AlertManager.showAlert(Alert.AlertType.INFORMATION,"Информация","ⓘ В момента нямате хотели без мениджър.");
        }
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
        if(UserSession.user!=null)
        {
            NewHotelInformation.makeRefreshedFalse(this);
        }
        else
        {
            addHotelAndManagerButton1.setDisable(true);
            addHotelAndManagerButton2.setDisable(true);
        }
        //notificationCircle.setVisible(true);
        //notificationLabel.setText("7");
        //notificationLabel.setVisible(true);
    }
}