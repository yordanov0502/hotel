package bg.tu_varna.sit.hotel.presentation.controllers.receptionist;

import bg.tu_varna.sit.hotel.business.*;
import bg.tu_varna.sit.hotel.common.AlertManager;
import bg.tu_varna.sit.hotel.common.Constants;
import bg.tu_varna.sit.hotel.common.UserSession;
import bg.tu_varna.sit.hotel.common.ViewManager;
import bg.tu_varna.sit.hotel.data.entities.Reservation;
import bg.tu_varna.sit.hotel.presentation.controllers.receptionist.custom.SwitchButton;
import bg.tu_varna.sit.hotel.presentation.models.*;
import bg.tu_varna.sit.hotel.presentation.models.custom.ReservationRowModel;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.stage.StageStyle;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.DateTime;
import org.joda.time.Days;

import java.time.LocalDate;
import java.time.Period;
import java.util.*;
import java.text.SimpleDateFormat;

import java.io.IOException;
import java.sql.Timestamp;

public class ReceptionistCompleteReservationController {
    private static final Logger log = Logger.getLogger(ReceptionistCompleteReservationController.class);
    private final ReservationService reservationService = ReservationService.getInstance();
    private final CustomerService customerService = CustomerService.getInstance();
    private final RoomService roomService = RoomService.getInstance();
    private final HotelService hotelService = HotelService.getInstance();
    private final ServiceService serviceService = ServiceService.getInstance();
    private ReservationRowModel reservationRowModel;
    private HotelModel hotelModel;

    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Button closeWindowButton;
    @FXML
    private Label customerRatingLabel;
    @FXML
    private Button completeReservationButton;


    public void initialize() {
        this.reservationRowModel = UserSession.selectedReservationRowModel;
        UserSession.selectedReservationRowModel = null;

        anchorPane.getChildren().add(new SwitchButton());
        anchorPane.getChildren().get(3).setLayoutX(113);
        anchorPane.getChildren().get(3).setLayoutY(100);

        //hotel to which reservation(s) exist
        hotelModel = hotelService.getHotelById(UserSession.user.getHotels().get(0).getId());
    }

    public void closeWindow() {
        ViewManager.closeDialogBox();
    }

