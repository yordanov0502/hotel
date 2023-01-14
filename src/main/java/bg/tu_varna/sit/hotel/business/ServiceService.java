package bg.tu_varna.sit.hotel.business;

import bg.tu_varna.sit.hotel.common.AlertManager;
import bg.tu_varna.sit.hotel.data.entities.Service;
import bg.tu_varna.sit.hotel.data.repositories.implementations.ServiceRepositoryImpl;
import bg.tu_varna.sit.hotel.presentation.models.HotelModel;
import bg.tu_varna.sit.hotel.presentation.models.ServiceModel;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import org.apache.log4j.Logger;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ServiceService {
    private static final Logger log = Logger.getLogger(ServiceService.class);
    private final ServiceRepositoryImpl serviceRepository = ServiceRepositoryImpl.getInstance();

    //lazy-loaded singleton pattern
    public static ServiceService getInstance() {
        return ServiceService.ServiceServiceHolder.INSTANCE;
    }

    private static class ServiceServiceHolder {
        public static final ServiceService INSTANCE = new ServiceService();
    }

    public ServiceModel getServiceByType(String type,HotelModel hotelModel) {
        Service service = serviceRepository.getByType(type, hotelModel.toEntity());
        return (service == null) ? null : new ServiceModel(service);
    }

    public boolean isServiceExists(String type,HotelModel hotelModel) {
        return getServiceByType(type,hotelModel) != null;
    }

    public boolean validateServiceName(String serviceName) {
        String regex = "^([\\u0430-\\u044F OR \\u0410-\\u042F OR a-zA-Z]{3,60})$";

        Pattern p = Pattern.compile(regex);
        if(serviceName == null) {return false;}
        else
        {
            Matcher m = p.matcher(serviceName);
            return m.matches();
        }
    }

    public boolean validateServiceField(String serviceName) {
        if (serviceName.equals(""))
        {
            AlertManager.showAlert(Alert.AlertType.ERROR, "Грешка", "Моля въведете тип хотелска услуга.");
            return false;
        }
        else if (!validateServiceName(serviceName))
        {
            AlertManager.showAlert(Alert.AlertType.ERROR, "Грешка", "Типът на хотелската услуга трябва да съдържа между 4 и 60 символа като символите могат да бъдат малки и големи букви на кирилица и латиница както и празно разстоняние.");
            return false;
        }
        else return true;
    }


}