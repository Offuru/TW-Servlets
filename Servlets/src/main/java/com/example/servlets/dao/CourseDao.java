package com.example.servlets.dao;

import com.example.servlets.database.DatabaseConnection;
import com.example.servlets.entities.AppUserEntity;
import com.example.servlets.entities.CourseEntity;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

public class CourseDao {

    private final DatabaseConnection databaseConnection = new DatabaseConnection();

    @SuppressWarnings("unchecked")
    public List<CourseEntity> getAll() {
        return databaseConnection.executeQueryTransaction(
                em -> em.createQuery("SELECT course FROM CourseEntity course", CourseEntity.class)
                        .setHint("eclipselink.refresh", true),
                TypedQuery.class
        ).getResultList();
    }

    @SuppressWarnings("unchecked")
    public Optional<CourseEntity> getById(long id) {
        return databaseConnection.executeQueryTransaction(
                em -> em.createQuery("SELECT course FROM CourseEntity course WHERE course.id = :id", CourseEntity.class)
                        .setParameter("id", id)
                        .setHint("eclipselink.refresh", true), TypedQuery.class
        ).getResultList().stream().findFirst();
    }

    public void add(CourseEntity course) {
        databaseConnection.executeTransaction(em -> em.persist(course));
    }

    @SuppressWarnings("unchecked")
    public List<CourseEntity> getAllByTeacher(long teacherId) {
        return databaseConnection.executeQueryTransaction(
                em -> em.createQuery("SELECT course FROM CourseEntity course WHERE course.userId = :teacherId", CourseEntity.class)
                        .setParameter("teacherId", teacherId)
                        .setHint("eclipselink.refresh", true),
                TypedQuery.class
        ).getResultList();
    }

}
