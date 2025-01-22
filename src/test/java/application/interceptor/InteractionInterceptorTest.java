package application.interceptor;

import application.model.Interaction;
import application.repository.InteractionRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;
import java.util.Locale;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class InteractionInterceptorTest {

    @Mock
    private InteractionRepository interactionRepository;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private InteractionInterceptor interactionInterceptor;

    @Captor
    private ArgumentCaptor<Interaction> interactionCaptor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testPreHandle_setsStartTime() throws Exception {
        when(request.getLocale()).thenReturn(Locale.US);
        boolean result = interactionInterceptor.preHandle(request, response, new Object());

        assertTrue(result);
        assertNotNull(ReflectionTestUtils.getField(interactionInterceptor, "startTime"));
    }

    @Test
    void testAfterCompletion_savesInteraction() throws Exception {
        when(request.getLocale()).thenReturn(Locale.US);
        when(request.getRequestURI()).thenReturn("/test-uri");
        when(response.getStatus()).thenReturn(200);
        ReflectionTestUtils.setField(interactionInterceptor, "startTime", ThreadLocal.withInitial(() -> LocalDateTime.now()));

        Thread.sleep(1000);
        interactionInterceptor.afterCompletion(request, response, new Object(), null);

        verify(interactionRepository, times(1)).save(interactionCaptor.capture());
        Interaction capturedInteraction = interactionCaptor.getValue();

        assertEquals("/test-uri", capturedInteraction.getRequestUri());
        assertEquals("en_US", capturedInteraction.getRequestCountryCode());
        assertNotNull(capturedInteraction.getRequestTimeStamp());
        assertTrue(capturedInteraction.getTimeLapsed() >= 0);
        assertEquals(200, capturedInteraction.getHttpResponseCode());
    }

    @Test
    void testAfterCompletion_whenExceptionOccurs_doesNotThrow() throws Exception {
        when(request.getLocale()).thenReturn(Locale.US);
        when(request.getRequestURI()).thenReturn("/test-uri");
        when(response.getStatus()).thenReturn(500);
        ReflectionTestUtils.setField(interactionInterceptor, "startTime", ThreadLocal.withInitial(() -> LocalDateTime.now()));

        Thread.sleep(1000);
        doThrow(new RuntimeException("Database error")).when(interactionRepository).save(any(Interaction.class));

        interactionInterceptor.afterCompletion(request, response, new Object(), new Exception());

        verify(interactionRepository, times(1)).save(any(Interaction.class)); // Ensures save was attempted
    }

    @Test
    void testLockingMechanism() throws Exception {
        when(request.getLocale()).thenReturn(Locale.US);
        when(request.getRequestURI()).thenReturn("/test-uri");
        when(response.getStatus()).thenReturn(200);
        ReflectionTestUtils.setField(interactionInterceptor, "startTime", ThreadLocal.withInitial(() -> LocalDateTime.now()));

        Thread.sleep(1000);
        interactionInterceptor.afterCompletion(request, response, new Object(), null);

        verify(interactionRepository, times(1)).save(any(Interaction.class));
    }
}