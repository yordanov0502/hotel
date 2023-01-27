package bg.tu_varna.sit.hotel.presentation.models.custom;

import java.util.Objects;

//this model class is used to combine multiple reservations with same number in one whole reservation
public class ReservationRowModel {
    private Long number;//number of reservation per hotel
    private String type;//reservation type (small/medium/big)
    private String status;//(not started/active/ended)
    private Integer rooms;//total rooms of a reservation
    private String customerFullName;

    public ReservationRowModel(){}

    public ReservationRowModel(Long number, String type, String status, Integer rooms, String customerFullName)
    {
        this.number = number;
        this.type = type;
        this.status = status;
        this.rooms = rooms;
        this.customerFullName = customerFullName;
    }

    public Long getNumber() {return number;}

    public void setNumber(Long number) {this.number = number;}

    public String getType() {return type;}

    public void setType(String type) {this.type = type;}

    public String getStatus() {return status;}

    public void setStatus(String status) {this.status = status;}

    public Integer getRooms() {return rooms;}

    public void setRooms(Integer rooms) {this.rooms = rooms;}

    public String getCustomerFullName() {return customerFullName;}

    public void setCustomerFullName(String customerFullName) {this.customerFullName = customerFullName;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReservationRowModel that = (ReservationRowModel) o;
        return number.equals(that.number);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number);
    }
}