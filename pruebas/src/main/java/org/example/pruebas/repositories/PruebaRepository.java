package org.example.pruebas.repositories;

import jakarta.transaction.Transactional;
import org.example.pruebas.models.Prueba;

import org.example.pruebas.services.ConfiguracionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Repository

public class PruebaRepository {
    @PersistenceContext
    private EntityManager em;
    private ConfiguracionService configuracionService;

    // Métodos
    @Transactional
    public Prueba save(Prueba prueba) {
        em.persist(prueba);
        return prueba;
    }
    @Autowired
    public PruebaRepository( ConfiguracionService configuracionService) {
        this.configuracionService = configuracionService;
    }

    public Boolean existePruebaActivaParaVehiculo(Integer idVehiculo) {
        try {
            return !em.createQuery("SELECT p FROM Prueba p WHERE p.vehiculo.id = :idVehiculo AND (p.fecha_hora_fin IS NULL)", Prueba.class)
                    .setParameter("idVehiculo", idVehiculo)
                    .getResultList().isEmpty();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Boolean existePruebaActiva(Integer idPrueba) {
        try {
            return !em.createQuery("SELECT p FROM Prueba p WHERE p.id = :idPrueba AND (p.fecha_hora_fin IS NULL)", Prueba.class)
                    .setParameter("idPrueba", idPrueba)
                    .getResultList().isEmpty();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Prueba pruebaByVehiculo(Integer idVehiculo) {
        try {
            return em.createQuery("SELECT p FROM Prueba p WHERE p.vehiculo.id = :idVehiculo AND p.fecha_hora_fin IS NULL", Prueba.class)
                    .setParameter("idVehiculo", idVehiculo)
                    .getResultStream()
                    .findFirst()
                    .orElse(null);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public Prueba findByID(Integer id) {
        Prueba prueba = em.find(Prueba.class, id);
        return prueba;
    }

    public void finalizarPruebaEnCurso(Integer id, String comentario){
        Prueba prueba = findByID(id);
        Timestamp fechaActual = Timestamp.from(Instant.now());
        prueba.setFecha_hora_fin(fechaActual);
        if (comentario != null){
            prueba.setComentarios(comentario);
        }
        em.persist(prueba);
    }

    // LISTAR PRUEBAS EN CURSO
    public List<Prueba> findAllEnCurso() {
        try {
            return em.createQuery("SELECT p FROM Prueba p WHERE p.fecha_hora_fin IS NULL", Prueba.class)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<Prueba> obtenerPruebasIncidente(){
        try {
            return em.createQuery("SELECT p FROM Prueba p WHERE p.inicidente = TRUE", Prueba.class)
                    .getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
       }

    }
}