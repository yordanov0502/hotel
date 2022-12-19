package bg.tu_varna.sit.hotel.data.repositories.interfaces;

import bg.tu_varna.sit.hotel.data.entities.Hotel;

import java.util.List;

public interface HotelRepository<T> extends Repository<T>{
    List<T> getAll();
    Hotel getById(String id);
}