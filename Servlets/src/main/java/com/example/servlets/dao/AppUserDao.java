package com.example.servlets.dao;

import com.example.servlets.database.DatabaseConnection;
import com.example.servlets.entities.AppUserEntity;
import com.example.servlets.entities.CourseEntity;
import com.example.servlets.entities.GradeEntity;
import com.example.servlets.entities.Role;
import jakarta.enterprise.inject.Typed;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.TypedQuery;

import java.util.*;

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
                em -> em.createQuery("SELECT user FROM AppUserEntity user", AppUserEntity.class)
                        .setHint("eclipselink.refresh", true),
                TypedQuery.class
        ).getResultList();
    }

    @SuppressWarnings("unchecked")
    public Optional<AppUserEntity> getById(long id) {
        return databaseConnection.executeQueryTransaction(
                em -> em.createQuery("SELECT user FROM AppUserEntity user WHERE user.id = :id", AppUserEntity.class)
                        .setParameter("id", id)
                        .setHint("eclipselink.refresh", true), TypedQuery.class
        ).getResultList().stream().findFirst();
    }

    @SuppressWarnings("unchecked")
    public Optional<AppUserEntity> getByEmail(String email) {
        return databaseConnection.executeQueryTransaction(
                em -> em.createQuery("SELECT user FROM AppUserEntity user WHERE user.email = :email", AppUserEntity.class)
                        .setParameter("email", email)
                        .setHint("eclipselink.refresh", true), TypedQuery.class
        ).getResultList().stream().findFirst();
    }

    @SuppressWarnings("unchecked")
    public List<AppUserEntity> getAllByRole(Role role) {
        return databaseConnection.executeQueryTransaction(
                em -> em.createQuery("SELECT user FROM AppUserEntity user WHERE user.role = :role", AppUserEntity.class)
                        .setParameter("role", role)
                        .setHint("eclipselink.refresh", true), TypedQuery.class
        ).getResultList().stream().toList();
    }

    public Map<CourseEntity, Set<AppUserEntity>> getByCourses(long teacherId) {
        List<AppUserEntity> users = getAll();
        Map<CourseEntity, Set<AppUserEntity>> usersByCourses = new HashMap<>();

        for (AppUserEntity user : users) {
            for (GradeEntity grade : user.getGrades()) {
                if (grade.getCourse().getUserId() == teacherId){
                    if (!usersByCourses.containsKey(grade.getCourse())) {
                        usersByCourses.put(grade.getCourse(), new HashSet<>());
                    }
                    usersByCourses.get(grade.getCourse()).add(user);
                }
            }
        }

        return usersByCourses;
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

    public Optional<AppUserEntity> login(AppUserEntity user) {

        Optional<AppUserEntity> databaseUser = getByEmail(user.getEmail());

        if (databaseUser.isEmpty())
            return Optional.empty();

        if (databaseUser.get().getPassword().equals(user.getPassword())) {
            return databaseUser;
        } else {
            return Optional.empty();
        }
    }
}
