package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsProcessing;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.MealsUtil.filteredByStreams;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);
    private static final String LIST_MEAL = "/meals.jsp";
    private static final String INSERT_OR_EDIT = "/meal.jsp";
    private static final int CALORIES_PER_DAY = 2000;
    MealsProcessing mealsProcessing = new MealsProcessing();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");

        String forward;
        String action = request.getParameter("action");

        if (action != null) {
            if (action.equalsIgnoreCase("delete")) {
                mealsProcessing.deleteMeal(Integer.parseInt(request.getParameter("mealId")));
                response.sendRedirect("meals");
            } else if (action.equalsIgnoreCase("edit")) {
                request.setAttribute("meal", mealsProcessing.getMeal(request.getParameter("mealId")));
                forward = INSERT_OR_EDIT;
                RequestDispatcher view = request.getRequestDispatcher(forward);
                view.forward(request, response);
            } else if (action.equalsIgnoreCase("insert")) {
                forward = INSERT_OR_EDIT;
                RequestDispatcher view = request.getRequestDispatcher(forward);
                view.forward(request, response);
            } else {
                List<Meal> meals = mealsProcessing.getMeals();
                request.setAttribute("mealsTo", filteredByStreams(meals, LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY));
                forward = LIST_MEAL;
                RequestDispatcher view = request.getRequestDispatcher(forward);
                view.forward(request, response);
            }
        } else {
            List<Meal> meals = mealsProcessing.getMeals();
            request.setAttribute("mealsTo", filteredByStreams(meals, LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY));
            forward = LIST_MEAL;
            RequestDispatcher view = request.getRequestDispatcher(forward);
            view.forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        mealsProcessing.addOrEditMeal(request.getParameter("mealId"),
                request.getParameter("mealDate"),
                request.getParameter("description"),
                request.getParameter("calories"));
        response.sendRedirect("meals");
    }
}