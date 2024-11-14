package org.example.gateway.security;

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

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http) throws Exception {
        http.authorizeExchange(exchanges -> exchanges

                        .pathMatchers("/api/notis/warning").permitAll()

                        //El administrador accede a los reportes
                        .pathMatchers("/api/pruebas/reporte*").hasRole("ADMIN")

                        //Los endpoints de las pruebas son usadas por el empleado y el administrador
                        .pathMatchers("/api/pruebas/create").hasAnyRole("ADMIN", "EMPLEADO")
                        .pathMatchers("/api/pruebas/list").hasAnyRole("ADMIN", "EMPLEADO")
                        .pathMatchers("/api/pruebas/complete").hasAnyRole("ADMIN", "EMPLEADO")

                        .pathMatchers("/api/notis/promotion").hasAnyRole("ADMIN", "EMPLEADO")

                        //Solamente el vehiculo puede realizar operaciones con la posicion
                        .pathMatchers("/api/pruebas/position").hasRole("VEHICULO")

                        //Autorizar para cualquier otro
                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()))
                .csrf(ServerHttpSecurity.CsrfSpec::disable);
        return http.build();
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

    /*
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        var jwtAuthenticationConverter = new JwtAuthenticationConverter();
        var grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        // Se especifica el nombre del claim para analizar
        grantedAuthoritiesConverter.setAuthoritiesClaimName("realm_access.roles");
        // Se agrega este prefijo en la conversión por una convención de Spring
        grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
        // Se asocia el conversor de Authorities al Bean que convierte el token JWT hacia un objeto Authorization
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }

     */


}