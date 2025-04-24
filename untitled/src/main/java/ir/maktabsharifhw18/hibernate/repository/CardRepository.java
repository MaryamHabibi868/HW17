package ir.maktabsharifhw18.hibernate.repository;

import ir.maktabsharifhw18.hibernate.entity.Card;
import ir.maktabsharifhw18.hibernate.repository.base.CrudRepository;

import java.util.Optional;

public interface CardRepository
        extends CrudRepository<Card, Integer> {

    Optional<Card> findByCardNumber(String cardNumber);
}
