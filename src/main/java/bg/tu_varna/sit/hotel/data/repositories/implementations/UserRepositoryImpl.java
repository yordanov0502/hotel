package bg.tu_varna.sit.hotel.data.repositories.implementations;

import bg.tu_varna.sit.hotel.common.AlertManager;
import bg.tu_varna.sit.hotel.common.Hasher;
import bg.tu_varna.sit.hotel.data.entities.User;
import bg.tu_varna.sit.hotel.data.access.Connection;
import bg.tu_varna.sit.hotel.data.repositories.interfaces.UserRepository;
import javafx.scene.control.Alert;
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
    public boolean save(User obj) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(obj); //insert object into table
            transaction.commit();//commit changes to the database
            log.info("User added successfully.");
            return true;
        } catch (Exception ex) {
            log.error("User add error: " + ex);
            return false;
        } finally {
            session.close();
        }
         }

    @Override
    public boolean update(User obj) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.update(obj);
            transaction.commit();
            log.info("User updated successfully.");
            return true;
        } catch (Exception ex) {
            log.error("User update error: " + ex);
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean delete(User obj) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.delete(obj);
            transaction.commit();
            log.info("User deleted successfully.");
            return true;
        }  catch (Exception e) {
            log.info("User delete error: " + e.getMessage());
            return false;
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
            log.info("Got all users successfully.");
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
            String jpql = "SELECT u FROM User u WHERE id = '" + id + "'";
            user = (User) session.createQuery(jpql).getSingleResult();
            transaction.commit();
            log.info("Got user by id successfully.");
        } catch(Exception ex) {
            log.error("Get user by id error: " + ex.getMessage());
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
            log.info("Got user by phone successfully.");
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
            log.info("Got user by username successfully.");
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
            log.info("Got user by email successfully.");
        } catch(Exception ex) {
            log.error("Get user by email error: " + ex.getMessage());
        } finally {
            session.close();
        }
        return user;
    }

    @Override
    public User getByUsernameAndPassword(String username, String password,String role) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        password= Hasher.SHA512.hash(password);//transforms entered password into hash
        User user = null;

        try{
            String jpql = "SELECT u FROM User u WHERE username = '" + username + "' AND password = '" + password + "' AND role = '" + role + "'";
            user = (User) session.createQuery(jpql).getSingleResult();
            transaction.commit();
            log.info("Got user by username & password successfully.");
        } catch (Exception ex) {
            log.error("Get user by username & password error: " + ex.getMessage());
        } finally {
            session.close();
        }
        return user;
    }
}