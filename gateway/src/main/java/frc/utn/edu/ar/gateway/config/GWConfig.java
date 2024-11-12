package frc.utn.edu.ar.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GWConfig {

    @Bean
    public RouteLocator configurarRutas(RouteLocatorBuilder builder,
                                        @Value("${gateway-tp.url-pruebas}") String uriPruebas,
                                        @Value("${gateway-tp.url-notificaciones}") String uriNotificaciones){

        return builder.routes()
                // Ruteo al Microservicio de notificaciones
                .route(p -> p.path("/api/notificaciones/**").uri(uriNotificaciones)) //ANDAN LAS NOTIFICACIONES
                // Ruteo al Microservicio de pruebas
                .route(p -> p.path("/api/pruebas/**").uri(uriPruebas))
                .build();
    }

}

