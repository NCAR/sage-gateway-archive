package sgf.gateway.dao;

import java.io.Serializable;
import java.util.List;

public interface Repository<E, PK extends Serializable> {

    /**
     * Return entity with given id (primary key).  Or null.
     */
    E get(PK id);

    /**
     * Return all entities.  Can return empty list.
     */
    List<E> getAll();

    /**
     * Add (save or update) an Entity.
     */
    void add(E newInstance);

    /**
     * Persist and return the new identifier PK.
     */
    Serializable create(E newInstance);

    /**
     * Remove (delete) a persistent Entity.
     */
    void remove(E persistentObject);

}
