package bg.tu_varna.sit.hotel.data.repositories.interfaces;

import bg.tu_varna.sit.hotel.data.entities.User;

import java.util.List;

public interface UserRepository<T> extends Repository<T> {
    List<T> getAll();
    User getById(String id);
    User getByPhone(String phone);
    User getByUsername(String username);
    User getByEmail(String email);
    User getByUsernameAndPassword(String username, String password, String role);
    List<T> getAllByRole(String role);
    List<T> getAllConfirmedAdmins();
    List<T> getAllNewlyRegisteredAdmins();
    User getNewlyRegisteredAdminById(String id);
}