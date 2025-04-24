package ir.maktabsharifhw18.hibernate.repository;

import ir.maktabsharifhw18.hibernate.config.ApplicationContext;
import ir.maktabsharifhw18.hibernate.entity.User;
import jakarta.persistence.EntityManager;
import lombok.NoArgsConstructor;

import javax.swing.text.html.parser.Entity;
import java.sql.*;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor
public class UserRepositoryImp implements UserRepository {

    EntityManager entityManager = ApplicationContext
            .getInstance().getEntityManager();

    @Override
    public void save(User user) {
        entityManager.persist(user);
    }

    @Override
    public User findByUserName(String userName) {
        return entityManager.find(User.class, userName);
    }

    @Override
    public User findByUserId(Integer id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public List<User> findAll() {
        return entityManager.createQuery
                ("SELECT u FROM User u", User.class).getResultList();
    }
}
