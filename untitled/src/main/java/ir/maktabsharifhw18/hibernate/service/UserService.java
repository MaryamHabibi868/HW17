/*
package ir.maktabsharifhw18.hibernate.service;

import ir.maktabsharifhw18.hibernate.config.ApplicationContext;
import ir.maktabsharifhw18.hibernate.entity.User;
import ir.maktabsharifhw18.hibernate.repository.UserRepository;

import java.util.Optional;

public class UserService {

    private final UserRepository userRepository = ApplicationContext.getInstance().getUserRepository();

    public Optional<User> register(User user) {
        if (userRepository.findByUserName(user.getUserName()).isPresent()) {
            System.out.println("this user already exists");
            return Optional.empty();
        }

        User savedUser = userRepository.create(user);
        return Optional.ofNullable(savedUser);
    }


    public Optional<User> login(String userName, String password) {
        return userRepository.login(userName, password);
    }


    public boolean existsByUsername(String userName) {
        return userRepository.findByUserName(userName).isPresent();
    }


    public Optional<User> findById(Integer id) {
        return userRepository.findById(id);
    }


    public void printAllUsers() {
        userRepository.findAll().forEach(System.out::println);
    }
}

*/
