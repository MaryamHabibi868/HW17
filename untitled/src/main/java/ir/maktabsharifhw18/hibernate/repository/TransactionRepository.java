package ir.maktabsharifhw18.hibernate.repository;

import ir.maktabsharifhw18.hibernate.entity.Transaction;

public interface TransactionRepository {
    void save(Transaction transaction);

     Transaction perfomCardToCard
            (String sourceCard, String destinationCard,
             double amount);

     Transaction performPaya(String sourceCard, String destinationCard,
                                   double amount);

    public Transaction performSatna(String sourceCard,
                                    String destinationCard,
                                    double amount);
}
