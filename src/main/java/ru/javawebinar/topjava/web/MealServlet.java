package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static java.time.LocalDateTime.parse;
import static ru.javawebinar.topjava.util.MealsUtil.filteredByStreams;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);
    private static final int CALORIES_PER_DAY = 2000;
    private static final String LIST_MEAL = "/meals.jsp?action=listMeal";
    private static final String INSERT_OR_EDIT = "/meal.jsp";

    private List<Meal> meals = new ArrayList<>(Arrays.asList(
            new Meal(1, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
            new Meal(2, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
            new Meal(3, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
            new Meal(4, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
            new Meal(5, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
            new Meal(6, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
            new Meal(7, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
    ));

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("redirect to meals");

        String forward;
        String action = request.getParameter("action");

        if (action.equalsIgnoreCase("delete")) {
            Meal deletedMeal = meals.stream()
                    .filter(meal -> meal.getMealId().equals(Integer.parseInt(request.getParameter("mealId"))))
                    .findFirst().orElse(null);
            meals.remove(deletedMeal);
            List<MealTo> mealsTo = filteredByStreams(meals, LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY);
            request.setAttribute("mealsTo", mealsTo);
            forward = LIST_MEAL;
        } else if (action.equalsIgnoreCase("edit")) {
            Meal editedMeal = meals.stream()
                    .filter(meal -> meal.getMealId().equals(Integer.parseInt(request.getParameter("mealId"))))
                    .findFirst().orElse(null);
            request.setAttribute("meal", editedMeal);
            forward = INSERT_OR_EDIT;
        } else if (action.equalsIgnoreCase("listMeal")) {
            List<MealTo> mealsTo = filteredByStreams(meals, LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY);
            request.setAttribute("mealsTo", mealsTo);
            forward = LIST_MEAL;
        } else {
            forward = INSERT_OR_EDIT;
        }

        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        Meal maxIdMeal = Collections.max(meals, Comparator.comparing(Meal::getMealId));

        meals.add(new Meal(
                maxIdMeal.getMealId() + 1,
                parse(request.getParameter("mealDate"), formatter),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories"))
        ));
        RequestDispatcher view = request.getRequestDispatcher(LIST_MEAL);
        List<MealTo> mealsTo = filteredByStreams(meals, LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY);
        request.setAttribute("mealsTo", mealsTo);
        view.forward(request, response);
    }
}