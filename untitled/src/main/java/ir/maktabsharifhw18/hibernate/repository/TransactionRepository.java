package ir.maktabsharifhw18.hibernate.repository;

import ir.maktabsharifhw18.hibernate.entity.Transaction;

public interface TransactionRepository {
    void save(Transaction transaction);

     Transaction perfomCardToCard
            (String sourceCard, String destinationCardNumber,
             double amount);

     Transaction performPaya(String sourceCardNumber, String destinationCardNumber,
                                   double amount);

    public Transaction performSatna(String sourceCardNumber, String destinationCardNumber,
                                    double amount);
}
