package ir.maktabsharifhw17.jdbc.repository;

import ir.maktabsharifhw17.jdbc.domains.Card;
import ir.maktabsharifhw17.jdbc.domains.Transaction;
import ir.maktabsharifhw17.jdbc.domains.TransactionStatus;
import ir.maktabsharifhw17.jdbc.domains.TransactionType;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public class TransactionRepositoryImp implements
        TransactionRepository {

    private Connection connection;

    public TransactionRepositoryImp(Connection connection) {
        this.connection = connection;
    }


    @Override
    public Transaction create(Transaction transaction) {
        String query = "insert into transactions" +
                "(transaction_id , source_card_number, destination_card_number," +
                " amount , transaction_type , transaction_status , transaction_date" +
                "values ( ? , ? , ? , ? , ? , ? , ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)){
            preparedStatement.setInt(1, transaction.getId());
            preparedStatement.setString(2 , String.valueOf(transaction.getSourceCardNumber()));
            preparedStatement.setString(3 , String.valueOf(transaction.getDestinationCardNumber()));
            preparedStatement.setDouble(4 , transaction.getAmount());
            preparedStatement.setString(5, transaction.getTransactionType().name());
            preparedStatement.setString(6, transaction.getTransactionStatus().name());
            preparedStatement.setDate(7, Date.valueOf(String.valueOf(transaction.getTransactionDate())));

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                return transaction;
            } else {
                return null;
            }

        } catch (SQLException e) {
            throw new RuntimeException("Error inserting transaction " + transaction);
        }
    }

    @Override
    public Transaction update(Transaction transaction) {
        return null;
    }

    @Override
    public List<Transaction> findAll() {
        return List.of();
    }

    @Override
    public Optional<Transaction> findById(Integer integer) {
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


    public Transaction perfomCardToCard
            (String sourceCardNumber, String destinationCardNumber,
             double amount) {

        CardRepository cardRepository = new CardRepositoryImp(connection);
        TransactionRepository transactionRepository = new TransactionRepositoryImp(connection);

        Transaction transaction = new Transaction();
        transaction.setId();
        transaction.setTransactionType(TransactionType.CARD_TO_CARD);
        transaction.setAmount(amount);
        transaction.setTransactionDate(LocalDateTime.now());

        Optional<Card> findSourceCard = cardRepository.findByCardNumber(sourceCardNumber);
        Optional<Card> findDestinationCard = cardRepository.findByCardNumber(destinationCardNumber);

        if (findSourceCard.isEmpty() || findDestinationCard.isEmpty()) {
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transactionRepository.create(transaction);
            return transaction;
        }

        Card sourceCard = findSourceCard.get();
        Card destinationCard = findDestinationCard.get();

        transaction.setSourceCardNumber(sourceCard);
        transaction.setDestinationCardNumber(destinationCard);

        if (amount > 15_000_000) {
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transactionRepository.create(transaction);
            return transaction;
        } else {
            double fee = 0;
            if (!sourceCard.getBankName().equals(destinationCard.getBankName())) {
                if (amount <= 10_000_000) {
                    fee = 720;
                } else {
                    double extra = amount - 10_000_000;
                    int extraMillions = (int) Math.ceil(extra / 1_000_000);
                    fee = 1000 + (extraMillions * 100);
                }
            }

            if (sourceCard.getBalance() < amount + fee) {
                transaction.setTransactionStatus(TransactionStatus.FAILED);
                transactionRepository.create(transaction);
                return transaction;
            }
            sourceCard.setBalance(sourceCard.getBalance() - (amount + fee));
            destinationCard.setBalance(destinationCard.getBalance() + fee);

            transaction.setTransactionStatus(TransactionStatus.SUCCESSFUL);

            cardRepository.update(sourceCard);
            cardRepository.update(destinationCard);

            transactionRepository.create(transaction);

            return transaction;
        }
    }
}
