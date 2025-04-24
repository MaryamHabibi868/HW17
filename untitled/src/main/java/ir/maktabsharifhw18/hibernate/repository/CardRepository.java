package ir.maktabsharifhw18.hibernate.repository;

import ir.maktabsharifhw18.hibernate.entity.BankName;
import ir.maktabsharifhw18.hibernate.entity.Card;

import java.util.List;
import java.util.Optional;

public interface CardRepository {

    void save(Card card);

    Card findByCardNumber(String cardNumber);

    Card findByCardId(Integer id);

    Boolean delete(Card card);

    List<Card> findAll();

    List<Card> findByBankName(BankName bankName);
}
