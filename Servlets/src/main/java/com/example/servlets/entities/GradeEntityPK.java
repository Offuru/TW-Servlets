package com.example.servlets.entities;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

import java.io.Serializable;

@Getter
public class GradeEntityPK implements Serializable {
    @Column(name = "user_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long userId;
    @Column(name = "course_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long courseId;

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GradeEntityPK that = (GradeEntityPK) o;

        if (userId != that.userId) return false;
        if (courseId != that.courseId) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (userId ^ (userId >>> 32));
        result = 31 * result + (int) (courseId ^ (courseId >>> 32));
        return result;
    }
}