    public void completeReservation() throws IOException {


        reservationService.refreshUncompletedReservationsStatus(hotelModel);

        //Information for one reservation(unit)
        ReservationModel reservationModel = reservationService.getReservationWithNumber(reservationRowModel.getNumber(), hotelModel);
        CustomerModel customerModell = customerService.getCustomerByEgn(reservationModel.getCustomer().getEgn(),hotelModel);



        if (reservationModel.getStatus().equals("обработена"))
        {
            AlertManager.showAlert(Alert.AlertType.ERROR, "Грешка", "Резервация с № " + reservationModel.getNumber() + " вече е обработена.");
            ViewManager.closeDialogBox();
            ViewManager.changeView(Constants.View.RECEPTIONIST_RESERVATIONS_VIEW, ViewManager.getPrimaryStage(), this.getClass(), "Receptionist Uncompleted Reservations", 800, 500);
        }
        else
        {
            //List of all reservation units(parts of the assembled reservation)
            List<Reservation> reservationUnits = reservationService.getAllUncompletedReservationsOfHotelWithNumber(reservationRowModel.getNumber(), hotelModel);

            boolean successfulUpdate = true;

            boolean customerAssessment = ((SwitchButton) anchorPane.getChildren().get(3)).getState();

            boolean customerIsAlreadyUpdated = false;

            boolean enteredIfStatement = false;

            boolean servicesUsageUpdated = false;


            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Потвърждение");
            alert.initStyle(StageStyle.UNDECORATED);
            if(reservationModel.getStatus().equals("изтекла"))
            {
                alert.setContentText("Потвърждавате ли завършване на резервация № " + reservationModel.getNumber() + " ?\n\n(С това се съгласявате, че сте получили от клиента "+customerModell.getFirstName()+" "+customerModell.getLastName()+" сумата от "+reservationModel.getTotalPrice()+"лв. за цялата резервация)");
            }
            else if(reservationModel.getStatus().equals("активна"))
            {
                alert.setContentText("Потвърждавате ли завършване на резервация № " + reservationModel.getNumber() + " ?\n\n(С това се съгласявате, че сте получили от клиента "+customerModell.getFirstName()+" "+customerModell.getLastName()+" сумата от "+reservationModel.getTotalPrice()+"лв. за цялата резервация, макар и резервацията все още да НЕ е изтекла)");
            }
            alert.setX(ViewManager.getPrimaryStage().getX() + 220);
            alert.setY(ViewManager.getPrimaryStage().getY() + 180);
            alert.getDialogPane().setMinHeight(Region.USE_PREF_SIZE);
            ButtonType yesButton = new ButtonType("Да", ButtonBar.ButtonData.YES);
            ButtonType noButton = new ButtonType("Не", ButtonBar.ButtonData.NO);
            alert.getButtonTypes().setAll(yesButton, noButton);
            Optional<ButtonType> answer = alert.showAndWait();

            if (answer.isPresent() && answer.get() == yesButton)
            {





                //ako rezervaciqata e iztekla ili e aktivna
                if (reservationModel.getStatus().equals("изтекла") || reservationModel.getStatus().equals("активна"))
                {

                    enteredIfStatement = true;

                    for (Reservation reservation : reservationUnits)
                    {
                        CustomerModel customerModel = customerService.getCustomerById(reservationUnits.get(0).getCustomer().getId());

                        Timestamp alteredEndDate;
                        if(reservation.getStatus().equals("активна"))
                        {
                            Timestamp today = new Timestamp(System.currentTimeMillis());
                            String earlyTimeStamp = today.toLocalDateTime().toLocalDate()+" "+reservation.getStartDate().toLocalDateTime().getHour()+":00:00.000";
                            alteredEndDate = Timestamp.valueOf(earlyTimeStamp);
                            reservation.setEndDate(alteredEndDate);
                        }
                        else
                        {
                            String tempTimeStamp = reservation.getEndDate().toLocalDateTime().toLocalDate()+" "+reservation.getStartDate().toLocalDateTime().getHour()+":00:00.000";
                            alteredEndDate = Timestamp.valueOf(tempTimeStamp);
                        }


                        if (customerAssessment)//ako ocenkata e "dobra"
                        {
                            if (!customerIsAlreadyUpdated)//if customer is not already updated
                            {
                                int nightsStayedNewCalculation = customerModel.getNightsStayed()+(reservationUnits.size()*Days.daysBetween(new DateTime(reservation.getStartDate().getTime()),new DateTime(alteredEndDate.getTime())).getDays());
                                reservation.setCustomerRating(determineCustomerRating(nightsStayedNewCalculation));
                                customerModel.setRating(reservation.getCustomerRating());
                                customerModel.setNightsStayed(nightsStayedNewCalculation);
                                if (!customerService.updateCustomer(customerModel))
                                {
                                    successfulUpdate = false;
                                    break;
                                }
                                else
                                {
                                    customerIsAlreadyUpdated = true;
                                }
                            }
                            else
                            {
                                reservation.setCustomerRating(customerModel.getRating());
                            }
                        }

                        else//ako ocenkata e "losha"
                        {
                            if (!customerIsAlreadyUpdated)//if customer is not already updated
                            {
                                reservation.setCustomerRating("слаб");
                                customerModel.setRating("слаб");
                                customerModel.setNightsStayed(-10);
                                if (!customerService.updateCustomer(customerModel))
                                {
                                    successfulUpdate = false;
                                    break;
                                }
                                else
                                {
                                    customerIsAlreadyUpdated = true;
                                }
                            }
                            else
                            {
                                reservation.setCustomerRating("слаб");
                            }
                        }


                        RoomModel roomModel = roomService.getRoomByNumber(reservation.getRoom().getNumber().toString(), hotelModel);

                        reservation.setNightsOccupied(/*roomModel.getNightsOccupied()+*/(Days.daysBetween(new DateTime(reservation.getStartDate().getTime()),new DateTime(alteredEndDate.getTime())).getDays()));
                        roomModel.setNightsOccupied(roomModel.getNightsOccupied() + reservation.getNightsOccupied());
                        if (roomService.calculateRoomRatings(roomModel, hotelModel))//if all room ratings were pre-calculated successfully
                        {
                            reservation.setRoomRating(roomModel.getRating()/*roomService.getRoomByNumber(reservation.getRoom().getNumber().toString(), hotelModel).getRating()*/);
                        }
                        else
                        {
                            successfulUpdate = false;
                            break;
                        }


                        if(!reservation.getServiceList().equals(".") && !servicesUsageUpdated)//if reservation has services specified
                        {
                            String[] serviceNames=reservationModel.getServiceList().split(",");//used services in the reservation

                            List<ServiceModel> allServicesOfHotel = serviceService.getAllServicesOfHotel(hotelModel);

                            for (ServiceModel serviceModel : allServicesOfHotel)
                            {
                                for (String usedService : serviceNames)
                                {
                                    if(serviceModel.getType().equals(usedService))
                                    {
                                        serviceModel.setTimes_used(serviceModel.getTimes_used()+1);
                                        break;
                                    }
                                }
                                if (!serviceService.updateService(serviceModel))
                                {
                                    successfulUpdate = false;
                                    break;
                                }
                            }
                            servicesUsageUpdated=true;
                        }



                        reservation.setStatus("обработена");


                        if (!reservationService.updateReservation(reservation.toModel()))
                        {
                            successfulUpdate = false;
                            break;
                        }
                    }

                }


                if (successfulUpdate && enteredIfStatement)
                {
                    if(reservationModel.getNotificationSent().equals(true))
                    {
                        ReservationService.uncompletedNotifiedReservations-=1;//decrement notification counter only if the assembled reservation's notificationSent attribute is true
                    }
                    AlertManager.showAlert(Alert.AlertType.INFORMATION, "Информация", "✅ Успешно завършихте резервация № " + reservationModel.getNumber());
                    ViewManager.closeDialogBox();
                    ViewManager.changeView(Constants.View.RECEPTIONIST_RESERVATIONS_VIEW, ViewManager.getPrimaryStage(), this.getClass(), "Receptionist Uncompleted Reservations", 800, 500);
                }
                else
                {
                    AlertManager.showAlert(Alert.AlertType.ERROR, "Грешка", "Неуспешно завършване на резервация.");
                    ViewManager.closeDialogBox();
                    ViewManager.changeView(Constants.View.RECEPTIONIST_RESERVATIONS_VIEW, ViewManager.getPrimaryStage(), this.getClass(), "Receptionist Uncompleted Reservations", 800, 500);
                }
            }

            else
            {
                ViewManager.closeDialogBox();
                ViewManager.changeView(Constants.View.RECEPTIONIST_RESERVATIONS_VIEW, ViewManager.getPrimaryStage(), this.getClass(), "Receptionist Uncompleted Reservations", 800, 500);
            }


        }
    }


    private String determineCustomerRating(int nightsStayed)
    {
        if(nightsStayed==0){return "неопределен";}
        else if(nightsStayed>0 && nightsStayed<10) {return "нормален";}
        else if(nightsStayed>=10 && nightsStayed<30) {return "добър";}
        else if(nightsStayed>=30 && nightsStayed<50) {return "много добър";}
        else {return "отличен";}//50+
    }

}