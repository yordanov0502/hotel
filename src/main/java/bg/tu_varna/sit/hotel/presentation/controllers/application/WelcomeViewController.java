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
    public AnchorPane anchorPane;
    @FXML
    public Button adminLoginButton;
    @FXML
    public Button ownerLoginButton;
    @FXML
    public Button managerLoginButton;
    @FXML
    public Button receptionistLoginButton;
    @FXML
    public Button closeWindowButton;

    @FXML
    void admin_Login(ActionEvent event) throws IOException {
        ViewManager.changeView(Constants.View.ADMIN_LOGIN_VIEW, Main.stage,this.getClass(),"Admin Login", 800, 500);
    }

    @FXML
    void owner_Login(ActionEvent event) throws IOException {
        ViewManager.changeView(Constants.View.OWNER_LOGIN_VIEW, Main.stage,this.getClass(),"Owner Login", 800, 500);
    }

    @FXML
    void manager_Login(ActionEvent event) throws IOException {
        ViewManager.changeView(Constants.View.MANAGER_LOGIN_VIEW, Main.stage,this.getClass(),"Manager Login", 800, 500);
    }

    @FXML
    void receptionist_Login(ActionEvent event) throws IOException {
        ViewManager.changeView(Constants.View.RECEPTIONIST_LOGIN_VIEW, Main.stage,this.getClass(),"Receptionist Login", 800, 500);
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