package bg.tu_varna.sit.hotel.data.repositories.interfaces;

import bg.tu_varna.sit.hotel.data.entities.Hotel;

import java.util.List;

public interface HotelRepository<T> extends Repository<T>{
    List<T> getAllHotels();
    List<T> getAllHotelsWithoutOwner();
    List<String> getAllHotelNamesWithoutOwner();
    List<T> getAllHotelsWithoutManager();
    List<String> getAllHotelNamesWithoutManager();
    Hotel getById(Long id);
    Hotel getByName(String name);
}