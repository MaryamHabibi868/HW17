package ir.maktabsharifhw18.hibernate.repository;

import ir.maktabsharifhw18.hibernate.entity.BankName;
import ir.maktabsharifhw18.hibernate.entity.Card;
import ir.maktabsharifhw18.hibernate.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository  {

    void save(User user);

    User findByUserName(String userName);

    User findByUserId(Integer id);

    List<User> findAll();

}
