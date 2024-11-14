package org.example.pruebas.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Repository;
import org.example.pruebas.models.*;

import java.util.List;

@NoArgsConstructor
@Repository
public class VehiculoRepository {
    @PersistenceContext
    private EntityManager em;

    // Métodos
    public Vehiculo findByPatente(String patente) {
        try {
            TypedQuery<Vehiculo> query = em.createQuery("SELECT veh FROM Vehiculo veh WHERE veh.patente = :patente", Vehiculo.class);
            query.setParameter("patente", patente);
            return query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // Métodos
    public Vehiculo findByID(Integer id){
        try {
            return em.find(Vehiculo.class, id);
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }

    //Pruebas realizadas para un vehiculo
    public List<Prueba> getPruebasFinalizadasPorVehiculo(String patente) {
        try {
            return em.createQuery("SELECT pru FROM Prueba pru WHERE pru.fecha_hora_fin IS NOT NULL AND pru.vehiculo.patente = :patente", Prueba.class)
                    .setParameter("patente", patente)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }




}
