package bg.tu_varna.sit.hotel.business;

import bg.tu_varna.sit.hotel.data.entities.Service;
import bg.tu_varna.sit.hotel.data.repositories.implementations.ServiceRepositoryImpl;
import bg.tu_varna.sit.hotel.presentation.models.HotelModel;
import bg.tu_varna.sit.hotel.presentation.models.ServiceModel;
import org.apache.log4j.Logger;

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

}