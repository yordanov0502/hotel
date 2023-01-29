package bg.tu_varna.sit.hotel.presentation.controllers.owner;

import bg.tu_varna.sit.hotel.business.*;
import bg.tu_varna.sit.hotel.common.UserSession;
import bg.tu_varna.sit.hotel.common.ViewManager;
import bg.tu_varna.sit.hotel.presentation.controllers.manager.ManagerReservationInfoController;
import bg.tu_varna.sit.hotel.presentation.models.CustomerModel;
import bg.tu_varna.sit.hotel.presentation.models.HotelModel;
import bg.tu_varna.sit.hotel.presentation.models.ReservationModel;
import bg.tu_varna.sit.hotel.presentation.models.UserModel;
import bg.tu_varna.sit.hotel.presentation.models.custom.ReservationRowModel;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import org.apache.log4j.Logger;

import java.util.List;

public class OwnerReservationInfoController {
    private static final Logger log = Logger.getLogger(OwnerReservationInfoController.class);
    private final CustomerService customerService = CustomerService.getInstance();
    private final UserService userService = UserService.getInstance();
    private final ServiceService serviceService = ServiceService.getInstance();
    private final RoomService roomService = RoomService.getInstance();
    private final ReservationService reservationService = ReservationService.getInstance();
    private ReservationRowModel reservationRowModel;

    @FXML
    private TextField resTypeField;
    @FXML
    private TextField resNumberField;
    @FXML
    private TextField resCreatedAtField;
    @FXML
    private TextField resStartDateField;
    @FXML
    private TextField resEndDateField;
    @FXML
    private ListView<Integer> roomsListView;
    @FXML
    private TextField resTotalPriceField;

    @FXML
    private TextField customerNameField;
    @FXML
    private TextField customerSurnameField;
    @FXML
    private TextField customerEgnField;
    @FXML
    private TextField customerPhoneField;
    @FXML
    private TextField customerRatingField;

    @FXML
    private TextField receptionistNameField;
    @FXML
    private TextField receptionistSurnameField;
    @FXML
    private TextField receptionistIdField;
    @FXML
    private TextField receptionistPhoneField;
    @FXML
    private TextField receptionistEmailField;

    private HotelModel hotelModel;

    public void initialize()
    {
        this.reservationRowModel=UserSession.selectedReservationRowModel;
        UserSession.selectedReservationRowModel=null;
        this.hotelModel=UserSession.selectedHotel;
        UserSession.selectedHotel=null;

        List<Integer> reservationsWithSameNumber_RoomNumbers = reservationService.getAllReservationsWithSameNumber_RoomsNumbers(reservationRowModel.getNumber(),hotelModel);
        ReservationModel reservationModel = reservationService.getReservationWithNumber(reservationRowModel.getNumber(),hotelModel);

        UserModel receptionist = userService.getUserById(reservationModel.getReceptionist().getId());

        if(reservationModel!=null)
        {
            resTypeField.setText(reservationRowModel.getType());
            resNumberField.setText(reservationRowModel.getNumber().toString());
            resCreatedAtField.setText(reservationModel.getCreatedAt().toString());
            resStartDateField.setText(reservationModel.getStartDate().toString());
            resEndDateField.setText(reservationModel.getEndDate().toString());
            resTotalPriceField.setText(reservationModel.getTotalPrice().toString());

            CustomerModel customer = customerService.getCustomerById(reservationModel.getCustomer().getId());
            customerNameField.setText(customer.getFirstName());
            customerSurnameField.setText(customer.getLastName());
            customerEgnField.setText(customer.getEgn());
            customerPhoneField.setText(customer.getPhone());
            customerRatingField.setText(customer.getRating());
        }
        if(reservationsWithSameNumber_RoomNumbers!=null)
        {
            roomsListView.getItems().addAll(reservationsWithSameNumber_RoomNumbers);
        }

        receptionistNameField.setText(receptionist.getFirstName());
        receptionistSurnameField.setText(receptionist.getLastName());
        receptionistIdField.setText(receptionist.getId());
        receptionistPhoneField.setText(receptionist.getPhone());
        receptionistEmailField.setText(receptionist.getEmail());
    }

    public void closeWindow(){
        ViewManager.closeDialogBox();
    }
}