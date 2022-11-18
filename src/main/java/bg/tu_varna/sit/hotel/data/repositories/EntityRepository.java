package bg.tu_varna.sit.hotel.data.repositories;

import java.util.List;
import java.util.Optional;

public interface EntityRepository<T> {

    void save(T obj);
    void update(T obj);
    void delete(T obj);
    Optional<T> getById(Long id);
    List<T> getAll();
}