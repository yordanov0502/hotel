package bg.tu_varna.sit.hotel.business;

import bg.tu_varna.sit.hotel.data.entities.User;
import bg.tu_varna.sit.hotel.data.repositories.implementations.UserRepositoryImpl;
import bg.tu_varna.sit.hotel.presentation.models.UserModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.log4j.Logger;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class UserService {
    private static final Logger log = Logger.getLogger(UserService.class);

    private final UserRepositoryImpl repository = UserRepositoryImpl.getInstance();

    //lazy-loaded singleton pattern
    public static UserService getInstance() {
        return UserServiceHolder.INSTANCE;
    }

    private static class UserServiceHolder {
        public static final UserService INSTANCE = new UserService();
    }

    public ObservableList<UserModel> getAllUser() {
        List<User> users = repository.getAll();

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
                            u.getRole(),
                            u.getCreatedAt(),
                            u.getLastLogin(),
                            u.getStatus()
                    )).collect(Collectors.toList())
            );
        }
    }

    public UserModel getUserById(String id) {
        User user = repository.getById(id);
        return (user == null) ? null : new UserModel(user);
    }

    public boolean isIdExists(String id) {
        return getUserById(id) != null;
    }

    public UserModel getUserByPhone(String phone) {
        User user = repository.getByPhone(phone);
        return (user == null) ? null : new UserModel(user);
    }

    public boolean isPhoneExists(String phone) {
        return getUserByPhone(phone) != null;
    }

    public UserModel getUserByUsername(String username) {
        User user = repository.getByUsername(username);
        return (user == null) ? null : new UserModel(user);
    }

    public boolean isUsernameExists(String username) {
        return getUserByUsername(username) != null;
    }

    public UserModel getUserByEmail(String email) {
        User user = repository.getByEmail(email);
        return (user == null) ? null : new UserModel(user);
    }

    public boolean isEmailExists(String email) {
        return getUserByEmail(email) != null;
    }

    public int hotelAdd(UserModel userModel) {
        repository.save(userModel.toEntity());
        return 0;
    }

    public int userUpdate(UserModel userModel) {
        repository.update(userModel.toEntity());
        return 0;
    }

    public int userDelete(UserModel userModel){
        repository.delete(userModel.toEntity());
        return 0;
    }

    public boolean userAuthentication(String username, String password) {
        User userTmp = repository.getByUsernameAndPassword(username, password);
        return userTmp != null;
    }


    public boolean usernameValidate(String username) {
        String regex = "^[^0-9][a-zA-Z0-9._]{5,200}$";

        Pattern p = Pattern.compile(regex);
        if(username == null) {return false;}
        else
        {
            Matcher m = p.matcher(username);
            return m.matches();
        }
    }

    public boolean passwordValidate(String password) {
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

    public boolean emailValidate(String email) {
        String regex = "^[\\w-\\.]{1,50}@([\\w-]{1,50}\\.)+[\\w-]{2,10}$";

        Pattern p = Pattern.compile(regex);
        if(email == null) {return false;}
        else
        {
            Matcher m = p.matcher(email);
            return m.matches();
        }
    }

    public boolean phoneValidate(String phone) {
        String regex = "^[0]{1}([0-9]{9})$";

        Pattern p = Pattern.compile(regex);
        if(phone == null) {return false;}
        else
        {
            Matcher m = p.matcher(phone);
            return m.matches();
        }
    }

    public boolean idValidate(String id) {
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

    public boolean firstNameValidate(String firstName) {
        String regex = "^[\\u0410-\\u042F]{1}([\\u0430-\\u044F]{2,50})$";

        Pattern p = Pattern.compile(regex);
        if(firstName == null) {return false;}
        else
        {
            Matcher m = p.matcher(firstName);
            return m.matches();
        }
    }

    public boolean lastNameValidate(String lastName) {
        String regex = "^[\\u0410-\\u042F]{1}([\\u0430-\\u044F]{2,50})$";

        Pattern p = Pattern.compile(regex);
        if(lastName == null) {return false;}
        else
        {
            Matcher m = p.matcher(lastName);
            return m.matches();
        }
    }

    public boolean emptyFieldsValidate(UserModel user) //checks for empty fields in registration forms
    {
        if(Objects.equals(user.getFirstName(), "")||Objects.equals(user.getLastName(), "")||Objects.equals(user.getId(), "")||Objects.equals(user.getPhone(), "")||Objects.equals(user.getUsername(), "")||Objects.equals(user.getEmail(), "")||Objects.equals(user.getPassword(), ""))
        {
            return false;
        }
        else{return true;}
    }
}