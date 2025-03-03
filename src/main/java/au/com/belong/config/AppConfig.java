package au.com.belong.config;

import au.com.belong.domain.enums.StatusConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;

@Configuration
public class AppConfig {
    @Autowired
    public void configureConverter(FormatterRegistry registry) {
        registry.addConverter(new StatusConverter());
    }
}
