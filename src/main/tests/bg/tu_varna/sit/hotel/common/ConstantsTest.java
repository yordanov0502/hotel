package bg.tu_varna.sit.hotel.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConstantsTest {
    @Test
    void checkPathsOfAllViews()
    {
        assertEquals("/bg.tu_varna.sit.hotel/presentation/application/pages/WelcomeView.fxml", Constants.View.WELCOME_VIEW);
        assertEquals("/bg.tu_varna.sit.hotel/presentation/admin/pages/AdminLogin.fxml",Constants.View.ADMIN_LOGIN_VIEW);
        assertEquals("/bg.tu_varna.sit.hotel/presentation/admin/pages/AdminRegistration.fxml",Constants.View.ADMIN_REGISTRATION_VIEW);
        assertEquals("/bg.tu_varna.sit.hotel/presentation/owner/pages/OwnerLogin.fxml",Constants.View.OWNER_LOGIN_VIEW);
        assertEquals("/bg.tu_varna.sit.hotel/presentation/manager/pages/ManagerLogin.fxml",Constants.View.MANAGER_LOGIN_VIEW);
        assertEquals("/bg.tu_varna.sit.hotel/presentation/receptionist/pages/ReceptionistLogin.fxml",Constants.View.RECEPTIONIST_LOGIN_VIEW);
    }

    @Test
    void CheckPathsOfAllConfigurations()
    {
        assertEquals("/log4j.properties",Constants.Configurations.LOG4J_PROPERTIES);
        assertEquals("/hibernate.cfg.xml",Constants.Configurations.HIBERNATE_PROPERTIES);
    }
}