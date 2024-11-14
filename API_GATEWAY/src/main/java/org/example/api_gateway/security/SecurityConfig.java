package org.example.api_gateway.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.server.resource.authentication.ReactiveJwtAuthenticationConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        return http
                .csrf(ServerHttpSecurity.CsrfSpec::disable)
                .authorizeExchange(exchanges -> exchanges

                        // Tratamos de hacerlo con /internal pero no pudimos
                        .pathMatchers("/api/notificaciones/advertencia").permitAll()

                        .pathMatchers("/api/pruebas/reporte*").hasRole("ADMIN") //BIEN

                        .pathMatchers("/api/pruebas/listar").hasAnyRole("ADMIN", "EMPLEADO") //BIEN
                        .pathMatchers("/api/pruebas/crear").hasAnyRole("ADMIN", "EMPLEADO") //BIEN
                        .pathMatchers("/api/pruebas/finalizar").hasAnyRole("ADMIN", "EMPLEADO") //BIEN

                        .pathMatchers("/api/notificaciones/promocion").hasAnyRole("ADMIN", "EMPLEADO") //BIEN

                        .pathMatchers("/api/pruebas/posicion").hasRole("VEHICULO")

                        // .pathMatchers("/api/pruebas/**").permitAll() // para que no deniegue las peticiones del servicio pruebas

                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2
                        .jwt(Customizer.withDefaults()))
                .build();
    }

    @Bean
    public ReactiveJwtAuthenticationConverter jwtAuthenticationConverter() {
        ReactiveJwtAuthenticationConverter converter = new ReactiveJwtAuthenticationConverter();
        converter.setJwtGrantedAuthoritiesConverter(jwt -> {
            Map<String, Object> realmAccess = jwt.getClaimAsMap("realm_access");
            if (realmAccess == null || !realmAccess.containsKey("roles")) {
                return Flux.empty();
            }

            @SuppressWarnings("unchecked")
            List<String> roles = (List<String>) realmAccess.get("roles");
            List<GrantedAuthority> authorities = roles.stream()
                    .map(role -> new SimpleGrantedAuthority("ROLE_" + role))
                    .collect(Collectors.toList());

            return Flux.fromIterable(authorities);
        });
        return converter;
    }

}