package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.function.Consumer;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        List<UserMealWithExcess> mealsTo = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsTo.forEach(System.out::println);

//        System.out.println(filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000));
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with excess. Implement by cycles
        List<UserMealWithExcess> returnMeals = new ArrayList<>();
        int currentCalories = 0;
        boolean excess = false;

        meals.sort((meal1, meal2) -> meal1.getDateTime().compareTo(meal2.getDateTime()));

        meals.forEach(meal -> {
            if (TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                returnMeals.add(new UserMealWithExcess(meal.getDateTime(), meal.getDescription(), meal.getCalories(), getExcess(meals, meal.getDateTime(), caloriesPerDay)));
            }
        });
        return returnMeals;
    }

    public static boolean getExcess(List<UserMeal> meals, LocalDateTime dateTime, int caloriesPerDay) {
        int currentCalories = 0;
        boolean excess = false;

        for (UserMeal meal : meals) {
            if ((meal.getDateTime().isAfter(dateTime.toLocalDate().atStartOfDay()) || meal.getDateTime().isEqual(dateTime.toLocalDate().atStartOfDay())) &&
                    meal.getDateTime().isBefore(dateTime.toLocalDate().atTime(LocalTime.MAX))) {
                currentCalories += meal.getCalories();
            }
        }
        if (currentCalories > caloriesPerDay) {
            excess = true;
        }
        return excess;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        return null;
    }
}
