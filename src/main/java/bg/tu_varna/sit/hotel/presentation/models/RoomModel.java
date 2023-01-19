package bg.tu_varna.sit.hotel.presentation.models;

import bg.tu_varna.sit.hotel.data.entities.Room;
import bg.tu_varna.sit.hotel.data.entities.User;

public class RoomModel implements EntityModel<Room> {
    private Long id;
    private Integer number;
    private HotelModel hotel;
    private Integer price;
    private String type;
    private Integer size;
    private Double rating;
    private Integer daysOccupied;

    public RoomModel(){}

    public RoomModel(Long id,Integer number,HotelModel hotel,Integer price,String type,Integer size,Double rating,Integer daysOccupied)
    {
      this.id=id;
      this.number=number;
      this.hotel=hotel;
      this.price=price;
      this.type=type;
      this.size=size;
      this.rating=rating;
      this.daysOccupied=daysOccupied;
    }

    public RoomModel(Room room)
    {
        this.id=room.getId();
        this.number=room.getNumber();
        this.hotel=new HotelModel(room.getHotel());
        this.price=room.getPrice();
        this.type=room.getType();
        this.size=room.getSize();
        this.rating=room.getRating();
        this.daysOccupied=room.getDaysOccupied();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public HotelModel getHotel() {
        return hotel;
    }

    public void setHotel(HotelModel hotel) {
        this.hotel = hotel;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Double getRating() {return rating;}

    public void setRating(Double rating) {this.rating = rating;}

    public Integer getDaysOccupied() {return daysOccupied;}

    public void setDaysOccupied(Integer daysOccupied) {this.daysOccupied = daysOccupied;}

    @Override
    public Room toEntity() {
        Room roomTemp = new Room();
        roomTemp.setId(this.id);
        roomTemp.setNumber(this.number);
        roomTemp.setHotel(this.hotel.toEntity());
        roomTemp.setPrice(this.price);
        roomTemp.setType(this.type);
        roomTemp.setSize(this.size);
        roomTemp.setRating(this.rating);
        roomTemp.setDaysOccupied(this.daysOccupied);
        return roomTemp;
    }

}