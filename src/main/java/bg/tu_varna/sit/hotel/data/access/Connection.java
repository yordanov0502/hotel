package bg.tu_varna.sit.hotel.data.access;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Connection {
    private static final Logger log = Logger.getLogger(Connection.class);
    private static SessionFactory sessionFactory;

    private Connection(){}

    static {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();//creates SessionFactory
            log.info("Initial SessionFactory created successfully");
        } catch (Throwable e) {
            log.error("Initial SessionFactory created failed with error: " + e);
        }
    }

    public static void createSessionFactory(){}

    public static Session openSession() {
        return sessionFactory.openSession();//opens session
    }

    public static void closeSessionFactory() {
        sessionFactory.close(); //closes Session Factory
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

}