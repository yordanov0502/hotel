package bg.tu_varna.sit.hotel.data.repositories.interfaces;

import bg.tu_varna.sit.hotel.data.entities.Hotel;
import bg.tu_varna.sit.hotel.data.entities.Service;
import bg.tu_varna.sit.hotel.data.entities.User;

public interface ServiceRepository<T> extends Repository<T> {
    Service getByType(String type, Hotel hotel);
}