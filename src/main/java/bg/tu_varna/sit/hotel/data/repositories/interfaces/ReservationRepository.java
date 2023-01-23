package bg.tu_varna.sit.hotel.data.repositories.interfaces;

import bg.tu_varna.sit.hotel.data.entities.Hotel;
import bg.tu_varna.sit.hotel.data.entities.Reservation;
import bg.tu_varna.sit.hotel.data.entities.Room;

import java.sql.Timestamp;
import java.util.List;

public interface ReservationRepository<T> extends Repository<T> {
    List<Room> getAllFreeRooms(Hotel hotel, Timestamp startDate, Timestamp endDate);
    Long getLastReservationNumberOfHotel(Hotel hotel);
    Reservation getReservationWithNumber(Long reservationNumber,Hotel hotel);
}