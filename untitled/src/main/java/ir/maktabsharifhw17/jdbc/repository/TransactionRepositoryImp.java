package ir.maktabsharifhw17.jdbc.repository;

import ir.maktabsharifhw17.jdbc.domains.Transaction;

import java.util.List;
import java.util.Optional;

public class TransactionRepositoryImp implements
TransactionRepository {
    @Override
    public Transaction create(Transaction transaction) {
        return null;
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
}
