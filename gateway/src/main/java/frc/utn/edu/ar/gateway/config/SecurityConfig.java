package frc.utn.edu.ar.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {
    @Bean
    public SecurityWebFilterChain filterChain(ServerHttpSecurity http) throws Exception {
        http.authorizeExchange(exchanges -> exchanges
                        //ACA VAN TODOS LOS ENDPOINTS DE TODOS LOS MICROSERVICIOS

                        //Es un endpoint de acceso no controlado, no es necesario estar autenticado para consultarlo
                        .pathMatchers("/").permitAll()

                        //EndPoint para el envío de notificaciones
                        .pathMatchers(HttpMethod.POST,"/api/notificaciones").hasRole("EMPLEADO")

                        .pathMatchers("/api/pruebas/promociones").hasAnyRole("ADMIN", "EMPLEADO")
                        .pathMatchers("/api/ms-posicion/posicion/recibir-posicion").hasRole("VEHICULO")
                        .pathMatchers("/api/pruebas/reportes???").hasRole("ADMIN")

                        //Cualquier otra peticion, seguramente haya que cambiarlo por .anyRequest()
                        .anyExchange().authenticated()
                )
                .oauth2ResourceServer(oauth2 -> oauth2.jwt(withDefaults()))
                .csrf(ServerHttpSecurity.CsrfSpec::disable);
        return http.build();
    }
    @Bean
    public JwtAuthenticationConverter jwtAuthenticationConverter() {
        var jwtAuthenticationConverter = new JwtAuthenticationConverter();
        var grantedAuthoritiesConverter = new JwtGrantedAuthoritiesConverter();
        // Se especifica el nombre del claim a analizar
        grantedAuthoritiesConverter.setAuthoritiesClaimName("realm_access.roles");
        // Se agrega este prefijo en la conversión por una convención de Spring
        grantedAuthoritiesConverter.setAuthorityPrefix("ROLE_");
        // Se asocia el conversor de Authorities al Bean que convierte el token JWT a un objeto Authorization
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(grantedAuthoritiesConverter);
        return jwtAuthenticationConverter;
    }
}
