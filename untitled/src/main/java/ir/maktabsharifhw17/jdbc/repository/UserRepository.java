package ir.maktabsharifhw17.jdbc.repository;

import ir.maktabsharifhw17.jdbc.domains.User;
import ir.maktabsharifhw17.jdbc.repository.base.CrudRepository;

public interface UserRepository extends
        CrudRepository<User, Integer> {
}
