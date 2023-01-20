package bg.tu_varna.sit.hotel.business;

import bg.tu_varna.sit.hotel.data.repositories.implementations.HotelRepositoryImpl;
import bg.tu_varna.sit.hotel.data.repositories.implementations.ReservationRepositoryImpl;
import org.apache.log4j.Logger;

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
}