package com.example.servlets.servlets;

import com.example.servlets.dao.AppUserDao;
import com.example.servlets.entities.AppUserEntity;
import com.example.servlets.entities.CourseEntity;
import com.example.servlets.entities.GradeEntity;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

@WebServlet(name = "homeServlet", urlPatterns = {"/home"})
public class HomeServlet extends HttpServlet {
    private final AppUserDao appUserDao = new AppUserDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();

        AppUserEntity currentUser = (AppUserEntity) session.getAttribute("currentUser");

        switch (currentUser.getRole()) {

            case STUDENT -> {
                Map<CourseEntity, List<GradeEntity>> gradesByCourse = currentUser.getGradesByCourse();
                Map<CourseEntity, Double> finalGrades = currentUser.getCourseFinalGrades();

                session.setAttribute("grades", gradesByCourse);
                session.setAttribute("finalGrades", finalGrades);
                getServletContext().getRequestDispatcher("/views/student.jsp").forward(req, resp);
            }
            case TEACHER -> {
                Map<CourseEntity, Set<AppUserEntity>> usersByCourses = appUserDao.getByCourses(currentUser.getId());
                session.setAttribute("usersByCourses", usersByCourses);
                getServletContext().getRequestDispatcher("/views/teacher.jsp").forward(req, resp);
            }

            default -> {
                getServletContext().getRequestDispatcher("/views/login.jsp").forward(req, resp);
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }

    @Override
    public void init() throws ServletException {
        super.init();
    }
}
