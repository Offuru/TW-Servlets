package com.example.servlets.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "AppUser", schema = "public", catalog = "servlets")
public class AppUserEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private long id;
    @Basic
    @Column(name = "username")
    private String username;
    @Basic
    @Column(name = "email")
    private String email;
    @Basic
    @Column(name = "password")
    private String password;
    @Enumerated(EnumType.STRING)
    @Column(name = "role", columnDefinition = "UserRole")
    private Role role;

    public void setGrades(List<GradeEntity> grades) {
        this.grades = grades;
    }

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GradeEntity> grades;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<CourseEntity> courses;

    public void setId(long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setCourses(List<CourseEntity> courses) {
        this.courses = courses;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AppUserEntity that = (AppUserEntity) o;

        if (id != that.id) return false;
        if (!Objects.equals(username, that.username)) return false;
        if (!Objects.equals(email, that.email)) return false;
        if (!Objects.equals(password, that.password)) return false;
        if (!Objects.equals(role, that.role)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (username != null ? username.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + (role != null ? role.hashCode() : 0);
        return result;
    }

    public Map<CourseEntity, List<GradeEntity>> getGradesByCourse() {

        Map<CourseEntity, List<GradeEntity>> courseGrades = new HashMap<>();

        for (var grade : grades) {
            if (!courseGrades.containsKey(grade.getCourse())) {
                courseGrades.put(grade.getCourse(), new ArrayList<GradeEntity>());
            }
            courseGrades.get(grade.getCourse()).add(grade);
        }

        return courseGrades;
    }

    public Map<CourseEntity, Double> getCourseFinalGrades() {

        Map<CourseEntity, List<GradeEntity>> courseGrades = getGradesByCourse();
        Map<CourseEntity, Double> finalGrades = new HashMap<>();

        for (var key : courseGrades.keySet()) {
            finalGrades.put(key, courseGrades.get(key).stream().mapToInt(GradeEntity::getScore).average().orElse(0.0));
        }

        return finalGrades;
    }
}
