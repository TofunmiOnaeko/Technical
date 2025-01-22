package application.config;

import application.interceptor.InteractionInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final InteractionInterceptor interactionInterceptor;

    public WebConfig(InteractionInterceptor interactionInterceptor) {
        this.interactionInterceptor = interactionInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(interactionInterceptor)
                .addPathPatterns("/**");
    }
}
