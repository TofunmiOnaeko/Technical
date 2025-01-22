package application.mapper;

import application.model.Content;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ContentMapperTest {

    private ContentMapper contentMapper;

    @BeforeEach
    void setUp() {
        contentMapper = new ContentMapper();
    }

    @Test
    void testMapArrayToContent_validInput() {
        String[] values = {"12345", "user1", "John Doe", "Likes Football", "Car", "60.5", "100.0"};
        Content content = contentMapper.mapArrayToContent(values);

        assertNotNull(content);
        assertEquals("12345", content.getUuId());
        assertEquals("user1", content.getUserId());
        assertEquals("John Doe", content.getFullName());
        assertEquals("Likes Football", content.getLikes());
        assertEquals("Car", content.getTransport());
        assertEquals(60.5, content.getAvgSpeed());
        assertEquals(100.0, content.getTopSpeed());
    }

    @Test
    void testMapArrayToContent_missingValues() {
        String[] values = {"12345", "user1", "John Doe"};
        Content content = contentMapper.mapArrayToContent(values);

        assertNotNull(content);
        assertEquals("12345", content.getUuId());
        assertEquals("user1", content.getUserId());
        assertEquals("John Doe", content.getFullName());
        assertNull(content.getLikes());
        assertNull(content.getTransport());
        assertEquals(0.0, content.getAvgSpeed()); // Default value (not set)
        assertEquals(0.0, content.getTopSpeed()); // Default value (not set)
    }

    @Test
    void testMapArrayToContent_extraValues() {
        String[] values = {"12345", "user1", "John Doe", "Likes Football", "Car", "60.5", "100.0", "extra"};
        Content content = contentMapper.mapArrayToContent(values);

        assertNotNull(content);
        assertEquals("12345", content.getUuId());
        assertEquals("user1", content.getUserId());
        assertEquals("John Doe", content.getFullName());
        assertEquals("Likes Football", content.getLikes());
        assertEquals("Car", content.getTransport());
        assertEquals(60.5, content.getAvgSpeed());
        assertEquals(100.0, content.getTopSpeed());
    }

    @Test
    void testMapArrayToContent_withNewLineInTopSpeed() {
        String[] values = {"12345", "user1", "John Doe", "Likes Football", "Car", "60.5", "100\\n"};
        Content content = contentMapper.mapArrayToContent(values);

        assertNotNull(content);
        assertEquals("12345", content.getUuId());
        assertEquals("user1", content.getUserId());
        assertEquals("John Doe", content.getFullName());
        assertEquals("Likes Football", content.getLikes());
        assertEquals("Car", content.getTransport());
        assertEquals(60.5, content.getAvgSpeed());
        assertEquals(100.0, content.getTopSpeed()); // Make sure the new line is properly handled
    }

    @Test
    void testMapArrayToContent_emptyArray() {
        String[] values = {};
        Content content = contentMapper.mapArrayToContent(values);

        assertNotNull(content);
        assertNull(content.getUuId());
        assertNull(content.getUserId());
        assertNull(content.getFullName());
        assertNull(content.getLikes());
        assertNull(content.getTransport());
        assertEquals(0.0, content.getAvgSpeed());
        assertEquals(0.0, content.getTopSpeed());
    }

    @Test
    void testMapArrayToContent_invalidAvgSpeed() {
        String[] values = {"12345", "user1", "John Doe", "Likes Football", "Car", "invalid", "100.0"};

        assertThrows(NumberFormatException.class, () -> {
            contentMapper.mapArrayToContent(values);  // This should throw a NumberFormatException when parsing the "invalid" string
        });
    }
}