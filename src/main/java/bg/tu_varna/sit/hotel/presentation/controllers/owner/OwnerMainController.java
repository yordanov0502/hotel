package bg.tu_varna.sit.hotel.presentation.controllers.owner;

import bg.tu_varna.sit.hotel.common.AlertManager;
import bg.tu_varna.sit.hotel.common.Constants;
import bg.tu_varna.sit.hotel.common.UserSession;
import bg.tu_varna.sit.hotel.common.ViewManager;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import org.apache.log4j.Logger;

import java.io.IOException;

public class OwnerMainController {
    private static final Logger log = Logger.getLogger(OwnerMainController.class);
    @FXML
    public Circle notificationCircle;
    @FXML
    public Label notificationLabel;

    @FXML
    public void addHotelAndManager() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.OWNER_ADD_HOTEL_AND_MANAGER_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Owner Add Hotel And Manager", 800, 500);
    }
/*
    @FXML
    public void showOwnersInfo() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.ADMIN_OWNERS_INFO_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Admin Owners Info", 800, 500);
    }

    @FXML
    public void showManagersInfo() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.ADMIN_MANAGERS_INFO_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Admin Managers Info", 800, 500);
    }

    @FXML
    public void showReceptionistsInfo() throws IOException{
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.ADMIN_RECEPTIONISTS_INFO_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Admin Receptionists Info", 800, 500);
    }

    @FXML
    public void showHotelsInfo() throws IOException{
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.ADMIN_HOTELS_INFO_VIEW,ViewManager.getPrimaryStage(),this.getClass(),"Admin Hotels Info",800,500);
    }

    @FXML
    public void showNewlyRegisteredAdmins() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.ADMINS_NEW_REGISTRATIONS_INFO, ViewManager.getPrimaryStage(),this.getClass(),"Admins New Registrations Info", 800, 500);
    }*/

    @FXML
    public void logout() throws IOException {
        ViewManager.closeDialogBox();
        if(UserSession.user!=null)
        {
            log.info("Owner \""+UserSession.user.getUsername()+"\" successfully logged out.");
            UserSession.user=null;//pri logout dannite za nastoqshta user sesiq se iztrivat, za da ne sa nali4ni otvun
        }
        ViewManager.changeView(Constants.View.OWNER_LOGIN_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Owner Login", 800, 500);
    }

    @FXML
    public void showAccountInformation() throws IOException {
        if(UserSession.user==null)
        {
            AlertManager.showAlert(Alert.AlertType.ERROR, "????????????", "???????? ???????????????? ?????????? ???? ????????????????????.");
        }
        else
        {
            ViewManager.closeDialogBox();
            ViewManager.openDialogBox(Constants.View.OWNER_INFO_VIEW, null,this.getClass(),"Owner Info", 652, 352);
        }
    }

    public void initialize()
    {
        //notificationCircle.setVisible(true);
        //notificationLabel.setText("7");
        //notificationLabel.setVisible(true);
    }
}