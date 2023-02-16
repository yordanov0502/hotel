package bg.tu_varna.sit.hotel.business;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoomServiceTest {

    private RoomService roomService;

    @BeforeEach
    void setUp() {roomService=RoomService.getInstance();}

    @Test
    void getInstance() {
        assertTrue(roomService instanceof RoomService);
        assertNotNull(roomService);
    }

    @Test
    void validateFloorsField() {
        assertTrue(roomService.validateFloorsField("1"));
        assertTrue(roomService.validateFloorsField("100"));
        assertTrue(roomService.validateFloorsField("20"));
        assertFalse(roomService.validateFloorsField("-"));
        assertFalse(roomService.validateFloorsField(".1"));
        assertFalse(roomService.validateFloorsField("++++dsavcqdsasaxz882321"));
        assertFalse(roomService.validateFloorsField("__=#$#DFdl; fds __"));
    }


    @Test
    void validateRoomsNumberDynamicField() {
        assertTrue(roomService.validateRoomsNumberDynamicField("1"));
        assertTrue(roomService.validateRoomsNumberDynamicField("98"));
        assertFalse(roomService.validateRoomsNumberDynamicField("100"));
        assertFalse(roomService.validateRoomsNumberDynamicField("-"));
        assertFalse(roomService.validateRoomsNumberDynamicField(".9"));
        assertFalse(roomService.validateRoomsNumberDynamicField("++++d31edsaacasd21213"));
        assertFalse(roomService.validateRoomsNumberDynamicField("__=#$#DFdl; fds __"));
    }

    @Test
    void validateRoomType() {
        assertTrue(roomService.validateRoomType("единична"));
        assertTrue(roomService.validateRoomType("зала"));
        assertTrue(roomService.validateRoomType("баня"));
        assertTrue(roomService.validateRoomType("тунел"));
        assertFalse(roomService.validateRoomType("Градина"));
        assertTrue(roomService.validateRoomType("bathroom"));
        assertFalse(roomService.validateRoomType("Bathroom"));
        assertFalse(roomService.validateRoomType(".92"));
    }


    @Test
    void validateRoomNumber() {
        assertTrue(roomService.validateRoomNumber("101"));
        assertTrue(roomService.validateRoomNumber("305"));
        assertTrue(roomService.validateRoomNumber("911"));
        assertTrue(roomService.validateRoomNumber("715"));
        assertFalse(roomService.validateRoomNumber(""));
        assertFalse(roomService.validateRoomNumber(" "));
        assertTrue(roomService.validateRoomNumber("10098"));
        assertTrue(roomService.validateRoomNumber("300"));
        assertFalse(roomService.validateRoomNumber("-2"));
        assertFalse(roomService.validateRoomNumber("test"));
        assertFalse(roomService.validateRoomNumber(".921"));
    }

    @Test
    void validateRoomAreaField() {
        assertTrue(roomService.validateRoomAreaField("10"));
        assertTrue(roomService.validateRoomAreaField("100"));
        assertTrue(roomService.validateRoomAreaField("1000"));
        assertFalse(roomService.validateRoomAreaField("10000"));
        assertFalse(roomService.validateRoomAreaField("-9"));
        assertFalse(roomService.validateRoomAreaField("i9"));
        assertFalse(roomService.validateRoomAreaField(""));
        assertFalse(roomService.validateRoomAreaField(" "));
        assertFalse(roomService.validateRoomAreaField(" _9"));
    }

    @Test
    void validateRoomPriceField() {
        assertTrue(roomService.validateRoomPriceField("1"));
        assertTrue(roomService.validateRoomPriceField("10"));
        assertTrue(roomService.validateRoomPriceField("10000"));
        assertFalse(roomService.validateRoomPriceField(" "));
        assertFalse(roomService.validateRoomPriceField(" 9"));
        assertFalse(roomService.validateRoomPriceField("2 "));
        assertFalse(roomService.validateRoomPriceField("1 1"));
    }

    @Test
    void validateRoomBedsField() {
        assertFalse(roomService.validateRoomBedsField(" "));
        assertFalse(roomService.validateRoomBedsField("i9"));
        assertFalse(roomService.validateRoomBedsField(" 9"));
        assertFalse(roomService.validateRoomBedsField("3 "));
        assertFalse(roomService.validateRoomBedsField("ds"));
        assertFalse(roomService.validateRoomBedsField("a"));
        assertFalse(roomService.validateRoomBedsField("л,"));
        assertFalse(roomService.validateRoomBedsField("е"));
        assertTrue(roomService.validateRoomBedsField("1"));
        assertTrue(roomService.validateRoomBedsField("10"));
        assertTrue(roomService.validateRoomBedsField("7"));
    }
}