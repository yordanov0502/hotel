package bg.tu_varna.sit.hotel.data.entities;

import javax.persistence.*;
import java.io.Serializable;

//https://www.youtube.com/watch?v=CFWSjeIapy4&t=408s
@Entity
@Table(name = "HOTELS_USERS")
public class HotelsUsers implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "USER_ID")
    private String userId;

    @Id
    @Column(name = "HOTEL_ID")
    private Long hotelId;

    public String getUserId() {
        return this.userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Long getHotelId() {
        return this.hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }


}
