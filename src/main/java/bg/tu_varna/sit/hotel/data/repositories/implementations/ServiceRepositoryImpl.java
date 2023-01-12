package bg.tu_varna.sit.hotel.data.repositories.implementations;

import bg.tu_varna.sit.hotel.data.access.Connection;
import bg.tu_varna.sit.hotel.data.entities.Hotel;
import bg.tu_varna.sit.hotel.data.entities.Service;
import bg.tu_varna.sit.hotel.data.repositories.interfaces.ServiceRepository;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ServiceRepositoryImpl implements ServiceRepository<Service> {

    private static final Logger log = Logger.getLogger(ServiceRepositoryImpl.class);

    //lazy-loaded singleton pattern
    public static ServiceRepositoryImpl getInstance() {return ServiceRepositoryHolder.INSTANCE;}

    private static class ServiceRepositoryHolder {
        public static final ServiceRepositoryImpl INSTANCE = new ServiceRepositoryImpl();
    }

    @Override
    public boolean save(Service obj) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(obj); //insert object into table
            transaction.commit();//commit changes to the database
            log.info("Service added successfully.");
            return true;
        } catch (Exception e) {
            log.error("Service add error: " + e);
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean update(Service obj) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.update(obj);
            transaction.commit();
            log.info("Service updated successfully.");
            return true;
        } catch (Exception e) {
            log.error("Service update error: " + e);
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean delete(Service obj) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.delete(obj);
            transaction.commit();
            log.info("Service deleted successfully.");
            return true;
        }  catch (Exception e) {
            log.info("Service delete error: " + e.getMessage());
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public Service getByType(String type,Hotel hotel) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Service service = null;

        try {
            String jpql = "SELECT s FROM Service s WHERE type = '" + type + "' AND hotel = '"+ hotel.getId() +"'";
            hotel = (Hotel) session.createQuery(jpql).getSingleResult();
            transaction.commit();
            log.info("Got service by type and by hotel successfully.");
        } catch(Exception e) {
            log.error("Get service by type and by hotel error: " + e.getMessage());
        } finally {
            session.close();
        }
        return service;
    }
}