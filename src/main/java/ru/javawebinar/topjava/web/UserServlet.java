package ru.javawebinar.topjava.web;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.logging.Logger;

import static java.util.logging.Logger.getLogger;

public class UserServlet extends HttpServlet {
    private static final Logger log = getLogger(String.valueOf(UserServlet.class));

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("Debug Message Logged !!!");
        response.sendRedirect("users.jsp");
    }
}
