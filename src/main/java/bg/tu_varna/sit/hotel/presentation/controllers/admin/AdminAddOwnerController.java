package bg.tu_varna.sit.hotel.presentation.controllers.admin;

import bg.tu_varna.sit.hotel.business.HotelService;
import bg.tu_varna.sit.hotel.common.*;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AdminAddOwnerController {
    private static final Logger log = Logger.getLogger(AdminMainController.class);
    private final HotelService hotelService = HotelService.getInstance();

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button addOwnerButton1;
    @FXML
    private Button addOwnerButton2;
    @FXML
    private Button addOwnerButton3;
    @FXML
    private Label timeLabel;



    public void showAdminMainView() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.ADMIN_MAIN_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Admin Main", 800, 500);
    }


    public void showOwnersInfo() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.ADMIN_OWNERS_INFO_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Admin Owners Info", 800, 500);
    }


    public void showManagersInfo() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.ADMIN_MANAGERS_INFO_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Admin Managers Info", 800, 500);
    }


    public void showReceptionistsInfo() throws IOException{
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.ADMIN_RECEPTIONISTS_INFO_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Admin Receptionists Info", 800, 500);
    }


    public void showHotelsInfo() throws IOException{
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.ADMIN_HOTELS_INFO_VIEW,ViewManager.getPrimaryStage(),this.getClass(),"Admin Hotels Info",800,500);
    }


    public void showNewlyRegisteredAdmins() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.ADMINS_NEW_REGISTRATIONS_INFO, ViewManager.getPrimaryStage(),this.getClass(),"Admins New Registrations Info", 800, 500);
    }


    public void addNewOwner() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.ADMIN_ADD_NEW_OWNER_VIEW,ViewManager.getPrimaryStage(),this.getClass(),"Admin Add New Owner",800,500);
    }


    public void addNewOwnerToVacantHotel() throws IOException {
        if(hotelService.getAllHotelsWithoutOwner()!=null)
        {
            ViewManager.closeDialogBox();
            ViewManager.changeView(Constants.View.ADMIN_ADD_NEW_OWNER_TO_VACANT_HOTEL_VIEW,ViewManager.getPrimaryStage(),this.getClass(),"Admin Add New Owner To Vacant Hotel",800,500);
        }
        else
        {
            AlertManager.showAlert(Alert.AlertType.INFORMATION,"Информация","ⓘ В момента няма хотели без собственик.");
        }
    }


    public void addOwnerToVacantHotel() throws IOException {
        if(hotelService.getAllHotelsWithoutOwner()!=null)
        {
            ViewManager.closeDialogBox();
            ViewManager.changeView(Constants.View.ADMIN_ADD_OWNER_TO_VACANT_HOTEL_VIEW, ViewManager.getPrimaryStage(), this.getClass(), "Admin Add Owner To Vacant Hotel", 800, 500);
        }
        else
        {
            AlertManager.showAlert(Alert.AlertType.INFORMATION,"Информация","ⓘ В момента няма хотели без собственик.");
        }
    }


    public void logout() throws IOException {
        ViewManager.closeDialogBox();
        if(UserSession.user!=null)
        {
            log.info("Admin \""+UserSession.user.getUsername()+"\" successfully logged out.");
            UserSession.user=null;//pri logout dannite za nastoqshta user sesiq se iztrivat, za da ne sa nali4ni otvun
        }
        ViewManager.changeView(Constants.View.ADMIN_LOGIN_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Admin Login", 800, 500);
    }


    public void showAccountInformation() throws IOException {
        if(UserSession.user==null)
        {
            AlertManager.showAlert(Alert.AlertType.ERROR, "Грешка", "Няма заредени данни за администратор.");
        }
        else
        {
            ViewManager.closeDialogBox();
            ViewManager.openDialogBox(Constants.View.ADMIN_INFO_VIEW, null,this.getClass(),"Admin Info", 652, 352);
        }
    }

    public void initialize()
    {
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                timeLabel.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            }
        };
        timer.start();

        if(UserSession.user==null)
        {
            addOwnerButton1.setDisable(true);
            addOwnerButton2.setDisable(true);
            addOwnerButton3.setDisable(true);
        }
    }

}