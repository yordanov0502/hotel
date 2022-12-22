package bg.tu_varna.sit.hotel.business;

import bg.tu_varna.sit.hotel.data.entities.HotelsUsers;
import bg.tu_varna.sit.hotel.data.repositories.implementations.HotelsUsersRepositoryImpl;
import bg.tu_varna.sit.hotel.presentation.models.HotelsUsersModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.stream.Collectors;

public class HotelsUsersService {

    private static final Logger log = Logger.getLogger(HotelsUsersService.class);
    private final HotelsUsersRepositoryImpl hotelsUsersRepository = HotelsUsersRepositoryImpl.getInstance();

    //lazy-loaded singleton pattern
    public static HotelsUsersService getInstance() {
        return HotelsUsersService.HotelsUsersServiceHolder.INSTANCE;
    }

    private static class HotelsUsersServiceHolder {
        public static final HotelsUsersService INSTANCE = new HotelsUsersService();
    }

    public ObservableList<HotelsUsersModel> getAllHotelsUsers() {
        List<HotelsUsers> hotelsUsers = hotelsUsersRepository.getAll();

        if(hotelsUsers.isEmpty()){return null;}

        else
        {
            return FXCollections.observableList(
                    hotelsUsers.stream().map(hu -> new HotelsUsersModel(
                            hu.getUserId(),
                            hu.getHotelId()
                    )).collect(Collectors.toList())
            );
        }
    }

    public boolean addHotelsUsers(HotelsUsersModel hotelsUsersModel)
    {
        return hotelsUsersRepository.save(hotelsUsersModel.toEntity());
    }

    public boolean updateHotelsUsers(HotelsUsersModel hotelsUsersModel)
    {
        return hotelsUsersRepository.update(hotelsUsersModel.toEntity());
    }

    public boolean deleteHotelsUsers(HotelsUsersModel hotelsUsersModel)
    {
        return hotelsUsersRepository.delete(hotelsUsersModel.toEntity());
    }

}