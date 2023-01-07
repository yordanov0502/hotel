package bg.tu_varna.sit.hotel.presentation.models;

import bg.tu_varna.sit.hotel.data.entities.Room;
import bg.tu_varna.sit.hotel.data.entities.User;

public class RoomModel implements EntityModel<Room> {
    private Long id;
    private Integer number;
    private HotelModel hotel;
    private Double price;
    private String type;
    private Integer size;
    private Integer rating;

    public RoomModel(){}

    public RoomModel(Long id,Integer number,HotelModel hotel,Double price,String type,Integer size,Integer rating)
    {
      this.id=id;
      this.number=number;
      this.hotel=hotel;
      this.price=price;
      this.type=type;
      this.size=size;
      this.rating=rating;
    }

    public RoomModel(Room room)
    {
        this.id=room.getId();
        this.number=room.getNumber();
        this.hotel=new HotelModel(room.getHotel());
        this.price= room.getPrice();
        this.type= room.getType();
        this.size= room.getSize();
        this.rating= room.getRating();
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

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
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

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

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
        return roomTemp;
    }

}