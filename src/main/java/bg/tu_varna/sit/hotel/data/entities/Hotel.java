package bg.tu_varna.sit.hotel.data.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "hotels")
public class Hotel implements Serializable {
    private static final long serialVersionUID = 1L;

    //https://stackoverflow.com/questions/24009042/org-hibernate-dialect-oracledialect-does-not-support-identity-key-generation
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "established_at", nullable = false)
    private Timestamp established_at;

    @Column(name = "stars", nullable = false)
    private Integer stars;

    @Column(name = "has_owner", nullable = false)
    private Boolean hasOwner = false;

    @Column(name = "has_manager", nullable = false)
    private Boolean hasManager = false;

    ////////////////////////////////////////////////////////////////////////
    @ManyToMany(mappedBy = "hotels")//
    private Set<User> users = new HashSet<>();

    public Set<User> getUsers(){
        return users;
    }

    public void addUser(User user) {
        users.add(user);
        user.getHotels().add(this);
    }
    ////////////////////////////////////////////////////////////////////////

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
}