package bg.tu_varna.sit.hotel.presentation.models;

import bg.tu_varna.sit.hotel.data.entities.Hotel;

import java.sql.Timestamp;

public class HotelModel implements EntityModel<Hotel>{
    private Integer id;
    private String name;
    private String address;
    private Timestamp established_at;
    private Integer stars;

    public HotelModel(){}

    public HotelModel(Integer id,String name,String address,Timestamp established_at,Integer stars)
    {
        this.id=id;
        this.name=name;
        this.address=address;
        this.established_at=established_at;
        this.stars=stars;
    }

    public HotelModel(Hotel hotel)
    {
        this.id=hotel.getId();
        this.name=hotel.getName();
        this.address= hotel.getAddress();
        this.established_at=hotel.getEstablished_at();
        this.stars=hotel.getStars();
    }

    public Integer getId() {return id;}

    public void setId(Integer id) {this.id = id;}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Timestamp getEstablished_at() {
        return established_at;
    }

    public void setEstablished_at(Timestamp established_at) {
        this.established_at = established_at;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }


    @Override
    public Hotel toEntity() {
        Hotel hotelTemp = new Hotel();
        hotelTemp.setId(this.id);
        hotelTemp.setName(this.name);
        hotelTemp.setAddress(this.address);
        hotelTemp.setEstablished_at(this.established_at);
        hotelTemp.setStars(this.stars);
        return hotelTemp;
    }
}