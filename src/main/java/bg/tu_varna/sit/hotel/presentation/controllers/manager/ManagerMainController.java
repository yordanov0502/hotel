package bg.tu_varna.sit.hotel.presentation.controllers.manager;

import bg.tu_varna.sit.hotel.common.AlertManager;
import bg.tu_varna.sit.hotel.common.Constants;
import bg.tu_varna.sit.hotel.common.UserSession;
import bg.tu_varna.sit.hotel.common.ViewManager;
import bg.tu_varna.sit.hotel.presentation.controllers.owner.OwnerMainController;
import bg.tu_varna.sit.hotel.presentation.controllers.owner.cache.NewHotelInformation;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.shape.Circle;
import org.apache.log4j.Logger;

import java.io.IOException;

public class ManagerMainController {
    private static final Logger log = Logger.getLogger(ManagerMainController.class);

    @FXML
    private Circle notificationCircle;
    @FXML
    private Label notificationLabel;



    public void addNewReceptionist() throws IOException {
        ViewManager.closeDialogBox();
        ViewManager.changeView(Constants.View.MANAGER_ADD_NEW_RECEPTIONIST_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Manager Add New Receptionist", 800, 500);
    }


    public void logout() throws IOException {
        ViewManager.closeDialogBox();
        if(UserSession.user!=null)
        {
            log.info("Manager \""+UserSession.user.getUsername()+"\" successfully logged out.");
            UserSession.user=null;//pri logout dannite za nastoqshta user sesiq se iztrivat, za da ne sa nali4ni otvun
        }
        ViewManager.changeView(Constants.View.MANAGER_LOGIN_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Manager Login", 800, 500);
    }


    public void showAccountInformation() throws IOException {
        if(UserSession.user==null)
        {
            AlertManager.showAlert(Alert.AlertType.ERROR, "Грешка", "Няма заредени данни за мениджър.");
        }
        else
        {
            ViewManager.closeDialogBox();
            ViewManager.openDialogBox(Constants.View.MANAGER_INFO_VIEW, null,this.getClass(),"Manager Info", 652, 352);
        }
    }

    public void initialize()
    {
        //notificationCircle.setVisible(true);
        //notificationLabel.setText("7");
        //notificationLabel.setVisible(true);
    }
}