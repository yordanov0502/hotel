package bg.tu_varna.sit.hotel.business;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReservationServiceTest {

    private ReservationService reservationService;

    @BeforeEach
    void setUp() {reservationService = ReservationService.getInstance();}

    @Test
    void getInstance() {
        assertTrue(reservationService instanceof ReservationService);
        assertNotNull(reservationService);
    }

    @Test
    void validateDaysAnnulationField() {
        assertFalse(reservationService.validateDaysAnnulationField(""));
        assertFalse(reservationService.validateDaysAnnulationField(" "));
        assertTrue(reservationService.validateDaysAnnulationField("0"));
        assertTrue(reservationService.validateDaysAnnulationField("1"));
        assertTrue(reservationService.validateDaysAnnulationField("5"));
        assertTrue(reservationService.validateDaysAnnulationField("7"));
        assertTrue(reservationService.validateDaysAnnulationField("9"));
        assertFalse(reservationService.validateDaysAnnulationField("10"));
        assertFalse(reservationService.validateDaysAnnulationField("-"));
        assertFalse(reservationService.validateDaysAnnulationField("."));
        assertFalse(reservationService.validateDaysAnnulationField("@"));
    }
}