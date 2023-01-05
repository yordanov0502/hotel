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
        public static final String ADMIN_ADD_NEW_OWNER_VIEW = "/bg.tu_varna.sit.hotel/presentation/admin/pages/AdminAddNewOwner.fxml";
        public static final String ADMIN_ADD_NEW_OWNER_TO_VACANT_HOTEL_VIEW = "/bg.tu_varna.sit.hotel/presentation/admin/pages/AdminAddNewOwnerToVacantHotel.fxml";
        public static final String ADMIN_ADD_OWNER_TO_VACANT_HOTEL_VIEW = "/bg.tu_varna.sit.hotel/presentation/admin/pages/AdminAddOwnerToVacantHotel.fxml";
        public static final String ADMIN_USER_INFO = "/bg.tu_varna.sit.hotel/presentation/admin/pages/AdminUserInfo.fxml";
        public static final String ADMIN_OWNERS_INFO_VIEW = "/bg.tu_varna.sit.hotel/presentation/admin/pages/AdminOwnersInfo.fxml";
        public static final String USER_EDIT_INFO_VIEW = "/bg.tu_varna.sit.hotel/presentation/admin/pages/UserEditInfo.fxml";
        public static final String ADMIN_MANAGERS_INFO_VIEW = "/bg.tu_varna.sit.hotel/presentation/admin/pages/AdminManagersInfo.fxml";
        public static final String ADMIN_RECEPTIONISTS_INFO_VIEW = "/bg.tu_varna.sit.hotel/presentation/admin/pages/AdminReceptionistsInfo.fxml";
        public static final String ADMIN_HOTELS_INFO_VIEW = "/bg.tu_varna.sit.hotel/presentation/admin/pages/AdminHotelsInfo.fxml";
        public static final String ADMIN_HOTEL_USERS_INFO_VIEW = "/bg.tu_varna.sit.hotel/presentation/admin/pages/AdminHotelUsersInfo.fxml";
        public static final String OWNER_LOGIN_VIEW = "/bg.tu_varna.sit.hotel/presentation/owner/pages/OwnerLogin.fxml";
        public static final String OWNER_MAIN_VIEW = "/bg.tu_varna.sit.hotel/presentation/owner/pages/OwnerMain.fxml";
        public static final String OWNER_INFO_VIEW = "/bg.tu_varna.sit.hotel/presentation/owner/pages/OwnerInfo.fxml";
        public static final String OWNER_EDIT_INFO_VIEW = "/bg.tu_varna.sit.hotel/presentation/owner/pages/OwnerEditInfo.fxml";
        public static final String OWNER_DELETE_ACCOUNT_CONFIRMATION_VIEW = "/bg.tu_varna.sit.hotel/presentation/owner/pages/OwnerDeleteAccountConfirmation.fxml";
        public static final String MANAGER_LOGIN_VIEW = "/bg.tu_varna.sit.hotel/presentation/manager/pages/ManagerLogin.fxml";
        public static final String RECEPTIONIST_LOGIN_VIEW = "/bg.tu_varna.sit.hotel/presentation/receptionist/pages/ReceptionistLogin.fxml";
        public static final String ADMINS_NEW_REGISTRATIONS_INFO = "/bg.tu_varna.sit.hotel/presentation/admin/pages/AdminsNewRegistrationsInfo.fxml";
    }

    public static class Configurations{
        public static final String LOG4J_PROPERTIES = "/log4j.properties";
        public static final String HIBERNATE_PROPERTIES = "/hibernate.cfg.xml";
    }

}