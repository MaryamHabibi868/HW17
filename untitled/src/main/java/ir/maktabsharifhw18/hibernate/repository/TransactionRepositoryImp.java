package ir.maktabsharifhw18.hibernate.repository;

import ir.maktabsharifhw18.hibernate.config.ApplicationContext;
import ir.maktabsharifhw18.hibernate.entity.Card;
import ir.maktabsharifhw18.hibernate.entity.Transaction;
import ir.maktabsharifhw18.hibernate.entity.TransactionStatus;
import ir.maktabsharifhw18.hibernate.entity.TransactionType;

import java.sql.*;
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
                "(source_card_number, destination_card_number," +
                " amount , transaction_type , transaction_status , transaction_date)" +
                " values ( ? , ? , ? , ? , ? , ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setString(1 , transaction.getSourceCard().getCardNumber());
            preparedStatement.setString(2 , transaction.getDestinationCard().getCardNumber());
            preparedStatement.setDouble(3 , transaction.getAmount());
            preparedStatement.setString(4, transaction.getTransactionType().name());
            preparedStatement.setString(5, transaction.getTransactionStatus().name());
            preparedStatement.setTimestamp(6, Timestamp.valueOf(transaction.getTransactionDate()));

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
                if (generatedKeys.next()) {
                    transaction.setId(generatedKeys.getInt(1));
                }
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

        transaction.setSourceCard(sourceCard);
        transaction.setDestinationCard(destinationCard);

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
            destinationCard.setBalance(destinationCard.getBalance() + amount);

            transaction.setTransactionStatus(TransactionStatus.SUCCESSFUL);

            cardRepository.update(sourceCard);
            cardRepository.update(destinationCard);

            transactionRepository.create(transaction);

            return transaction;
        }
    }

    public Transaction performPaya(String sourceCardNumber, String destinationCardNumber,
                                   double amount) {
        CardRepository cardRepository = ApplicationContext.getInstance().getCardRepository();
        TransactionRepository transactionRepository = ApplicationContext.getInstance().getTransactionRepository();

        Transaction transaction = new Transaction();
        transaction.setTransactionType(TransactionType.PAYA_INDIVIDUAL);
        transaction.setAmount(amount);
        transaction.setTransactionDate(LocalDateTime.now());

        Optional<Card> sourceOpt = cardRepository.findByCardNumber(sourceCardNumber);
        Optional<Card> destOpt = cardRepository.findByCardNumber(destinationCardNumber);

        if (sourceOpt.isEmpty() || destOpt.isEmpty()) {
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transactionRepository.create(transaction);
            return transaction;
        }

        Card sourceCard = sourceOpt.get();
        Card destinationCard = destOpt.get();

        transaction.setSourceCard(sourceCard);
        transaction.setDestinationCard(destinationCard);

        if (amount > 50_000_000) {
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transactionRepository.create(transaction);
            return transaction;
        }

        double fee = amount * 0.001;


        if (sourceCard.getBalance() < amount + fee) {
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transactionRepository.create(transaction);
            return transaction;
        }

        sourceCard.setBalance(sourceCard.getBalance() - (amount + fee));
        destinationCard.setBalance(destinationCard.getBalance() + amount);


        transaction.setTransactionStatus(TransactionStatus.SUCCESSFUL);

        cardRepository.update(sourceCard);
        cardRepository.update(destinationCard);

        transactionRepository.create(transaction);

        return transaction;
    }

    public Transaction performSatna(String sourceCardNumber, String destinationCardNumber,
                                    double amount) {
        CardRepository cardRepository = ApplicationContext.getInstance().getCardRepository();
        TransactionRepository transactionRepository = ApplicationContext.getInstance().getTransactionRepository();

        Transaction transaction = new Transaction();
        transaction.setTransactionType(TransactionType.SATNA);
        transaction.setAmount(amount);
        transaction.setTransactionDate(LocalDateTime.now());

        Optional<Card> sourceOpt = cardRepository.findByCardNumber(sourceCardNumber);
        Optional<Card> destOpt = cardRepository.findByCardNumber(destinationCardNumber);

        if (sourceOpt.isEmpty() || destOpt.isEmpty()) {
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transactionRepository.create(transaction);
            return transaction;
        }

        Card sourceCard = sourceOpt.get();
        Card destinationCard = destOpt.get();

        transaction.setSourceCard(sourceCard);
        transaction.setDestinationCard(destinationCard);

        if (amount <= 50_000_000 || amount > 200_000_000) {
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transactionRepository.create(transaction);
            return transaction;
        }


        double fee = amount * 0.002;


        if (sourceCard.getBalance() < amount + fee) {
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transactionRepository.create(transaction);
            return transaction;
        }


        sourceCard.setBalance(sourceCard.getBalance() - (amount + fee));
        destinationCard.setBalance(destinationCard.getBalance() + amount);

        cardRepository.update(sourceCard);
        cardRepository.update(destinationCard);

        transaction.setTransactionStatus(TransactionStatus.SUCCESSFUL);

        transactionRepository.create(transaction);

        return transaction;
    }


}
