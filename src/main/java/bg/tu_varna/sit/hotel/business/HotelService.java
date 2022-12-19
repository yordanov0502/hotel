package bg.tu_varna.sit.hotel.business;

import bg.tu_varna.sit.hotel.data.entities.Hotel;
import bg.tu_varna.sit.hotel.data.repositories.implementations.HotelRepositoryImpl;
import bg.tu_varna.sit.hotel.presentation.models.HotelModel;
import org.apache.log4j.Logger;

public class HotelService {
    private static final Logger log = Logger.getLogger(HotelService.class);
    private final HotelRepositoryImpl hotelRepository = HotelRepositoryImpl.getInstance();

    //lazy-loaded singleton pattern
    public static HotelService getInstance() {
        return HotelServiceHolder.INSTANCE;
    }

    private static class HotelServiceHolder {
        public static final HotelService INSTANCE = new HotelService();
    }

    public HotelModel getHotelById(String id) {
        Hotel hotel = hotelRepository.getById(id);
        return (hotel == null) ? null : new HotelModel(hotel);
    }

    public boolean isIdExists(String id) {
        return getHotelById(id) != null;
    }

}