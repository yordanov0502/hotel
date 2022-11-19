package bg.tu_varna.sit.hotel.data.repositories.interfaces;

public interface Repository<T> {
    void save(T obj);
    void update(T obj);
    void delete(T obj);
}