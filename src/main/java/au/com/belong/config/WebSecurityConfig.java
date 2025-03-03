package au.com.belong.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import jakarta.servlet.Filter;

@Configuration
@EnableWebSecurity(debug = false)
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http,
                                           @Qualifier("authenticationFilter") Filter authenticationFilter,
                                           @Qualifier("delegatedAuthenticationEntryPoint") AuthenticationEntryPoint authEntryPoint)
            throws Exception {
        http.csrf(crsfConfigurer -> crsfConfigurer.disable())
                .authorizeHttpRequests(configurer -> configurer.requestMatchers(new AntPathRequestMatcher("/api-docs/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/swagger-ui/**")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/customers/**")).authenticated()
                        .requestMatchers(new AntPathRequestMatcher("/phones/**")).authenticated()
                        .anyRequest().denyAll())
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling((exception)-> exception.authenticationEntryPoint(authEntryPoint));
        return http.build();
    }
}