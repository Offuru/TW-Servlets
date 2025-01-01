package com.example.servlets.servlets;

import com.example.servlets.dao.AppUserDao;
import com.example.servlets.entities.AppUserEntity;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Objects;
import java.util.Optional;

@WebServlet(name = "IndexServlet", urlPatterns = {"/", "/login/*"})
public class LoginServlet extends HttpServlet {
    private final AppUserDao appUserDao = new AppUserDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        AppUserEntity currentUser = (AppUserEntity) session.getAttribute("currentUser");

        if (Objects.isNull(currentUser)) {
            getServletContext().getRequestDispatcher("/views/login.jsp").forward(req, resp);
        } else {
            resp.sendRedirect(req.getContextPath() + "/home");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        String password = req.getParameter("password");

        AppUserEntity user = AppUserEntity.builder()
                .email(email)
                .password(password)
                .build();

        HttpSession session = req.getSession();

        Optional<AppUserEntity> loggedUser = this.appUserDao.login(user);

        if (loggedUser.isPresent()) {
            session.setAttribute("currentUser", loggedUser.get());
            resp.sendRedirect(req.getContextPath() + "/home");
        } else {
            getServletContext().getRequestDispatcher("/views/login.jsp").forward(req, resp);
        }
    }

    @Override
    public void init() throws ServletException {
        super.init();
    }
}
