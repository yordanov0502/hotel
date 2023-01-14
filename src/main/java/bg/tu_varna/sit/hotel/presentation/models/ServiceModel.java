package bg.tu_varna.sit.hotel.presentation.models;

import bg.tu_varna.sit.hotel.data.entities.Room;
import bg.tu_varna.sit.hotel.data.entities.Service;

public class ServiceModel implements EntityModel<Service>{
    private Long id;
    private String type;
    private String season;
    private Integer times_used;
    private HotelModel hotel;

    public ServiceModel(){}

    public ServiceModel(Long id,String type,String season,Integer times_used,HotelModel hotel)
    {
        this.id=id;
        this.type=type;
        this.season=season;
        this.times_used=times_used;
        this.hotel=hotel;
    }

    public ServiceModel(Service service)
    {
        this.id=service.getId();
        this.type=service.getType();
        this.season=service.getSeason();
        this.times_used=service.getTimes_used();
        this.hotel=new HotelModel(service.getHotel());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSeason() {return season;}

    public void setSeason(String season) {this.season = season;}

    public Integer getTimes_used() {
        return times_used;
    }

    public void setTimes_used(Integer times_used) {
        this.times_used = times_used;
    }

    public HotelModel getHotel() {
        return hotel;
    }

    public void setHotel(HotelModel hotel) {
        this.hotel = hotel;
    }


    @Override
    public Service toEntity() {
        Service serviceTemp = new Service();
        serviceTemp.setId(this.id);
        serviceTemp.setType(this.type);
        serviceTemp.setSeason(this.season);
        serviceTemp.setTimes_used(this.times_used);
        serviceTemp.setHotel(this.hotel.toEntity());
        return serviceTemp;
    }
}