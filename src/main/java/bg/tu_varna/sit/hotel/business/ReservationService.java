package bg.tu_varna.sit.hotel.business;

import bg.tu_varna.sit.hotel.data.entities.Reservation;
import bg.tu_varna.sit.hotel.data.entities.Room;
import bg.tu_varna.sit.hotel.data.entities.User;
import bg.tu_varna.sit.hotel.data.repositories.implementations.HotelRepositoryImpl;
import bg.tu_varna.sit.hotel.data.repositories.implementations.ReservationRepositoryImpl;
import bg.tu_varna.sit.hotel.presentation.models.HotelModel;
import bg.tu_varna.sit.hotel.presentation.models.ReservationModel;
import bg.tu_varna.sit.hotel.presentation.models.RoomModel;
import bg.tu_varna.sit.hotel.presentation.models.UserModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.log4j.Logger;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

public class ReservationService {
    private static final Logger log = Logger.getLogger(ReservationService.class);
    private final ReservationRepositoryImpl reservationRepository = ReservationRepositoryImpl.getInstance();

    //lazy-loaded singleton pattern
    public static ReservationService getInstance() {
        return ReservationService.ReservationServiceHolder.INSTANCE;
    }

    private static class ReservationServiceHolder {
        public static final ReservationService INSTANCE = new ReservationService();
    }

    public ObservableList<RoomModel> getAllFreeRooms(HotelModel hotelModel, Timestamp startDate, Timestamp endDate) {
        List<Room> freeRooms = reservationRepository.getAllFreeRooms(hotelModel.toEntity(),startDate,endDate);


        if(freeRooms.isEmpty()){return null;}

        else
        {
            return FXCollections.observableList(
                    freeRooms.stream().map(r -> new RoomModel(
                            r.getId(),
                            r.getNumber(),
                            new HotelModel(r.getHotel()),
                            r.getPrice(),
                            r.getType(),
                            r.getSize(),
                            r.getRating(),
                            r.getNightsOccupied(),
                            r.getIsOccupied(),
                            r.getBeds()
                    )).collect(Collectors.toList())
            );
        }
    }

    public Long getLastReservationNumberOfHotel(HotelModel hotelModel) {
        return reservationRepository.getLastReservationNumberOfHotel(hotelModel.toEntity());
    }

    public ReservationModel getReservationWithNumber(Long reservationNumber, HotelModel hotelModel) {
        Reservation reservation = reservationRepository.getReservationWithNumber(reservationNumber,hotelModel.toEntity());
        return (reservation == null) ? null : new ReservationModel(reservation);
    }


    public boolean addReservation(ReservationModel reservationModel)
    {
        return reservationRepository.save(reservationModel.toEntity());
    }

    public boolean updateReservation(ReservationModel reservationModel)
    {
        return reservationRepository.update(reservationModel.toEntity());
    }

    public boolean deleteReservation(ReservationModel reservationModel)
    {
        return reservationRepository.delete(reservationModel.toEntity());
    }

}