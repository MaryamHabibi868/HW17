package ir.maktabsharifhw18.hibernate.repository;

import ir.maktabsharifhw18.hibernate.entity.User;
import ir.maktabsharifhw18.hibernate.repository.base.CrudRepository;

import java.util.Optional;

public interface UserRepository extends
        CrudRepository<User, Integer> {
    Optional<User> findByUserName(String userName);

    Optional<User> login(String userName, String password);

}
