package bg.tu_varna.sit.hotel.business;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CustomerServiceTest {

    private CustomerService customerService;

    @BeforeEach
    void setUp() {customerService = CustomerService.getInstance();}

    @Test
    void getInstance() {
        assertTrue(customerService instanceof CustomerService);
        assertNotNull(customerService);
    }

    @Test
    void validateFirstName() {
        assertTrue(customerService.validateFirstName("Тодор"));
        assertTrue(customerService.validateFirstName("Елица"));
        assertFalse(customerService.validateFirstName("тодор"));
        assertFalse(customerService.validateFirstName("елица"));
        assertFalse(customerService.validateFirstName("Todor"));
        assertFalse(customerService.validateFirstName("Elica"));
        assertFalse(customerService.validateFirstName(""));
        assertFalse(customerService.validateFirstName(" "));
        assertFalse(customerService.validateFirstName(" Тодор "));
        assertFalse(customerService.validateFirstName(" Елица "));
        assertFalse(customerService.validateFirstName("ТОДОР"));
        assertFalse(customerService.validateFirstName("ЕЛИЦА"));
    }

    @Test
    void validateLastName() {
        assertTrue(customerService.validateLastName("Йорданов"));
        assertTrue(customerService.validateLastName("Енчева"));
        assertFalse(customerService.validateLastName("йорданов"));
        assertFalse(customerService.validateLastName("енчева"));
        assertFalse(customerService.validateLastName("yordanov"));
        assertFalse(customerService.validateLastName("encheva"));
        assertFalse(customerService.validateLastName(""));
        assertFalse(customerService.validateLastName(" "));
        assertFalse(customerService.validateLastName(" Yordanov "));
        assertFalse(customerService.validateLastName(" Encheva "));
        assertFalse(customerService.validateLastName("YORDANOV"));
        assertFalse(customerService.validateLastName("ENCHEVA"));
    }

    @Test
    void validateEgn() {
        assertTrue(customerService.validateEgn("0348291099"));
        assertTrue(customerService.validateEgn("0211111111"));
        assertFalse(customerService.validateEgn("0000000000"));
        assertFalse(customerService.validateEgn("8888888881"));
        assertFalse(customerService.validateEgn("034829109"));
        assertFalse(customerService.validateEgn("03482910777"));
        assertFalse(customerService.validateEgn(""));
        assertFalse(customerService.validateEgn(" "));
        assertFalse(customerService.validateEgn("          "));
    }

    @Test
    void validatePhone() {
        assertTrue(customerService.validatePhone("0882970258"));
        assertTrue(customerService.validatePhone("0878555245"));
        assertFalse(customerService.validateEgn(""));
        assertFalse(customerService.validateEgn(" "));
        assertFalse(customerService.validateEgn("."));
        assertFalse(customerService.validateEgn("          "));
        assertFalse(customerService.validatePhone("08829702582"));
        assertFalse(customerService.validatePhone("088297058"));
    }
}