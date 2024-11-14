package org.example.api_gateway.config;

import org.example.api_gateway.security.MyCustomFilter;
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
                                        @Value("http://localhost:8080/api/notificaciones") String uriNotificaciones) {
        return builder.routes()
                // Ruteo al Microservicio de Notificaciones
                .route(p -> p.path("/api/notificaciones/**").uri(uriNotificaciones))
                // Ruteo al Microservicio de Pruebas
                .route(p -> p.path("/api/pruebas/**").uri(uriPruebas))
                .build();

    }

    @Bean
    public GlobalFilter MycustomFilter() {
        return new MyCustomFilter();
    }
}
