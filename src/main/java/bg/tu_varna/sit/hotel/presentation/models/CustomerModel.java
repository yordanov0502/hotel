package bg.tu_varna.sit.hotel.presentation.models;

import bg.tu_varna.sit.hotel.data.entities.Customer;
import bg.tu_varna.sit.hotel.data.entities.Hotel;
import bg.tu_varna.sit.hotel.data.entities.Room;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class CustomerModel implements EntityModel<Customer>{
    private Long id;
    private String egn;
    private String firstName;
    private String lastName;
    private String phone;
    private Timestamp createdAt;
    private String rating;
    private HotelModel hotel;

    public CustomerModel(){}

    public CustomerModel(Long id,String egn,String firstName,String lastName,String phone, Timestamp createdAt,String rating,HotelModel hotel)
    {
        this.id=id;
        this.egn=egn;
        this.firstName=firstName;
        this.lastName=lastName;
        this.phone=phone;
        this.createdAt=createdAt;
        this.rating=rating;
        this.hotel=hotel;
    }

    public CustomerModel(Customer customer)
    {
        this.id=customer.getId();
        this.egn=customer.getEgn();
        this.firstName=customer.getFirst_name();
        this.lastName=customer.getLast_name();
        this.phone=customer.getPhone();
        this.createdAt=customer.getCreatedAt();
        this.rating=customer.getRating();
        this.hotel=new HotelModel(customer.getHotel());
    }

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    public String getEgn() {return egn;}

    public void setEgn(String egn) {this.egn = egn;}

    public String getFirstName() {return firstName;}

    public void setFirstName(String firstName) {this.firstName = firstName;}

    public String getLastName() {return lastName;}

    public void setLastName(String lastName) {this.lastName = lastName;}

    public String getPhone() {return phone;}

    public void setPhone(String phone) {this.phone = phone;}

    public Timestamp getCreatedAt() {return createdAt;}

    public void setCreatedAt(Timestamp createdAt) {this.createdAt = createdAt;}

    public String getRating() {return rating;}

    public void setRating(String rating) {this.rating = rating;}

    public HotelModel getHotel() {return hotel;}

    public void setHotel(HotelModel hotel) {this.hotel = hotel;}


    @Override
    public Customer toEntity() {
        Customer customerTemp = new Customer();
        customerTemp.setId(this.id);
        customerTemp.setEgn(this.egn);
        customerTemp.setFirst_name(this.firstName);
        customerTemp.setLast_name(this.lastName);
        customerTemp.setPhone(this.phone);
        customerTemp.setCreatedAt(this.createdAt);
        customerTemp.setRating(this.rating);
        customerTemp.setHotel(this.hotel.toEntity());
        return customerTemp;
    }
}