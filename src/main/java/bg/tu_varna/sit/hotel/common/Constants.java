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
        public static final String ADMIN_DELETE_ACCOUNT_CONFIRMATION_VIEW = "/bg.tu_varna.sit.hotel/presentation/admin/pages/AdminDeleteAccountConfirmation.fxml";
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
        public static final String ADMINS_NEW_REGISTRATIONS_INFO = "/bg.tu_varna.sit.hotel/presentation/admin/pages/AdminsNewRegistrationsInfo.fxml";
        public static final String OWNER_LOGIN_VIEW = "/bg.tu_varna.sit.hotel/presentation/owner/pages/OwnerLogin.fxml";
        public static final String OWNER_MAIN_VIEW = "/bg.tu_varna.sit.hotel/presentation/owner/pages/OwnerMain.fxml";
        public static final String OWNER_INFO_VIEW = "/bg.tu_varna.sit.hotel/presentation/owner/pages/OwnerInfo.fxml";
        public static final String OWNER_EDIT_INFO_VIEW = "/bg.tu_varna.sit.hotel/presentation/owner/pages/OwnerEditInfo.fxml";
        public static final String OWNER_DELETE_ACCOUNT_CONFIRMATION_VIEW = "/bg.tu_varna.sit.hotel/presentation/owner/pages/OwnerDeleteAccountConfirmation.fxml";
        public static final String OWNER_ADD_HOTEL_AND_MANAGER_VIEW = "/bg.tu_varna.sit.hotel/presentation/owner/pages/OwnerAddHotelAndManager.fxml";
        public static final String OWNER_ADD_NEW_MANAGER_TO_VACANT_HOTEL_VIEW = "/bg.tu_varna.sit.hotel/presentation/owner/pages/OwnerAddNewManagerToVacantHotel.fxml";
        public static final String OWNER_ADD_NEW_HOTEL_AND_NEW_MANAGER_VIEW = "/bg.tu_varna.sit.hotel/presentation/owner/pages/OwnerAddNewHotelAndNewManager.fxml";
        public static final String OWNER_HOTEL_MANAGER_ADD_VIEW = "/bg.tu_varna.sit.hotel/presentation/owner/pages/OwnerHotelManagerAdd.fxml";
        public static final String OWNER_HOTEL_MAJOR_INFORMATION_VIEW = "/bg.tu_varna.sit.hotel/presentation/owner/pages/OwnerHotelMajorInformation.fxml";
        public static final String OWNER_HOTEL_ROOMS_INFORMATION_VIEW = "/bg.tu_varna.sit.hotel/presentation/owner/pages/OwnerHotelRoomsInformation.fxml";
        public static final String OWNER_HOTELS_INFO_VIEW = "/bg.tu_varna.sit.hotel/presentation/owner/pages/OwnerHotelsInfo.fxml";
        public static final String OWNER_HOTELS_INFO_MAJOR_INFORMATION_VIEW = "/bg.tu_varna.sit.hotel/presentation/owner/pages/OwnerHotelsInfoMajorInformation.fxml";
        public static final String OWNER_HOTELS_INFO_EMPLOYEES_INFORMATION_VIEW = "/bg.tu_varna.sit.hotel/presentation/owner/pages/OwnerHotelsInfoEmployeesInformation.fxml";
        public static final String OWNER_USER_EDIT_INFO_VIEW = "/bg.tu_varna.sit.hotel/presentation/owner/pages/OwnerUserEditInfo.fxml";
        public static final String OWNER_HOTELS_INFO_ROOMS_INFORMATION_VIEW = "/bg.tu_varna.sit.hotel/presentation/owner/pages/OwnerHotelsInfoRoomsInformation.fxml";
        public static final String OWNER_ROOM_EDIT_INFO_VIEW = "/bg.tu_varna.sit.hotel/presentation/owner/pages/OwnerRoomEditInfo.fxml";
        public static final String OWNER_HOTELS_INFO_SERVICES_INFORMATION_VIEW="/bg.tu_varna.sit.hotel/presentation/owner/pages/OwnerHotelsInfoServicesInformation.fxml";
        public static final String OWNER_ADD_ROOM_VIEW = "/bg.tu_varna.sit.hotel/presentation/owner/pages/OwnerAddRoom.fxml";
        public static final String MANAGER_LOGIN_VIEW = "/bg.tu_varna.sit.hotel/presentation/manager/pages/ManagerLogin.fxml";
        public static final String MANAGER_MAIN_VIEW = "/bg.tu_varna.sit.hotel/presentation/manager/pages/ManagerMain.fxml";
        public static final String MANAGER_INFO_VIEW = "/bg.tu_varna.sit.hotel/presentation/manager/pages/ManagerInfo.fxml";
        public static final String MANAGER_EDIT_INFO_VIEW = "/bg.tu_varna.sit.hotel/presentation/manager/pages/ManagerEditInfo.fxml";
        public static final String MANAGER_DELETE_ACCOUNT_CONFIRMATION_VIEW = "/bg.tu_varna.sit.hotel/presentation/manager/pages/ManagerDeleteAccountConfirmation.fxml";
        public static final String MANAGER_ADD_NEW_RECEPTIONIST_VIEW = "/bg.tu_varna.sit.hotel/presentation/manager/pages/ManagerAddNewReceptionist.fxml";
        public static final String RECEPTIONIST_LOGIN_VIEW = "/bg.tu_varna.sit.hotel/presentation/receptionist/pages/ReceptionistLogin.fxml";
        public static final String RECEPTIONIST_MAIN_VIEW = "/bg.tu_varna.sit.hotel/presentation/receptionist/pages/ReceptionistMain.fxml";
        public static final String RECEPTIONIST_INFO_VIEW = "/bg.tu_varna.sit.hotel/presentation/receptionist/pages/ReceptionistInfo.fxml";
        public static final String RECEPTIONIST_EDIT_INFO_VIEW = "/bg.tu_varna.sit.hotel/presentation/receptionist/pages/ReceptionistEditInfo.fxml";
        public static final String RECEPTIONIST_DELETE_ACCOUNT_CONFIRMATION_VIEW = "/bg.tu_varna.sit.hotel/presentation/receptionist/pages/ReceptionistDeleteAccountConfirmation.fxml";
        public static final String RECEPTIONIST_ADD_NEW_CUSTOMER_VIEW = "/bg.tu_varna.sit.hotel/presentation/receptionist/pages/ReceptionistAddNewCustomer.fxml";
        public static final String RECEPTIONIST_ADD_NEW_RESERVATION_VIEW = "/bg.tu_varna.sit.hotel/presentation/receptionist/pages/ReceptionistAddNewReservation.fxml";
        public static final String RECEPTIONIST_CUSTOMER_INFO_VIEW = "/bg.tu_varna.sit.hotel/presentation/receptionist/pages/ReceptionistCustomerInfo.fxml";
        public static final String CUSTOMER_EDIT_INFO_VIEW = "/bg.tu_varna.sit.hotel/presentation/receptionist/pages/CustomerEditInfo.fxml";
        public static final String RECEPTIONIST_ADD_NEW_SERVICE_VIEW = "/bg.tu_varna.sit.hotel/presentation/receptionist/pages/ReceptionistAddNewService.fxml";
        public static final String RECEPTIONIST_HOTEL_INFO_VIEW = "/bg.tu_varna.sit.hotel/presentation/receptionist/pages/ReceptionistHotelInfo.fxml";
        public static final String RECEPTIONIST_RESERVATIONS_VIEW = "/bg.tu_varna.sit.hotel/presentation/receptionist/pages/ReceptionistReservations.fxml";
        public static final String RECEPTIONIST_RESERVATION_INFO_VIEW = "/bg.tu_varna.sit.hotel/presentation/receptionist/pages/ReceptionistReservationInfo.fxml";
        public static final String RECEPTIONIST_COMPLETE_RESERVATION_VIEW = "/bg.tu_varna.sit.hotel/presentation/receptionist/pages/ReceptionistCompleteReservation.fxml";
    }

    public static class Configurations{
        public static final String LOG4J_PROPERTIES = "/log4j.properties";
        public static final String HIBERNATE_PROPERTIES = "/hibernate.cfg.xml";
    }

    public static final String[] seasons = {
            "зима",//януари
            "зима",//февруари
            "пролет",//март
            "пролет",//април
            "пролет",//май
            "лято",//юни
            "лято",//юли
            "лято",//август
            "есен",//септември
            "есен",//октомври
            "есен",//ноември
            "зима",//декември
            "цяла година"
    };

}