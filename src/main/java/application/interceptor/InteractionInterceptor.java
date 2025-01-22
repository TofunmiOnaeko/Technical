package application.interceptor;

import application.model.Interaction;
import application.repository.InteractionRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

@Slf4j
@Component
public class InteractionInterceptor implements HandlerInterceptor {

    @Autowired
    InteractionRepository interactionRepository;
    private ThreadLocal<LocalDateTime> startTime = new ThreadLocal<>();
    ReadWriteLock lock = new ReentrantReadWriteLock();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        startTime.set(LocalDateTime.now());
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        double timeLapsed = Duration.between(startTime.get(), LocalDateTime.now()).getSeconds();

        Interaction interaction = Interaction.builder()
                .requestCountryCode(String.valueOf(request.getLocale()))
                .requestTimeStamp(startTime.get())
                .timeLapsed(timeLapsed)
                .requestUri(request.getRequestURI())
                .httpResponseCode(response.getStatus())
                .build();

        try {
            lock.writeLock().lock();
            interactionRepository.save(interaction);
        } catch (RuntimeException e) {
            log.error("unable to save a transaction to the database");
        } finally {
            lock.writeLock().unlock();
        }

        startTime.remove();
    }

}
