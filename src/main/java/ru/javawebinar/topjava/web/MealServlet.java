package ru.javawebinar.topjava.web;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

import static java.util.logging.Logger.getLogger;
import static ru.javawebinar.topjava.util.MealsUtil.filteredByStreams;

public class MealServlet extends HttpServlet {
    List<Meal> meals = Arrays.asList(
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
    );

    private static final Logger log = getLogger(String.valueOf(UserServlet.class));

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.info("Info Message Logged!");
        int caloriesPerDay = 2000;

        List<MealTo> mealsToStreams = filteredByStreams(meals, LocalTime.of(0, 0), LocalTime.of(23, 59), caloriesPerDay);
        response.setContentType("text/html;charset=utf-8");

        PrintWriter pw = response.getWriter();
        pw.println("<H4>Meals</H4>");
        pw.println("<table border=\"1\">");
        pw.println("<tr>\n" +
                "        <th>Date</th>\n" +
                "        <th>Description</th>\n" +
                "        <th>Calories</th>\n" +
                "        <th>Update</th>\n" +
                "        <th>Delete</th>\n" +
                "    </tr>");
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        for (MealTo meal : mealsToStreams) {
            if (meal.getExcess()) {
                pw.println("<tr style=\"text-align: left; color: red\">\n");
            } else {
                pw.println("<tr style=\"text-align: left; color: green\">\n");
            }

            pw.println("<th>" + dtf.format(meal.getDateTime()) + "</th>\n" +
                    "<th>" + meal.getDescription() + "</th>\n" +
                    "<th>" + meal.getCalories() + "</th>\n" +
                    "<th></th>\n" +
                    "<th></th>\n" +
                    "</tr>");

        }
    }
}
