package bg.tu_varna.sit.hotel.common;

//This is a utility class which is often used in the application
public final class Constants {
    private Constants(){}

    public static class View {
        public static final String WELCOME_VIEW = "/bg.tu_varna.sit.hotel/presentation/application/pages/WelcomeView.fxml";
        public static final String ADMIN_LOGIN_VIEW = "/bg.tu_varna.sit.hotel/presentation/admin/pages/AdminLogin.fxml";
        public static final String ADMIN_REGISTRATION_VIEW = "/bg.tu_varna.sit.hotel/presentation/admin/pages/AdminRegistration.fxml";
        public static final String ADMIN_MAIN_VIEW = "/bg.tu_varna.sit.hotel/presentation/admin/pages/AdminMain.fxml";
        public static final String ADMIN_INFO_VIEW = "/bg.tu_varna.sit.hotel/presentation/admin/pages/AdminInfo.fxml";
        public static final String ADMIN_EDIT_INFO_VIEW = "/bg.tu_varna.sit.hotel/presentation/admin/pages/AdminEditInfo.fxml";
        public static final String ADMIN_ADD_OWNER_VIEW = "/bg.tu_varna.sit.hotel/presentation/admin/pages/AdminAddOwner.fxml";
        public static final String OWNER_LOGIN_VIEW = "/bg.tu_varna.sit.hotel/presentation/owner/pages/OwnerLogin.fxml";
        public static final String MANAGER_LOGIN_VIEW = "/bg.tu_varna.sit.hotel/presentation/manager/pages/ManagerLogin.fxml";
        public static final String RECEPTIONIST_LOGIN_VIEW = "/bg.tu_varna.sit.hotel/presentation/receptionist/pages/ReceptionistLogin.fxml";
    }

    public static class Configurations{
        public static final String LOG4J_PROPERTIES = "/log4j.properties";
        public static final String HIBERNATE_PROPERTIES = "/hibernate.cfg.xml";
    }

}