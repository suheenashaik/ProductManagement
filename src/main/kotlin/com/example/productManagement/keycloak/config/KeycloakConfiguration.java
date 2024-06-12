package com.example.productManagement.keycloak.config;

import jakarta.servlet.http.HttpServletResponse;
import org.jetbrains.annotations.NotNull;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;


import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class KeycloakConfiguration {
    private Keycloak keycloak = null;

    @Value("${keycloak1.auth-server-url}")
    private String serverUrl;

    @Value("${keycloak1.realm}")
    public String realm;

    @Value("${keycloak1.resource}")
    private String clientId;

    @Value("${keycloak1.credentials.secret}")
    private String clientSecret;

    @Value("${keycloak1.userName}")
    private String userName;

    @Value("${keycloak1.password}")
    private String password;

    @Value("keycloak.authority-prefix")
    private String authorityPrefix;

  @Bean
    public Keycloak getInstance() {
        if (keycloak == null) {
            keycloak = KeycloakBuilder.builder()
                    .serverUrl(serverUrl)
                    .realm(realm)
                    .grantType(OAuth2Constants.PASSWORD)
                    .username(userName)
                    .password(password)
                    .clientId(clientId)
                    .clientSecret(clientSecret)
//                    .resteasyClient(
//                    ResteasyClientBuilder.newClient()
////                        .connectionPoolSize(10)
////                        .build()
//                )
                    .build();
        }
        return keycloak;
    }

    @Bean
    public SecurityFilterChain filterChain(@NotNull HttpSecurity http) throws Exception{
        http.cors(AbstractHttpConfigurer::disable).csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests((authz) -> authz
                        .requestMatchers(new AntPathRequestMatcher( "/userLogins/login/*"))
                        .permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/register/*"))
                        .permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/registers/*"))
                        .permitAll()
                        .requestMatchers(new AntPathRequestMatcher("http://localhost:8080/*"))
                        .permitAll()
                        .requestMatchers(new AntPathRequestMatcher( "/users/logout"))
                        .permitAll()
                        .requestMatchers(new AntPathRequestMatcher( "/sessions"))
                        .permitAll()
                        .requestMatchers(new AntPathRequestMatcher( "/broadcasts/*"))
                        .permitAll()
                        .requestMatchers(new AntPathRequestMatcher( "/recordings/*"))
                        .permitAll()
                        .requestMatchers(new AntPathRequestMatcher( "/call/*"))
                        .permitAll()
                        .anyRequest().permitAll())
                .sessionManagement((sessionManagement) -> {
                    sessionManagement.sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                }).exceptionHandling(exceptionHandling -> exceptionHandling
                        .authenticationEntryPoint((request, response, ex) -> {
                            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
                        }))
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(jwt -> jwt.jwtAuthenticationConverter(new KeycloakJwtAuthenticationConverter())));

        return http.build();
    }
    class KeycloakJwtAuthenticationConverter extends JwtAuthenticationConverter {

        public KeycloakJwtAuthenticationConverter() {
            KeycloakJwtAuthoritiesConverter grantedAuthoritiesConverter =
                    new KeycloakJwtAuthoritiesConverter();
            grantedAuthoritiesConverter.setAuthorityPrefix(authorityPrefix);

            setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
            setPrincipalClaimName(clientId);
        }
    }
    static class KeycloakJwtAuthoritiesConverter implements Converter<Jwt, Collection<GrantedAuthority>> {

        private String authorityPrefix = "";

        public KeycloakJwtAuthoritiesConverter() {}

        public KeycloakJwtAuthoritiesConverter setAuthorityPrefix(String authorityPrefix) {
            Assert.notNull(authorityPrefix, "authorityPrefix cannot be null");
            this.authorityPrefix = authorityPrefix;
            return this;
        }

        @Override
        public Collection<GrantedAuthority> convert(@NotNull Jwt source) {
            Map<String, Object> realmAccess = source.getClaim("realm_access");
            if (Objects.isNull(realmAccess)) {
                return Collections.emptySet();
            }

            Object roles = realmAccess.get("roles");
            if (Objects.isNull(roles) || !Collection.class.isAssignableFrom(roles.getClass())) {
                return Collections.emptySet();
            }

            Collection<?> rolesCollection = (Collection<?>) roles;

            return rolesCollection.stream().filter(String.class::isInstance)
                    .map(x -> new SimpleGrantedAuthority(authorityPrefix + x)).collect(Collectors.toSet());
        }
    }


}
