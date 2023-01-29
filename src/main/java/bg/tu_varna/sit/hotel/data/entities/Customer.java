package bg.tu_varna.sit.hotel.data.entities;

import bg.tu_varna.sit.hotel.presentation.models.custom.CustomerRowModel;
import bg.tu_varna.sit.hotel.presentation.models.custom.ReservationRowModel;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "customers")
public class Customer implements Serializable{
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_id_generator")
    @SequenceGenerator(name="customer_id_generator",sequenceName = "customer_seq",allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "egn", nullable = false, length = 10)
    private String egn;//EGN

    @Column(name = "first_name", nullable = false)
    private String first_name;

    @Column(name = "last_name", nullable = false)
    private String last_name;

    @Column(name = "phone", nullable = false, length = 10)
    private String phone;

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;

    @Column(name = "rating", nullable = false, length = 30)
    private String rating;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)//allows "foreign key on cascade delete"(deletes all customers when the hotel they were associated with is deleted)
    @JoinColumn(name = "hotel_id",referencedColumnName = "id", nullable = false)
    private Hotel hotel;

    @Column(name = "nights_stayed", nullable = false)
    private Integer nightsStayed;//number of nights the customer stayed (calculated from all reserved rooms)

    public Integer getNightsStayed() {return nightsStayed;}

    public void setNightsStayed(Integer nightsStayed) {this.nightsStayed = nightsStayed;}

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public String getRating() {return rating;}

    public void setRating(String rating) {this.rating = rating;}

    public Timestamp getCreatedAt() {return createdAt;}

    public void setCreatedAt(Timestamp createdAt) {this.createdAt = createdAt;}

    public String getPhone() {return phone;}

    public void setPhone(String phone) {this.phone = phone;}

    public String getLast_name() {return last_name;}

    public void setLast_name(String last_name) {this.last_name = last_name;}

    public String getFirst_name() {return first_name;}

    public void setFirst_name(String first_name) {this.first_name = first_name;}

    public String getEgn() {return egn;}

    public void setEgn(String egn) {this.egn = egn;}

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}


}