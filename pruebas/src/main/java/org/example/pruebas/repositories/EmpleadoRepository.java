package org.example.pruebas.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;
import org.example.pruebas.models.*;

import java.util.List;

@NoArgsConstructor
@Repository
public class EmpleadoRepository {
    @PersistenceContext
    private EntityManager em;

    // Métodos
    public Empleado findByLegajo(Integer legajo){
        try {
            return em.find(Empleado.class, legajo);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    public List<Prueba> obtenerPruebasIncidentePorLegajo(Integer legajo) {
        try {
            return em.createQuery("SELECT p FROM Prueba p WHERE p.inicidente = TRUE AND p.empleado.legajo = :legajo", Prueba.class)
                    .setParameter("legajo", legajo)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}

