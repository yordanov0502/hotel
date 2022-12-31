package bg.tu_varna.sit.hotel.presentation.models;

import bg.tu_varna.sit.hotel.data.entities.Hotel;
import bg.tu_varna.sit.hotel.data.entities.User;

import java.sql.Timestamp;
import java.util.*;

public class UserModel implements EntityModel<User> {
    private String id;
    private String firstName;
    private String lastName;
    private String phone;
    private String username;
    private String email;
    private String password;
    private String hash;
    private String role;
    private Timestamp createdAt;
    private Timestamp lastLogin;
    private String status;
    private List<Hotel> hotels = new ArrayList<>();//////////////////////////////////////

    public UserModel(){}

    public UserModel(String id,String firstName,String lastName,String phone,String username,String email,String password,String hash,String role,Timestamp createdAt,Timestamp lastLogin,String status,List<Hotel> hotels)
    {
        this.id=id;
        this.firstName=firstName;
        this.lastName=lastName;
        this.phone=phone;
        this.username=username;
        this.email=email;
        this.password=password;
        this.hash=hash;
        this.role=role;
        this.createdAt=createdAt;
        this.lastLogin=lastLogin;
        this.status=status;
        this.hotels=hotels;///////////////////////////////////////////
    }

    public UserModel(User user) {
        this.id=user.getId();
        this.firstName=user.getFirstName();
        this.lastName=user.getLastName();
        this.phone=user.getPhone();
        this.username=user.getUsername();
        this.email=user.getEmail();
        this.password=user.getPassword();
        this.hash=user.getHash();
        this.role=user.getRole();
        this.createdAt=user.getCreatedAt();
        this.lastLogin=user.getLastLogin();
        this.status=user.getStatus();
        this.hotels=user.getHotels();
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getHash() {return hash;}

    public void setHash(String hash) {this.hash = hash;}

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Timestamp lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Hotel> getHotels() {return hotels;}////////////////////////////////////////////////

    public void setHotels(List<Hotel> hotels) {this.hotels = hotels;}//////////////////////////////

    @Override
    public User toEntity() {
        User userTemp = new User();
        userTemp.setId(this.id);
        userTemp.setFirstName(this.firstName);
        userTemp.setLastName(this.lastName);
        userTemp.setPhone(this.phone);
        userTemp.setUsername(this.username);
        userTemp.setEmail(this.email);
        userTemp.setPassword(this.password);
        userTemp.setHash(this.hash);
        userTemp.setRole(this.role);
        userTemp.setCreatedAt(this.createdAt);
        userTemp.setLastLogin(this.lastLogin);
        userTemp.setStatus(this.status);
        userTemp.setHotels(this.hotels);
        return userTemp;
    }
}