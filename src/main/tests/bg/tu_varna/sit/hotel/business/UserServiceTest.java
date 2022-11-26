package bg.tu_varna.sit.hotel.business;

import bg.tu_varna.sit.hotel.common.Constants;
import bg.tu_varna.sit.hotel.presentation.models.UserModel;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = UserService.getInstance();
    }

    @Test
    void getInstance() {
        assertTrue(userService instanceof UserService);
        assertNotNull(userService);
    }

    //@Test
   // void getAllUser() {
    //    assertNotNull(userService.getAllUser());
    //}

    @Test
    void usernameValidate() {
        assertTrue(UserService.getInstance().usernameValidate("yordanov5.0"));
        assertTrue(UserService.getInstance().usernameValidate("Zdravk000_1.05"));
        assertFalse(UserService.getInstance().usernameValidate("mAri@ a.02"));
        assertFalse(UserService.getInstance().usernameValidate("GEORGI#()-202"));
    }

    @Test
    void passwordValidate() {
        assertTrue(UserService.getInstance().passwordValidate("awbfg_!?fA9"));
        assertTrue(UserService.getInstance().passwordValidate("req_._2314A"));
        assertFalse(UserService.getInstance().passwordValidate("mSIDi02 13@#$%8765"));
        assertFalse(UserService.getInstance().passwordValidate("pPlK-54***1`~12©"));
    }

    @Test
    void emailValidate() {
        assertTrue(UserService.getInstance().emailValidate("yordanovtodor281@gmail.com"));
        assertTrue(UserService.getInstance().emailValidate("natoto01@abv.bg"));
        assertFalse(UserService.getInstance().emailValidate("ivanov24.com"));
        assertFalse(UserService.getInstance().emailValidate("john@com"));
    }

    @Test
    void phoneValidate() {
        assertTrue(UserService.getInstance().phoneValidate("0882960178"));
        assertTrue(UserService.getInstance().phoneValidate("0894735533"));
        assertFalse(UserService.getInstance().phoneValidate("052500500"));
        assertFalse(UserService.getInstance().phoneValidate("0521144 "));
    }

    @Test
    void idValidate() {
        assertTrue(UserService.getInstance().idValidate("0146302585"));
        assertTrue(UserService.getInstance().idValidate("1428063011"));
        assertFalse(UserService.getInstance().idValidate("0100301025"));
        assertFalse(UserService.getInstance().idValidate("0146345515"));
    }

    @Test
    void firstNameValidate() {
        assertTrue(UserService.getInstance().firstNameValidate("Тодор"));
        assertTrue(UserService.getInstance().firstNameValidate("Благовеста"));
        assertFalse(UserService.getInstance().firstNameValidate("ЗАхари"));
        assertFalse(UserService.getInstance().firstNameValidate("митко"));
        assertFalse(UserService.getInstance().firstNameValidate("012321432903423"));
        assertFalse(UserService.getInstance().firstNameValidate("popov"));
    }

    @Test
    void lastNameValidate() {
        assertTrue(UserService.getInstance().firstNameValidate("Йорданов"));
        assertTrue(UserService.getInstance().firstNameValidate("Енчева"));
        assertFalse(UserService.getInstance().firstNameValidate("ГеоргиеВ"));
        assertFalse(UserService.getInstance().firstNameValidate("димитров"));
        assertFalse(UserService.getInstance().firstNameValidate("012321 432903423"));
        assertFalse(UserService.getInstance().lastNameValidate("popov"));
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

    @Test
    void userAuthentication() {
        // Non-existent user
        assertFalse(userService.userAuthentication("nothing", "12345"));
    }
}