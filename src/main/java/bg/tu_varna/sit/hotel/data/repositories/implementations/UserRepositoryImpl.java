package bg.tu_varna.sit.hotel.data.repositories.implementations;

import bg.tu_varna.sit.hotel.data.entities.User;
import bg.tu_varna.sit.hotel.data.access.Connection;
import bg.tu_varna.sit.hotel.data.repositories.interfaces.UserRepository;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.LinkedList;
import java.util.List;

public class UserRepositoryImpl implements UserRepository<User> {

    private static final Logger log = Logger.getLogger(UserRepositoryImpl.class);

    //lazy-loaded singleton pattern
    public static UserRepositoryImpl getInstance() { return UserRepositoryImpl.UserRepositoryHolder.INSTANCE;}

    private static class UserRepositoryHolder {
        public static final UserRepositoryImpl INSTANCE = new UserRepositoryImpl();
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
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.update(obj);
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
    public List<User> getAll() {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        List<User> users = new LinkedList<>();
        try{
            String jpql = "SELECT u FROM User u";
            users.addAll(session.createQuery(jpql, User.class).getResultList());
            transaction.commit();
            log.info("Get all users.");
        } catch (Exception ex) {
            log.error("Get all users error: " + ex.getMessage());
        } finally {
            session.close();
        }
        return users;
    }

    @Override //EGN
    public User getById(String id) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        User user = null;

        try {
            user =  (User) session.get(User.class, id);
            transaction.commit();
            log.info("Get User by id successfully.");
        } catch(Exception ex) {
            log.error("Get all users error: " + ex.getMessage());
        } finally {
            session.close();
        }
        return user;
    }



    @Override
    public User getByPhone(String phone) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        User user = null;

        try{
            String jpql = "SELECT u FROM User u WHERE phone = '" + phone + "'";
            user = (User) session.createQuery(jpql).getSingleResult();
            transaction.commit();
            log.info("Get User by phone successfully.");
        } catch(Exception ex) {
            log.error("Get user by phone error: " + ex.getMessage());
        } finally {
            session.close();
        }

        return user;
    }

    @Override
    public User getByUsername(String username) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        User user = null;

        try {
            String jpql = "SELECT u FROM User u WHERE username = '" + username + "'";
            user = (User) session.createQuery(jpql).getSingleResult();
            transaction.commit();
            log.info("Get User by username successfully.");
        } catch(Exception ex) {
            log.error("Get user by username error: " + ex.getMessage());
        } finally {
            session.close();
        }

        return user;
    }

    @Override
    public User getByEmail(String email) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        User user = null;

        try{
            String jpql = "SELECT u FROM User u WHERE email = '" + email + "'";
            user = (User) session.createQuery(jpql).getSingleResult();
            transaction.commit();
            log.info("Get User by email successfully.");
        } catch(Exception ex) {
            log.error("Get user by email error: " + ex.getMessage());
        } finally {
            session.close();
        }

        return user;
    }




}