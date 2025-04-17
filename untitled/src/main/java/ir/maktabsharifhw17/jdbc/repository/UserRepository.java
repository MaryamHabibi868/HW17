package ir.maktabsharifhw17.jdbc.repository;

import ir.maktabsharifhw17.jdbc.domains.User;
import ir.maktabsharifhw17.jdbc.repository.base.CrudRepository;

import java.util.Optional;

public interface UserRepository extends
        CrudRepository<User, Integer> {
    Optional<User> findByUserName(String userName);

    Optional<User> login(String userName, String password);

}
