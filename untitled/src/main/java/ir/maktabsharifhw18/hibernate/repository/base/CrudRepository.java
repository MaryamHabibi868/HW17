package ir.maktabsharifhw18.hibernate.repository.base;

import java.util.List;
import java.util.Optional;

public interface CrudRepository <T extends BaseEntity<ID>, ID> {
    T create(T t);

    T update(T t);

    List<T> findAll();

    Optional<T> findById(ID id);

    int deleteById(ID id);

    int deleteAll();

    boolean existsById(ID id);

    long countAll();


}
