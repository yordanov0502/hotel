package bg.tu_varna.sit.hotel.data.repositories.interfaces;

import bg.tu_varna.sit.hotel.data.entities.Customer;
import bg.tu_varna.sit.hotel.data.entities.Hotel;
import bg.tu_varna.sit.hotel.data.entities.User;

import java.util.List;

public interface CustomerRepository<T>  extends Repository<T>{
    List<T> getAllCustomersOfHotel(Hotel hotel);
    Customer getByEgn(String egn, Hotel hotel);
    Customer getByPhone(String phone, Hotel hotel);
}