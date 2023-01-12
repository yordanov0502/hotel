package bg.tu_varna.sit.hotel.presentation.controllers.application;

import bg.tu_varna.sit.hotel.application.Main;
import bg.tu_varna.sit.hotel.common.ViewManager;
import bg.tu_varna.sit.hotel.common.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;

public class WelcomeViewController{

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button adminLoginButton;
    @FXML
    private Button ownerLoginButton;
    @FXML
    private Button managerLoginButton;
    @FXML
    private Button receptionistLoginButton;
    @FXML
    private Button closeWindowButton;


    public void admin_Login() throws IOException {
        ViewManager.changeView(Constants.View.ADMIN_LOGIN_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Admin Login", 800, 500);
    }


    public void owner_Login() throws IOException {
        ViewManager.changeView(Constants.View.OWNER_LOGIN_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Owner Login", 800, 500);
    }


    public void manager_Login() throws IOException {
        ViewManager.changeView(Constants.View.MANAGER_LOGIN_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Manager Login", 800, 500);
    }


    public void receptionist_Login() throws IOException {
        ViewManager.changeView(Constants.View.RECEPTIONIST_LOGIN_VIEW, ViewManager.getPrimaryStage(),this.getClass(),"Receptionist Login", 800, 500);
    }


    public void initialize()
    {
        anchorPane.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ESCAPE){
                System.exit(0);
                keyEvent.consume();
            }
        });

        closeWindowButton.setOnMouseClicked(event -> System.exit(0));
    }

}