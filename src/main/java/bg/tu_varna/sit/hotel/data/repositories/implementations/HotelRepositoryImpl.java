package bg.tu_varna.sit.hotel.data.repositories.implementations;

import bg.tu_varna.sit.hotel.data.access.Connection;
import bg.tu_varna.sit.hotel.data.entities.Hotel;
import bg.tu_varna.sit.hotel.data.entities.User;
import bg.tu_varna.sit.hotel.data.repositories.interfaces.HotelRepository;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.LinkedList;
import java.util.List;

public class HotelRepositoryImpl implements HotelRepository<Hotel> {

    private static final Logger log = Logger.getLogger(HotelRepositoryImpl.class);

    //lazy-loaded singleton pattern
    public static HotelRepositoryImpl getInstance() {return HotelRepositoryImpl.HotelRepositoryHolder.INSTANCE;}

    private static class HotelRepositoryHolder {
        public static final HotelRepositoryImpl INSTANCE = new HotelRepositoryImpl();
    }

    @Override
    public boolean save(Hotel obj) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(obj); //insert object into table
            transaction.commit();//commit changes to the database
            log.info("Hotel added successfully.");
            return true;
        } catch (Exception ex) {
            log.error("Hotel add error: " + ex);
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean update(Hotel obj) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.update(obj);
            transaction.commit();
            log.info("Hotel updated successfully.");
            return true;
        } catch (Exception ex) {
            log.error("Hotel update error: " + ex);
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean delete(Hotel obj) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.delete(obj);
            transaction.commit();
            log.info("Hotel deleted successfully.");
            return true;
        }  catch (Exception e) {
            log.info("Hotel delete error: " + e.getMessage());
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public List<Hotel> getAll() {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        List<Hotel> hotels = new LinkedList<>();
        try{
            String jpql = "SELECT h FROM Hotel h";
            hotels.addAll(session.createQuery(jpql, Hotel.class).getResultList());
            transaction.commit();
            log.info("Got all hotels successfully.");
        } catch (Exception ex) {
            log.error("Get all hotels error: " + ex.getMessage());
        } finally {
            session.close();
        }
        return hotels;
    }

    @Override
    public Hotel getById(Integer id) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Hotel hotel = null;

        try {
            String jpql = "SELECT h FROM Hotel h WHERE id = '" + id + "'";
            hotel = (Hotel) session.createQuery(jpql).getSingleResult();
            transaction.commit();
            log.info("Got hotel by id successfully.");
        } catch(Exception ex) {
            log.error("Get hotel by id error: " + ex.getMessage());
        } finally {
            session.close();
        }
        return hotel;
    }


    @Override //unique hotel name
    public Hotel getByName(String name) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Hotel hotel = null;

        try {
            String jpql = "SELECT h FROM Hotel h WHERE name = '" + name + "'";
            hotel = (Hotel) session.createQuery(jpql).getSingleResult();
            transaction.commit();
            log.info("Got hotel by name successfully.");
        } catch(Exception ex) {
            log.error("Get hotel by name error: " + ex.getMessage());
        } finally {
            session.close();
        }
        return hotel;
    }




}