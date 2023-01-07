package bg.tu_varna.sit.hotel.data.entities;

import bg.tu_varna.sit.hotel.business.HotelService;
import bg.tu_varna.sit.hotel.data.repositories.implementations.HotelRepositoryImpl;
import bg.tu_varna.sit.hotel.data.repositories.implementations.UserRepositoryImpl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.*;

@Entity
@Table(name = "hotels")
public class Hotel implements Serializable {
    private static final long serialVersionUID = 1L;

    //https://stackoverflow.com/questions/24009042/org-hibernate-dialect-oracledialect-does-not-support-identity-key-generation
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "hotel_id_generator")
    @SequenceGenerator(name="hotel_id_generator",sequenceName = "hotel_seq",allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "address", nullable = false, unique = true)
    private String address;

    @Column(name = "established_at", nullable = false)
    private Timestamp established_at;

    @Column(name = "stars", nullable = false)
    private Integer stars;

    @Column(name = "has_owner", nullable = false)
    private Boolean hasOwner = false;

    @Column(name = "has_manager", nullable = false)
    private Boolean hasManager = false;


    @ManyToMany(mappedBy = "hotels")
    private List<User> users = new ArrayList<>();

    //@OneToMany(mappedBy = "hotels", cascade = CascadeType.ALL)
    //private List<Room> rooms = new ArrayList<>();

   // public List<Room> getRooms() {
   //     return rooms;
   // }

   // public void setRooms(List<Room> rooms) {
   //     this.rooms = rooms;
   // }

    public List<User> getUsers(){
        return users;
    }

    public void setUsers(List<User> users) {this.users = users;}


    public Boolean getHasManager() {
        return hasManager;
    }

    public void setHasManager(Boolean hasManager) {
        this.hasManager = hasManager;
    }

    public Boolean getHasOwner() {
        return hasOwner;
    }

    public void setHasOwner(Boolean hasOwner) {
        this.hasOwner = hasOwner;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public Timestamp getEstablished_at() {
        return established_at;
    }

    public void setEstablished_at(Timestamp established_at) {
        this.established_at = established_at;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

    @Override
    public String toString() {
        return "Hotel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", established_at=" + established_at +
                ", stars=" + stars +
                ", hasOwner=" + hasOwner +
                ", hasManager=" + hasManager +
               // ", users=" + users +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Hotel hotel = (Hotel) o;
        return id.equals(hotel.id) && name.equals(hotel.name) && address.equals(hotel.address) && established_at.equals(hotel.established_at) && stars.equals(hotel.stars) && hasOwner.equals(hotel.hasOwner) && hasManager.equals(hotel.hasManager) && users.equals(hotel.users);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, address, established_at, stars, hasOwner, hasManager, users);
    }
}