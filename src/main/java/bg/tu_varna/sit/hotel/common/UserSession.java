package bg.tu_varna.sit.hotel.common;

import bg.tu_varna.sit.hotel.presentation.models.UserModel;

public final class UserSession {//this class holds the last UserModel, which was loaded

    private static UserModel user;

    private UserSession(){}

    public static UserModel getUser() {return user;}

    public static void setUser(UserModel user) {UserSession.user = user;}

}