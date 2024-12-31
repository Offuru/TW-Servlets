package com.example.servlets.database;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import lombok.Getter;

import javax.management.RuntimeErrorException;
import java.util.function.Consumer;
import java.util.function.Function;

@Getter
public class DatabaseConnection {

    private final EntityManager entityManager;

    public DatabaseConnection() {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("default");
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    @SuppressWarnings("unchecked")
    public <T, R> R executeQueryTransaction(Function<EntityManager, T> action, Class<R> resultClass) {
        EntityTransaction entityTransaction = entityManager.getTransaction();
        Object queryResult = null;

        try {
            entityTransaction.begin();
            queryResult = action.apply(entityManager);
            entityTransaction.commit();
        } catch (RuntimeException e) {
            System.err.println("Transaction error: " + e.getLocalizedMessage());
            entityTransaction.rollback();
        }

        return (R) queryResult;
    }

    public void executeTransaction(Consumer<EntityManager> action) {
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            entityTransaction.begin();
            action.accept(entityManager);
            entityTransaction.commit();
        } catch (RuntimeErrorException e) {
            System.err.println("Transaction error " + e.getLocalizedMessage());
            entityTransaction.rollback();
        }
    }

}
