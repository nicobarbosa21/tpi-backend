package org.example.pruebas.repositories;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import lombok.NoArgsConstructor;
import org.example.pruebas.models.Interesado;
import org.springframework.stereotype.Repository;

@NoArgsConstructor
@Repository
public class InteresadoRepository {
    @PersistenceContext
    private EntityManager em;

    // METODOS
    public Interesado findByDocumento(String documento) {
        try {
            System.out.println(documento);
            TypedQuery<Interesado> query = em.createQuery("SELECT i FROM Interesado i WHERE i.documento = :documento", Interesado.class);
            query.setParameter("documento", documento);
            System.out.println(query);
            return query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
    
}
