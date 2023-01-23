package bg.tu_varna.sit.hotel.application;

import bg.tu_varna.sit.hotel.business.*;
import bg.tu_varna.sit.hotel.common.Constants;
import bg.tu_varna.sit.hotel.common.ViewManager;
import bg.tu_varna.sit.hotel.data.access.Connection;
import bg.tu_varna.sit.hotel.presentation.controllers.owner.OwnerHotelRoomsInformationController;
import bg.tu_varna.sit.hotel.presentation.models.ReservationModel;
import bg.tu_varna.sit.hotel.presentation.models.RoomModel;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


public class Main extends Application {
    @Override
    public void start(Stage stageParameter) throws IOException
    {
        Connection.createSessionFactory();
        ViewManager.changeView(Constants.View.WELCOME_VIEW,ViewManager.getPrimaryStage(),this.getClass(),"Hotel Management System",800,500);



        // ReservationService.getInstance().addReservation(new ReservationModel(1L,1,"direct","unfinished",Timestamp.valueOf("2023-01-01 15:00:00.000"),Timestamp.valueOf("2023-01-27 18:00:00.000"),Timestamp.valueOf("2023-01-29 20:00:00.000"),HotelService.getInstance().getHotelByName("Udinese Hotel"),UserService.getInstance().getUserById("0546301028"),CustomerService.getInstance().getCustomerByEgn("0145301025",HotelService.getInstance().getHotelByName("Udinese Hotel")),"неопределен",RoomService.getInstance().getRoomByNumber("102",HotelService.getInstance().getHotelByName("Udinese Hotel")),0,0D,"няма",false ));
    }



    public static void main(String[] args) {launch();}
}