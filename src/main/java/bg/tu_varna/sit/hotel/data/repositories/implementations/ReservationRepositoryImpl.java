package bg.tu_varna.sit.hotel.data.repositories.implementations;

import bg.tu_varna.sit.hotel.data.access.Connection;
import bg.tu_varna.sit.hotel.data.entities.Reservation;
import bg.tu_varna.sit.hotel.data.repositories.interfaces.ReservationRepository;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

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


}