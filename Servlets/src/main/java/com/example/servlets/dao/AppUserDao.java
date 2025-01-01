package com.example.servlets.dao;

import com.example.servlets.database.DatabaseConnection;
import com.example.servlets.entities.AppUserEntity;
import jakarta.enterprise.inject.Typed;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class AppUserDao {
    private final DatabaseConnection databaseConnection = new DatabaseConnection();


    public void add(AppUserEntity user) {
        databaseConnection.executeTransaction(em -> em.persist(user));
    }

    public void addAll(AppUserEntity... users) {
        for (AppUserEntity user : users) {
            databaseConnection.executeTransaction(em -> em.persist(user));
        }
    }

    @SuppressWarnings("unchecked")
    public List<AppUserEntity> getAll() {
        return databaseConnection.executeQueryTransaction(
                em -> em.createQuery("SELECT user FROM AppUserEntity user", AppUserEntity.class),
                TypedQuery.class
        ).getResultList();
    }

    @SuppressWarnings("unchecked")
    public Optional<AppUserEntity> getById(long id) {
        return databaseConnection.executeQueryTransaction(
                em -> em.createQuery("SELECT user FROM AppUserEntity user WHERE user.id = :id", AppUserEntity.class)
                        .setParameter("id", id), TypedQuery.class
        ).getResultList().stream().findFirst();
    }

    @SuppressWarnings("unchecked")
    public Optional<AppUserEntity> getByEmail(String email) {
        return databaseConnection.executeQueryTransaction(
                em -> em.createQuery("SELECT user FROM AppUserEntity user WHERE user.email = :email", AppUserEntity.class)
                        .setParameter("email", email), TypedQuery.class
        ).getResultList().stream().findFirst();
    }

    @SuppressWarnings("unchecked")
    public void update(AppUserEntity user) {
        databaseConnection.executeTransaction(
                em -> em.merge(user));
    }

    public void remove(AppUserEntity user) {
        databaseConnection.executeTransaction(
                em -> em.remove(user)
        );
    }

    public void removeById(long id) {
        databaseConnection.executeTransaction(
                em -> em.createQuery("DELETE FROM AppUserEntity user WHERE user.id = :id")
                        .setParameter("id", id)
                        .executeUpdate()
        );
    }

    public void removeByEmail(String email) {
        databaseConnection.executeTransaction(
                em -> em.createQuery("DELETE FROM AppUserEntity user WHERE user.email = :email")
                        .setParameter("email", email)
                        .executeUpdate()
        );
    }
}
