package bg.tu_varna.sit.hotel.application;

import bg.tu_varna.sit.hotel.business.UserService;
import bg.tu_varna.sit.hotel.common.ViewManager;
import bg.tu_varna.sit.hotel.common.Constants;
import bg.tu_varna.sit.hotel.data.access.Connection;
import bg.tu_varna.sit.hotel.data.entities.User;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;


public class Main extends Application {
    public static Stage stage;//initial and main stage
    public static double x, y;//initial and current coordinates
    public static double xx, yy;//temporary coordinates(when moving the window)
    @Override
    public void start(Stage stageParameter) throws IOException
    {
        Connection.createSessionFactory();
        ViewManager.changeView(Constants.View.WELCOME_VIEW,Main.stage,this.getClass(),"Hotel Management System",800,500);
    }



    public static void main(String[] args) {launch();}
}