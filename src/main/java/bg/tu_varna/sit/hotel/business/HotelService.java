package bg.tu_varna.sit.hotel.business;

import bg.tu_varna.sit.hotel.common.AlertManager;
import bg.tu_varna.sit.hotel.common.Constants;
import bg.tu_varna.sit.hotel.common.ViewManager;
import bg.tu_varna.sit.hotel.data.entities.Hotel;
import bg.tu_varna.sit.hotel.data.entities.User;
import bg.tu_varna.sit.hotel.data.repositories.implementations.HotelRepositoryImpl;
import bg.tu_varna.sit.hotel.presentation.models.HotelModel;
import bg.tu_varna.sit.hotel.presentation.models.UserModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.SubScene;
import javafx.scene.control.Alert;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

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

    public ObservableList<HotelModel> getAllHotels() {
        List<Hotel> hotels = hotelRepository.getAll();

        if(hotels.isEmpty()){return null;}

        else
        {
            return FXCollections.observableList(
                    hotels.stream().map(h -> new HotelModel(
                            /**/h.getId(),
                            h.getName(),
                            h.getAddress(),
                            h.getEstablished_at(),
                            h.getStars(),
                            h.getHasOwner(),
                            h.getHasManager(),
                            h.getUsers()
                    )).collect(Collectors.toList())
            );
        }
    }

    public ObservableList<UserModel> getAllHotelUsers(HotelModel hotelModel) {
        List<User> users = hotelModel.getUsers();

        if(users.isEmpty()){return null;}

        else
        {
            return FXCollections.observableList(
                    users.stream().map(u -> new UserModel(
                            u.getId(),
                            u.getFirstName(),
                            u.getLastName(),
                            u.getPhone(),
                            u.getUsername(),
                            u.getEmail(),
                            u.getPassword(),
                            u.getHash(),
                            u.getRole(),
                            u.getCreatedAt(),
                            u.getLastLogin(),
                            u.getStatus(),
                            u.getHotels()
                    )).collect(Collectors.toList())
            );
        }
    }

    public ObservableList<HotelModel> getAllVacantHotels() {
        List<Hotel> hotels = hotelRepository.getAllVacant();

        if(hotels.isEmpty()){return null;}

        else
        {
            return FXCollections.observableList(
                    hotels.stream().map(h -> new HotelModel(
                            /**/h.getId(),
                            h.getName(),
                            h.getAddress(),
                            h.getEstablished_at(),
                            h.getStars(),
                            h.getHasOwner(),
                            h.getHasManager(),
                            h.getUsers()
                    )).collect(Collectors.toList())
            );
        }
    }

    public ObservableList<String> getAllVacantHotelsNames() {
        List<String> hotelsNames = hotelRepository.getAllVacantNames();

        if(hotelsNames.isEmpty()){return null;}

        else
        {
            return FXCollections.observableList(
                    new ArrayList<>(hotelsNames)
            );
        }
    }

    public HotelModel getHotelById(Long id) {
        Hotel hotel = hotelRepository.getById(id);
        return (hotel == null) ? null : new HotelModel(hotel);
    }

    public boolean isIdExists(Long id) {
        return getHotelById(id) != null;
    }

    public HotelModel getHotelByName(String name) {
        Hotel hotel = hotelRepository.getByName(name);
        return (hotel == null) ? null : new HotelModel(hotel);
    }

    public boolean isNameExists(String name) {
        return getHotelByName(name) != null;
    }



    public boolean addHotel(HotelModel hotelModel) {return hotelRepository.save(hotelModel.toEntity());}

    public boolean updateHotel(HotelModel hotelModel) {
        return hotelRepository.update(hotelModel.toEntity());
    }

    public boolean deleteHotel(HotelModel hotelModel){return hotelRepository.delete(hotelModel.toEntity());}

    public boolean addUser(HotelModel hotelModel,UserModel userModel)
    {
        hotelModel.toEntity().getUsers().add(userModel.toEntity());//adds user in hotel's set of users
        if(updateHotel(hotelModel))
        {
            log.info("Successfully added user to hotel's set of users.");
            AlertManager.showAlert(Alert.AlertType.INFORMATION,"Информация","✅ Успешно добавихте "+userModel.getRole()+" "+userModel.getFirstName()+" "+userModel.getLastName()+" към хотел \""+hotelModel.getName()+"\".");
            return true;
        }
        else
        {
            log.info("Failed to add user to hotel's set of users.");
            AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","❌ Неуспешно добавяне на "+userModel.getRole()+" "+userModel.getFirstName()+" "+userModel.getLastName()+" към хотел \""+hotelModel.getName()+"\".");
            return false;
        }
    }

    public UserModel getHotelOwner(HotelModel hotelModel)
    {
        if(hotelModel.getHasOwner())
        {
            for(UserModel u : getAllHotelUsers(hotelModel))
            {
                if(u.getRole().equals("собственик"))
                {
                    return u;
                }
            }
        }
        return null;
    }

    public boolean validateName(String hotelName) {
        String regex = "^[\\u0410-\\u042F OR A-Z]{1}([\\u0430-\\u044F OR \\u0410-\\u042F OR a-zA-Z0-9._]{2,60})$";

        Pattern p = Pattern.compile(regex);
        if(hotelName == null) {return false;}
        else
        {
            Matcher m = p.matcher(hotelName);
            return m.matches();
        }
    }



   // public void printAllUsersInformation(HotelModel hotelModel)
   // {
   //     for(User user : hotelModel.getUsers())
    //    {
    //        System.out.println(user.toString());
   //     }
   // }

}