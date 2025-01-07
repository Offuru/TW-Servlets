package com.example.servlets.servlets;

import com.example.servlets.dao.AppUserDao;
import com.example.servlets.dao.CourseDao;
import com.example.servlets.dao.GradeDao;
import com.example.servlets.entities.AppUserEntity;
import com.example.servlets.entities.CourseEntity;
import com.example.servlets.entities.GradeEntity;
import com.example.servlets.entities.Role;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

@WebServlet(name = "GradeServlet", urlPatterns = {"/grade/*"})
public class GradeServlet extends HttpServlet {

    private AppUserDao appUserDao = new AppUserDao();
    private CourseDao courseDao = new CourseDao();
    private GradeDao gradeDao = new GradeDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();

        if (session.getAttribute("currentUser") == null) {
            getServletContext().getRequestDispatcher("/views/login.jsp").forward(req, resp);
            return;
        }

        AppUserEntity teacher = (AppUserEntity) session.getAttribute("currentUser");

        if(teacher.getRole() != Role.TEACHER) {
            getServletContext().getRequestDispatcher("/views/login.jsp").forward(req, resp);
            return;
        }

        List<AppUserEntity> students = appUserDao.getAllByRole(Role.STUDENT);
        List<CourseEntity> courses = courseDao.getAllByTeacher(teacher.getId());

        session.setAttribute("allStudents", students);
        session.setAttribute("availableCourses", courses);

        if (req.getParameter("studentId") != null && req.getParameter("courseId") != null) {
            AppUserEntity student = appUserDao.getById(Long.parseLong(req.getParameter("studentId"))).get();
            CourseEntity course = courseDao.getById(Long.parseLong(req.getParameter("courseId"))).get();

            session.setAttribute("student", student);
            session.setAttribute("course", course);
        }

        getServletContext().getRequestDispatcher("/views/grade.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();

        long studentId = Long.parseLong(req.getParameter("student"));
        long courseId = Long.parseLong(req.getParameter("course"));

        AppUserEntity student = appUserDao.getById(studentId).get();
        CourseEntity course = courseDao.getById(courseId).get();
        int score = Integer.parseInt(req.getParameter("score"));

        GradeEntity grade = GradeEntity.builder()
                .user(student)
                .userId(studentId)
                .course(course)
                .courseId(courseId)
                .score(score)
                .dateAssigned(LocalDate.now())
                .build();

        gradeDao.add(grade);

        resp.sendRedirect(req.getContextPath() + "/home");
    }

    @Override
    public void init() throws ServletException {
        super.init();
    }
}
