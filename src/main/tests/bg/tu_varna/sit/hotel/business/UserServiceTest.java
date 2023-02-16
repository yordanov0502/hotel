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
        assertTrue(userService.validateUsername("yordanov5.0"));
        assertTrue(userService.validateUsername("Zdravk000_1.05"));
        assertFalse(userService.validateUsername("mAri@ a.02"));
        assertFalse(userService.validateUsername("GEORGI#()-202"));
        assertFalse(userService.validateUsername(""));
        assertFalse(userService.validateUsername("    dsda"));
        assertFalse(userService.validateUsername("     ._9da       "));
    }

    @Test
    void passwordValidate() {
        assertTrue(userService.validatePassword("awbfg_!?fA9"));
        assertTrue(userService.validatePassword("req_._2314A"));
        assertFalse(userService.validatePassword("mSIDi02 13@#$%8765"));
        assertFalse(userService.validatePassword("pPlK-54***1`~12©"));
        assertFalse(userService.validatePassword(""));
        assertFalse(userService.validatePassword(" "));
        assertFalse(userService.validatePassword("     ._9da       "));
    }

    @Test
    void emailValidate() {
        assertTrue(userService.validateEmail("yordanovtodor281@gmail.com"));
        assertTrue(userService.validateEmail("natoto01@abv.bg"));
        assertFalse(userService.validateEmail("ivanov24.com"));
        assertFalse(userService.validateEmail("john@com"));
        assertFalse(userService.validateEmail(""));
        assertFalse(userService.validateEmail(" "));
        assertFalse(userService.validateEmail("     ._9da       "));
    }

    @Test
    void phoneValidate() {
        assertTrue(userService.validatePhone("0882960178"));
        assertTrue(userService.validatePhone("0894735533"));
        assertFalse(userService.validatePhone("052500500"));
        assertFalse(userService.validatePhone("0521144 "));
        assertFalse(userService.validatePhone(""));
        assertFalse(userService.validatePhone(" "));
        assertFalse(userService.validatePhone(" ._9da"));
    }

    @Test
    void idValidate() {
        assertTrue(userService.validateId("0146302585"));
        assertTrue(userService.validateId("1428063011"));
        assertFalse(userService.validateId("0100301025"));
        assertFalse(userService.validateId("0146345515"));
        assertFalse(userService.validateId(""));
        assertFalse(userService.validateId(" "));
        assertFalse(userService.validateId("     ._9da       "));
    }

    @Test
    void firstNameValidate() {
        assertTrue(userService.validateFirstName("Тодор"));
        assertTrue(userService.validateFirstName("Благовеста"));
        assertFalse(userService.validateFirstName("ЗАхари"));
        assertFalse(userService.validateFirstName("митко"));
        assertFalse(userService.validateFirstName("012321432903423"));
        assertFalse(userService.validateFirstName("popov"));
        assertFalse(userService.validateFirstName(""));
        assertFalse(userService.validateFirstName(" Иаяьа "));
        assertFalse(userService.validateFirstName("     ._9da       "));
    }

    @Test
    void lastNameValidate() {
        assertTrue(userService.validateLastName("Йорданов"));
        assertTrue(userService.validateLastName("Енчева"));
        assertFalse(userService.validateLastName("ГеоргиеВ"));
        assertFalse(userService.validateLastName("димитров"));
        assertFalse(userService.validateLastName("012321 432903423"));
        assertFalse(userService.validateLastName("popov"));
        assertFalse(userService.validateLastName(""));
        assertFalse(userService.validateLastName(" Иаяьа "));
        assertFalse(userService.validateLastName("     ._9da       "));
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