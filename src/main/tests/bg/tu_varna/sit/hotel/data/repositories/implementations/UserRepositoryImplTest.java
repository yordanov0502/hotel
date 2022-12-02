package bg.tu_varna.sit.hotel.data.repositories.implementations;


import bg.tu_varna.sit.hotel.business.UserService;
import bg.tu_varna.sit.hotel.common.Hasher;
import bg.tu_varna.sit.hotel.data.entities.User;
import bg.tu_varna.sit.hotel.data.repositories.interfaces.Repository;
import bg.tu_varna.sit.hotel.data.repositories.interfaces.UserRepository;
import bg.tu_varna.sit.hotel.presentation.models.UserModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryImplTest {

    private UserRepositoryImpl userRepository;

    @BeforeEach
    void setUp() {userRepository = userRepository.getInstance();}

    @Test
    void getInstance() {
        assertTrue(userRepository instanceof Repository);
        assertTrue(userRepository instanceof UserRepository);
        assertTrue(userRepository instanceof UserRepositoryImpl);
        assertNotNull(userRepository);
    }

    @Test
    void getAll() {
        assertNotNull(userRepository.getAll());
    }

    @Test
    void getById() {
        assertNull(userRepository.getById("1111111111"));
        assertNull(userRepository.getById("11111111112313312"));
        assertNull(userRepository.getById("1"));
        assertNull(userRepository.getById("dasd0923122312431ewtedw"));
        assertNull(userRepository.getById("1542"));
    }

    @Test
    void getByPhone() {
        assertNull(userRepository.getById("0921232111"));
        assertNull(userRepository.getById("11111111112313312"));
        assertNull(userRepository.getById("9823"));
        assertNull(userRepository.getById("dasdw"));
        assertNull(userRepository.getById("8998899889"));
    }

    @Test
    void getByUsername() {
        assertNull(userRepository.getById("example 89 ^$@#"));
        assertNull(userRepository.getById("phil18$"));
        assertNull(userRepository.getById("hello*"));
        assertNull(userRepository.getById("8888"));
        assertNull(userRepository.getById("john"));
    }

    @Test
    void getByEmail() {
        assertNull(userRepository.getById("email@dotcom"));
        assertNull(userRepository.getById("email* test!1"));
        assertNull(userRepository.getById("yahoo19194"));
    }

    @Test
    void getByUsernameAndPassword() {
        assertNull(userRepository.getByUsernameAndPassword("noth","1234","whatever"));
        assertNull(userRepository.getByUsernameAndPassword("","",""));
    }
}