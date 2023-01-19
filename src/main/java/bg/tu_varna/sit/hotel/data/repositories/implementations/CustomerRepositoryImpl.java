package bg.tu_varna.sit.hotel.data.repositories.implementations;

import bg.tu_varna.sit.hotel.data.access.Connection;
import bg.tu_varna.sit.hotel.data.entities.Customer;
import bg.tu_varna.sit.hotel.data.entities.Hotel;
import bg.tu_varna.sit.hotel.data.entities.Room;
import bg.tu_varna.sit.hotel.data.entities.User;
import bg.tu_varna.sit.hotel.data.repositories.interfaces.CustomerRepository;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.LinkedList;
import java.util.List;

public class CustomerRepositoryImpl implements CustomerRepository<Customer> {

    private static final Logger log = Logger.getLogger(CustomerRepositoryImpl.class);

    //lazy-loaded singleton pattern
    public static CustomerRepositoryImpl getInstance() {return CustomerRepositoryImpl.CustomerRepositoryHolder.INSTANCE;}

    private static class CustomerRepositoryHolder {
        public static final CustomerRepositoryImpl INSTANCE = new CustomerRepositoryImpl();
    }


    @Override
    public boolean save(Customer obj) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.save(obj); //insert object into table
            transaction.commit();//commit changes to the database
            log.info("Customer added successfully.");
            return true;
        } catch (Exception e) {
            log.error("Customer add error: " + e);
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean update(Customer obj) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.update(obj);
            transaction.commit();
            log.info("Customer updated successfully.");
            return true;
        } catch (Exception e) {
            log.error("Customer update error: " + e);
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public boolean delete(Customer obj) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        try {
            session.delete(obj);
            transaction.commit();
            log.info("Customer deleted successfully.");
            return true;
        }  catch (Exception e) {
            log.info("Customer delete error: " + e.getMessage());
            return false;
        } finally {
            session.close();
        }
    }

    @Override
    public List<Customer> getAllCustomersOfHotel(Hotel hotel) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        List<Customer> customers = new LinkedList<>();
        try{
            String jpql = "SELECT c FROM Customer c WHERE hotel = '"+ hotel.getId() +"' ORDER BY createdAt DESC ";
            customers.addAll(session.createQuery(jpql, Customer.class).getResultList());
            transaction.commit();
            log.info("Got all customers of hotel \""+hotel.getName()+"\" successfully.");
        } catch (Exception e) {
            log.error("Get all customers of hotel \""+hotel.getName()+"\" error: " + e.getMessage());
        } finally {
            session.close();
        }
        return customers;
    }

    @Override //EGN
    public Customer getByEgn(String egn, Hotel hotel) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Customer customer = null;
        try{
            String jpql = "SELECT c FROM Customer c WHERE egn = '"+egn+"' AND hotel = '"+ hotel.getId() +"'";
            customer = (Customer) session.createQuery(jpql).getSingleResult();
            transaction.commit();
            log.info("Got a customer from hotel successfully by egn.");
        } catch (Exception e) {
            log.error("Get a customer from hotel by egn error: " + e.getMessage());
        } finally {
            session.close();
        }
        return customer;
    }

    @Override
    public Customer getByPhone(String phone, Hotel hotel) {
        Session session = Connection.openSession();
        Transaction transaction = session.beginTransaction();
        Customer customer = null;
        try{
            String jpql = "SELECT c FROM Customer c WHERE phone = '"+phone+"' AND hotel = '"+ hotel.getId() +"'";
            customer = (Customer) session.createQuery(jpql).getSingleResult();
            transaction.commit();
            log.info("Got a customer from hotel successfully by phone.");
        } catch (Exception e) {
            log.error("Get a customer from hotel by phone error: " + e.getMessage());
        } finally {
            session.close();
        }
        return customer;
    }

}