package bg.tu_varna.sit.hotel.data.repositories.implementations;

import bg.tu_varna.sit.hotel.data.access.Connection;
import bg.tu_varna.sit.hotel.data.entities.Hotel;
import bg.tu_varna.sit.hotel.data.entities.User;
import bg.tu_varna.sit.hotel.data.repositories.interfaces.HotelRepository;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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
        } catch (Exception e) {
            log.error("Hotel add error: " + e);
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
        } catch (Exception e) {
            log.error("Hotel update error: " + e);
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
    public List<Hotel> getAllHotels() {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        List<Hotel> hotels = new LinkedList<>();
        try{
            String jpql = "SELECT h FROM Hotel h";
            hotels.addAll(session.createQuery(jpql, Hotel.class).getResultList());
            transaction.commit();
            log.info("Got all hotels successfully.");
        } catch (Exception e) {
            log.error("Get all hotels error: " + e.getMessage());
        } finally {
            session.close();
        }
        return hotels;
    }

    @Override
    public List<Hotel> getAllHotelsWithoutOwner() {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        List<Hotel> hotels = new LinkedList<>();
        try{
            String jpql = "SELECT h FROM Hotel h WHERE hasOwner = '"+ 0 + "'";
            hotels.addAll(session.createQuery(jpql, Hotel.class).getResultList());
            transaction.commit();
            log.info("Got all hotels successfully.");
        } catch (Exception e) {
            log.error("Get all hotels error: " + e.getMessage());
        } finally {
            session.close();
        }
        return hotels;
    }

    @Override
    public List<String> getAllHotelNamesWithoutOwner() {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        List<String> hotelsNames = new LinkedList<>();
        try{
            String jpql = "SELECT name FROM Hotel  WHERE hasOwner = '"+ 0 + "'";
            hotelsNames.addAll(session.createQuery(jpql, String.class).getResultList());
            transaction.commit();
            log.info("Got all hotel names successfully.");
        } catch (Exception e) {
            log.error("Get all hotel names error: " + e.getMessage());
        } finally {
            session.close();
        }
        return hotelsNames;
    }

    @Override
    public List<Hotel> getAllHotelsWithoutManager() {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        List<Hotel> hotels = new LinkedList<>();
        try{
            String jpql = "SELECT h FROM Hotel h WHERE hasManager = '"+ 0 + "'";
            hotels.addAll(session.createQuery(jpql, Hotel.class).getResultList());
            transaction.commit();
            log.info("Got all hotels successfully.");
        } catch (Exception e) {
            log.error("Get all hotels error: " + e.getMessage());
        } finally {
            session.close();
        }
        return hotels;
    }

    @Override
    public List<String> getAllHotelNamesWithoutManager() {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        List<String> hotelsNames = new LinkedList<>();
        try{
            String jpql = "SELECT name FROM Hotel  WHERE hasManager = '"+ 0 + "'";
            hotelsNames.addAll(session.createQuery(jpql, String.class).getResultList());
            transaction.commit();
            log.info("Got all hotel names successfully.");
        } catch (Exception e) {
            log.error("Get all hotel names error: " + e.getMessage());
        } finally {
            session.close();
        }
        return hotelsNames;
    }


    @Override
    public Hotel getById(Long id) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Hotel hotel = null;

        try {
            String jpql = "SELECT h FROM Hotel h WHERE id = '" + id + "'";
            hotel = (Hotel) session.createQuery(jpql).getSingleResult();
            transaction.commit();
            log.info("Got hotel by id successfully.");
        } catch(Exception e) {
            log.error("Get hotel by id error: " + e.getMessage());
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
        } catch(Exception e) {
            log.error("Get hotel by name error: " + e.getMessage());
        } finally {
            session.close();
        }
        return hotel;
    }

    @Override //unique hotel address
    public Hotel getByAddress(String address) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Hotel hotel = null;

        try {
            String jpql = "SELECT h FROM Hotel h WHERE address = '" + address + "'";
            hotel = (Hotel) session.createQuery(jpql).getSingleResult();
            transaction.commit();
            log.info("Got hotel by address successfully.");
        } catch(Exception e) {
            log.error("Get hotel by address error: " + e.getMessage());
        } finally {
            session.close();
        }
        return hotel;
    }

}