package bg.tu_varna.sit.hotel.data.repositories.interfaces;

import bg.tu_varna.sit.hotel.data.entities.Hotel;
import bg.tu_varna.sit.hotel.data.entities.Reservation;
import bg.tu_varna.sit.hotel.data.entities.Room;
import bg.tu_varna.sit.hotel.data.entities.User;

import java.sql.Timestamp;
import java.util.List;

public interface ReservationRepository<T> extends Repository<T> {
    List<Room> getAllFreeRooms(Hotel hotel, Timestamp startDate, Timestamp endDate);
    Long getLastReservationNumberOfHotel(Hotel hotel);
    Reservation getReservationWithNumber(Long reservationNumber,Hotel hotel);
    List<T> getAllUncompletedReservationsOfHotel(Hotel hotel);
    List<T> getAllUncompletedReservationsOfHotelWithNumber(Long reservationNumber, Hotel hotel);
    List<T> getAllReservationsWithSameNumber(Long reservationNumber, Hotel hotel);
    List<Integer> getAllReservationsWithSameNumber_RoomsNumbers(Long reservationNumber, Hotel hotel);
    Long getRoomId(Long id);
    Long getHotelIdByRoomById(Long id);
    List<T> getAllReservationsWithSameRoomId(Long roomId, Hotel hotel);
    Reservation getReservationWithId(Long reservationId, Hotel hotel);
    String getReceptionistId(String id);
    List<Reservation> getAllReservationsWithSameReceptionistId(String id, Hotel hotel);
    Long getHotelIdByReceptionistById(String id);
    Long checkIfRoomCanBeEditedOrDeleted(Long roomId,Hotel hotel);
    List<T> getAllUncompletedReservationsOfCustomerById(Long customerId, Hotel hotel);
    List<T> getAllReservationsOfHotelWithoutExpiryNotification(Hotel hotel);
    List<T> getAllReservationsOfHotelWithExpiryNotification(Hotel hotel);
    List<T> getReservationsForPeriod(Hotel hotel,Timestamp startDate,Timestamp endDate);
    List<T> getAllReservationsOfReceptionist(User receptionist, Hotel hotel);
}