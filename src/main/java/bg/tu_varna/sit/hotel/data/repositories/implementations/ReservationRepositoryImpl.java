package bg.tu_varna.sit.hotel.data.repositories.implementations;

import bg.tu_varna.sit.hotel.data.access.Connection;
import bg.tu_varna.sit.hotel.data.entities.Hotel;
import bg.tu_varna.sit.hotel.data.entities.Reservation;
import bg.tu_varna.sit.hotel.data.entities.Room;
import bg.tu_varna.sit.hotel.data.repositories.interfaces.ReservationRepository;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.sql.Timestamp;
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

    @Override
    public List<Room> getAllFreeRooms(Hotel hotel, Timestamp startDate, Timestamp endDate) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        List<Room> rooms = new LinkedList<>();
        try{
            String jpql = "SELECT r FROM Room r " +
                          "LEFT JOIN Reservation rr ON rr.room.id = r.id "+
                          "WHERE r.hotel.id = '"+hotel.getId()+"' AND (rr.room.id IS NULL OR ( ('"+startDate+"' > rr.endDate) OR ('"+endDate+"' < rr.startDate) ) ) ORDER BY r.number";


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
            String jpql = "SELECT COUNT(*) FROM Reservation rr WHERE rr.hotel.id = '"+hotel.getId()+"'";
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
    public Reservation getReservationWithNumber(Long reservationNumber, Hotel hotel) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Reservation reservation = null;
        try{
            String jpql = "SELECT rr FROM Reservation rr WHERE rr.number = '"+reservationNumber+"' AND rr.hotel = '"+ hotel.getId() +"'";
            reservation = (Reservation) session.createQuery(jpql).getSingleResult();
            transaction.commit();
            log.info("Got a reservation of hotel successfully by number.");
        } catch (Exception e) {
            log.error("Get a reservation of hotel number error: " + e.getMessage());
        } finally {
            session.close();
        }
        return reservation;
    }

}