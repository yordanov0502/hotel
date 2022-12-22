package bg.tu_varna.sit.hotel.presentation.models;

import bg.tu_varna.sit.hotel.data.entities.Hotel;
import bg.tu_varna.sit.hotel.data.entities.HotelsUsers;

public class HotelsUsersModel implements EntityModel<HotelsUsers>{
    private String userId;//egn na potrebitel(PK na tablica "users")
    private Long hotelId;//id na hotel(PK na tablica "hotels")

    public HotelsUsersModel(){}

    public HotelsUsersModel(String userId,Long hotelId)
    {
        this.userId=userId;
        this.hotelId=hotelId;
    }

    public HotelsUsersModel(HotelsUsersModel hotelsUsersModel)
    {
        this.userId= hotelsUsersModel.getUserId();
        this.hotelId=hotelsUsersModel.getHotelId();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }


    @Override
    public HotelsUsers toEntity() {
        HotelsUsers hotelsUsersTemp = new HotelsUsers();
        hotelsUsersTemp.setUserId(this.userId);
        hotelsUsersTemp.setHotelId(this.hotelId);
        return hotelsUsersTemp;
    }
}