package bg.tu_varna.sit.hotel.data.access;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Connection {
    private static final Logger log = Logger.getLogger(Connection.class);
    private static SessionFactory sessionFactory;

    static {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();//loads SessionFactory
        } catch (Throwable ex) {
            log.error("Initial SessionFactory created failed with error: " + ex);
        }
    }

    public static Session openSession() {
        return sessionFactory.openSession();//opens session
    }

    public static void closeSessionFactory() {
        sessionFactory.close(); //closes Session Factory
    }
}