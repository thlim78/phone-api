package au.com.belong.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Component;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;

@Component
public class AuthenticationService {
    private static final String AUTH_TOKEN_HEADER_NAME = "X-API-KEY";

    @Value("${belong.api.key}")
    private String API_KEY;

    public Optional<Authentication> getAuthentication(HttpServletRequest request) {
        String apiKey = request.getHeader(AUTH_TOKEN_HEADER_NAME);
        if (apiKey == null || !apiKey.equals(API_KEY)) {
            return Optional.empty();
        }
        return Optional.of(new ApiKeyAuthentication(apiKey, AuthorityUtils.NO_AUTHORITIES));
    }
}