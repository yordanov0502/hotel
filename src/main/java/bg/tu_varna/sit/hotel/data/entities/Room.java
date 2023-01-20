package bg.tu_varna.sit.hotel.data.entities;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "rooms")
public class Room implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "room_id_generator")
    @SequenceGenerator(name="room_id_generator",sequenceName = "room_seq",allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "numberr", nullable = false)
    private Integer number;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)//allows "foreign key on cascade delete"(deletes all rooms when the hotel they were associated with is deleted)
    @JoinColumn(name = "hotel_id",referencedColumnName = "id", nullable = false)
    private Hotel hotel;

    @Column(name = "price", nullable = false)
    private Integer price;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "sizee", nullable = false)
    private Integer size;

    @Column(name = "rating", nullable = false)
    private Double rating;

    @Column(name = "days_occupied", nullable = false)
    private Integer daysOccupied;

    @Column(name = "is_occupied", nullable = false)
    private Boolean isOccupied = false;

    public Boolean getIsOccupied() {return isOccupied;}

    public void setIsOccupied(Boolean isOccupied) {this.isOccupied = isOccupied;}

    public Integer getDaysOccupied() {return daysOccupied;}

    public void setDaysOccupied(Integer daysOccupied) {this.daysOccupied = daysOccupied;}

    public Double getRating() {return rating;}

    public void setRating(Double rating) {this.rating = rating;}

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}