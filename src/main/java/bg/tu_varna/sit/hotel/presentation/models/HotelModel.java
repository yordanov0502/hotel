package bg.tu_varna.sit.hotel.presentation.models;

import bg.tu_varna.sit.hotel.data.entities.Hotel;
import bg.tu_varna.sit.hotel.data.entities.User;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HotelModel implements EntityModel<Hotel>{
    private Long id;
    private String name;
    private String address;
    private Timestamp established_at;
    private Integer stars;
    private Boolean hasOwner;
    private Boolean hasManager;
    private List<User> users = new ArrayList<>();//////////////////////////////////////

    public HotelModel(){}

    public HotelModel(/**/Long id,String name,String address,Timestamp established_at,Integer stars,Boolean hasOwner,Boolean hasManager, List<User> users)
    {
        /**/this.id=id;
        this.name=name;
        this.address=address;
        this.established_at=established_at;
        this.stars=stars;
        this.hasOwner=hasOwner;
        this.hasManager=hasManager;
        this.users=users;
    }

    public HotelModel(Hotel hotel)
    {
        this.id=hotel.getId();
        this.name=hotel.getName();
        this.address= hotel.getAddress();
        this.established_at=hotel.getEstablished_at();
        this.stars=hotel.getStars();
        this.hasOwner=hotel.getHasOwner();
        this.hasManager=hotel.getHasManager();
        this.users=hotel.getUsers();
    }

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

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

    public Boolean getHasOwner() {return hasOwner;}

    public void setHasOwner(Boolean hasOwner) {this.hasOwner = hasOwner;}

    public Boolean getHasManager() {return hasManager;}

    public void setHasManager(Boolean hasManager) {this.hasManager = hasManager;}

    public List<User> getUsers() {return users;}

    public void setUsers(List<User> users) {this.users = users;}

    @Override
    public Hotel toEntity() {
        Hotel hotelTemp = new Hotel();
        hotelTemp.setId(this.id);
        hotelTemp.setName(this.name);
        hotelTemp.setAddress(this.address);
        hotelTemp.setEstablished_at(this.established_at);
        hotelTemp.setStars(this.stars);
        hotelTemp.setHasOwner(this.hasOwner);
        hotelTemp.setHasManager(this.hasManager);
        hotelTemp.setUsers(this.users);
        return hotelTemp;
    }
}