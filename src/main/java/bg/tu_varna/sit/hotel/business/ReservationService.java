package bg.tu_varna.sit.hotel.business;

import bg.tu_varna.sit.hotel.common.AlertManager;
import bg.tu_varna.sit.hotel.data.entities.Hotel;
import bg.tu_varna.sit.hotel.data.entities.Reservation;
import bg.tu_varna.sit.hotel.data.entities.Room;
import bg.tu_varna.sit.hotel.data.repositories.implementations.ReservationRepositoryImpl;
import bg.tu_varna.sit.hotel.presentation.models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Minutes;

import java.sql.Timestamp;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ReservationService {
    private static final Logger log = Logger.getLogger(ReservationService.class);
    private final ReservationRepositoryImpl reservationRepository = ReservationRepositoryImpl.getInstance();

    //lazy-loaded singleton pattern
    public static ReservationService getInstance() {
        return ReservationService.ReservationServiceHolder.INSTANCE;
    }

    private static class ReservationServiceHolder {
        public static final ReservationService INSTANCE = new ReservationService();
    }

    public ObservableList<RoomModel> getAllFreeRooms(HotelModel hotelModel, Timestamp startDate, Timestamp endDate) {
        List<Room> freeRooms = reservationRepository.getAllFreeRooms(hotelModel.toEntity(),startDate,endDate);

        if(freeRooms.isEmpty()){return null;}

        else
        {
            return FXCollections.observableList(
                  freeRooms.stream().map(r -> new RoomModel(
                            r.getId(),
                            r.getNumber(),
                            new HotelModel(r.getHotel()),
                            r.getPrice(),
                            r.getType(),
                            r.getSize(),
                            r.getRating(),
                            r.getNightsOccupied(),
                            r.getIsOccupied(),
                            r.getBeds()
                    )).collect(Collectors.toList())
            );
        }
    }

    public List<ReservationModel> getAllReservationsWithSameRoomId(Long roomId,HotelModel hotelModel) {
        List<Reservation> reservationsWithSameRoomId = reservationRepository.getAllReservationsWithSameRoomId(roomId,hotelModel.toEntity());

        if(reservationsWithSameRoomId.isEmpty()){return null;}

        else
        {
            return FXCollections.observableList(
                    reservationsWithSameRoomId.stream().map(rr -> new ReservationModel(
                            rr.getId(),
                            rr.getNumber(),
                            rr.getType(),
                            rr.getStatus(),
                            rr.getCreatedAt(),
                            rr.getStartDate(),
                            rr.getEndDate(),
                            new HotelModel(rr.getHotel()),
                            new UserModel(rr.getReceptionist()),
                            new CustomerModel(rr.getCustomer()),
                            rr.getCustomerRating(),
                            new RoomModel(rr.getRoom()),
                            rr.getNightsOccupied(),
                            rr.getRoomRating(),
                            rr.getServiceList(),
                            rr.getRequestSent(),
                            rr.getFinalAnnulationDate(),
                            rr.getTotalPrice()
                    )).collect(Collectors.toList())
            );
        }
    }


    public List<Reservation> getAllUncompletedReservationsOfHotel(HotelModel hotelModel) {
        List<Reservation> uncompletedReservations = reservationRepository.getAllUncompletedReservationsOfHotel(hotelModel.toEntity());

        if(uncompletedReservations.isEmpty()){return null;}

        else {return uncompletedReservations;}
    }

    public List<Reservation> getAllUncompletedReservationsOfHotelWithNumber(Long reservationNumber,HotelModel hotelModel) {
        List<Reservation> uncompletedReservationsWithNumber = reservationRepository.getAllUncompletedReservationsOfHotelWithNumber(reservationNumber,hotelModel.toEntity());

        if(uncompletedReservationsWithNumber.isEmpty()){return null;}

        else {return uncompletedReservationsWithNumber;}
    }

    public Long getLastReservationNumberOfHotel(HotelModel hotelModel) {
        return reservationRepository.getLastReservationNumberOfHotel(hotelModel.toEntity());
    }

    public ReservationModel getReservationWithNumber(Long reservationNumber, HotelModel hotelModel) {
        Reservation reservation = reservationRepository.getReservationWithNumber(reservationNumber,hotelModel.toEntity());
        return (reservation == null) ? null : new ReservationModel(reservation);
    }



    public List<Reservation> getAllReservationsWithSameNumber(Long reservationNumber, HotelModel hotelModel) {
        List<Reservation> reservationsWithSameNumber = reservationRepository.getAllReservationsWithSameNumber(reservationNumber,hotelModel.toEntity());

        if(reservationsWithSameNumber.isEmpty()) {return null;}

        else {return reservationsWithSameNumber;}
    }

    public List<Integer> getAllReservationsWithSameNumber_RoomsNumbers(Long reservationNumber, HotelModel hotelModel) {
        List<Integer> reservationsWithSameNumber_RoomsNumbers = reservationRepository.getAllReservationsWithSameNumber_RoomsNumbers(reservationNumber,hotelModel.toEntity());

        if(reservationsWithSameNumber_RoomsNumbers.isEmpty()) {return null;}

        else {return reservationsWithSameNumber_RoomsNumbers;}
    }

    public Long getRoomIdFromReservations(Long id) {
        Long roomId = reservationRepository.getRoomId(id);
        return roomId;
    }

    public boolean isRoomExistsInReservations(Long id) {
        return getRoomIdFromReservations(id) != null;
    }

    public Long getHotelIdByRoomId(Long id){
        Long hotelId = reservationRepository.getHotelIdByRoomById(id);
        return hotelId;
    }


    public String getReceptionistIdFromReservations(String id) {
        String receptionistId = reservationRepository.getReceptionistId(id);
        return receptionistId;
    }

    public boolean isReceptionistExistsInReservations(String id) {
        return getReceptionistIdFromReservations(id) != null;
    }

    public List<ReservationModel> getAllReservationsWithSameReceptionistId(String receptionistId,HotelModel hotelModel) {
        List<Reservation> reservationsWithSameReceptionistId = reservationRepository.getAllReservationsWithSameReceptionistId(receptionistId,hotelModel.toEntity());

        if(reservationsWithSameReceptionistId.isEmpty()){return null;}

        else
        {
            return FXCollections.observableList(
                    reservationsWithSameReceptionistId.stream().map(rr -> new ReservationModel(
                            rr.getId(),
                            rr.getNumber(),
                            rr.getType(),
                            rr.getStatus(),
                            rr.getCreatedAt(),
                            rr.getStartDate(),
                            rr.getEndDate(),
                            new HotelModel(rr.getHotel()),
                            new UserModel(rr.getReceptionist()),
                            new CustomerModel(rr.getCustomer()),
                            rr.getCustomerRating(),
                            new RoomModel(rr.getRoom()),
                            rr.getNightsOccupied(),
                            rr.getRoomRating(),
                            rr.getServiceList(),
                            rr.getRequestSent(),
                            rr.getFinalAnnulationDate(),
                            rr.getTotalPrice()
                    )).collect(Collectors.toList())
            );
        }
    }

    public Long getHotelIdByReceptionistId(String id){
        Long hotelId = reservationRepository.getHotelIdByReceptionistById(id);
        return hotelId;
    }

    //NULL POINTER EXCEPTION IS NOT CHECKED!!!!!!!!!!(because I use this method only if I am sure it will not be null)
    public ReservationModel getReservationById(Long id,HotelModel hotelModel) {
        return reservationRepository.getReservationWithId(id,hotelModel.toEntity()).toModel();
    }

    //NULL POINTER EXCEPTION IS NOT CHECKED!!!!!!!!!!
    public Long checkIfRoomCanBeEditedOrDeleted(Long id, HotelModel hotelModel){
        Long roomId = reservationRepository.checkIfRoomCanBeEditedOrDeleted(id, hotelModel.toEntity());
        return roomId;
    }

    public boolean addReservation(ReservationModel reservationModel)
    {
        return reservationRepository.save(reservationModel.toEntity());
    }

    public boolean updateReservation(ReservationModel reservationModel)
    {
        return reservationRepository.update(reservationModel.toEntity());
    }

    public boolean deleteReservation(ReservationModel reservationModel)
    {
        return reservationRepository.delete(reservationModel.toEntity());
    }


    public boolean validateDaysAnnulationField(String phone) {
        String regex = "^([0-9]{1})$";

        Pattern p = Pattern.compile(regex);
        if(phone == null) {return false;}
        else
        {
            Matcher m = p.matcher(phone);
            return m.matches();
        }
    }


    public void refreshUncompletedReservationsStatus(HotelModel hotelModel){
        List<Reservation> uncompletedReservationsOfHotel = getAllUncompletedReservationsOfHotel(hotelModel);
        if(uncompletedReservationsOfHotel!=null)
        {

            DateTime today = new DateTime((new Timestamp(System.currentTimeMillis())).getTime());
            for(Reservation reservation: uncompletedReservationsOfHotel)
            {
                if(Minutes.minutesBetween(today,new DateTime(reservation.getStartDate().getTime())).getMinutes()>0 && !reservation.getStatus().equals("незапочнала"))//if today is before startDate
                {
                    reservation.setStatus("незапочнала");
                    if(!updateReservation(reservation.toModel()))
                    {
                        log.info("ERROR when refreshing uncompleted[status!='обработена'] reservations.");
                        AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","Възникна грешка. Моля презаредете страницата.");
                        return;
                    }
                }
                //if today is between startDate and endDate(calculated with precision to minutes)
                else if(Minutes.minutesBetween(today,new DateTime(reservation.getStartDate().getTime())).getMinutes()<=0 && Minutes.minutesBetween(today,new DateTime(reservation.getEndDate().getTime())).getMinutes()>0 && !reservation.getStatus().equals("активна"))
                {
                    reservation.setStatus("активна");
                    if (!updateReservation(reservation.toModel()))
                    {
                        log.info("ERROR when refreshing uncompleted[status!='обработена'] reservations.");
                        AlertManager.showAlert(Alert.AlertType.ERROR, "Грешка", "Възникна грешка. Моля презаредете страницата.");
                        return;
                    }
                }
                //if today is after endDate(calculated with precision to minutes)
                else if(Minutes.minutesBetween(today,new DateTime(reservation.getEndDate().getTime())).getMinutes()<0 && !reservation.getStatus().equals("изтекла"))
                {
                    reservation.setStatus("изтекла");
                    if (!updateReservation(reservation.toModel()))
                    {
                        log.info("ERROR when refreshing uncompleted[status!='обработена'] reservations.");
                        AlertManager.showAlert(Alert.AlertType.ERROR, "Грешка", "Възникна грешка. Моля презаредете страницата.");
                        return;
                    }
                }
            }
            log.info("Uncompleted[status!='обработена'] reservations refreshed.");
        }
    }

}