package ir.maktabsharifhw17.jdbc.repository;

import ir.maktabsharifhw17.jdbc.config.ApplicationContext;
import ir.maktabsharifhw17.jdbc.domains.BankName;
import ir.maktabsharifhw17.jdbc.domains.Card;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CardRepositoryImp implements CardRepository {

    private final Connection connection;

    public CardRepositoryImp(Connection connection) {
        this.connection = connection;
    }
    @Override
    public Card create(Card card) {
        String query = "insert into cards (card_id, card_number, bank_name, " +
                "balance, expired_date, user_id) values (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, card.getId());
            preparedStatement.setString(2, card.getCardNumber());
            preparedStatement.setString(3, card.getBankName().name());
            preparedStatement.setDouble(4, card.getBalance());
            preparedStatement.setDate(5, Date.valueOf(card.getExpiredDate()));
            preparedStatement.setInt(6, card.getUser().getId());

            int rowAffected = preparedStatement.executeUpdate();

            if (rowAffected > 0) {
                return card;
            } else {
                return null;
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting card");
        }


    }

    @Override
    public Card update(Card card) {
        return null;
    }

    @Override
    public List<Card> findAll() {
        List<Card> cards = new ArrayList<>();
        String query = "select * from cards";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                Card card = new Card();
                card.setId(resultSet.getInt("card_id"));
                card.setCardNumber(resultSet.getString("card_number"));
                card.setBankName(BankName.valueOf(resultSet.getString("bank_name")));
                card.setBalance(resultSet.getDouble("balance"));
                card.setExpiredDate(resultSet.getDate("expired_date").toLocalDate());

                UserRepository userRepository = ApplicationContext.getInstance().getUserRepository();
                userRepository.findById(resultSet.getInt("user_id"))
                        .ifPresent(card::setUser);

                cards.add(card);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error finding all cards");
        }
        return cards;
    }


    @Override
    public Optional<Card> findById(Integer integer) {
        return Optional.empty();
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

    public Optional<Card> findByCardNumber(String cardNumber) {
        String query = "select * from cards where card_number = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, cardNumber);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()){
                Card card = new Card();
                card.setId(resultSet.getInt("card_id"));
                card.setCardNumber(resultSet.getString("card_number"));
                card.setBankName(BankName.valueOf(resultSet.getString("bank_name")));
                card.setBalance(resultSet.getDouble("balance"));
                card.setExpiredDate(resultSet.getDate("expired_date").toLocalDate());


                UserRepository userRepository = ApplicationContext.getInstance().getUserRepository();

                userRepository
                        .findById(resultSet.getInt("user_id"))
                        .ifPresent(card :: setUser);

                return Optional.of(card);
            }
        } catch (SQLException e){
            throw new RuntimeException("Error finding card by card number" + cardNumber);
        }
        return Optional.empty();
    }


    public int deleteByCardNumber(String cardNumber) {
        String query = "delete from cards where card_id = ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, cardNumber);
            return preparedStatement.executeUpdate();
        }
        catch (SQLException e){
            throw new RuntimeException("Error deleting card by card number" + cardNumber);
        }
    }

    public List<Card> findAllCardByBankName(BankName bankName) {
        List<Card> cards = new ArrayList<>();
        String query = "select * from cards where bank_name = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setString(1, bankName.name());
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()){
                Card card = new Card();
                card.setId(resultSet.getInt("card_id"));
                card.setCardNumber(resultSet.getString("card_number"));
                card.setBankName(BankName.valueOf(resultSet.getString("bank_name")));
                card.setBalance(resultSet.getDouble("balance"));
                card.setExpiredDate(resultSet.getDate("expired_date").toLocalDate());

                UserRepository userRepository = ApplicationContext.getInstance().getUserRepository();

                userRepository.findById(resultSet.getInt("user_id"))
                        .ifPresent(card :: setUser);

                cards.add(card);
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error finding card by bank name" + bankName);
        }
        return cards;
    }
}
