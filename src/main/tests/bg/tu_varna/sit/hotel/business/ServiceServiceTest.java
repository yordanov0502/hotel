package bg.tu_varna.sit.hotel.business;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ServiceServiceTest {

    private ServiceService serviceService;

    @BeforeEach
    void setUp() {serviceService = ServiceService.getInstance();}

    @Test
    void getInstance() {
        assertTrue(serviceService instanceof ServiceService);
        assertNotNull(serviceService);
    }

    @Test
    void validateServiceName() {
        assertTrue(serviceService.validateServiceName("джакузи"));
        assertTrue(serviceService.validateServiceName("басейн"));
        assertTrue(serviceService.validateServiceName("кино"));
        assertTrue(serviceService.validateServiceName("Конна езда"));
        assertFalse(serviceService.validateServiceName("б@гане"));
        assertFalse(serviceService.validateServiceName("08л31л23 .31 )##@#;"));
        assertFalse(serviceService.validateServiceName(" "));
        assertFalse(serviceService.validateServiceName(""));
    }

    @Test
    void validateServiceField() {
        assertFalse(serviceService.validateServiceName(""));
        assertTrue(serviceService.validateServiceName("Мини голф"));
        assertFalse(serviceService.validateServiceName("billi@rd"));
        assertFalse(serviceService.validateServiceName("1212185;"));
    }

}