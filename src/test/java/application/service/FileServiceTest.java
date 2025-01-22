package application.service;

import application.mapper.ContentMapper;
import application.mapper.OutputMapper;
import application.model.Content;
import application.model.Output;
import application.repository.ContentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FileServiceTest {

    @Mock
    private ContentMapper contentMapper;

    @Mock
    private OutputMapper outputMapper;

    @Mock
    private ContentRepository contentRepository;

    @InjectMocks
    private FileService fileService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testMapFileToContent_validFile() {
        String fileContent = "1|John|Doe|Car|120\n2|Jane|Doe|Bike|80";
        ArrayList<Content> mockContentList = new ArrayList<>();
        Content content1 = new Content();
        Content content2 = new Content();
        mockContentList.add(content1);
        mockContentList.add(content2);

        Output mockOutput1 = new Output("John Doe", "Car", 120);
        Output mockOutput2 = new Output("Jane Doe", "Bike", 80);
        ArrayList<Output> mockOutputList = new ArrayList<>();
        mockOutputList.add(mockOutput1);
        mockOutputList.add(mockOutput2);

        when(contentMapper.mapArrayToContent(any())).thenReturn(content1, content2);
        when(outputMapper.mapToOutput(any())).thenReturn(mockOutputList);

        String result = fileService.mapFileToContent(fileContent);

        assertNotNull(result);
        assertTrue(result.contains("John Doe"));
        assertTrue(result.contains("Jane Doe"));
        verify(contentRepository, times(2)).save(any(Content.class));
    }

    @Test
    void testMapFileToContent_emptyFile() {
        String emptyFile = "";
        String result = fileService.mapFileToContent(emptyFile);

        assertEquals("Error mapping file to content", result);
        verify(contentRepository, never()).save(any(Content.class)); // Ensure save was never called
    }

    @Test
    void testMapFileToContent_nullFile() {
        String nullFile = null;

        String result = fileService.mapFileToContent(nullFile);

        assertEquals("Error mapping file to content", result);
        verify(contentRepository, never()).save(any(Content.class)); // Ensure save was never called
    }
}
