package bg.tu_varna.sit.hotel.business;

import bg.tu_varna.sit.hotel.common.AlertManager;
import bg.tu_varna.sit.hotel.common.UserSession;
import bg.tu_varna.sit.hotel.data.entities.Hotel;
import bg.tu_varna.sit.hotel.data.entities.User;
import bg.tu_varna.sit.hotel.data.repositories.implementations.UserRepositoryImpl;
import bg.tu_varna.sit.hotel.presentation.models.HotelModel;
import bg.tu_varna.sit.hotel.presentation.models.ReservationModel;
import bg.tu_varna.sit.hotel.presentation.models.UserModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import org.apache.log4j.Logger;
import org.apache.commons.lang3.StringUtils;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class UserService {
    private static final Logger log = Logger.getLogger(UserService.class);
    private final ReservationService reservationService = ReservationService.getInstance();
    private final UserRepositoryImpl userRepository = UserRepositoryImpl.getInstance();

    //lazy-loaded singleton pattern
    public static UserService getInstance() {
        return UserServiceHolder.INSTANCE;
    }

    private static class UserServiceHolder {
        public static final UserService INSTANCE = new UserService();
    }

    public ObservableList<UserModel> getAllByRole(String role) {
        List<User> users = userRepository.getAllByRole(role);

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

    public ObservableList<HotelModel> getAllHotelsOfUser(UserModel userModel) {
        List<Hotel> hotels = userModel.getHotels();

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

    public ObservableList<UserModel> getAllNewlyRegisteredAdmins() {
        List<User> users = userRepository.getAllNewlyRegisteredAdmins();

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

    public ObservableList<UserModel> getAllConfirmedAdmins() {
        List<User> users = userRepository.getAllConfirmedAdmins();

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


    public ObservableList<HotelModel> getAllHotelsOfOwnerWithoutManager(UserModel owner) {

        if(owner.getHotels().isEmpty()){return null;}

        else
        {
            List<Hotel> hotelsWithoutManager = new ArrayList<>();
            for(Hotel hotel:owner.getHotels())
            {
                if(hotel.getHasManager().equals(false))
                {
                    hotelsWithoutManager.add(hotel);
                }
            }

            if(hotelsWithoutManager.isEmpty()) {return null;}
            else
            {
                return FXCollections.observableList(
                        hotelsWithoutManager.stream().map(h -> new HotelModel(
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
    }

    public ObservableList<String> getAllHotelNamesOfOwnerWithoutManager(UserModel owner) {

        if(owner.getHotels().isEmpty()){return null;}

        else
        {
            List<String> hotelNamesWithoutManager = new ArrayList<>();
            for(Hotel hotel:owner.getHotels())
            {
                if(hotel.getHasManager().equals(false))
                {
                    hotelNamesWithoutManager.add(hotel.getName());
                }
            }

            if(hotelNamesWithoutManager.isEmpty()) {return null;}
            else {return FXCollections.observableList(new ArrayList<>(hotelNamesWithoutManager));}
        }
    }


    public UserModel getUserById(String id) {
        User user = userRepository.getById(id);
        return (user == null) ? null : new UserModel(user);
    }

    public boolean isIdExists(String id) {
        return getUserById(id) != null;
    }

    public UserModel getUserByPhone(String phone) {
        User user = userRepository.getByPhone(phone);
        return (user == null) ? null : new UserModel(user);
    }

    public boolean isPhoneExists(String phone) {
        return getUserByPhone(phone) != null;
    }

    public UserModel getUserByUsername(String username) {
        User user = userRepository.getByUsername(username);
        return (user == null) ? null : new UserModel(user);
    }

    public boolean isUsernameExists(String username) {
        return getUserByUsername(username) != null;
    }

    public UserModel getUserByEmail(String email) {
        User user = userRepository.getByEmail(email);
        return (user == null) ? null : new UserModel(user);
    }

    public boolean isEmailExists(String email) {
        return getUserByEmail(email) != null;
    }

    public UserModel getNewlyRegisteredAdminById(String id) {
        User user = userRepository.getNewlyRegisteredAdminById(id);
        return ((user) == null) ? null : new UserModel(user);
    }

    public boolean addUser(UserModel userModel) {
        return userRepository.save(userModel.toEntity());
    }

    public boolean updateUser(UserModel userModel) {
        return userRepository.update(userModel.toEntity());
    }

    public boolean deleteUser(UserModel userModel){

        //ako recepcionista sushtestvuva v tablica rezervacii
        if(userModel.getRole().equals("рецепционист") && reservationService.isReceptionistExistsInReservations(userModel.getId()))
        {
            Long hotelId = reservationService.getHotelIdByReceptionistId(userModel.getId());
            HotelModel hotelModel = HotelService.getInstance().getHotelById(hotelId);

            List<ReservationModel> reservationsWithSameReceptionistId = reservationService.getAllReservationsWithSameReceptionistId(userModel.getId(),hotelModel);
            List<ReservationModel> previousReservationModels = new LinkedList<>();

            if(reservationsWithSameReceptionistId ==null){return false;}


            if(!isIdExists("13"))//ako prazen recepcionist ne sushtestvuva
            {
                UserModel NULL_User_Model = new UserModel("13","изтрити данни","изтрити данни","изтрит","изтрити данни","изтрити данни","изтрити данни","изтрити данни","изтрити данни",new Timestamp(System.currentTimeMillis()),new Timestamp(System.currentTimeMillis()),"изтрити данни",new ArrayList<>());
                if(!addUser(NULL_User_Model))//ako praznq recepcionist ne se dobavi uspeshno
                {
                    log.info("ERROR when adding Null Receptionist.");
                    return false;
                }
                else
                {
                    log.info("Null Receptionist has been added successfully.");
                    for(ReservationModel reservationModel: reservationsWithSameReceptionistId)
                    {
                        previousReservationModels.add(reservationService.getReservationById(reservationModel.getId(),reservationModel.getHotel()));
                        reservationModel.setReceptionist(getUserById(NULL_User_Model.getId()));
                        if(!reservationService.updateReservation(reservationModel))
                        {
                            log.info("ERROR when updating receptionist_id of reservation with the 'Null Receptionist'.");
                            return false;
                        }
                    }
                }
            }
            else //ako prazen recepcionist veche otdavna sushtestvuva
            {
                UserModel NULL_User_Model = getUserById("13");
                for(ReservationModel reservationModel: reservationsWithSameReceptionistId)
                {
                    previousReservationModels.add(reservationService.getReservationById(reservationModel.getId(),reservationModel.getHotel()));
                    reservationModel.setReceptionist(NULL_User_Model);
                    if(!reservationService.updateReservation(reservationModel))
                    {
                        log.info("ERROR when updating receptionist_id of reservation with the 'Null Receptionist'.");
                        return false;
                    }
                }
            }

            if(!userRepository.delete(userModel.toEntity()))//ako recepcionista ne se iztrie uspeshno
            {
                for(int i = 0; i< reservationsWithSameReceptionistId.size(); i++)
                {
                    reservationService.updateReservation(previousReservationModels.get(i));
                }
                UserSession.unsuccessfulReceptionistDelete=true;
                addHotel(getUserById(userModel.getId()),HotelService.getInstance().getHotelById(hotelId));
                return false;
            }
            else {return true;}
        }

        return userRepository.delete(userModel.toEntity());
    }

    public boolean addHotel(UserModel userModel, HotelModel hotelModel)
    {
        if(!userModel.getRole().equals("администратор"))
        {
            if (hotelModel.getHasOwner() && userModel.getRole().equals("собственик"))
            {
                AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","❌ Хотел \""+hotelModel.getName()+"\" вече си има собственик.");
                return false;
            }
            else if (hotelModel.getHasManager() && userModel.getRole().equals("мениджър"))
            {
                AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","❌ Хотел \""+hotelModel.getName()+"\" вече си има мениджър.");
                return false;
            }
            else
            {
                if(!hotelModel.getHasOwner() && userModel.getRole().equals("собственик"))
                {
                    hotelModel.setHasOwner(true);
                }
                if(!hotelModel.getHasManager() && userModel.getRole().equals("мениджър"))
                {
                    hotelModel.setHasManager(true);
                }

                userModel.toEntity().getHotels().add(hotelModel.toEntity());//adds hotel to user's list of hotels
                hotelModel.toEntity().getUsers().add(userModel.toEntity());//////adds user to hotel's list of users


                if(updateUser(userModel) && HotelService.getInstance().updateHotel(hotelModel))
                {
                    log.info("Successfully added hotel \""+hotelModel.getName()+"\" to "+userModel.getFirstName()+" "+userModel.getLastName()+" list of hotels and vice versa.");
                    if(!UserSession.unsuccessfulReceptionistDelete)
                    {
                        AlertManager.showAlert(Alert.AlertType.INFORMATION,"Информация","✅ Успешно добавихте "+userModel.getRole()+" "+userModel.getFirstName()+" "+userModel.getLastName()+" към хотел \""+hotelModel.getName()+"\".");
                    }
                    else
                    {
                        UserSession.unsuccessfulReceptionistDelete=false;
                    }
                    return true;
                }
                else
                {
                    log.info("Failed to add hotel \""+hotelModel.getName()+"\" to "+userModel.getFirstName()+" "+userModel.getLastName()+" list of hotels and vice versa.");
                    AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","❌ Неуспешно добавяне на "+userModel.getRole()+" "+userModel.getFirstName()+" "+userModel.getLastName()+" към хотел \""+hotelModel.getName()+"\".");
                    return false;
                }
            }
        }
        else
        {
            AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","❌ Не може да добавяте администратор на системата като служител на хотел \""+hotelModel.getName()+"\".");
            return false;
        }
    }

    public boolean removeHotel(UserModel userModel,HotelModel hotelModel,String stageTitle)
    {
        //removes hotel from user's list of hotels                                        ////removes user from hotel's list of users
        if(userModel.toEntity().getHotels().removeIf(h -> h.getName().equals(hotelModel.getName())) && hotelModel.toEntity().getUsers().removeIf(u -> u.getId().equals(userModel.getId())))
        {
            if (userModel.getRole().equals("собственик"))
            {
                hotelModel.setHasOwner(false);
            }
            if (userModel.getRole().equals("мениджър"))
            {
                hotelModel.setHasManager(false);
            }

            if(updateUser(userModel) && HotelService.getInstance().updateHotel(hotelModel))
            {
                log.info("Successfully removed hotel \""+hotelModel.getName()+"\" from user's \""+userModel.getFirstName()+" "+userModel.getLastName()+"\" list of hotels and vice versa.");
                return true;
                // if(stageTitle.equals("Hotel Users Info") || stageTitle.equals("Owner Hotels Info"))
               // {
               //     AlertManager.showAlert(Alert.AlertType.INFORMATION,"Информация","✅ "+userModel.getFirstName()+" "+userModel.getLastName()+" вече не е "+userModel.getRole()+" на хотел \""+hotelModel.getName()+"\".");
               // }
            }
            else
            {
                log.info("Failed to remove hotel \""+hotelModel.getName()+"\" from user's \""+userModel.getFirstName()+" "+userModel.getLastName()+"\" list of hotels and vice versa.");
                return false;
                // if(stageTitle.equals("Hotel Users Info") || stageTitle.equals("Owner Hotels Info"))
                //{
                //    AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","❌ Операцията по премахване на \""+userModel.getFirstName()+" "+userModel.getLastName()+"\" като "+userModel.getRole()+" на хотел \""+hotelModel.getName()+"\" е неуспешна.");
                //}
            }
        }
        else
        {
            log.info("Failed to remove hotel \""+hotelModel.getName()+"\" from user's \""+userModel.getFirstName()+" "+userModel.getLastName()+"\" list of hotels and vice versa.");
            return false;
            // if(stageTitle.equals("Hotel Users Info") || stageTitle.equals("Owner Hotels Info"))
            //{
           //     AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","❌ Операцията по премахване на \""+userModel.getFirstName()+" "+userModel.getLastName()+"\" като "+userModel.getRole()+" на хотел \""+hotelModel.getName()+"\" е неуспешна.");
           // }
        }
    }

    public boolean validateFirstName(String firstName) {
        String regex = "^[\\u0410-\\u042F]{1}([\\u0430-\\u044F]{2,29})$";

        Pattern p = Pattern.compile(regex);
        if(firstName == null) {return false;}
        else
        {
            Matcher m = p.matcher(firstName);
            return m.matches();
        }
    }

    public boolean validateLastName(String lastName) {
        String regex = "^[\\u0410-\\u042F]{1}([\\u0430-\\u044F]{2,29})$";

        Pattern p = Pattern.compile(regex);
        if(lastName == null) {return false;}
        else
        {
            Matcher m = p.matcher(lastName);
            return m.matches();
        }
    }

    public boolean validateId(String id) {
        String regex = "^[0-9]{10}$";

        Pattern p = Pattern.compile(regex);
        if(id == null) {return false;}
        else
        {
            Matcher m = p.matcher(id);
            if(m.matches())//checks if the password matches the regex
            {
                String dayOfBirth = id.substring(4,6);
                int day = Integer.parseInt(dayOfBirth);
                String monthOfBirth = id.substring(2,4);
                int month = Integer.parseInt(monthOfBirth);
                return day != 0 && day <= 31 && month != 0;//checks if day is [1;31] and month>0
            }
            else {return false;}
        }
    }

    public boolean validatePhone(String phone) {
        String regex = "^[0]{1}([0-9]{9})$";

        Pattern p = Pattern.compile(regex);
        if(phone == null) {return false;}
        else
        {
            Matcher m = p.matcher(phone);
            return m.matches();
        }
    }

    public boolean validateUsername(String username) {
        String regex = "^[^0-9][a-zA-Z0-9._]{4,50}$";

        Pattern p = Pattern.compile(regex);
        if(username == null) {return false;}
        else
        {
            Matcher m = p.matcher(username);
            return m.matches();
        }
    }

    public boolean validateEmail(String email) {
        String regex = "^[\\w-\\.]{1,50}@([\\w-]{1,50}\\.)+[\\w-]{2,10}$";

        Pattern p = Pattern.compile(regex);
        if(email == null) {return false;}
        else
        {
            Matcher m = p.matcher(email);
            return m.matches();
        }
    }

    public boolean validatePassword(String password) {
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=_*~!)(./:;<>?{}|`',-])(?=\\S+$).{7,200}$";

        Pattern p = Pattern.compile(regex);
        if(password == null) {return false;}
        else if(StringUtils.isAsciiPrintable(password))//checks if the password contains only of ASCII symbols
        {
            Matcher m = p.matcher(password);
            return m.matches();//checks if the password matches the regex
        }
        else {return false;}
    }

    public boolean validateFields(String [] fields) //validate fields in registration and edit-info forms
    {
        if(Objects.equals(fields[0], "")||Objects.equals(fields[1], "")||Objects.equals(fields[2], "")||Objects.equals(fields[3], "")||Objects.equals(fields[4], "")||Objects.equals(fields[5], "")||Objects.equals(fields[6], ""))
        {
            AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","Моля въведете данни във всички полета.");
            return false;
        }
        else if(!validateFirstName(fields[0]))
        {
            AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","Името трябва да съдържа от 3 до 30 символа като започва с главна буква, последвана от малки букви на кирилица.");
            return false;
        }
        else if(!validateLastName(fields[1]))
        {
            AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","Фамилията трябва да съдържа от 3 до 30 символа като започва с главна буква, последвана от малки букви на кирилица.");
            return false;
        }
        else if(!validateId(fields[2]))
        {
            AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","ЕГН-то трябва да съдържа 10 цифри [0-9] и да бъде валидно.");
            return false;
        }
        else if(!validatePhone(fields[3]))
        {
            AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","Мобилният номер трябва да съдържа 10 цифри [0-9] като първата цифра задължително трябва да е \"0\".");
            return false;
        }
        else if(!validateUsername(fields[4]))
        {
            AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","Потребителското име трябва да съдържа от 5 до 50 символа. (малки и главни латински букви, цифри[0-9] както и символите (_) и (.))");
            return false;
        }
        else if(!validateEmail(fields[5]))
        {
            AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","Имейл адресът трябва да съдържа от 6 до 112 символа като зъдължително символите \"@\" и \".\" трябва да присъстват веднъж и преди и след тях да има други символи. (малки и главни латински букви, цифри[0-9]");
            return false;
        }
        else if(!validatePassword(fields[6]))
        {
            AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","Паролата трябва да съдържа от 7 до 200 символа като зъдължително трябва да има поне 1 малка и 1 главна латинска буква, както и поне 1 цифра [0-9] и 1 специален символ[@#$%^&+=_*~!)(./:;<>?{}|`',-].");
            return false;
        }
        else {return true;}
    }
    public boolean checkForExistingUserData(String [] fields) //checks for already existing user data in the database
    {
        if(fields.length==4) //proverka pri registraciq na user
        {
            if(isIdExists(fields[0]))
            {
                AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","ЕГН: \""+fields[0]+"\" вече съществува в базата данни.");
                return true;
            }
            else if(isPhoneExists(fields[1]))
            {
                AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","Мобилен номер: \""+fields[1]+"\" вече съществува в базата данни.");
                return true;
            }
            else if(isUsernameExists(fields[2]))
            {
                AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","Потребителско име: \""+fields[2]+"\" вече съществува в базата данни.");
                return true;
            }
            else if(isEmailExists(fields[3]))
            {
                AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","Имейл адрес: \""+fields[3]+"\" вече съществува в базата данни.");
                return true;
            }
            else {return false;}
        }
        else return true;
    }

    public boolean validateLoginFields(String [] fields) //validate fields in login forms
    {
        if (Objects.equals(fields[0], "") || Objects.equals(fields[1], ""))
        {
            AlertManager.showAlert(Alert.AlertType.ERROR, "Грешка", "Моля въведете данни във всички полета.");
            return false;
        }
        else if(!validateUsername(fields[0]))
        {
            AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","Потребителското име трябва да съдържа от 5 до 50 символа. (малки и главни латински букви, цифри[0-9] както и символите (_) и (.))");
            return false;
        }
        else if(!validatePassword(fields[1]))
        {
            AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","Паролата трябва да съдържа от 7 до 200 символа като зъдължително трябва да има поне 1 малка и 1 главна латинска буква, както и поне 1 цифра [0-9] и 1 специален символ[@#$%^&+=_*~!)(./:;<>?{}|`',-].");
            return false;
        }
        else {return true;}
    }

    public boolean authenticateUser(String username, String password, String role) {
        User userTmp = userRepository.getByUsernameAndPassword(username, password, role);
        if(userTmp==null){AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","❌ "+role+" с подобно потребителско име или парола не съществува в системата.");}
        return userTmp != null;
    }

    public boolean checkForCorrectPersonalDataUpdate(String [] fields) //proverka pri redaktirane na li4ni danni
    {
        if(fields.length==6)
        {
            if(Objects.equals(UserSession.user.getFirstName(), fields[0]) && Objects.equals(UserSession.user.getLastName(), fields[1]) && Objects.equals(UserSession.user.getPhone(), fields[2]) && Objects.equals(UserSession.user.getUsername(), fields[3]) && Objects.equals(UserSession.user.getEmail(), fields[4]) && Objects.equals(UserSession.user.getPassword(), fields[5]))
            {
                 return false;
            }
            else if(!Objects.equals(UserSession.user.getPhone(), fields[2]) && isPhoneExists(fields[2]))
            {
                AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","Мобилен номер: \""+fields[2]+"\" вече съществува в базата данни.");
                return false;
            }
            else if(!Objects.equals(UserSession.user.getUsername(), fields[3]) && isUsernameExists(fields[3]))
            {
                AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","Потребителско име: \""+fields[3]+"\" вече съществува в базата данни.");
                return false;
            }
            else if(!Objects.equals(UserSession.user.getEmail(), fields[4]) && isEmailExists(fields[4]))
            {
                AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","Имейл адрес: \""+fields[4]+"\" вече съществува в базата данни.");
                return false;
            }
            else return true;
        }
        else {return false;}
    }

    public boolean checkForCorrectUserDataUpdate(String [] fields, UserModel selectedUser) //proverka pri redaktirane danni na user
    {
        if(fields.length==6)
        {
            if(Objects.equals(selectedUser.getFirstName(), fields[0]) && Objects.equals(selectedUser.getLastName(), fields[1]) && Objects.equals(selectedUser.getPhone(), fields[2]) && Objects.equals(selectedUser.getUsername(), fields[3]) && Objects.equals(selectedUser.getEmail(), fields[4]) && Objects.equals(selectedUser.getPassword(), fields[5]))
            {
                return false;
            }
            else if(!Objects.equals(selectedUser.getPhone(), fields[2]) && isPhoneExists(fields[2]))
            {
                AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","Мобилен номер: \""+fields[2]+"\" вече съществува в базата данни.");
                return false;
            }
            else if(!Objects.equals(selectedUser.getUsername(), fields[3]) && isUsernameExists(fields[3]))
            {
                AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","Потребителско име: \""+fields[3]+"\" вече съществува в базата данни.");
                return false;
            }
            else if(!Objects.equals(selectedUser.getEmail(), fields[4]) && isEmailExists(fields[4]))
            {
                AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","Имейл адрес: \""+fields[4]+"\" вече съществува в базата данни.");
                return false;
            }
            else return true;
        }
        else {return false;}
    }

}