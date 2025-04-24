package ir.maktabsharifhw18.hibernate.config;

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

}
