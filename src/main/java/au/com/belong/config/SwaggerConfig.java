package au.com.belong.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI springOpenApiConfig() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("X-API-KEY", new SecurityScheme()
                                .type(SecurityScheme.Type.APIKEY)
                                .description("API Key access")
                                .in(SecurityScheme.In.HEADER)
                                .name("X-API-Key")
                        ))
                .security(List.of(new SecurityRequirement().addList("api_key")))
                .info(new Info().title("Belong Phone API")
                        .description("Belong Phone API")
                        .contact(new Contact().name("Tom Lim")
                                .email("thlim78@yahoo.com")
                                .url("https://www.github.com/thlim78"))
                        .version("v1"));
    }
}
