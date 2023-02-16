package bg.tu_varna.sit.hotel.business;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HotelServiceTest {

    private HotelService hotelService;

    @BeforeEach
    void setUp() {hotelService=HotelService.getInstance();}

    @Test
    void getInstance() {
        assertTrue(hotelService instanceof HotelService);
        assertNotNull(hotelService);
    }

    @Test
    void validateName() {
        assertFalse(hotelService.validateName(""));
        assertFalse(hotelService.validateName(" "));
        assertFalse(hotelService.validateName(". ."));
        assertFalse(hotelService.validateName("."));
        assertTrue(hotelService.validateName("Udinese Hotel"));
        assertTrue(hotelService.validateName("Obzor Palace"));
        assertTrue(hotelService.validateName("Parizi"));
        assertTrue(hotelService.validateName("Golden Tulip"));
        assertTrue(hotelService.validateName("Plaza Hotel"));
        assertTrue(hotelService.validateName("Шипка"));
        assertFalse(hotelService.validateName("             "));
        assertTrue(hotelService.validateName("      s       "));
    }

    @Test
    void validateAddress() {
        assertTrue(hotelService.validateAddress("България, гр.Варна, ул.Радост №19"));
        assertTrue(hotelService.validateAddress("България, гр.Варна, ул.Мир №99"));
        assertTrue(hotelService.validateAddress("BG, Sliven town, str.Nikola Petkov №18"));
        assertFalse(hotelService.validateAddress(""));
        assertFalse(hotelService.validateAddress(" "));
        assertFalse(hotelService.validateAddress("                                     "));
        assertTrue(hotelService.validateAddress("               proba                "));
    }
}