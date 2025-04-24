package ir.maktabsharifhw18.hibernate.repository;

import ir.maktabsharifhw18.hibernate.config.ApplicationContext;
import ir.maktabsharifhw18.hibernate.entity.Transaction;
import ir.maktabsharifhw18.hibernate.entity.TransactionStatus;
import ir.maktabsharifhw18.hibernate.entity.TransactionType;
import ir.maktabsharifhw18.hibernate.entity.Card;
import jakarta.persistence.EntityManager;
import lombok.NoArgsConstructor;



import java.time.LocalDateTime;

@NoArgsConstructor
public class TransactionRepositoryImp implements TransactionRepository {

    EntityManager entityManager = ApplicationContext
            .getInstance().getEntityManager();

    @Override
    public void save(Transaction transaction) {
        entityManager.persist(transaction);
    }


    @Override
    public Transaction perfomCardToCard(String sourceCard,
                                        String destinationCard, double amount) {

        Transaction transaction = new Transaction();
        transaction.setTransactionType(TransactionType.CARD_TO_CARD);
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setAmount(amount);

        Card source = entityManager.createQuery(
                        "SELECT c FROM Card c WHERE c.cardNumber = :num",
                        Card.class)
                .setParameter("num", sourceCard)
                .getResultStream()
                .findFirst()
                .orElse(null);

        Card destination = entityManager.createQuery(
                        "SELECT c FROM Card c WHERE c.cardNumber = :num",
                        Card.class)
                .setParameter("num", destinationCard)
                .getResultStream()
                .findFirst()
                .orElse(null);

        if (source == null || destination == null) {
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            System.out.println("Source card or destination card is null");

            entityManager.persist(transaction);

            return transaction;
        }

        transaction.setSourceCard(source);
        transaction.setDestinationCard(destination);

        if (amount > 15_000_000) {
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            System.out.println("Amount is too large to perform card");
        } else {
            double fee = 0;

            if (!source.getBankName().equals(destination.getBankName())) {
                if (amount <= 10_000_000) {
                    fee = 720;
                } else {
                    double extra = amount - 10_000_000;
                    int extraMil = (int) Math.ceil(extra / 1_000_000);
                    fee = 1000 + (extraMil * 100);
                }
            }

            double total = amount + fee;

            if (source.getBalance() < total) {
                transaction.setTransactionStatus(TransactionStatus.FAILED);
                System.out.println("Source balance is too large to perform card");
            } else {
                source.setBalance(source.getBalance() - total);
                destination.setBalance(destination.getBalance() + amount);
                transaction.setTransactionStatus(TransactionStatus.SUCCESSFUL);
                System.out.println("Card performed successfully");
            }
        }


        entityManager.persist(transaction);
        entityManager.merge(source);
        entityManager.merge(destination);


        return transaction;
    }

    @Override
    public Transaction performPaya(String sourceCard,
                                   String destinationCard, double amount) {
        Transaction transaction = new Transaction();
        transaction.setTransactionType(TransactionType.PAYA_INDIVIDUAL);
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setAmount(amount);

        Card source = entityManager.createQuery(
                        "SELECT c FROM Card c WHERE c.cardNumber = :num",
                        Card.class)
                .setParameter("num", sourceCard)
                .getResultStream()
                .findFirst()
                .orElse(null);

        Card destination = entityManager.createQuery(
                        "SELECT c FROM Card c WHERE c.cardNumber = :num", Card.class)
                .setParameter("num", destinationCard)
                .getResultStream()
                .findFirst()
                .orElse(null);

        if (source == null || destination == null || amount > 50_000_000) {
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            System.out.println("Source card or destination card is null or amount should be less than 50_000_000");
        } else {
            double fee = amount * 0.001;
            double total = amount + fee;

            if (source.getBalance() < total) {
                transaction.setTransactionStatus(TransactionStatus.FAILED);
                System.out.println("Balance is not enough to perform paya");
            } else {
                source.setBalance(source.getBalance() - total);
                destination.setBalance(destination.getBalance() + amount);
                transaction.setTransactionStatus(TransactionStatus.SUCCESSFUL);
                System.out.println("Paya performed successfully");
            }
        }

        transaction.setSourceCard(source);
        transaction.setDestinationCard(destination);


        entityManager.persist(transaction);
        entityManager.merge(source);
        entityManager.merge(destination);


        return transaction;
    }

    @Override
    public Transaction performSatna(String sourceCard,
                                    String destinationCard, double amount) {
        Transaction transaction = new Transaction();
        transaction.setTransactionType(TransactionType.SATNA);
        transaction.setTransactionDate(LocalDateTime.now());
        transaction.setAmount(amount);

        Card source = entityManager.createQuery(
                        "SELECT c FROM Card c WHERE c.cardNumber = :num",
                        Card.class)
                .setParameter("num", sourceCard)
                .getResultStream()
                .findFirst()
                .orElse(null);

        Card destination = entityManager.createQuery(
                        "SELECT c FROM Card c WHERE c.cardNumber = :num",
                        Card.class)
                .setParameter("num", destinationCard)
                .getResultStream()
                .findFirst()
                .orElse(null);

        if (source == null || destination == null || amount < 50_000_000 || amount > 200_000_000) {
            transaction.setTransactionStatus(TransactionStatus.FAILED);
            System.out.println("Source card or destination card is null or amount should be beetween 50_000_000 and 200_000_000");
        } else {
            double fee = amount * 0.002;
            double total = amount + fee;

            if (source.getBalance() < total) {
                transaction.setTransactionStatus(TransactionStatus.FAILED);
                System.out.println("Source balance is not enough to perform satna");
            } else {
                source.setBalance(source.getBalance() - total);
                destination.setBalance(destination.getBalance() + amount);
                transaction.setTransactionStatus(TransactionStatus.SUCCESSFUL);
                System.out.println("Satna performed successfully");
            }
        }

        transaction.setSourceCard(source);
        transaction.setDestinationCard(destination);


        entityManager.persist(transaction);
        entityManager.merge(source);
        entityManager.merge(destination);


        return transaction;
    }
}
