package bg.tu_varna.sit.hotel.business;

import bg.tu_varna.sit.hotel.application.Main;
import bg.tu_varna.sit.hotel.common.Constants;
import bg.tu_varna.sit.hotel.common.ViewManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private UserService userService;

    @BeforeEach
    void setUp() {userService = UserService.getInstance();}

    @Test
    void getInstance() {
        assertTrue(userService instanceof UserService);
        assertNotNull(userService);
    }

    @Test
    void usernameValidate() {
        assertTrue(UserService.getInstance().validateUsername("yordanov5.0"));
        assertTrue(UserService.getInstance().validateUsername("Zdravk000_1.05"));
        assertFalse(UserService.getInstance().validateUsername("mAri@ a.02"));
        assertFalse(UserService.getInstance().validateUsername("GEORGI#()-202"));
    }

    @Test
    void passwordValidate() {
        assertTrue(UserService.getInstance().validatePassword("awbfg_!?fA9"));
        assertTrue(UserService.getInstance().validatePassword("req_._2314A"));
        assertFalse(UserService.getInstance().validatePassword("mSIDi02 13@#$%8765"));
        assertFalse(UserService.getInstance().validatePassword("pPlK-54***1`~12©"));
    }

    @Test
    void emailValidate() {
        assertTrue(UserService.getInstance().validateEmail("yordanovtodor281@gmail.com"));
        assertTrue(UserService.getInstance().validateEmail("natoto01@abv.bg"));
        assertFalse(UserService.getInstance().validateEmail("ivanov24.com"));
        assertFalse(UserService.getInstance().validateEmail("john@com"));
    }

    @Test
    void phoneValidate() {
        assertTrue(UserService.getInstance().validatePhone("0882960178"));
        assertTrue(UserService.getInstance().validatePhone("0894735533"));
        assertFalse(UserService.getInstance().validatePhone("052500500"));
        assertFalse(UserService.getInstance().validatePhone("0521144 "));
    }

    @Test
    void idValidate() {
        assertTrue(UserService.getInstance().validateId("0146302585"));
        assertTrue(UserService.getInstance().validateId("1428063011"));
        assertFalse(UserService.getInstance().validateId("0100301025"));
        assertFalse(UserService.getInstance().validateId("0146345515"));
    }

    @Test
    void firstNameValidate() {
        assertTrue(UserService.getInstance().validateFirstName("Тодор"));
        assertTrue(UserService.getInstance().validateFirstName("Благовеста"));
        assertFalse(UserService.getInstance().validateFirstName("ЗАхари"));
        assertFalse(UserService.getInstance().validateFirstName("митко"));
        assertFalse(UserService.getInstance().validateFirstName("012321432903423"));
        assertFalse(UserService.getInstance().validateFirstName("popov"));
    }

    @Test
    void lastNameValidate() {
        assertTrue(UserService.getInstance().validateFirstName("Йорданов"));
        assertTrue(UserService.getInstance().validateFirstName("Енчева"));
        assertFalse(UserService.getInstance().validateFirstName("ГеоргиеВ"));
        assertFalse(UserService.getInstance().validateFirstName("димитров"));
        assertFalse(UserService.getInstance().validateFirstName("012321 432903423"));
        assertFalse(UserService.getInstance().validateLastName("popov"));
    }

    @Test
    void isIdExists()
    {
        assertFalse(userService.isIdExists("0000000000"));
        assertFalse(userService.isIdExists("9999999999"));
    }

    @Test
    void isPhoneExists()
    {
        assertFalse(userService.isPhoneExists("8882960194"));
        assertFalse(userService.isPhoneExists("359894735637"));
    }

    @Test
    void isUsernameExists()
    {
        assertFalse(userService.isUsernameExists("zdravko.O1@"));
        assertFalse(userService.isUsernameExists("user0123211 23"));
    }

    @Test
    void isEmailExists()
    {
        assertFalse(userService.isEmailExists("proba@abvbg"));
        assertFalse(userService.isEmailExists("georgigmail.com"));
    }

}