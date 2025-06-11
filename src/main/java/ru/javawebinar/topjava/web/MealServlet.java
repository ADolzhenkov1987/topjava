package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsDao;
import ru.javawebinar.topjava.util.MealsDaoProcessingInMemory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

import static ru.javawebinar.topjava.util.MealsUtil.filteredByStreams;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);
    private static final String LIST_MEAL = "/meals.jsp";
    private static final String INSERT_OR_EDIT = "/meal.jsp";
    private static final int CALORIES_PER_DAY = 2000;
    private MealsDao dao = new MealsDaoProcessingInMemory();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");

        String forward;
        String action = request.getParameter("action") == null ? "" : request.getParameter("action");

        if (action.equalsIgnoreCase("delete")) {
            dao.delete(Integer.parseInt(request.getParameter("mealId")));
            response.sendRedirect("meals");
        } else if (action.equalsIgnoreCase("edit")) {
            request.setAttribute("meal", dao.getMeal(Integer.parseInt(request.getParameter("mealId"))));
            forward = INSERT_OR_EDIT;
            RequestDispatcher view = request.getRequestDispatcher(forward);
            view.forward(request, response);
        } else if (action.equalsIgnoreCase("insert")) {
            forward = INSERT_OR_EDIT;
            RequestDispatcher view = request.getRequestDispatcher(forward);
            view.forward(request, response);
        } else {
            List<Meal> meals = dao.getMeals();
            request.setAttribute("mealsTo", filteredByStreams(meals, LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY));
            forward = LIST_MEAL;
            RequestDispatcher view = request.getRequestDispatcher(forward);
            view.forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        dao.addOrEdit(!Objects.equals(request.getParameter("mealId"), "") ? Integer.parseInt(request.getParameter("mealId")) : -1,
                LocalDateTime.parse(request.getParameter("mealDate")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories")));
        response.sendRedirect("meals");
    }
}