package bg.tu_varna.sit.hotel.presentation.controllers.admin;

import bg.tu_varna.sit.hotel.business.UserService;
import bg.tu_varna.sit.hotel.common.AlertManager;
import bg.tu_varna.sit.hotel.common.UserSession;
import bg.tu_varna.sit.hotel.common.ViewManager;
import bg.tu_varna.sit.hotel.common.Constants;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import org.apache.log4j.Logger;

import java.io.IOException;

public class AdminMainController{
    private static final Logger log = Logger.getLogger(AdminMainController.class);
    @FXML
    public AnchorPane anchorPane;
    @FXML
    public MenuButton secondaryMenuButton;

    @FXML
    public void logout() throws IOException {
        if(ViewManager.getSecondaryStage()!=null)
        {
            ViewManager.getSecondaryStage().close();
            ViewManager.setSecondaryStage(null);
        }
        if(UserSession.getUser()!=null)
        {
            log.info("Admin \""+UserSession.getUser().getUsername()+"\" successfully logged out.");
            UserSession.setUser(null);//pri logout dannite za nastoqshta user sesiq se iztrivat, za da ne sa nali4ni otvun
        }
        ViewManager.changeView(Constants.View.ADMIN_LOGIN_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Admin Login", 800, 500);
    }

    @FXML
    public void showAccountInformation() throws IOException {
        if(UserSession.getUser()==null)
        {
            AlertManager.showAlert(Alert.AlertType.ERROR, "Грешка", "Няма заредени данни за администратор.");
        }
        else if(ViewManager.getSecondaryStage()==null)
        {
            ViewManager.openDialogBox(Constants.View.ADMIN_INFO, null,this.getClass(),"Admin Info", 652, 352);
        }
    }



}