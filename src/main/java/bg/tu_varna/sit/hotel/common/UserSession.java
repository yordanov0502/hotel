package bg.tu_varna.sit.hotel.common;

import bg.tu_varna.sit.hotel.presentation.models.CustomerModel;
import bg.tu_varna.sit.hotel.presentation.models.HotelModel;
import bg.tu_varna.sit.hotel.presentation.models.RoomModel;
import bg.tu_varna.sit.hotel.presentation.models.UserModel;

import java.util.List;

public final class UserSession {//this class holds the last UserModel, which was loaded

    public static UserModel user;//currently logged user
    public static UserModel selectedUser;//represents selected user from one of the TableViews in the system
    public static HotelModel selectedHotel;//represents selected hotel from one of the TableViews in the system
    public static RoomModel selectedRoom;//represents selected room from one of the TableViews in the system
    public static CustomerModel selectedCustomer;//represents selected customer from one of the TableViews in the system

    private UserSession(){}
}