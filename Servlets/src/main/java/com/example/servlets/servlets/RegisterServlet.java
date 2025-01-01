package com.example.servlets.servlets;

import com.example.servlets.dao.AppUserDao;
import com.example.servlets.entities.AppUserEntity;
import com.example.servlets.entities.Role;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

@WebServlet(name = "RegisterServlet", urlPatterns = {"/register/*"})
public class RegisterServlet extends HttpServlet {
    private final AppUserDao appUserDao = new AppUserDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/views/register.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession();
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String username = req.getParameter("username");
        Role role = Role.valueOf(req.getParameter("userType").toUpperCase());
        String confirmPassword = req.getParameter("confirmPassword");

        AppUserEntity user = AppUserEntity.builder()
                .email(email)
                .password(password)
                .username(username)
                .role(role)
                .build();

        if (!password.equals(confirmPassword) || appUserDao.getByEmail(email).isPresent()) {
            resp.sendRedirect(req.getContextPath() + "/register");
        } else {
            appUserDao.add(user);
            session.setAttribute("currentUser", user);
            resp.sendRedirect(req.getContextPath() + "/home");
        }
    }

    @Override
    public void init() throws ServletException {
        super.init();
    }
}
