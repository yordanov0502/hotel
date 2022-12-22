package bg.tu_varna.sit.hotel.data.repositories.interfaces;

import java.util.List;

public interface HotelsUsersRepository<T> extends Repository<T>{
    List<T> getAll();
}
