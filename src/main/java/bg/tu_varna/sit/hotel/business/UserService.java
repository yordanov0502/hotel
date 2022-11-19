package bg.tu_varna.sit.hotel.business;

import bg.tu_varna.sit.hotel.data.entities.User;
import bg.tu_varna.sit.hotel.data.repositories.implementations.UserRepositoryImpl;
import bg.tu_varna.sit.hotel.presentation.models.UserModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.apache.log4j.Logger;

import java.util.List;
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


}