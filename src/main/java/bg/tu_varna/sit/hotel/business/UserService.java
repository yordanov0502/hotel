package bg.tu_varna.sit.hotel.business;

import bg.tu_varna.sit.hotel.data.entities.User;
import bg.tu_varna.sit.hotel.data.repositories.UserRepository;
import bg.tu_varna.sit.hotel.presentation.models.UserModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.List;
import java.util.stream.Collectors;

public class UserService {

    private final UserRepository repository = UserRepository.getInstance();

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
                        u.getLastName()
                )).collect(Collectors.toList())
        );
    }
}