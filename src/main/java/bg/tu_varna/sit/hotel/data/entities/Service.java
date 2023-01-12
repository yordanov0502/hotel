package bg.tu_varna.sit.hotel.data.entities;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "service")
public class Service implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "service_id_generator")
    @SequenceGenerator(name="service_id_generator",sequenceName = "service_seq",allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "times_used", nullable = false)
    private Integer times_used;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)//allows "foreign key on cascade delete"(deletes all services when the hotel they were associated with is deleted)
    @JoinColumn(name = "hotel_id",referencedColumnName = "id", nullable = false)
    private Hotel hotel;

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Integer getTimes_used() {
        return times_used;
    }

    public void setTimes_used(Integer times_used) {
        this.times_used = times_used;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}