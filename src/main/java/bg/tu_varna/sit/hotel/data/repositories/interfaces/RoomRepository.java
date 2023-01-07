package bg.tu_varna.sit.hotel.data.repositories.interfaces;

import bg.tu_varna.sit.hotel.data.entities.Hotel;
import bg.tu_varna.sit.hotel.data.entities.Room;

import java.util.List;

public interface RoomRepository<T> extends Repository<T> {
    List<T> getAllRoomsOfHotel(Hotel hotel);
}