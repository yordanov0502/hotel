package bg.tu_varna.sit.hotel.business;

import bg.tu_varna.sit.hotel.common.AlertManager;
import bg.tu_varna.sit.hotel.common.UserSession;
import bg.tu_varna.sit.hotel.data.entities.User;
import bg.tu_varna.sit.hotel.data.repositories.implementations.UserRepositoryImpl;
import bg.tu_varna.sit.hotel.presentation.models.UserModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
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

    public ObservableList<UserModel> getAllUsers() {
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
                            u.getHash(),
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

    public boolean addUser(UserModel userModel) {
        if(repository.save(userModel.toEntity()))
        {
            AlertManager.showAlert(Alert.AlertType.INFORMATION,"Информация","✅ Извършихте успешна регистрация.");
            return true;
        }
        else
        {
            AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","❌ Регистрацията ви е неуспешна.");
            return false;
        }
    }

    public boolean updateUser(UserModel userModel) {
        if(repository.update(userModel.toEntity()))
        {
            //AlertManager.showAlert(Alert.AlertType.INFORMATION,"Информация","✅ Извършихте успешно актуализиране на данни.");
            return true;
        }
        else
        {
            //AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","❌ Актуализирането на данни е неуспешно.");
            return false;
        }
    }

    public boolean deleteUser(UserModel userModel){
        if(repository.delete(userModel.toEntity()))
        {
            AlertManager.showAlert(Alert.AlertType.INFORMATION,"Информация","✅ Извършихте успешно изтриване на данни.");
            return true;
        }
        else
        {
            AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","❌ Изтриването на данни е неуспешно.");
            return false;
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
        String regex = "^[^0-9][a-zA-Z0-9._]{5,50}$";

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
    public boolean checkForExistingData(String [] fields) //checks for already existing data in the database
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
        User userTmp = repository.getByUsernameAndPassword(username, password, role);
        if(userTmp!=null){AlertManager.showAlert(Alert.AlertType.INFORMATION,"Информация","✅ Извършихте успешен вход в системата.");}
        else {AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","❌ "+role+" с подобно потребителско име или парола не съществува в системата.");}
        return userTmp != null;
    }

    public boolean checkForCorrectDataUpdate(String [] fields) //proverka pri redaktirane danni na user
    {
        if(fields.length==6)
        {
            if(Objects.equals(UserSession.getUser().getFirstName(), fields[0]) && Objects.equals(UserSession.getUser().getLastName(), fields[1]) && Objects.equals(UserSession.getUser().getPhone(), fields[2]) && Objects.equals(UserSession.getUser().getUsername(), fields[3]) && Objects.equals(UserSession.getUser().getEmail(), fields[4]) && Objects.equals(UserSession.getUser().getPassword(), fields[5]))
            {
                 return false;
            }
            else if(!Objects.equals(UserSession.getUser().getPhone(), fields[2]) && isPhoneExists(fields[2]))
            {
                AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","Мобилен номер: \""+fields[2]+"\" вече съществува в базата данни.");
                return false;
            }
            else if(!Objects.equals(UserSession.getUser().getUsername(), fields[3]) &&isUsernameExists(fields[3]))
            {
                AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","Потребителско име: \""+fields[3]+"\" вече съществува в базата данни.");
                return false;
            }
            else if(!Objects.equals(UserSession.getUser().getEmail(), fields[4]) &&isEmailExists(fields[4]))
            {
                AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","Имейл адрес: \""+fields[4]+"\" вече съществува в базата данни.");
                return false;
            }
            else return true;
        }
        else {return false;}
    }
}