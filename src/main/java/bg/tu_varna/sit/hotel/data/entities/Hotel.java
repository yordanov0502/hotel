package bg.tu_varna.sit.hotel.data.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "hotels")
public class Hotel implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, length = 10)
    private Integer id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "established_at", nullable = false)
    private Timestamp established_at;

    @Column(name = "stars", nullable = false)
    private Integer stars;

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

    public Integer getId() {return id;}

    public void setId(Integer id) {this.id = id;}
}