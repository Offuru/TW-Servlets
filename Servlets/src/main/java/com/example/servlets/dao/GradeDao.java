package com.example.servlets.dao;

import com.example.servlets.database.DatabaseConnection;
import com.example.servlets.entities.AppUserEntity;
import com.example.servlets.entities.GradeEntity;

public class GradeDao {

    private final DatabaseConnection databaseConnection = new DatabaseConnection();

    public void add(GradeEntity grade) {
        databaseConnection.executeTransaction(em -> em.persist(grade));
    }

}
