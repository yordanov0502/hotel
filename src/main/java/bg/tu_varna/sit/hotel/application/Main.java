package bg.tu_varna.sit.hotel.application;

import bg.tu_varna.sit.hotel.business.HotelService;
import bg.tu_varna.sit.hotel.business.RoomService;
import bg.tu_varna.sit.hotel.business.ServiceService;
import bg.tu_varna.sit.hotel.common.Constants;
import bg.tu_varna.sit.hotel.common.ViewManager;
import bg.tu_varna.sit.hotel.data.access.Connection;
import bg.tu_varna.sit.hotel.presentation.controllers.owner.OwnerHotelRoomsInformationController;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class Main extends Application {
    @Override
    public void start(Stage stageParameter) throws IOException
    {
        Connection.createSessionFactory();
        ViewManager.changeView(Constants.View.WELCOME_VIEW,ViewManager.getPrimaryStage(),this.getClass(),"Hotel Management System",800,500);
    }



    public static void main(String[] args) {launch();}
}