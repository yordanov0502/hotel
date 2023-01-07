package bg.tu_varna.sit.hotel.business;

import bg.tu_varna.sit.hotel.data.entities.Room;
import bg.tu_varna.sit.hotel.data.entities.User;
import bg.tu_varna.sit.hotel.data.repositories.implementations.HotelRepositoryImpl;
import bg.tu_varna.sit.hotel.data.repositories.implementations.RoomRepositoryImpl;
import bg.tu_varna.sit.hotel.presentation.models.HotelModel;
import bg.tu_varna.sit.hotel.presentation.models.RoomModel;
import bg.tu_varna.sit.hotel.presentation.models.UserModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

public class RoomService {
    private static final Logger log = Logger.getLogger(RoomService.class);
    private final RoomRepositoryImpl roomRepository = RoomRepositoryImpl.getInstance();

    //lazy-loaded singleton pattern
    public static RoomService getInstance() {
        return RoomService.RoomServiceHolder.INSTANCE;
    }

    private static class RoomServiceHolder {
        public static final RoomService INSTANCE = new RoomService();
    }

    public ObservableList<RoomModel> getAllHotelRooms(HotelModel hotelModel) {
        List<Room> rooms = roomRepository.getAllRoomsOfHotel(hotelModel.toEntity());

        if(rooms.isEmpty()){return null;}

        else
        {
            return FXCollections.observableList(
                    rooms.stream().map(r -> new RoomModel(
                            r.getId(),
                            r.getNumber(),
                            new HotelModel(r.getHotel()),
                            r.getPrice(),
                            r.getType(),
                            r.getSize(),
                            r.getRating()
                    )).collect(Collectors.toList())
            );
        }
    }

    public boolean addRoom(RoomModel roomModel) {
        return roomRepository.save(roomModel.toEntity());
    }

    public boolean updateRoom(RoomModel roomModel) {
        return roomRepository.update(roomModel.toEntity());
    }

    public boolean deleteRoom(RoomModel roomModel){return roomRepository.delete(roomModel.toEntity());}
}