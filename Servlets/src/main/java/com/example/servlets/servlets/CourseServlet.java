package com.example.servlets.servlets;

import com.example.servlets.dao.CourseDao;
import com.example.servlets.entities.AppUserEntity;
import com.example.servlets.entities.CourseEntity;
import com.example.servlets.entities.Role;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "CourseServlet", urlPatterns = {"/course"})
public class CourseServlet extends HttpServlet {

    CourseDao courseDao = new CourseDao();

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

        getServletContext().getRequestDispatcher("/views/course.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession();

        String name = req.getParameter("courseName");
        int credits = Integer.parseInt(req.getParameter("courseCredits"));
        AppUserEntity appUserEntity = (AppUserEntity) session.getAttribute("currentUser");

        CourseEntity course = CourseEntity.builder()
                .name(name)
                .credits(credits)
                .user(appUserEntity)
                .userId(appUserEntity.getId())
                .build();

        courseDao.add(course);

        resp.sendRedirect(req.getContextPath() + "/home");
    }

    @Override
    public void init() throws ServletException {
        super.init();
    }
}
