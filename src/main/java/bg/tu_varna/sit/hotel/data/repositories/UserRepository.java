package bg.tu_varna.sit.hotel.data.repositories;

import bg.tu_varna.sit.hotel.data.entities.User;
import bg.tu_varna.sit.hotel.data.access.Connection;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public class UserRepository implements EntityRepository<User> {

    private static final Logger log = Logger.getLogger(UserRepository.class);

    //lazy-loaded singleton pattern
    public static UserRepository getInstance() { return UserRepository.UserRepositoryHolder.INSTANCE;}

    private static class UserRepositoryHolder {
        public static final UserRepository INSTANCE = new UserRepository();
    }


    @Override
    public void save(User obj) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(obj);
            transaction.commit();
            log.info("User saved successfully.");
        } catch (Exception ex) {
            log.error("User save error: " + ex);
            transaction.rollback();
        } finally {
            session.close();
        }

         }

    @Override
    public void update(User obj) {

    }

    @Override
    public void delete(User obj) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();

        try {
            session.delete(obj);
            transaction.commit();
            log.info("Station has been deleted.");
        }  catch (Exception e) {
            log.info("Failed to delete station: " + e.getMessage());
            transaction.rollback();
        } finally {
            session.close();
        }

    }

    @Override
    public Optional<User> getById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<User> getAll() {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        List<User> users = new LinkedList<>();
        try{
            String jpql = "SELECT u FROM User u";
            users.addAll(session.createQuery(jpql, User.class).getResultList());
            log.info("Get all users.");
        } catch (Exception ex) {
            log.error("Get all users error: " + ex.getMessage());
        } finally {
            transaction.commit();
        }
        return users;
    }
}
