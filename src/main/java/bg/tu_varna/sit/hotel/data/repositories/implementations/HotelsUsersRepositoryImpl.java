package bg.tu_varna.sit.hotel.data.repositories.implementations;

import bg.tu_varna.sit.hotel.data.access.Connection;
import bg.tu_varna.sit.hotel.data.entities.HotelsUsers;
import bg.tu_varna.sit.hotel.data.repositories.interfaces.HotelsUsersRepository;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.tool.schema.spi.CommandAcceptanceException;

import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class HotelsUsersRepositoryImpl implements HotelsUsersRepository<HotelsUsers> {

    private static final Logger log = Logger.getLogger(HotelsUsersRepositoryImpl.class);

    //lazy-loaded singleton pattern
    public static HotelsUsersRepositoryImpl getInstance() { return HotelsUsersRepositoryImpl.HotelsUsersRepositoryHolder.INSTANCE;}

    private static class HotelsUsersRepositoryHolder {
        public static final HotelsUsersRepositoryImpl INSTANCE = new HotelsUsersRepositoryImpl();
    }

    //https://stackoverflow.com/questions/34147863/java-how-to-ignore-some-sqlexception
    //https://stackoverflow.com/questions/19774745/grails-ora-02275-such-a-referential-constraint-already-exist
    @Override
    public boolean save(HotelsUsers obj) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(obj); //insert object into table
            transaction.commit();//commit changes to the database
            log.info("(Hotel+User) added successfully.");
            return true;
        } catch (Exception e) {
            log.error("(Hotel+User) add error: " + e);
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean update(HotelsUsers obj) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.update(obj);
            transaction.commit();
            log.info("(Hotel+User) updated successfully.");
            return true;
        } catch (Exception e) {
            log.error("(Hotel+User) update error: " + e);
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean delete(HotelsUsers obj) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.delete(obj);
            transaction.commit();
            log.info("(Hotel+User) deleted successfully.");
            return true;
        }  catch (Exception e) {
            log.error("(Hotel+User) delete error: " + e);
            return false;
        } finally {
            session.close();
        }
    }


    @Override
    public List<HotelsUsers> getAll() {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        List<HotelsUsers> hotelsUsers = new LinkedList<>();
        try{
            String jpql = "SELECT hu FROM HotelsUsers hu";
            hotelsUsers.addAll(session.createQuery(jpql, HotelsUsers.class).getResultList());
            transaction.commit();
            log.info("Got all (hotels+users) successfully.");
        } catch (Exception ex) {
            log.error("Get all (hotels+users) error: " + ex.getMessage());
        } finally {
            session.close();
        }
        return hotelsUsers;
    }


}