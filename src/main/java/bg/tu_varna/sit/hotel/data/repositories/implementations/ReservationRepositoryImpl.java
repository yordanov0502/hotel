package bg.tu_varna.sit.hotel.data.repositories.implementations;

import bg.tu_varna.sit.hotel.data.access.Connection;
import bg.tu_varna.sit.hotel.data.entities.Hotel;
import bg.tu_varna.sit.hotel.data.entities.Reservation;
import bg.tu_varna.sit.hotel.data.entities.Room;
import bg.tu_varna.sit.hotel.data.entities.User;
import bg.tu_varna.sit.hotel.data.repositories.interfaces.ReservationRepository;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.sql.Timestamp;
import java.time.temporal.ChronoUnit;
import java.util.LinkedList;
import java.util.List;

public class ReservationRepositoryImpl implements ReservationRepository<Reservation> {

    private static final Logger log = Logger.getLogger(ReservationRepositoryImpl.class);

    //lazy-loaded singleton pattern
    public static ReservationRepositoryImpl getInstance() { return ReservationRepositoryImpl.ReservationRepositoryHolder.INSTANCE;}

    private static class ReservationRepositoryHolder {
        public static final ReservationRepositoryImpl INSTANCE = new ReservationRepositoryImpl();
    }




    @Override
    public boolean save(Reservation obj) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(obj); //insert object into table
            transaction.commit();//commit changes to the database
            log.info("Reservation added successfully.");
            return true;
        } catch (Exception e) {
            log.error("Reservation add error: " + e);
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean update(Reservation obj) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.update(obj);
            transaction.commit();
            log.info("Reservation updated successfully.");
            return true;
        } catch (Exception e) {
            log.error("Reservation update error: " + e);
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean delete(Reservation obj) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.delete(obj);
            transaction.commit();
            log.info("Reservation deleted successfully.");
            return true;
        }  catch (Exception e) {
            log.info("Reservation delete error: " + e.getMessage());
            return false;
        } finally {
            session.close();
        }
    }

    //Source of code : https://stackoverflow.com/questions/35831746/sql-query-to-find-free-rooms-in-hotel
    @Override
    public List<Room> getAllFreeRooms(Hotel hotel, Timestamp startDate, Timestamp endDate) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        List<Room> rooms = new LinkedList<>();
        try{
            String jpql = "SELECT r FROM Room r " +
                          "LEFT JOIN Reservation rr ON (rr.room.id = r.id " +
                          "AND NOT ( " +
                          "(rr.startDate < '"+startDate+"' AND rr.endDate < '"+startDate+"') " +
                          "OR (rr.startDate > '"+endDate+"' AND rr.endDate > '"+endDate+"'))) " +
                          "WHERE rr.room.id IS NULL AND r.hotel.id = '"+hotel.getId()+"' AND r.number != '"+13+"'" +
                          "ORDER BY r.number ASC";

            rooms.addAll(session.createQuery(jpql, Room.class).getResultList());
            transaction.commit();
            log.info("Got all free rooms successfully.");
        } catch (Exception e) {
            log.error("Get all  free rooms error: " + e.getMessage());
        } finally {
            session.close();
        }
        return rooms;
    }

    @Override
    public Long getLastReservationNumberOfHotel(Hotel hotel) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Long lastReservationNumber = null;
        try{
           String jpql = "SELECT MAX (rr.number)  FROM Reservation rr WHERE rr.hotel.id = '"+hotel.getId()+"'";
            lastReservationNumber = (Long) session.createQuery(jpql).getSingleResult();
            transaction.commit();
            log.info("Got last reservation number of hotel successfully.");
        } catch (Exception e) {
            log.error("Get last reservation number of hotel error: " + e.getMessage());
        } finally {
            session.close();
        }
        return lastReservationNumber;
    }

    @Override
    public List<Reservation> getReservationsForPeriod(Hotel hotel,Timestamp startDate,Timestamp endDate) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        List<Reservation> reservationsWithUniqueRoomIds = new LinkedList<>();
        try{
            String jpql = "SELECT rr FROM Reservation rr WHERE ( (rr.startDate < '"+startDate+"' AND rr.endDate >= '"+startDate+"' AND rr.endDate <= '"+endDate+"') OR (rr.startDate >= '"+startDate+"' AND rr.endDate <= '"+endDate+"')  OR (rr.startDate >= '"+startDate+"' AND rr.startDate <= '"+endDate+"' AND rr.endDate > '"+endDate+"') OR (rr.startDate < '"+startDate+"' AND rr.endDate > '"+endDate+"' ) OR ( '"+startDate+"' < rr.startDate AND '"+endDate+"' > rr.endDate ) )   AND rr.hotel = '"+ hotel.getId() +"' ORDER BY rr.endDate ASC";

            //String jpql = "SELECT rr FROM Reservation rr WHERE NOT EXISTS ( SELECT rr FROM Reservation rr WHERE (rr.endDate < '"+startDate+"' AND rr.startDate < '"+startDate+"') OR (rr.startDate > '"+endDate+"' AND rr.endDate > '"+endDate+"') ) ORDER BY rr.startDate ASC";

            reservationsWithUniqueRoomIds.addAll(session.createQuery(jpql, Reservation.class).getResultList());
            transaction.commit();
            log.info("Got all reservations of hotel for period(or around) successfully.");
        } catch (Exception e) {
            log.error("Get all reservations of hotel for period(or around) error: " + e.getMessage());
        } finally {
            session.close();
        }
        return reservationsWithUniqueRoomIds;
    }

    @Override
    public Reservation getReservationWithNumber(Long reservationNumber, Hotel hotel) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Reservation reservation = null;
        try{
            String jpql = "SELECT rr FROM Reservation rr WHERE rr.number = '"+reservationNumber+"' AND rr.hotel = '"+ hotel.getId() +"' AND ROWNUM <=1 ORDER BY rr.room.number ";
            reservation = (Reservation) session.createQuery(jpql).getSingleResult();
            transaction.commit();
            log.info("Got reservation of hotel successfully by number.");
        } catch (Exception e) {
            log.error("Get reservation of hotel by number error: " + e.getMessage());
        } finally {
            session.close();
        }
        return reservation;
    }

    @Override
    public List<Reservation> getAllReservationsWithSameNumber(Long reservationNumber, Hotel hotel) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        List<Reservation> reservationsWithSameNumber = new LinkedList<>();
        try{
            String jpql = "SELECT rr FROM Reservation rr WHERE rr.number = '"+reservationNumber+"' AND rr.hotel = '"+ hotel.getId() +"' ORDER BY rr.room.number";
            reservationsWithSameNumber.addAll(session.createQuery(jpql, Reservation.class).getResultList());
            transaction.commit();
            log.info("Got all reservations of hotel successfully by same number.");
        } catch (Exception e) {
            log.error("Get all reservations of hotel by same number error: " + e.getMessage());
        } finally {
            session.close();
        }
        return reservationsWithSameNumber;
    }

    @Override
    public List<Integer> getAllReservationsWithSameNumber_RoomsNumbers(Long reservationNumber, Hotel hotel) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        List<Integer> reservationsWithSameNumber_RoomNumbers = new LinkedList<>();
        try{
            String jpql = "SELECT rr.room.number FROM Reservation rr WHERE rr.number = '"+reservationNumber+"' AND rr.hotel = '"+ hotel.getId() +"' ORDER BY rr.room.number";
            reservationsWithSameNumber_RoomNumbers.addAll(session.createQuery(jpql, Integer.class).getResultList());
            transaction.commit();
            log.info("Got all reservations of hotel successfully by same number _ rooms numbers.");
        } catch (Exception e) {
            log.error("Get all reservations of hotel by same number _ rooms numbers error: " + e.getMessage());
        } finally {
            session.close();
        }
        return reservationsWithSameNumber_RoomNumbers;
    }

    @Override
    public List<Reservation> getAllUncompletedReservationsOfHotel(Hotel hotel) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        List<Reservation> uncompletedReservations = new LinkedList<>();
        try{
            String jpql = "SELECT rr FROM Reservation rr WHERE rr.hotel.id = '"+hotel.getId()+"' AND rr.status != 'обработена'" +
                    " ORDER BY (\n" +
                    "    CASE rr.status\n" +
                    "    \n" +
                    "    WHEN 'изтекла'\n" +
                    "    THEN 1\n" +
                    "    \n" +
                    "    WHEN 'активна'\n" +
                    "    THEN 2\n" +
                    "    \n" +
                    "    WHEN 'незапочнала'\n" +
                    "    THEN 3\n" +
                    "    END\n" +
                    ") ASC";
            uncompletedReservations.addAll(session.createQuery(jpql, Reservation.class).getResultList());
            transaction.commit();
            log.info("Got all uncompleted reservations of hotel successfully.");
        } catch (Exception e) {
            log.error("Get all uncompleted reservations of hotel error: " + e.getMessage());
        } finally {
            session.close();
        }
        return uncompletedReservations;
    }

    @Override
    public List<Reservation> getAllUncompletedReservationsOfHotelWithNumber(Long reservationNumber, Hotel hotel) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        List<Reservation> uncompletedReservationsWithNumber = new LinkedList<>();
        try{
            String jpql = "SELECT rr FROM Reservation rr WHERE " +
                    "rr.number = '"+reservationNumber+"' AND rr.hotel = '"+ hotel.getId() +"' AND rr.status != 'обработена'";

            uncompletedReservationsWithNumber.addAll(session.createQuery(jpql, Reservation.class).getResultList());
            transaction.commit();
            log.info("Got all uncompleted reservations of hotel successfully by number.");
        } catch (Exception e) {
            log.error("Get all uncompleted reservations of hotel successfully by number error: " + e.getMessage());
        } finally {
            session.close();
        }
        return uncompletedReservationsWithNumber;
    }

    public Long getRoomId(Long id){
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Long roomId = null;
        try{
            String jpql = "SELECT rr.room.id FROM Reservation rr WHERE rr.room.id = '"+id+"' AND ROWNUM <=1";
            roomId = (Long) session.createQuery(jpql).getSingleResult();
            transaction.commit();
            log.info("Got room_id from reservations successfully.");
        } catch (Exception e) {
            log.error("Get room_id from reservations error: " + e.getMessage());
        } finally {
            session.close();
        }
        return roomId;
    }

    @Override
    public Long getHotelIdByRoomById(Long id){
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Long hotelId = null;
        try{
            String jpql = "SELECT rr.hotel.id FROM Reservation rr WHERE rr.room.id = '"+id+"' AND ROWNUM <=1";
            hotelId = (Long) session.createQuery(jpql).getSingleResult();
            transaction.commit();
            log.info("Got hotel_id from reservations by room_id successfully.");
        } catch (Exception e) {
            log.error("Get hotel_id from reservations by room_id error: " + e.getMessage());
        } finally {
            session.close();
        }
        return hotelId;
    }

    @Override
    public List<Reservation> getAllReservationsWithSameRoomId(Long roomId, Hotel hotel) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        List<Reservation> reservationsWithSameRoomId = new LinkedList<>();
        try{
            String jpql = "SELECT rr FROM Reservation rr WHERE rr.room.id = '"+roomId+"' AND rr.hotel = '"+ hotel.getId() +"' ORDER BY rr.number ASC";
            reservationsWithSameRoomId.addAll(session.createQuery(jpql, Reservation.class).getResultList());
            transaction.commit();
            log.info("Got all reservations of hotel successfully by same room_id.");
        } catch (Exception e) {
            log.error("Get all reservations of hotel by same room_id error: " + e.getMessage());
        } finally {
            session.close();
        }
        return reservationsWithSameRoomId;
    }

    @Override
    public Reservation getReservationWithId(Long reservationId, Hotel hotel) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Reservation reservation = null;
        try{
            String jpql = "SELECT rr FROM Reservation rr WHERE rr.id = '"+reservationId+"' AND rr.hotel = '"+ hotel.getId() +"'";
            reservation = (Reservation) session.createQuery(jpql).getSingleResult();
            transaction.commit();
            log.info("Got reservation of hotel successfully by id.");
        } catch (Exception e) {
            log.error("Get reservation of hotel by id error: " + e.getMessage());
        } finally {
            session.close();
        }
        return reservation;
    }

    @Override
    public String getReceptionistId(String id){
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        String receptionistId = null;
        try{
            String jpql = "SELECT rr.receptionist.id FROM Reservation rr WHERE rr.receptionist.id = '"+id+"' AND ROWNUM <=1";
            receptionistId = (String) session.createQuery(jpql).getSingleResult();
            transaction.commit();
            log.info("Got receptionist_id from reservations successfully.");
        } catch (Exception e) {
            log.error("Get receptionist_id from reservations error: " + e.getMessage());
        } finally {
            session.close();
        }
        return receptionistId;
    }

    @Override
    public List<Reservation> getAllReservationsWithSameReceptionistId(String receptionistId, Hotel hotel) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        List<Reservation> reservationsWithSameReceptionistId = new LinkedList<>();
        try{
            String jpql = "SELECT rr FROM Reservation rr WHERE rr.receptionist.id = '"+receptionistId+"' AND rr.hotel = '"+ hotel.getId() +"' ORDER BY rr.number ASC";
            reservationsWithSameReceptionistId.addAll(session.createQuery(jpql, Reservation.class).getResultList());
            transaction.commit();
            log.info("Got all reservations of hotel successfully by same receptionist_id.");
        } catch (Exception e) {
            log.error("Get all reservations of hotel by same receptionist_id error: " + e.getMessage());
        } finally {
            session.close();
        }
        return reservationsWithSameReceptionistId;
    }

    @Override
    public Long getHotelIdByReceptionistById(String id){
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Long hotelId = null;
        try{
            String jpql = "SELECT rr.hotel.id FROM Reservation rr WHERE rr.receptionist.id = '"+id+"' AND ROWNUM <=1";
            hotelId = (Long) session.createQuery(jpql).getSingleResult();
            transaction.commit();
            log.info("Got hotel_id from reservations by receptionist_id successfully.");
        } catch (Exception e) {
            log.error("Get hotel_id from reservations by receptionist_id error: " + e.getMessage());
        } finally {
            session.close();
        }
        return hotelId;
    }

    @Override
    public Long checkIfRoomCanBeEditedOrDeleted(Long id,Hotel hotel){
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Long roomId = null;
        try{
            String jpql = "SELECT rr.room.id FROM Reservation rr WHERE rr.hotel.id = '"+hotel.getId()+"' AND rr.room.id = '"+id+"' AND rr.status != 'обработена'  AND ROWNUM <=1";
            roomId = (Long) session.createQuery(jpql).getSingleResult();
            transaction.commit();
            log.info("Got room_id from reservations by hotel_id successfully.");
        } catch (Exception e) {
            log.error("Get room_id from reservations by hotel_id error: " + e.getMessage());
        } finally {
            session.close();
        }
        return roomId;
    }


    @Override
    public List<Reservation> getAllUncompletedReservationsOfCustomerById(Long customerId, Hotel hotel) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        List<Reservation> uncompletedReservationsOfCustomer = new LinkedList<>();
        try{
            String jpql = "SELECT rr FROM Reservation rr WHERE rr.customer.id = '"+customerId+"' AND rr.hotel = '"+ hotel.getId() +"' AND rr.status != 'обработена' ORDER BY rr.number ASC";
            uncompletedReservationsOfCustomer.addAll(session.createQuery(jpql, Reservation.class).getResultList());
            transaction.commit();
            log.info("Got all uncompleted reservations of customer successfully by customer_id.");
        } catch (Exception e) {
            log.error("Get all uncompleted reservations of customer by customer_id error: " + e.getMessage());
        } finally {
            session.close();
        }
        return uncompletedReservationsOfCustomer;
    }


    @Override
    public List<Reservation> getAllReservationsOfHotelWithoutExpiryNotification(Hotel hotel) {

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        Timestamp today = Timestamp.from(timestamp.toInstant().plus(2, ChronoUnit.HOURS));

        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        List<Reservation> allReservationsWithoutExpiryNotification = new LinkedList<>();
        try{
            String jpql = "SELECT rr FROM Reservation rr WHERE rr.hotel = '"+ hotel.getId() +"' AND (rr.status = 'изтекла' OR (rr.status = 'активна' AND   rr.endDate < '"+today+"')) AND rr.notificationSent = '"+ 0 +"'";
            allReservationsWithoutExpiryNotification.addAll(session.createQuery(jpql, Reservation.class).getResultList());
            transaction.commit();
            log.info("Got all reservations about to or already expired without notification sent.");
        } catch (Exception e) {
            log.error("Get all reservations about to or already expired without notification sent error: " + e.getMessage());
        } finally {
            session.close();
        }
        return allReservationsWithoutExpiryNotification;
    }


    @Override
    public List<Reservation> getAllReservationsOfHotelWithExpiryNotification(Hotel hotel) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        List<Reservation> allReservationsWithoutExpiryNotification = new LinkedList<>();
        try{
            String jpql = "SELECT rr FROM Reservation rr WHERE rr.hotel = '"+ hotel.getId() +"' AND (rr.status = 'изтекла' OR rr.status = 'активна') AND rr.notificationSent = '"+ 1 +"'";
            allReservationsWithoutExpiryNotification.addAll(session.createQuery(jpql, Reservation.class).getResultList());
            transaction.commit();
            log.info("Got all reservations about to or already expired with notification sent.");
        } catch (Exception e) {
            log.error("Get all reservations about to or already expired with notification sent error: " + e.getMessage());
        } finally {
            session.close();
        }
        return allReservationsWithoutExpiryNotification;
    }


    @Override
    public List<Reservation> getAllReservationsOfReceptionist(User receptionist, Hotel hotel) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        List<Reservation> allReservationsOfReceptionist = new LinkedList<>();
        try{
            String jpql = "SELECT rr FROM Reservation rr WHERE rr.receptionist.id = '"+receptionist.getId()+"' AND rr.hotel = '"+ hotel.getId() +"'";
            allReservationsOfReceptionist.addAll(session.createQuery(jpql, Reservation.class).getResultList());
            transaction.commit();
            log.info("Got all reservations of receptionist successfully.");
        } catch (Exception e) {
            log.error("Get all reservations of receptionist error: " + e.getMessage());
        } finally {
            session.close();
        }
        return allReservationsOfReceptionist;
    }




    public Long getCountOfReservationNumber(Long number, Hotel hotel) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Long  countOfReservationNumber = 0L;
        try{
            String jpql = "SELECT COUNT (rr.number) FROM Reservation rr WHERE rr.number = '"+number+"' AND rr.hotel = '"+ hotel.getId() +"'";
            countOfReservationNumber = (Long) session.createQuery(jpql).getSingleResult();
            transaction.commit();
            log.info("Got count of reservation number "+number+" of hotel \""+hotel.getName()+"\" successfully.");
        } catch (Exception e) {
            log.error("Get count of reservation number "+number+" of hotel \""+hotel.getName()+"\" error: " + e.getMessage());
        } finally {
            session.close();
        }
        return countOfReservationNumber;
    }

    @Override
    public List<Reservation> getReservationsOfCustomerForPeriod(Hotel hotel, Timestamp startDate, Timestamp endDate, Long customerId) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        List<Reservation> reservationsOfCustomer = new LinkedList<>();
        try{
            String jpql = "SELECT rr  FROM Reservation rr WHERE  rr.customer.id = '"+customerId+"' AND ( (rr.startDate < '"+startDate+"' AND rr.endDate >= '"+startDate+"' AND rr.endDate <= '"+endDate+"') OR (rr.startDate >= '"+startDate+"' AND rr.endDate <= '"+endDate+"')  OR (rr.startDate >= '"+startDate+"' AND rr.startDate <= '"+endDate+"' AND rr.endDate > '"+endDate+"') OR (rr.startDate < '"+startDate+"' AND rr.endDate > '"+endDate+"' ) )   AND rr.hotel = '"+ hotel.getId() +"'  ORDER BY rr.endDate ASC";

            reservationsOfCustomer.addAll(session.createQuery(jpql, Reservation.class).getResultList());
            transaction.commit();
            log.info("Got all reservations of customer of hotel for period(or around) successfully.");
        } catch (Exception e) {
            log.error("Got all reservations of customer of hotel for period(or around) error: " + e.getMessage());
        } finally {
            session.close();
        }
        return reservationsOfCustomer;
    }

}