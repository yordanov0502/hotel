package bg.tu_varna.sit.hotel.data.entities;

import bg.tu_varna.sit.hotel.presentation.models.*;
import bg.tu_varna.sit.hotel.presentation.models.custom.ReservationRowModel;
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
    private Long number;//reservation number (per hotel)

    @Column(name = "type", nullable = false)
    private String type;//reservation type (small/medium/big)

    @Column(name = "status", nullable = false)
    private String status;//(not started/active/ended/completed)

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
    @OnDelete(action = OnDeleteAction.CASCADE)//* when a reservation is about to be deleted because we deleted a receptionist, the receptionist_id FK is updated to a FK which points to a specific receptionist_id which does not have any data(this is done in order to avoid deleting reservation row when trying to delete receptionist_id FK) - however the receptionist which we tried to delete from the database will be deleted(if everything is OK)
    @JoinColumn(name = "receptionist_id", nullable = false)
    private User receptionist;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @Column(name = "customer_rating", nullable = false)
    private String customerRating;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)//* when a reservation is about to be deleted because we deleted a room, the room_id FK is updated to a FK which points to a specific room_id which does not have any data(this is done in order to avoid deleting reservation row when trying to delete room_id FK) - however the room which we tried to delete from the database will be deleted(if everything is OK)
    @JoinColumn(name = "room_id", nullable = false)
    private Room room;

    @Column(name = "nights_occupied", nullable = false)
    private Integer nightsOccupied;

    @Column(name = "room_rating", nullable = false)
    private Integer roomRating;

    @Column(name = "service_list", nullable = false)
    private String serviceList;

    @Column(name = "request_sent", nullable = false)
    private Boolean requestSent = false;

    @Column(name = "final_annulation_date", nullable = false)
    private Timestamp finalAnnulationDate;

    @Column(name = "total_price", nullable = false)
    private Long totalPrice;

    public Long getTotalPrice() {return totalPrice;}

    public void setTotalPrice(Long totalPrice) {this.totalPrice = totalPrice;}

    public Timestamp getFinalAnnulationDate() {return finalAnnulationDate;}

    public void setFinalAnnulationDate(Timestamp finalAnnulationDate) {this.finalAnnulationDate = finalAnnulationDate;}

    public Boolean getRequestSent() {return requestSent;}

    public void setRequestSent(Boolean requestSent) {this.requestSent = requestSent;}

    public String getServiceList() {return serviceList;}

    public void setServiceList(String serviceList) {this.serviceList = serviceList;}

    public Integer getRoomRating() {return roomRating;}

    public void setRoomRating(Integer roomRating) {this.roomRating = roomRating;}

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

    public String getStatus() {return status;}

    public void setStatus(String status) {this.status = status;}

    public String getType() {return type;}

    public void setType(String type) {this.type = type;}

    public Long getNumber() {return number;}

    public void setNumber(Long number) {this.number = number;}

    public Long getId() {return id;}

    public void setId(Long id) {this.id = id;}


    public ReservationRowModel toReservationRowModel(Integer rooms){
        ReservationRowModel reservationRowModel = new ReservationRowModel();
        reservationRowModel.setNumber(this.number);
        reservationRowModel.setType(this.type);
        reservationRowModel.setStatus(this.status);
        reservationRowModel.setRooms(rooms);
        reservationRowModel.setCustomerFullName(this.customer.getFirst_name()+" "+this.customer.getLast_name());
        return reservationRowModel;
    }

    public ReservationModel toModel(){
        ReservationModel reservationModel = new ReservationModel();
        reservationModel.setId(this.id);
        reservationModel.setNumber(this.number);
        reservationModel.setType(this.type);
        reservationModel.setStatus(this.status);
        reservationModel.setCreatedAt(this.createdAt);
        reservationModel.setStartDate(this.startDate);
        reservationModel.setEndDate(this.endDate);
        reservationModel.setHotel(new HotelModel(this.hotel));
        reservationModel.setReceptionist(new UserModel(this.receptionist));
        reservationModel.setCustomer(new CustomerModel(this.customer));
        reservationModel.setCustomerRating(this.customerRating);
        reservationModel.setRoom(new RoomModel(this.room));
        reservationModel.setNightsOccupied(this.nightsOccupied);
        reservationModel.setRoomRating(this.roomRating);
        reservationModel.setServiceList(this.serviceList);
        reservationModel.setRequestSent(this.requestSent);
        reservationModel.setFinalAnnulationDate(this.finalAnnulationDate);
        reservationModel.setTotalPrice(this.totalPrice);
        return reservationModel;
    }

}