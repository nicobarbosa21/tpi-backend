package org.example.notificaciones.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import jakarta.servlet.Filter;


import java.io.IOException;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CorsFilter implements Filter {


    // Cambiar el valor de ALLOWED_ORIGIN por el valor correcto, permitiendo solo el acceso desde el gateway y no desde los puertos de los microservicios
    private static final String ALLOWED_ORIGIN = "http://localhost:8050";

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) res;

        String origin = request.getHeader("Origin");

        //SI LA PETICION ES DESDE EL PUERTO 8050, SE PERMITE
        if (origin != null && origin.equals(ALLOWED_ORIGIN)) {
            System.out.println(origin);
            chain.doFilter(req, res);

        } else {
            //SI NO ES UNA PETICION DESDE EL PUERTO 8050, SE RECHAZA
            System.out.println(origin);
            response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        }
    }
}
