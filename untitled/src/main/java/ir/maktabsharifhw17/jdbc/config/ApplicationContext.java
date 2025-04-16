package ir.maktabsharifhw17.jdbc.config;

import ir.maktabsharifhw17.jdbc.repository.CardRepository;
import ir.maktabsharifhw17.jdbc.repository.CardRepositoryImp;
import ir.maktabsharifhw17.jdbc.repository.UserRepository;
import ir.maktabsharifhw17.jdbc.repository.UserRepositoryImp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ApplicationContext {

    private static ApplicationContext applicationContext;

    private ApplicationContext() {
    }

    public static ApplicationContext getInstance() {
        if (applicationContext == null) {
            applicationContext = new ApplicationContext();
        }
        return applicationContext;
    }

    private Connection connection;

    public Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(
                        ApplicationProperties.JDBC_URL,
                        ApplicationProperties.JDBC_USER,
                        ApplicationProperties.JDBC_PASSWORD
                );
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
        return connection;
    }

    private UserRepository userRepository;

    public UserRepository getUserRepository() {
        if (userRepository == null) {
            userRepository = new UserRepositoryImp(getConnection());
        }
        return userRepository;
    }

    private CardRepository cardRepository;

    public CardRepository getCardRepository() {
        if (cardRepository == null) {
            cardRepository = new CardRepositoryImp(getConnection());
        }
        return cardRepository;
    }
}
