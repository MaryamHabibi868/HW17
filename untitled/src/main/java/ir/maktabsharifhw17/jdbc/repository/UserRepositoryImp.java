package ir.maktabsharifhw17.jdbc.repository;

import ir.maktabsharifhw17.jdbc.domains.User;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class UserRepositoryImp implements UserRepository {

    private final Connection connection;

    public UserRepositoryImp(Connection connection) {
        this.connection = connection;
    }

    @Override
    public User create(User user) {
        String query = "INSERT INTO users (first_name, " +
                "last_name, user_name, password) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setString(1, user.getFirstName());
            preparedStatement.setString(2, user.getLastName());
            preparedStatement.setString(3, user.getUserName());
            preparedStatement.setString(4, user.getPassword());

            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    user.setId(generatedKeys.getInt(1));
                }
                return user;
            } else {
                return null;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error creating user");
        }
    }

    @Override
    public User update(User user) {
        return null;
    }

    @Override
    public List<User> findAll() {
        return List.of();
    }

    @Override
    public Optional<User> findById(Integer id) {
       User user = new User();
       try (PreparedStatement preparedStatement = connection.prepareStatement(
               "select * from users where user_id = ?")) {
           preparedStatement.setInt(1, id);
           ResultSet resultSet = preparedStatement.executeQuery();
           while (resultSet.next()) {
               user.setId(resultSet.getInt("user_id"));
               user.setFirstName(resultSet.getString("first_name"));
               user.setLastName(resultSet.getString("last_name"));
               user.setUserName(resultSet.getString("user_name"));
               user.setPassword(resultSet.getString("password"));
           }
       } catch (SQLException e) {
           throw new RuntimeException("User with id " + id + " not found");
       }
       return Optional.of(user);
    }

    @Override
    public int deleteById(Integer integer) {
        return 0;
    }

    @Override
    public int deleteAll() {
        return 0;
    }

    @Override
    public boolean existsById(Integer integer) {
        return false;
    }

    @Override
    public long countAll() {
        return 0;
    }

    public Optional<User> login(String userName, String password) {
        String query = "select first_name , last_name from users" +
                " where user_name = ? and password = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, userName);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                User user = new User();
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));

                return Optional.of(user);
            }

        }
        catch (SQLException e) {
            throw new RuntimeException("User with name " + userName + " password" + password + " not found");
        }
        return Optional.empty();
    }

    public Optional<User> findByUserName(String userName) {
        String query = "select * from users where user_name = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, userName);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                User user = new User();
                user.setUserName(resultSet.getString("user_name"));
                return Optional.of(user);
            }

        } catch (SQLException e) {
            throw new RuntimeException("User with name " + userName + " not found");
        }
        return Optional.empty();
    }
}
