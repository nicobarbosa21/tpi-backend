package org.example.gateway.config;

import org.example.gateway.security.MyCustomFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GWConfig {

    @Bean
    public RouteLocator configurarRutas(RouteLocatorBuilder builder,
                                        @Value("http://localhost:8081/api/pruebas") String uriPruebas,
                                        @Value("http://localhost:8080/api/notis") String uriNotificaciones) {
        return builder.routes()
                // Ruteo a notificaciones
                .route(p -> p.path("/api/notis/**").uri(uriNotificaciones))
                // Ruteo a pruebas
                .route(p -> p.path("/api/pruebas/**").uri(uriPruebas))
                .build();

    }

    @Bean
    public GlobalFilter MycustomFilter() {
        return new MyCustomFilter();
    }
    // Implementar un filtro global que agregue el header "Origin" con el valor
    // "http://localhost:8050" a todas las peticiones que pasen por el gateway.
}
