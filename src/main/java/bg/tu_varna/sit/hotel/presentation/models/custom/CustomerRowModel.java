package bg.tu_varna.sit.hotel.presentation.models.custom;

import bg.tu_varna.sit.hotel.presentation.models.HotelModel;

import java.sql.Timestamp;

public class CustomerRowModel {
    private Long id;
    private String egn;
    private String firstName;
    private String lastName;
    private String phone;
    private Timestamp createdAt;
    private String rating;
    private HotelModel hotel;
    private Integer nightsStayed;
    private String [] usedServicesList;

    public CustomerRowModel(){}

    public CustomerRowModel(Long id, String egn, String firstName, String lastName, String phone, Timestamp createdAt, String rating, HotelModel hotel, Integer nightsStayed, String[] usedServicesList) {
        this.id = id;
        this.egn = egn;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.createdAt = createdAt;
        this.rating = rating;
        this.hotel = hotel;
        this.nightsStayed = nightsStayed;
        this.usedServicesList = usedServicesList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEgn() {
        return egn;
    }

    public void setEgn(String egn) {
        this.egn = egn;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public HotelModel getHotel() {
        return hotel;
    }

    public void setHotel(HotelModel hotel) {
        this.hotel = hotel;
    }

    public Integer getNightsStayed() {
        return nightsStayed;
    }

    public void setNightsStayed(Integer nightsStayed) {
        this.nightsStayed = nightsStayed;
    }


    public String[] getUsedServicesList() {
        return usedServicesList;
    }

    public void setUsedServicesList(String[] usedServicesList) {
        this.usedServicesList = usedServicesList;
    }
}