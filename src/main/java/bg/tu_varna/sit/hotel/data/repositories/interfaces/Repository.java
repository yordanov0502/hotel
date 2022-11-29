package bg.tu_varna.sit.hotel.data.repositories.interfaces;

public interface Repository<T> {
    boolean save(T obj);
    boolean update(T obj);
    boolean delete(T obj);
}