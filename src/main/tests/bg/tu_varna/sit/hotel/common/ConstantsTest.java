package bg.tu_varna.sit.hotel.common;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ConstantsTest {
    @Test
    void checkPathsOfAllViews()
    {
        assertEquals("/bg.tu_varna.sit.hotel/presentation/application/pages/WelcomeView.fxml", Constants.View.WELCOME_VIEW);
        assertEquals("/bg.tu_varna.sit.hotel/presentation/admin/pages/AdminLogin.fxml", Constants.View.ADMIN_LOGIN_VIEW);
        assertEquals("/bg.tu_varna.sit.hotel/presentation/admin/pages/AdminRegistration.fxml", Constants.View.ADMIN_REGISTRATION_VIEW);
        assertEquals("/bg.tu_varna.sit.hotel/presentation/admin/pages/AdminMain.fxml", Constants.View.ADMIN_MAIN_VIEW);
        assertEquals("/bg.tu_varna.sit.hotel/presentation/admin/pages/AdminInfo.fxml", Constants.View.ADMIN_INFO_VIEW);
        assertEquals("/bg.tu_varna.sit.hotel/presentation/admin/pages/AdminEditInfo.fxml", Constants.View.ADMIN_EDIT_INFO_VIEW);
        assertEquals("/bg.tu_varna.sit.hotel/presentation/admin/pages/AdminDeleteAccountConfirmation.fxml", Constants.View.ADMIN_DELETE_ACCOUNT_CONFIRMATION_VIEW);
        assertEquals("/bg.tu_varna.sit.hotel/presentation/admin/pages/AdminAddOwner.fxml", Constants.View.ADMIN_ADD_OWNER_VIEW);
        assertEquals("/bg.tu_varna.sit.hotel/presentation/admin/pages/AdminAddNewOwner.fxml", Constants.View.ADMIN_ADD_NEW_OWNER_VIEW);
        assertEquals("/bg.tu_varna.sit.hotel/presentation/admin/pages/AdminAddNewOwnerToVacantHotel.fxml", Constants.View.ADMIN_ADD_NEW_OWNER_TO_VACANT_HOTEL_VIEW);
        assertEquals("/bg.tu_varna.sit.hotel/presentation/admin/pages/AdminAddOwnerToVacantHotel.fxml", Constants.View.ADMIN_ADD_OWNER_TO_VACANT_HOTEL_VIEW);
        assertEquals("/bg.tu_varna.sit.hotel/presentation/admin/pages/AdminUserInfo.fxml", Constants.View.ADMIN_USER_INFO);
        assertEquals("/bg.tu_varna.sit.hotel/presentation/admin/pages/AdminOwnersInfo.fxml", Constants.View.ADMIN_OWNERS_INFO_VIEW);
        assertEquals("/bg.tu_varna.sit.hotel/presentation/admin/pages/UserEditInfo.fxml", Constants.View.USER_EDIT_INFO_VIEW);
        assertEquals("/bg.tu_varna.sit.hotel/presentation/admin/pages/AdminManagersInfo.fxml", Constants.View.ADMIN_MANAGERS_INFO_VIEW);
        assertEquals("/bg.tu_varna.sit.hotel/presentation/admin/pages/AdminReceptionistsInfo.fxml", Constants.View.ADMIN_RECEPTIONISTS_INFO_VIEW);
        assertEquals("/bg.tu_varna.sit.hotel/presentation/admin/pages/AdminHotelsInfo.fxml", Constants.View.ADMIN_HOTELS_INFO_VIEW);
        assertEquals("/bg.tu_varna.sit.hotel/presentation/admin/pages/AdminHotelUsersInfo.fxml", Constants.View.ADMIN_HOTEL_USERS_INFO_VIEW);
        assertEquals("/bg.tu_varna.sit.hotel/presentation/admin/pages/AdminsNewRegistrationsInfo.fxml", Constants.View.ADMINS_NEW_REGISTRATIONS_INFO);
        assertEquals("/bg.tu_varna.sit.hotel/presentation/owner/pages/OwnerLogin.fxml", Constants.View.OWNER_LOGIN_VIEW);
        assertEquals("/bg.tu_varna.sit.hotel/presentation/owner/pages/OwnerMain.fxml", Constants.View.OWNER_MAIN_VIEW);
        assertEquals("/bg.tu_varna.sit.hotel/presentation/owner/pages/OwnerInfo.fxml", Constants.View.OWNER_INFO_VIEW);
        assertEquals("/bg.tu_varna.sit.hotel/presentation/owner/pages/OwnerEditInfo.fxml", Constants.View.OWNER_EDIT_INFO_VIEW);
        assertEquals("/bg.tu_varna.sit.hotel/presentation/owner/pages/OwnerDeleteAccountConfirmation.fxml", Constants.View.OWNER_DELETE_ACCOUNT_CONFIRMATION_VIEW);
        assertEquals("/bg.tu_varna.sit.hotel/presentation/owner/pages/OwnerAddHotelAndManager.fxml", Constants.View.OWNER_ADD_HOTEL_AND_MANAGER_VIEW);
        assertEquals("/bg.tu_varna.sit.hotel/presentation/owner/pages/OwnerAddNewManagerToVacantHotel.fxml", Constants.View.OWNER_ADD_NEW_MANAGER_TO_VACANT_HOTEL_VIEW);
        assertEquals("/bg.tu_varna.sit.hotel/presentation/owner/pages/OwnerAddNewHotelAndNewManager.fxml", Constants.View.OWNER_ADD_NEW_HOTEL_AND_NEW_MANAGER_VIEW);
        assertEquals("/bg.tu_varna.sit.hotel/presentation/owner/pages/OwnerHotelManagerAdd.fxml", Constants.View.OWNER_HOTEL_MANAGER_ADD_VIEW);
        assertEquals("/bg.tu_varna.sit.hotel/presentation/owner/pages/OwnerHotelMajorInformation.fxml", Constants.View.OWNER_HOTEL_MAJOR_INFORMATION_VIEW);
        assertEquals("/bg.tu_varna.sit.hotel/presentation/owner/pages/OwnerHotelRoomsInformation.fxml", Constants.View.OWNER_HOTEL_ROOMS_INFORMATION_VIEW);
        assertEquals("/bg.tu_varna.sit.hotel/presentation/owner/pages/OwnerHotelsInfo.fxml", Constants.View.OWNER_HOTELS_INFO_VIEW);
        assertEquals("/bg.tu_varna.sit.hotel/presentation/owner/pages/OwnerHotelsInfoMajorInformation.fxml", Constants.View.OWNER_HOTELS_INFO_MAJOR_INFORMATION_VIEW);
        assertEquals("/bg.tu_varna.sit.hotel/presentation/owner/pages/OwnerHotelsInfoEmployeesInformation.fxml", Constants.View.OWNER_HOTELS_INFO_EMPLOYEES_INFORMATION_VIEW);
        assertEquals("/bg.tu_varna.sit.hotel/presentation/owner/pages/OwnerUserEditInfo.fxml", Constants.View.OWNER_USER_EDIT_INFO_VIEW);
        assertEquals("/bg.tu_varna.sit.hotel/presentation/owner/pages/OwnerHotelsInfoRoomsInformation.fxml", Constants.View.OWNER_HOTELS_INFO_ROOMS_INFORMATION_VIEW);
        assertEquals("/bg.tu_varna.sit.hotel/presentation/owner/pages/OwnerRoomEditInfo.fxml", Constants.View.OWNER_ROOM_EDIT_INFO_VIEW);
        assertEquals("/bg.tu_varna.sit.hotel/presentation/owner/pages/OwnerHotelsInfoServicesInformation.fxml", Constants.View.OWNER_HOTELS_INFO_SERVICES_INFORMATION_VIEW);
        assertEquals("/bg.tu_varna.sit.hotel/presentation/owner/pages/OwnerAddRoom.fxml", Constants.View.OWNER_ADD_ROOM_VIEW);
        assertEquals("/bg.tu_varna.sit.hotel/presentation/owner/pages/OwnerRoomsRatings.fxml", Constants.View.OWNER_ROOMS_RATINGS_VIEW);
        assertEquals("/bg.tu_varna.sit.hotel/presentation/owner/pages/OwnerCreatedRegistrations.fxml", Constants.View.OWNER_REGISTRATIONS_VIEW);
        assertEquals("/bg.tu_varna.sit.hotel/presentation/owner/pages/OwnerRegistrationsForm.fxml", Constants.View.OWNER_REGISTRATIONS_FORM_VIEW);
        assertEquals("/bg.tu_varna.sit.hotel/presentation/owner/pages/OwnerReceptionistsReservations.fxml", Constants.View.OWNER_RECEPTIONISTS_RESERVATIONS_VIEW);
        assertEquals("/bg.tu_varna.sit.hotel/presentation/owner/pages/OwnerReservationInfo.fxml", Constants.View.OWNER_RESERVATION_INFO_VIEW);
        assertEquals("/bg.tu_varna.sit.hotel/presentation/owner/pages/OwnerCustomers.fxml", Constants.View.OWNER_CUSTOMERS_INFO_VIEW);
        assertEquals("/bg.tu_varna.sit.hotel/presentation/owner/pages/OwnerServicesUsed.fxml", Constants.View.OWNER_SERVICES_USED_INFO);
        assertEquals("/bg.tu_varna.sit.hotel/presentation/manager/pages/ManagerLogin.fxml", Constants.View.MANAGER_LOGIN_VIEW);
        assertEquals("/bg.tu_varna.sit.hotel/presentation/manager/pages/ManagerMain.fxml", Constants.View.MANAGER_MAIN_VIEW);
        assertEquals("/bg.tu_varna.sit.hotel/presentation/manager/pages/ManagerInfo.fxml", Constants.View.MANAGER_INFO_VIEW);
        assertEquals("/bg.tu_varna.sit.hotel/presentation/manager/pages/ManagerEditInfo.fxml", Constants.View.MANAGER_EDIT_INFO_VIEW);
        assertEquals("/bg.tu_varna.sit.hotel/presentation/manager/pages/ManagerDeleteAccountConfirmation.fxml", Constants.View.MANAGER_DELETE_ACCOUNT_CONFIRMATION_VIEW);
        assertEquals("/bg.tu_varna.sit.hotel/presentation/manager/pages/ManagerAddNewReceptionist.fxml", Constants.View.MANAGER_ADD_NEW_RECEPTIONIST_VIEW);
        assertEquals("/bg.tu_varna.sit.hotel/presentation/manager/pages/ManagerHotelInfo.fxml", Constants.View.MANAGER_HOTEL_INFO_VIEW);
        assertEquals("/bg.tu_varna.sit.hotel/presentation/manager/pages/ManagerRoomsRatings.fxml", Constants.View.MANAGER_ROOMS_RATINGS_VIEW);
        assertEquals("/bg.tu_varna.sit.hotel/presentation/manager/pages/ManagerCreatedRegistrations.fxml", Constants.View.MANAGER_REGISTRATIONS_VIEW);
        assertEquals("/bg.tu_varna.sit.hotel/presentation/manager/pages/ManagerRegistrationsForm.fxml", Constants.View.MANAGER_REGISTRATIONS_FORM_VIEW);
        assertEquals("/bg.tu_varna.sit.hotel/presentation/manager/pages/ManagerReceptionistsReservations.fxml", Constants.View.MANAGER_RECEPTIONISTS_RESERVATIONS_VIEW);
        assertEquals("/bg.tu_varna.sit.hotel/presentation/manager/pages/ManagerReservationInfo.fxml", Constants.View.MANAGER_RESERVATION_INFO_VIEW);
        assertEquals("/bg.tu_varna.sit.hotel/presentation/manager/pages/ManagerCustomers.fxml", Constants.View.MANAGER_CUSTOMERS_INFO_VIEW);
        assertEquals("/bg.tu_varna.sit.hotel/presentation/manager/pages/ManagerServicesUsed.fxml", Constants.View.MANAGER_SERVICES_USED_VIEW);
        assertEquals("/bg.tu_varna.sit.hotel/presentation/receptionist/pages/ReceptionistLogin.fxml", Constants.View.RECEPTIONIST_LOGIN_VIEW);
        assertEquals("/bg.tu_varna.sit.hotel/presentation/receptionist/pages/ReceptionistMain.fxml", Constants.View.RECEPTIONIST_MAIN_VIEW);
        assertEquals("/bg.tu_varna.sit.hotel/presentation/receptionist/pages/ReceptionistInfo.fxml", Constants.View.RECEPTIONIST_INFO_VIEW);
        assertEquals("/bg.tu_varna.sit.hotel/presentation/receptionist/pages/ReceptionistEditInfo.fxml", Constants.View.RECEPTIONIST_EDIT_INFO_VIEW);
        assertEquals("/bg.tu_varna.sit.hotel/presentation/receptionist/pages/ReceptionistDeleteAccountConfirmation.fxml", Constants.View.RECEPTIONIST_DELETE_ACCOUNT_CONFIRMATION_VIEW);
        assertEquals("/bg.tu_varna.sit.hotel/presentation/receptionist/pages/ReceptionistAddNewCustomer.fxml", Constants.View.RECEPTIONIST_ADD_NEW_CUSTOMER_VIEW);
        assertEquals("/bg.tu_varna.sit.hotel/presentation/receptionist/pages/ReceptionistAddNewReservation.fxml", Constants.View.RECEPTIONIST_ADD_NEW_RESERVATION_VIEW);
        assertEquals("/bg.tu_varna.sit.hotel/presentation/receptionist/pages/ReceptionistCustomerInfo.fxml", Constants.View.RECEPTIONIST_CUSTOMER_INFO_VIEW);
        assertEquals("/bg.tu_varna.sit.hotel/presentation/receptionist/pages/CustomerEditInfo.fxml", Constants.View.CUSTOMER_EDIT_INFO_VIEW);
        assertEquals("/bg.tu_varna.sit.hotel/presentation/receptionist/pages/ReceptionistAddNewService.fxml", Constants.View.RECEPTIONIST_ADD_NEW_SERVICE_VIEW);
        assertEquals("/bg.tu_varna.sit.hotel/presentation/receptionist/pages/ReceptionistHotelInfo.fxml", Constants.View.RECEPTIONIST_HOTEL_INFO_VIEW);
        assertEquals("/bg.tu_varna.sit.hotel/presentation/receptionist/pages/ReceptionistReservations.fxml", Constants.View.RECEPTIONIST_RESERVATIONS_VIEW);
        assertEquals("/bg.tu_varna.sit.hotel/presentation/receptionist/pages/ReceptionistReservationInfo.fxml", Constants.View.RECEPTIONIST_RESERVATION_INFO_VIEW);
        assertEquals("/bg.tu_varna.sit.hotel/presentation/receptionist/pages/ReceptionistCompleteReservation.fxml", Constants.View.RECEPTIONIST_COMPLETE_RESERVATION_VIEW);
    }

    @Test
    void CheckPathsOfAllConfigurations()
    {
        assertEquals("/log4j.properties",Constants.Configurations.LOG4J_PROPERTIES);
        assertEquals("/hibernate.cfg.xml",Constants.Configurations.HIBERNATE_PROPERTIES);
    }
}