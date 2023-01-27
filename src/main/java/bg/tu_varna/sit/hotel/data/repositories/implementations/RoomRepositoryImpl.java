package bg.tu_varna.sit.hotel.data.repositories.implementations;

import bg.tu_varna.sit.hotel.data.access.Connection;
import bg.tu_varna.sit.hotel.data.entities.Hotel;
import bg.tu_varna.sit.hotel.data.entities.Room;
import bg.tu_varna.sit.hotel.data.repositories.interfaces.RoomRepository;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.LinkedList;
import java.util.List;

public class RoomRepositoryImpl implements RoomRepository<Room> {

    private static final Logger log = Logger.getLogger(RoomRepositoryImpl.class);

    //lazy-loaded singleton pattern
    public static RoomRepositoryImpl getInstance() { return RoomRepositoryImpl.RoomRepositoryHolder.INSTANCE;}

    private static class RoomRepositoryHolder {
        public static final RoomRepositoryImpl INSTANCE = new RoomRepositoryImpl();
    }

    @Override
    public boolean save(Room obj) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(obj); //insert object into table
            transaction.commit();//commit changes to the database
            log.info("Room added successfully.");
            return true;
        } catch (Exception e) {
            log.error("Room add error: " + e);
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean update(Room obj) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.update(obj);
            transaction.commit();
            log.info("Room updated successfully.");
            return true;
        } catch (Exception e) {
            log.error("Room update error: " + e);
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean delete(Room obj) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.delete(obj);
            transaction.commit();
            log.info("Room deleted successfully.");
            return true;
        }  catch (Exception e) {
            log.info("Room delete error: " + e.getMessage());
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public List<Room> getAllRoomsOfHotel(Hotel hotel) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        List<Room> rooms = new LinkedList<>();
        try{
            String jpql = "SELECT r FROM Room r WHERE hotel = '"+ hotel.getId() +"' AND r.number != '"+13+"' ORDER BY number";
            rooms.addAll(session.createQuery(jpql, Room.class).getResultList());
            transaction.commit();
            log.info("Got all rooms successfully.");
        } catch (Exception e) {
            log.error("Get all rooms error: " + e.getMessage());
        } finally {
            session.close();
        }
        return rooms;
    }

    @Override
    public Room getByNumber(String number, Hotel hotel) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Room room = null;
        try{
            String jpql = "SELECT r FROM Room r WHERE number = '"+Integer.parseInt(number)+"' AND hotel = '"+ hotel.getId() +"'";
            room = (Room) session.createQuery(jpql).getSingleResult();
            transaction.commit();
            log.info("Got a room successfully by number.");
        } catch (Exception e) {
            log.error("Get a room by number error: " + e.getMessage());
        } finally {
            session.close();
        }
        return room;
    }


}