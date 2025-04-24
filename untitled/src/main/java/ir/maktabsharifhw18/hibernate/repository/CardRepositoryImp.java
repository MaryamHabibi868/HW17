package ir.maktabsharifhw18.hibernate.repository;

import ir.maktabsharifhw18.hibernate.config.ApplicationContext;
import ir.maktabsharifhw18.hibernate.entity.BankName;
import ir.maktabsharifhw18.hibernate.entity.Card;
import jakarta.persistence.EntityManager;


import java.util.List;


public class CardRepositoryImp implements CardRepository {

    EntityManager entityManager = ApplicationContext
            .getInstance().getEntityManager();


    @Override
    public void save(Card card) {
        entityManager.persist(card);
    }

    @Override
    public Card findByCardNumber(String cardNumber) {
        return entityManager.find(Card.class, cardNumber);

    }

    @Override
    public Card findByCardId(Integer id) {
        return entityManager.find(Card.class, id);

    }

    @Override
    public Boolean delete(Card card) {
            entityManager.remove(card);
            return true;
    }

    @Override
    public List<Card> findAll() {
       return entityManager.createQuery
               ("SELECT c FROM Card c", Card.class).getResultList();
    }

    @Override
    public List<Card> findByBankName(BankName bankName) {
       return entityManager.createQuery(
                "SELECT c FROM Card c WHERE c.bankName = :bankName",
               Card.class).getResultList();
    }
}
