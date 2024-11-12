package ar.edu.utn.frc.pruebas.servicies.interfaces;

import java.util.List;

public interface Service<T, ID> {
    void add(T entity);
    void update(T entity);
    T delete(ID id);
    T findById(ID id);
    List<T> findAll();
}
// Interface Service que recibe dos parametros genericos, T e ID.
// T es el tipo de dato de la entidad y ID es el tipo de dato del identificador de la entidad.
// Tiene los metodos add, update, delete, findById y findAll.
// add recibe un parametro de tipo T y no retorna nada.
// update recibe un parametro de tipo T y no retorna nada.
// delete recibe un parametro de tipo ID y retorna un objeto de tipo T.
// findById recibe un parametro de tipo ID y retorna un objeto de tipo T.
// findAll no recibe parametros y retorna una lista de objetos de tipo T.
