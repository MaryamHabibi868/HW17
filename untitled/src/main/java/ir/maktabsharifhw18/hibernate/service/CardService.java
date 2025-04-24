package ir.maktabsharifhw18.hibernate.service;

import ir.maktabsharifhw18.hibernate.config.ApplicationContext;
import ir.maktabsharifhw18.hibernate.entity.BankName;
import ir.maktabsharifhw18.hibernate.entity.Card;
import ir.maktabsharifhw18.hibernate.repository.CardRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CardService {

    private final CardRepository cardRepository = ApplicationContext.getInstance().getCardRepository();


    public Optional<Card> create(Card card) {
        return Optional.ofNullable(cardRepository.create(card));
    }


    public Optional<Card> findByCardNumber(String cardNumber) {
        return cardRepository.findByCardNumber(cardNumber);
    }


    public boolean deleteById(Integer cardId) {
        return cardRepository.deleteById(cardId) > 0;
    }


    public List<Card> findAllByBankName(BankName bankName) {
        return cardRepository.findAll().stream()
                .filter(card -> card.getBankName().equals(bankName))
                .collect(Collectors.toList());
    }


    public Optional<Card> update(Card card) {
        return Optional.ofNullable(cardRepository.update(card));
    }


    public List<Card> findAll() {
        return cardRepository.findAll();
    }
}
