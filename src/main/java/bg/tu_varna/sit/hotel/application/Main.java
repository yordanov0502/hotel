package bg.tu_varna.sit.hotel.application;

import bg.tu_varna.sit.hotel.business.HotelService;
import bg.tu_varna.sit.hotel.business.HotelsUsersService;
import bg.tu_varna.sit.hotel.business.UserService;
import bg.tu_varna.sit.hotel.common.Hasher;
import bg.tu_varna.sit.hotel.common.ViewManager;
import bg.tu_varna.sit.hotel.common.Constants;
import bg.tu_varna.sit.hotel.data.access.Connection;
import bg.tu_varna.sit.hotel.presentation.models.HotelModel;
import bg.tu_varna.sit.hotel.presentation.models.HotelsUsersModel;
import bg.tu_varna.sit.hotel.presentation.models.UserModel;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Timestamp;


public class Main extends Application {
    @Override
    public void start(Stage stageParameter) throws IOException
    {
        Connection.createSessionFactory();
        ViewManager.changeView(Constants.View.WELCOME_VIEW,ViewManager.getPrimaryStage(),this.getClass(),"Hotel Management System",800,500);



       // UserService userService = UserService.getInstance();
       // HotelService hotelService = HotelService.getInstance();
       // HotelsUsersService.getInstance().addHotelsUsers(new HotelsUsersModel(userService.getUserById("0146301025").getId(),hotelService.getHotelById(1).getId()));

    }



    public static void main(String[] args) {launch();}
}