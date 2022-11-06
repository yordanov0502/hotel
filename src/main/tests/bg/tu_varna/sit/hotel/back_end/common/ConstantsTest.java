package bg.tu_varna.sit.hotel.back_end.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConstantsTest {
    @Test
    void checkPathsOfAllViews()
    {
        assertEquals("/bg/tu_varna/sit/hotel/front_end/presentation/application/pages/WelcomeView.fxml",Constants.View.WELCOME_VIEW);
        assertEquals("/bg/tu_varna/sit/hotel/front_end/presentation/admin/pages/AdminLogin.fxml",Constants.View.ADMIN_LOGIN_VIEW);
        assertEquals("/bg/tu_varna/sit/hotel/front_end/presentation/admin/pages/AdminRegistration.fxml",Constants.View.ADMIN_REGISTRATION_VIEW);
        assertEquals("/bg/tu_varna/sit/hotel/front_end/presentation/owner/pages/OwnerLogin.fxml",Constants.View.OWNER_LOGIN_VIEW);
        assertEquals("/bg/tu_varna/sit/hotel/front_end/presentation/manager/pages/ManagerLogin.fxml",Constants.View.MANAGER_LOGIN_VIEW);
        assertEquals("/bg/tu_varna/sit/hotel/front_end/presentation/receptionist/pages/ReceptionistLogin.fxml",Constants.View.RECEPTIONIST_LOGIN_VIEW);
    }

    @Test
    void CheckPathsOfAllConfigurations()
    {
        assertEquals("/bg/tu_varna/sit/hotel/configurations/log4j.properties",Constants.Configurations.LOG4J_PROPERTIES);
    }
}