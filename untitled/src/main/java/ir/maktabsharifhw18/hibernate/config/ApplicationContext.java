package ir.maktabsharifhw18.hibernate.config;

import ir.maktabsharifhw18.hibernate.repository.CardRepositoryImp;
import ir.maktabsharifhw18.hibernate.repository.TransactionRepositoryImp;
import ir.maktabsharifhw18.hibernate.repository.UserRepositoryImp;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class ApplicationContext {

    private static ApplicationContext applicationContext;
    private ApplicationContext() {
    }


    public static ApplicationContext getInstance(){
        if(applicationContext == null){
            applicationContext = new ApplicationContext();
        }
        return applicationContext;
    }


    private EntityManagerFactory entityManagerFactory;

    public EntityManagerFactory getEntityManagerFactory() {
        if(entityManagerFactory == null){
            entityManagerFactory = Persistence.createEntityManagerFactory("default");
        }
        return entityManagerFactory;
    }


    private EntityManager entityManager;

    public EntityManager getEntityManager() {
        if(entityManager == null){
            entityManager = getEntityManagerFactory().createEntityManager();
        }
        return entityManager;
    }

    private CardRepositoryImp cardRepository;
    public CardRepositoryImp getCardRepository() {
        if(cardRepository == null){
            cardRepository = new CardRepositoryImp();
        }
        return cardRepository;
    }

    private TransactionRepositoryImp transactionRepository;
    public TransactionRepositoryImp getTransactionRepository() {
        if(transactionRepository == null){
            transactionRepository = new TransactionRepositoryImp();
        }
        return transactionRepository;
    }

    private UserRepositoryImp userRepository;
    public UserRepositoryImp getUserRepository() {
        if(userRepository == null){
            userRepository = new UserRepositoryImp();
        }
        return userRepository;
    }

}
