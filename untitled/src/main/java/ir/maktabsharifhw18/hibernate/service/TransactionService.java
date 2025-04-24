package ir.maktabsharifhw18.hibernate.service;


import ir.maktabsharifhw18.hibernate.config.ApplicationContext;
import ir.maktabsharifhw17.jdbc.domains.*;
import ir.maktabsharifhw18.hibernate.entity.Card;
import ir.maktabsharifhw18.hibernate.entity.Transaction;
import ir.maktabsharifhw18.hibernate.entity.TransactionStatus;
import ir.maktabsharifhw18.hibernate.entity.TransactionType;
import ir.maktabsharifhw18.hibernate.repository.CardRepository;
import ir.maktabsharifhw18.hibernate.repository.TransactionRepository;


import java.time.LocalDateTime;
import java.util.Optional;

public class TransactionService {

    private final CardRepository cardRepository = ApplicationContext.getInstance().getCardRepository();
    private final TransactionRepository transactionRepository = ApplicationContext.getInstance().getTransactionRepository();

    public Transaction performCardToCard(String sourceCardNumber, String destinationCardNumber, double amount) {
        Transaction transaction = new Transaction();
        transaction.setTransactionType(TransactionType.CARD_TO_CARD);
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

        if (amount > 15_000_000) {
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            transactionRepository.create(transaction);
            return transaction;
        }

        double fee = 0;
        if (!sourceCard.getBankName().equals(destinationCard.getBankName())) {
            if (amount <= 10_000_000) {
                fee = 720;
            } else {
                int extraMillions = (int) Math.ceil((amount - 10_000_000) / 1_000_000);
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

        cardRepository.update(sourceCard);
        cardRepository.update(destinationCard);

        transaction.setTransactionStatus(TransactionStatus.SUCCESSFUL);
        transactionRepository.create(transaction);

        return transaction;
    }

    public Transaction performPaya(String sourceCardNumber, String destinationCardNumber, double amount) {
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

        cardRepository.update(sourceCard);
        cardRepository.update(destinationCard);

        transaction.setTransactionStatus(TransactionStatus.SUCCESSFUL);
        transactionRepository.create(transaction);

        return transaction;
    }


    public Transaction performSatna(String sourceCardNumber, String destinationCardNumber, double amount) {
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
