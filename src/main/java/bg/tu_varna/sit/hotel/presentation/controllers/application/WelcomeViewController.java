package bg.tu_varna.sit.hotel.presentation.controllers.application;

import bg.tu_varna.sit.hotel.application.Main;
import bg.tu_varna.sit.hotel.common.ViewManager;
import bg.tu_varna.sit.hotel.common.Constants;
import javafx.animation.Animation;
import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.util.Duration;

import java.io.IOException;
import java.text.DateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

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
    @FXML
    private Label timeLabel;


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
        //source code: https://stackoverflow.com/questions/42383857/javafx-live-time-and-date
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                timeLabel.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")));
            }
        };
        timer.start();

        anchorPane.addEventHandler(KeyEvent.KEY_PRESSED, keyEvent -> {
            if(keyEvent.getCode() == KeyCode.ESCAPE){
                System.exit(0);
                keyEvent.consume();
            }
        });

        closeWindowButton.setOnMouseClicked(event -> System.exit(0));
    }

/*
    final Label clock = new Label();
    final DateFormat format = DateFormat.getInstance();
    final Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent event) {
            final Calendar cal = Calendar.getInstance();
            clock.setText(format.format(cal.getTime()));
        }
    }));
      timeline.setCycleCount(Animation.INDEFINITE);
      timeline.play();*/

    /*AnimationTimer timer = new AnimationTimer() {
        @Override
        public void handle(long now) {
            timer.setText(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        }
    };
timer.start();*/


}