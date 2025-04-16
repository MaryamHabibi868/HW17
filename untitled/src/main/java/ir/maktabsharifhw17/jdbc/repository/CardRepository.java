package ir.maktabsharifhw17.jdbc.repository;

import ir.maktabsharifhw17.jdbc.domains.Card;
import ir.maktabsharifhw17.jdbc.repository.base.CrudRepository;

import java.util.Optional;

public interface CardRepository
        extends CrudRepository<Card, Integer> {

    Optional<Card> findByCardNumber(String cardNumber);
}
