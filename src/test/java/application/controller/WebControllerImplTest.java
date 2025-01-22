package application.controller;

import application.model.response.Response;
import application.service.FileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WebControllerImplTest {

    @Mock
    private FileService fileService;
    private String errorMessage = "Error mapping file to content";

    @InjectMocks
    private WebControllerImpl webController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testImportFile_success() {
        String validFile = "valid file content";
        String mappedContent = "processed content";

        when(fileService.mapFileToContent(validFile)).thenReturn(mappedContent);
        ResponseEntity<Response> responseEntity = webController.importFile(validFile);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(mappedContent, responseEntity.getBody().getContent());
        verify(fileService, times(1)).mapFileToContent(validFile);
    }

    @Test
    void testImportFile_errorMapping() {
        String invalidFile = "invalid file content";
        when(fileService.mapFileToContent(invalidFile)).thenReturn(errorMessage);

        ResponseEntity<Response> responseEntity = webController.importFile(invalidFile);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("Error mapping file to content, file is null or empty", responseEntity.getBody().getContent());
        verify(fileService, times(1)).mapFileToContent(invalidFile);
    }

    @Test
    void testImportFile_emptyFile() {
        String emptyFile = "";
        when(fileService.mapFileToContent(emptyFile)).thenReturn(errorMessage);

        ResponseEntity<Response> responseEntity = webController.importFile(emptyFile);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("Error mapping file to content, file is null or empty", responseEntity.getBody().getContent());
        verify(fileService, times(1)).mapFileToContent(emptyFile);
    }

    @Test
    void testImportFile_nullFile() {
        String nullFile = null;
        when(fileService.mapFileToContent(nullFile)).thenReturn(errorMessage);

        ResponseEntity<Response> responseEntity = webController.importFile(nullFile);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("Error mapping file to content, file is null or empty", responseEntity.getBody().getContent());
        verify(fileService, times(1)).mapFileToContent(nullFile);
    }
}
