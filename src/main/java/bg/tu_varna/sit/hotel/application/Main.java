package bg.tu_varna.sit.hotel.application;

import bg.tu_varna.sit.hotel.business.HotelService;
import bg.tu_varna.sit.hotel.business.RoomService;
import bg.tu_varna.sit.hotel.business.UserService;
import bg.tu_varna.sit.hotel.common.ViewManager;
import bg.tu_varna.sit.hotel.common.Constants;
import bg.tu_varna.sit.hotel.data.access.Connection;
import bg.tu_varna.sit.hotel.data.entities.Hotel;
import bg.tu_varna.sit.hotel.presentation.models.HotelModel;
import bg.tu_varna.sit.hotel.presentation.models.RoomModel;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;


public class Main extends Application {
    @Override
    public void start(Stage stageParameter) throws IOException
    {
        Connection.createSessionFactory();
        ViewManager.changeView(Constants.View.WELCOME_VIEW,ViewManager.getPrimaryStage(),this.getClass(),"Hotel Management System",800,500);
       // HotelService.getInstance().addHotel(new HotelModel(19L,"Aqua","Varna,BG",new Timestamp(System.currentTimeMillis()),5,false,false,new ArrayList<>()));

       // RoomService.getInstance().addRoom(new RoomModel(88L,101, HotelService.getInstance().getHotelById(1L),155.23,"единична",50,7));
        //HotelModel hotelModel = HotelService.getInstance().getHotelById(1L);
        //HotelService.getInstance().deleteHotel(HotelService.getInstance().getHotelById(2L));
        //System.out.println(RoomService.getInstance().getAllHotelRooms(HotelService.getInstance().getHotelById(1L)));
        //HotelService.getInstance().addHotel(hotelModel);

        //HotelService.getInstance().addUser(HotelService.getInstance().getHotelById(1L), UserService.getInstance().getUserById("0146301024"));
        //HotelService.getInstance().addUser(HotelService.getInstance().getHotelById(1L), UserService.getInstance().getUserById("0146301028"));
        //HotelService.getInstance().addUser(HotelService.getInstance().getHotelById(1L), UserService.getInstance().getUserById("0146301022"));
    }

    public static void main(String[] args) {launch();}
}