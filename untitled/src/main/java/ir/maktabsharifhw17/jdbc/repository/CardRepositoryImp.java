package ir.maktabsharifhw17.jdbc.repository;

import ir.maktabsharifhw17.jdbc.domains.Card;

import java.util.List;
import java.util.Optional;

public class CardRepositoryImp implements CardRepository {
    @Override
    public Card create(Card card) {
        return null;
    }

    @Override
    public Card update(Card card) {
        return null;
    }

    @Override
    public List<Card> findAll() {
        return List.of();
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
}
