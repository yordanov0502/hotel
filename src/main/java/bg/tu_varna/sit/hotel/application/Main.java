package bg.tu_varna.sit.hotel.application;

import bg.tu_varna.sit.hotel.business.HotelService;
import bg.tu_varna.sit.hotel.business.ReservationService;
import bg.tu_varna.sit.hotel.common.Constants;
import bg.tu_varna.sit.hotel.common.ViewManager;
import bg.tu_varna.sit.hotel.data.access.Connection;

import bg.tu_varna.sit.hotel.data.entities.Reservation;
import bg.tu_varna.sit.hotel.data.repositories.implementations.ReservationRepositoryImpl;
import javafx.application.Application;

import javafx.stage.Stage;
import org.joda.time.DateTime;
import org.joda.time.Days;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class Main extends Application {
    @Override
    public void start(Stage stageParameter) throws IOException, ParseException {
        Connection.createSessionFactory();
        ViewManager.changeView(Constants.View.WELCOME_VIEW,ViewManager.getPrimaryStage(),this.getClass(),"Hotel Management System",800,500);


    }



    public static void main(String[] args) {launch();}
}