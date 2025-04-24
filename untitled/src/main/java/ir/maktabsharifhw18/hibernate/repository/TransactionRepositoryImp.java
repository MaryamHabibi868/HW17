package ir.maktabsharifhw18.hibernate.repository;

import ir.maktabsharifhw18.hibernate.config.ApplicationContext;
import ir.maktabsharifhw18.hibernate.entity.Card;
import ir.maktabsharifhw18.hibernate.entity.Transaction;
import ir.maktabsharifhw18.hibernate.entity.TransactionStatus;
import ir.maktabsharifhw18.hibernate.entity.TransactionType;
import jakarta.persistence.EntityManager;
import lombok.NoArgsConstructor;

import javax.swing.text.html.parser.Entity;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@NoArgsConstructor
public class TransactionRepositoryImp implements
        TransactionRepository {

    EntityManager entityManager = ApplicationContext
            .getInstance().getEntityManager();


    @Override
    public void save(Transaction transaction) {
        entityManager.persist(transaction);
    }

    @Override
    public Transaction perfomCardToCard(String sourceCard, String destinationCardNumber, double amount) {
        return null;
    }

    @Override
    public Transaction performPaya(String sourceCardNumber, String destinationCardNumber, double amount) {
        return null;
    }

    @Override
    public Transaction performSatna(String sourceCardNumber, String destinationCardNumber, double amount) {
        return null;
    }
}
