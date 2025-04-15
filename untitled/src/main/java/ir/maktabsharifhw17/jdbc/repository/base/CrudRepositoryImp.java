package ir.maktabsharifhw17.jdbc.repository.base;

import ir.maktabsharifhw17.jdbc.domains.base.BaseEntity;

import java.util.List;
import java.util.Optional;

public class CrudRepositoryImp <T extends BaseEntity<ID>, ID>
        implements CrudRepository<T, ID>{
    @Override
    public T create(T t) {
        return null;
    }

    @Override
    public T update(T t) {
        return null;
    }

    @Override
    public List<T> findAll() {
        return List.of();
    }

    @Override
    public Optional<T> findById(ID id) {
        return Optional.empty();
    }

    @Override
    public int deleteById(ID id) {
        return 0;
    }

    @Override
    public int deleteAll() {
        return 0;
    }

    @Override
    public boolean existsById(ID id) {
        return false;
    }

    @Override
    public long countAll() {
        return 0;
    }
}
