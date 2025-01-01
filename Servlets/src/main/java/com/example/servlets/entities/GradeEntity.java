package com.example.servlets.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Grade", schema = "public", catalog = "servlets")
@IdClass(GradeEntityPK.class)
public class GradeEntity {
    @Id
    @Column(name = "user_id")
    private long userId;
    @Id
    @Column(name = "course_id")
    private long courseId;
    @Basic
    @Column(name = "score")
    private int score;

    @Basic
    @Column(name = "date") // New date field
    private LocalDate dateAssigned;

    @ManyToOne
    @JoinColumn(name = "user_id", insertable = false, updatable = false)
    private AppUserEntity user;

    @ManyToOne
    @JoinColumn(name = "course_id", insertable = false, updatable = false)
    private CourseEntity course;

    public void setUser(AppUserEntity user) {
        this.user = user;
    }

    public void setCourse(CourseEntity course) {
        this.course = course;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setCourseId(long courseId) {
        this.courseId = courseId;
    }
    public void setDateAssigned(LocalDate dateAssigned) {
        this.dateAssigned = dateAssigned;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GradeEntity that = (GradeEntity) o;

        if (userId != that.userId) return false;
        if (courseId != that.courseId) return false;
        if (score != that.score) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (userId ^ (userId >>> 32));
        result = 31 * result + (int) (courseId ^ (courseId >>> 32));
        result = 31 * result + score;
        return result;
    }
}
