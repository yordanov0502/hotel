package bg.tu_varna.sit.hotel.business;

import bg.tu_varna.sit.hotel.common.AlertManager;
import bg.tu_varna.sit.hotel.data.entities.Customer;
import bg.tu_varna.sit.hotel.data.entities.Room;
import bg.tu_varna.sit.hotel.data.entities.User;
import bg.tu_varna.sit.hotel.data.repositories.implementations.CustomerRepositoryImpl;
import bg.tu_varna.sit.hotel.data.repositories.implementations.HotelRepositoryImpl;
import bg.tu_varna.sit.hotel.presentation.models.CustomerModel;
import bg.tu_varna.sit.hotel.presentation.models.HotelModel;
import bg.tu_varna.sit.hotel.presentation.models.RoomModel;
import bg.tu_varna.sit.hotel.presentation.models.UserModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class CustomerService {
    private static final Logger log = Logger.getLogger(CustomerService.class);
    private final CustomerRepositoryImpl customerRepository = CustomerRepositoryImpl.getInstance();

    //lazy-loaded singleton pattern
    public static CustomerService getInstance() {
        return CustomerService.CustomerServiceHolder.INSTANCE;
    }

    private static class CustomerServiceHolder {
        public static final CustomerService INSTANCE = new CustomerService();
    }

    public ObservableList<CustomerModel> getAllCustomersOfHotel(HotelModel hotelModel) {
        List<Customer> customers = customerRepository.getAllCustomersOfHotel(hotelModel.toEntity());

        if(customers.isEmpty()){return null;}

        else
        {
            return FXCollections.observableList(
                    customers.stream().map(c -> new CustomerModel(
                            c.getId(),
                            c.getEgn(),
                            c.getFirst_name(),
                            c.getLast_name(),
                            c.getPhone(),
                            c.getCreatedAt(),
                            c.getRating(),
                            new HotelModel(c.getHotel()),
                            c.getNightsStayed()
                    )).collect(Collectors.toList())
            );
        }
    }



    public CustomerModel getCustomerByEgn(String egn,HotelModel hotelModel) {
        Customer customer = customerRepository.getByEgn(egn,hotelModel.toEntity());
        return (customer == null) ? null : new CustomerModel(customer);
    }

    public boolean isEgnExists(String egn,HotelModel hotelModel) {
        return getCustomerByEgn(egn,hotelModel) != null;
    }

    public CustomerModel getCustomerByPhone(String phone,HotelModel hotelModel) {
        Customer customer = customerRepository.getByPhone(phone,hotelModel.toEntity());
        return (customer == null) ? null : new CustomerModel(customer);
    }

    public boolean isPhoneExists(String phone,HotelModel hotelModel) {return getCustomerByPhone(phone,hotelModel) != null;}



    public boolean addCustomer(CustomerModel customerModel) {return customerRepository.save(customerModel.toEntity());}

    public boolean updateCustomer(CustomerModel customerModel) {return customerRepository.update(customerModel.toEntity());}

    public boolean deleteCustomer(CustomerModel customerModel){return customerRepository.delete(customerModel.toEntity());}



    public boolean validateFirstName(String firstName) {
        String regex = "^[\\u0410-\\u042F]{1}([\\u0430-\\u044F]{2,29})$";

        Pattern p = Pattern.compile(regex);
        if(firstName == null) {return false;}
        else
        {
            Matcher m = p.matcher(firstName);
            return m.matches();
        }
    }

    public boolean validateLastName(String lastName) {
        String regex = "^[\\u0410-\\u042F]{1}([\\u0430-\\u044F]{2,29})$";

        Pattern p = Pattern.compile(regex);
        if(lastName == null) {return false;}
        else
        {
            Matcher m = p.matcher(lastName);
            return m.matches();
        }
    }

    public boolean validateEgn(String egn) {
        String regex = "^[0-9]{10}$";

        Pattern p = Pattern.compile(regex);
        if(egn == null) {return false;}
        else
        {
            Matcher m = p.matcher(egn);
            if(m.matches())//checks if the password matches the regex
            {
                String dayOfBirth = egn.substring(4,6);
                int day = Integer.parseInt(dayOfBirth);
                String monthOfBirth = egn.substring(2,4);
                int month = Integer.parseInt(monthOfBirth);
                return day != 0 && day <= 31 && month != 0;//checks if day is [1;31] and month>0
            }
            else {return false;}
        }
    }

    public boolean validatePhone(String phone) {
        String regex = "^[0]{1}([0-9]{9})$";

        Pattern p = Pattern.compile(regex);
        if(phone == null) {return false;}
        else
        {
            Matcher m = p.matcher(phone);
            return m.matches();
        }
    }

    public boolean validateFields(String [] fields) //validate fields in registration and edit-info forms
    {
        if(Objects.equals(fields[0], "")||Objects.equals(fields[1], "")||Objects.equals(fields[2], "")||Objects.equals(fields[3], ""))
        {
            AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","Моля въведете данни във всички полета.");
            return false;
        }
        else if(!validateFirstName(fields[0]))
        {
            AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","Името трябва да съдържа от 3 до 30 символа като започва с главна буква, последвана от малки букви на кирилица.");
            return false;
        }
        else if(!validateLastName(fields[1]))
        {
            AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","Фамилията трябва да съдържа от 3 до 30 символа като започва с главна буква, последвана от малки букви на кирилица.");
            return false;
        }
        else if(!validateEgn(fields[2]))
        {
            AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","ЕГН-то трябва да съдържа 10 цифри [0-9] и да бъде валидно.");
            return false;
        }
        else if(!validatePhone(fields[3]))
        {
            AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","Мобилният номер трябва да съдържа 10 цифри [0-9] като първата цифра задължително трябва да е \"0\".");
            return false;
        }
        else {return true;}
    }

    //This method is used when creating a new customer to a hotel
    public boolean checkForExistingUserData(String [] fields,HotelModel hotelModel) //checks for already existing user data in the database
    {
        if(fields.length==2) //proverka pri registraciq na user
        {
            if(isEgnExists(fields[0],hotelModel))
            {
                AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","ЕГН: \""+fields[0]+"\" вече съществува в базата данни.");
                return true;
            }
            else if(isPhoneExists(fields[1],hotelModel))
            {
                AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","Мобилен номер: \""+fields[1]+"\" вече съществува в базата данни.");
                return true;
            }
            else {return false;}
        }
        else return true;
    }

    //This method is used when editting information of a customer
    public boolean checkForCorrectCustomerDataUpdate(String [] fields, CustomerModel selectedCustomer) //proverka pri redaktirane danni na customer
    {
        if(fields.length==4)
        {
            if(Objects.equals(selectedCustomer.getFirstName(), fields[0]) && Objects.equals(selectedCustomer.getLastName(), fields[1]) && Objects.equals(selectedCustomer.getEgn(), fields[2]) && Objects.equals(selectedCustomer.getPhone(), fields[3]))
            {
                return false;
            }
            else if(!Objects.equals(selectedCustomer.getEgn(), fields[2]) && isEgnExists(fields[2], selectedCustomer.getHotel()))
            {
                AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","ЕГН: \""+fields[2]+"\" вече съществува в базата данни на хотел \""+selectedCustomer.getHotel().getName()+"\".");
                return false;
            }
            else if(!Objects.equals(selectedCustomer.getPhone(), fields[3]) && isPhoneExists(fields[3],selectedCustomer.getHotel()))
            {
                AlertManager.showAlert(Alert.AlertType.ERROR,"Грешка","Мобилен номер: \""+fields[3]+"\" вече съществува в базата данни на хотел \""+selectedCustomer.getHotel().getName()+"\".");
                return false;
            }
            else return true;//everything is OK
        }
        else {return false;}
    }


}