package bg.tu_varna.sit.hotel.presentation.models;

import bg.tu_varna.sit.hotel.data.entities.Reservation;

import java.sql.Timestamp;

public class ReservationModel implements EntityModel<Reservation>{
    private Long id;
    private Long number;//number of reservation per hotel
    private String type;//reservation type (small/medium/big)
    private String status;//(not started/active/ended/completed)
    private Timestamp createdAt;//date and time when reservation was created
    private Timestamp startDate;//first day of reservation
    private Timestamp endDate;//last day of reservation
    private HotelModel hotel;
    private UserModel receptionist;
    private CustomerModel customer;
    private String customerRating;
    private RoomModel room;
    private Integer nightsOccupied;
    private Integer roomRating;
    private String serviceList;
    private Boolean notificationSent;
    private Timestamp finalAnnulationDate;//last date which reservation can be annulated for free
    private Long totalPrice;

    public ReservationModel(){}

    public ReservationModel(Long id, Long number, String type, String status, Timestamp createdAt, Timestamp startDate, Timestamp endDate, HotelModel hotel, UserModel receptionist, CustomerModel customer, String customerRating, RoomModel room, Integer nightsOccupied, Integer roomRating, String serviceList, Boolean notificationSent,Timestamp finalAnnulationDate,Long totalPrice)
    {
        this.id = id;
        this.number = number;
        this.type = type;
        this.status = status;
        this.createdAt = createdAt;
        this.startDate = startDate;
        this.endDate = endDate;
        this.hotel = hotel;
        this.receptionist = receptionist;
        this.customer = customer;
        this.customerRating = customerRating;
        this.room = room;
        this.nightsOccupied = nightsOccupied;
        this.roomRating = roomRating;
        this.serviceList = serviceList;
        this.notificationSent = notificationSent;
        this.finalAnnulationDate = finalAnnulationDate;
        this.totalPrice=totalPrice;
    }

    public ReservationModel(Reservation reservation)
    {
        this.id=reservation.getId();
        this.number=reservation.getNumber();
        this.type= reservation.getType();
        this.status=reservation.getStatus();
        this.createdAt=reservation.getCreatedAt();
        this.startDate=reservation.getStartDate();
        this.endDate=reservation.getEndDate();
        this.hotel=new HotelModel(reservation.getHotel());
        this.receptionist=new UserModel(reservation.getReceptionist());
        this.customer=new CustomerModel(reservation.getCustomer());
        this.customerRating=reservation.getCustomerRating();
        this.room=new RoomModel(reservation.getRoom());
        this.nightsOccupied=reservation.getNightsOccupied();
        this.roomRating=reservation.getRoomRating();
        this.serviceList=reservation.getServiceList();
        this.notificationSent =reservation.getNotificationSent();
        this.finalAnnulationDate=reservation.getFinalAnnulationDate();
        this.totalPrice= reservation.getTotalPrice();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumber() {
        return number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public HotelModel getHotel() {
        return hotel;
    }

    public void setHotel(HotelModel hotel) {
        this.hotel = hotel;
    }

    public UserModel getReceptionist() {
        return receptionist;
    }

    public void setReceptionist(UserModel receptionist) {
        this.receptionist = receptionist;
    }

    public CustomerModel getCustomer() {
        return customer;
    }

    public void setCustomer(CustomerModel customer) {
        this.customer = customer;
    }

    public String getCustomerRating() {
        return customerRating;
    }

    public void setCustomerRating(String customerRating) {
        this.customerRating = customerRating;
    }

    public RoomModel getRoom() {
        return room;
    }

    public void setRoom(RoomModel room) {
        this.room = room;
    }

    public Integer getNightsOccupied() {
        return nightsOccupied;
    }

    public void setNightsOccupied(Integer nightsOccupied) {
        this.nightsOccupied = nightsOccupied;
    }

    public Integer getRoomRating() {
        return roomRating;
    }

    public void setRoomRating(Integer roomRating) {
        this.roomRating = roomRating;
    }

    public String getServiceList() {
        return serviceList;
    }

    public void setServiceList(String serviceList) {
        this.serviceList = serviceList;
    }

    public Boolean getNotificationSent() {
        return notificationSent;
    }

    public void setNotificationSent(Boolean notificationSent) {
        this.notificationSent = notificationSent;
    }

    public Timestamp getFinalAnnulationDate() {return finalAnnulationDate;}

    public void setFinalAnnulationDate(Timestamp finalAnnulationDate) {this.finalAnnulationDate = finalAnnulationDate;}

    public Long getTotalPrice() {return totalPrice;}

    public void setTotalPrice(Long totalPrice) {this.totalPrice = totalPrice;}

    @Override
    public Reservation toEntity() {
        Reservation reservationTemp = new Reservation();
        reservationTemp.setId(this.id);
        reservationTemp.setNumber(this.number);
        reservationTemp.setType(this.type);
        reservationTemp.setStatus(this.status);
        reservationTemp.setCreatedAt(this.createdAt);
        reservationTemp.setStartDate(this.startDate);
        reservationTemp.setEndDate(this.endDate);
        reservationTemp.setHotel(this.hotel.toEntity());
        reservationTemp.setReceptionist(this.receptionist.toEntity());
        reservationTemp.setCustomer(this.customer.toEntity());
        reservationTemp.setCustomerRating(this.customerRating);
        reservationTemp.setRoom(this.room.toEntity());
        reservationTemp.setNightsOccupied(this.nightsOccupied);
        reservationTemp.setRoomRating(this.roomRating);
        reservationTemp.setServiceList(this.serviceList);
        reservationTemp.setNotificationSent(this.notificationSent);
        reservationTemp.setFinalAnnulationDate(this.finalAnnulationDate);
        reservationTemp.setTotalPrice(this.totalPrice);
        return reservationTemp;
    }

    //@Override
    //public boolean equals(Object o) {
    //    if (this == o) return true;
     //   if (o == null || getClass() != o.getClass()) return false;
    //    ReservationModel that = (ReservationModel) o;
     //   return id.equals(that.id);
    //}

    //@Override
  //  public int hashCode() {
    //    return Objects.hash(id);
    //}

}