package application.mapper;

import application.model.Content;
import application.model.Output;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class OutputMapperTest {

    private OutputMapper outputMapper;
    private Content contentMock1;
    private Content contentMock2;

    @BeforeEach
    void setUp() {
        outputMapper = new OutputMapper();
        contentMock1 = mock(Content.class);
        contentMock2 = mock(Content.class);
    }

    @Test
    void testMapToOutput_withValidContentList() {
        // Arrange
        when(contentMock1.getFullName()).thenReturn("John Doe");
        when(contentMock1.getTransport()).thenReturn("Car");
        when(contentMock1.getTopSpeed()).thenReturn(120.0);
        when(contentMock2.getFullName()).thenReturn("Mark Doe");
        when(contentMock2.getTransport()).thenReturn("Bus");
        when(contentMock2.getTopSpeed()).thenReturn(130.5);

        ArrayList<Content> contentList = new ArrayList<>();
        contentList.add(contentMock1);
        contentList.add(contentMock2);

        ArrayList<Output> outputList = outputMapper.mapToOutput(contentList);

        assertNotNull(outputList);
        assertEquals(2, outputList.size());
        assertEquals("John Doe", outputList.get(0).getName());
        assertEquals("Car", outputList.get(0).getTransport());
        assertEquals(120, outputList.get(0).getTopSpeed());
        assertEquals("Mark Doe", outputList.get(1).getName());
        assertEquals("Bus", outputList.get(1).getTransport());
        assertEquals(130.5, outputList.get(1).getTopSpeed());
    }

    @Test
    void testMapToOutput_withEmptyContentList() {
        ArrayList<Content> contentList = new ArrayList<>();

        ArrayList<Output> outputList = outputMapper.mapToOutput(contentList);

        assertNotNull(outputList);
        assertTrue(outputList.isEmpty());
    }

}
