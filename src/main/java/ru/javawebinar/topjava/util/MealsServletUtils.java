package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import static ru.javawebinar.topjava.util.MealsUtil.filteredByStreams;

public class MealsServletUtils implements MealsServletInterface {
    private static final int CALORIES_PER_DAY = 2000;
    private final AtomicInteger counterMealId = new AtomicInteger(7);
    int mealId;

    public static List<MealTo> getMeals(List<Meal> meals) {
        return filteredByStreams(meals, LocalTime.MIN, LocalTime.MAX, CALORIES_PER_DAY);
    }

    public void addOrEditMeal(List<Meal> meals, HttpServletRequest request) {
        if (!Objects.equals(request.getParameter("mealId"), "")) {
            mealId = Integer.parseInt(request.getParameter("mealId"));
            deleteMeal(meals, mealId);
        }
        else{
            synchronized (this) {
                mealId = counterMealId.incrementAndGet();
            }
        }
        meals.add(new Meal(
                mealId,
                LocalDateTime.parse(request.getParameter("mealDate")),
                request.getParameter("description"),
                Integer.parseInt(request.getParameter("calories"))
        ));
    }

    public static void deleteMeal(List<Meal> meals, int mealId) {
        meals.removeIf(meal -> meal.getMealId().equals(mealId));
    }

    public static Meal getMeal(List<Meal> meals, String mealId) {
        return meals.stream()
                .filter(meal -> meal.getMealId().equals(Integer.parseInt(mealId)))
                .findFirst().orElse(null);
    }
}