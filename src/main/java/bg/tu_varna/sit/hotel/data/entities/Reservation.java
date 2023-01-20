package bg.tu_varna.sit.hotel.data.entities;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "reservations")
public class Reservation implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "reservation_id_generator")
    @SequenceGenerator(name="reservation_id_generator",sequenceName = "reservation_seq",allocationSize = 1)
    @Column(name = "id", nullable = false)
    private Long id;

    @Column(name = "numberr", nullable = false)
    private Integer number;//reservation number (per hotel)

    @Column(name = "type", nullable = false)
    private String type;//reservation type (direct/indirect)

    @Column(name = "finish_type", nullable = false)
    private String finishType;//(unfinished/normal/earlier/forced)

    @Column(name = "created_at", nullable = false)
    private Timestamp createdAt;//date and time when reservation was created

    @Column(name = "start_date", nullable = false)
    private Timestamp startDate;//first day of reservation

    @Column(name = "end_date", nullable = false)
    private Timestamp endDate;//last day of reservation

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)//allows "foreign key on cascade delete"(deletes all reservations when the hotel they were associated with is deleted)
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)//allows foreign key "receptionist_id" to remain in the table even if a user(receptionist) is removed from the users table
    @JoinColumn(name = "receptionist_id", nullable = false)
    private User receptionist;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)//allows "foreign key on cascade delete"(deletes all reservations when the customer they were associated with is deleted)
    @JoinColumn(name = "customer_id",  nullable = false)
    private Customer customer;

    @Column(name = "customer_rating", nullable = false)
    private String customerRating;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.NO_ACTION)//allows foreign key "room_id" to remain in the table even if a room is removed from the rooms table so customer rating would be available (for queries) (the reservation row will not be deleted)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @Column(name = "nights_occupied", nullable = false)
    private Integer nightsOccupied;

    @Column(name = "room_rating", nullable = false)
    private Double roomRating;

    @Column(name = "service_list", nullable = false)
    private String serviceList;

    @Column(name = "request_sent", nullable = false)
    private Boolean requestSent = false;


    public Boolean getRequestSent() {return requestSent;}

    public void setRequestSent(Boolean requestSent) {this.requestSent = requestSent;}

    public String getServiceList() {return serviceList;}

    public void setServiceList(String serviceList) {this.serviceList = serviceList;}

    public Double getRoomRating() {return roomRating;}

    public void setRoomRating(Double roomRating) {this.roomRating = roomRating;}

    public Integer getNightsOccupied() {return nightsOccupied;}

    public void setNightsOccupied(Integer nightsOccupied) {this.nightsOccupied = nightsOccupied;}

    public Room getRoom() {return room;}

    public void setRoom(Room room) {this.room = room;}

    public String getCustomerRating() {return customerRating;}

    public void setCustomerRating(String customerRating) {this.customerRating = customerRating;}

    public Customer getCustomer() {return customer;}

    public void setCustomer(Customer customer) {this.customer = customer;}

    public User getReceptionist() {return receptionist;}

    public void setReceptionist(User receptionist) {this.receptionist = receptionist;}

    public Hotel getHotel() {return hotel;}

    public void setHotel(Hotel hotel) {this.hotel = hotel;}

    public Timestamp getEndDate() {return endDate;}

    public void setEndDate(Timestamp endDate) {this.endDate = endDate;}

    public Timestamp getStartDate() {return startDate;}

    public void setStartDate(Timestamp startDate) {this.startDate = startDate;}

    public Timestamp getCreatedAt() {return createdAt;}

    public void setCreatedAt(Timestamp createdAt) {this.createdAt = createdAt;}

    public String getFinishType() {return finishType;}

    public void setFinishType(String finishType) {this.finishType = finishType;}

    public String getType() {return type;}

    public void setType(String type) {this.type = type;}

    public Integer getNumber() {return number;}

    public void setNumber(Integer number) {this.number = number;}

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}

}