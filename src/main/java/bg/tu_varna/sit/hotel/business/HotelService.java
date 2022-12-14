package bg.tu_varna.sit.hotel.business;

import bg.tu_varna.sit.hotel.common.AlertManager;
import bg.tu_varna.sit.hotel.data.entities.Hotel;
import bg.tu_varna.sit.hotel.data.entities.User;
import bg.tu_varna.sit.hotel.data.repositories.implementations.HotelRepositoryImpl;
import bg.tu_varna.sit.hotel.presentation.models.HotelModel;
import bg.tu_varna.sit.hotel.presentation.models.UserModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
        List<Hotel> hotels = hotelRepository.getAllHotels();

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
                            //h.getRooms()
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

    public ObservableList<HotelModel> getAllHotelsWithoutOwner() {
        List<Hotel> hotels = hotelRepository.getAllHotelsWithoutOwner();

        if(hotels.isEmpty()){return null;}

        else
        {
            return FXCollections.observableList(
                    hotels.stream().map(h -> new HotelModel(
                            h.getId(),
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

    public ObservableList<String> getAllHotelNamesWithoutOwner() {
        List<String> hotelsNames = hotelRepository.getAllHotelNamesWithoutOwner();

        if(hotelsNames.isEmpty()){return null;}

        else
        {
            return FXCollections.observableList(
                    new ArrayList<>(hotelsNames)
            );
        }
    }

    public ObservableList<HotelModel> getAllHotelsWithoutManager() {
        List<Hotel> hotels = hotelRepository.getAllHotelsWithoutManager();

        if(hotels.isEmpty()){return null;}

        else
        {
            return FXCollections.observableList(
                    hotels.stream().map(h -> new HotelModel(
                            h.getId(),
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

    public ObservableList<String> getAllHotelNamesWithoutManager() {
        List<String> hotelsNames = hotelRepository.getAllHotelNamesWithoutManager();

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
        if(!userModel.getRole().equals("??????????????????????????"))
        {
            if (hotelModel.getHasOwner() && userModel.getRole().equals("????????????????????"))
            {
                AlertManager.showAlert(Alert.AlertType.ERROR,"????????????","??? ?????????? \""+hotelModel.getName()+"\" ???????? ???? ?????? ????????????????????.");
                return false;
            }
            else if (hotelModel.getHasManager() && userModel.getRole().equals("????????????????"))
            {
                AlertManager.showAlert(Alert.AlertType.ERROR,"????????????","??? ?????????? \""+hotelModel.getName()+"\" ???????? ???? ?????? ????????????????.");
                return false;
            }
            else
            {
                if(!hotelModel.getHasOwner() && userModel.getRole().equals("????????????????????"))
                {
                    hotelModel.setHasOwner(true);
                }
                if(!hotelModel.getHasManager() && userModel.getRole().equals("????????????????"))
                {
                    hotelModel.setHasManager(true);
                }

                hotelModel.toEntity().getUsers().add(userModel.toEntity());//adds user to hotel's list of users
                userModel.toEntity().getHotels().add(hotelModel.toEntity());//adds hotel to user's list of hotels


                if(updateHotel(hotelModel) && UserService.getInstance().updateUser(userModel))
                {
                    log.info("Successfully added user \""+userModel.getFirstName()+" "+userModel.getLastName()+"\" to hotel's \""+hotelModel.getName()+"\" list of users and vice versa.");
                    AlertManager.showAlert(Alert.AlertType.INFORMATION,"????????????????????","??? ?????????????? ?????????????????? "+userModel.getRole()+" "+userModel.getFirstName()+" "+userModel.getLastName()+" ?????? ?????????? \""+hotelModel.getName()+"\".");
                    return true;
                }
                else
                {
                    log.info("Failed to add user \""+userModel.getFirstName()+" "+userModel.getLastName()+"\" to hotel's \""+hotelModel.getName()+"\" list of users and vice versa.");
                    AlertManager.showAlert(Alert.AlertType.ERROR,"????????????","??? ?????????????????? ???????????????? ???? "+userModel.getRole()+" "+userModel.getFirstName()+" "+userModel.getLastName()+" ?????? ?????????? \""+hotelModel.getName()+"\".");
                    return false;
                }
            }
        }
        else
        {
            AlertManager.showAlert(Alert.AlertType.ERROR,"????????????","??? ???? ???????? ???? ???????????????? ?????????????????????????? ???? ?????????????????? ???????? ???????????????? ???? ?????????? \""+hotelModel.getName()+"\".");
            return false;
        }
    }

    public void removeUser(HotelModel hotelModel,UserModel userModel,String stageTitle)
    {
           //removes user from hotel's list of users                                      //removes hotel from user's list of hotels
        if(hotelModel.toEntity().getUsers().removeIf(u -> u.getId().equals(userModel.getId())) && userModel.toEntity().getHotels().removeIf(h -> h.getName().equals(hotelModel.getName())) )
        {
            if (userModel.getRole().equals("????????????????????"))
            {
                hotelModel.setHasOwner(false);
            }
            if (userModel.getRole().equals("????????????????"))
            {
                hotelModel.setHasManager(false);
            }

            if(updateHotel(hotelModel) && UserService.getInstance().updateUser(userModel))
            {
                log.info("Successfully removed user \""+userModel.getFirstName()+" "+userModel.getLastName()+"\" from hotel's \""+hotelModel.getName()+"\" list of users and vice versa.");
                if(stageTitle.equals("Hotel Users Info") /*|| stageTitle.equals("Owner Hotel Users Info")*/)
                {
                    AlertManager.showAlert(Alert.AlertType.INFORMATION,"????????????????????","??? "+userModel.getFirstName()+" "+userModel.getLastName()+" ???????? ???? ?? "+userModel.getRole()+" ???? ?????????? \""+hotelModel.getName()+"\".");
                }
            }
            else
            {
                log.info("Failed to remove user \""+userModel.getFirstName()+" "+userModel.getLastName()+"\" from hotel's \""+hotelModel.getName()+"\" list of users and vice versa.");
                if(stageTitle.equals("Hotel Users Info") /*|| stageTitle.equals("Owner Hotel Users Info")*/)
                {
                    AlertManager.showAlert(Alert.AlertType.ERROR,"????????????","??? ???????????????????? ???? ???????????????????? ???? \""+userModel.getFirstName()+" "+userModel.getLastName()+"\" ???????? "+userModel.getRole()+" ???? ?????????? \""+hotelModel.getName()+"\" ?? ??????????????????.");
                }
            }
        }
        else
        {
            log.info("Failed to remove user \""+userModel.getFirstName()+" "+userModel.getLastName()+"\" from hotel's \""+hotelModel.getName()+"\" list of users and vice versa.");
            if(stageTitle.equals("Hotel Users Info") /*|| stageTitle.equals("Owner Hotel Users Info")*/)
            {
                AlertManager.showAlert(Alert.AlertType.ERROR,"????????????","??? ???????????????????? ???? ???????????????????? ???? \""+userModel.getFirstName()+" "+userModel.getLastName()+"\" ???????? "+userModel.getRole()+" ???? ?????????? \""+hotelModel.getName()+"\" ?? ??????????????????.");
            }
        }
    }

    public UserModel getHotelOwner(HotelModel hotelModel)
    {
        if(hotelModel.getHasOwner())
        {
            for(UserModel u : getAllHotelUsers(hotelModel))
            {
                if(u.getRole().equals("????????????????????"))
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