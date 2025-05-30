package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExcess;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

import static java.time.LocalDateTime.*;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> meals = Arrays.asList(
                new UserMeal(of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new UserMeal(of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new UserMeal(of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new UserMeal(of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new UserMeal(of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new UserMeal(of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new UserMeal(of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );

        System.out.println("Реализация через циклы:");
        List<UserMealWithExcess> mealsToCycles = filteredByCycles(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsToCycles.forEach(System.out::println);

        System.out.println("Реализация через потоки:");
        List<UserMealWithExcess> mealsToStreams = filteredByStreams(meals, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        mealsToStreams.forEach(System.out::println);
    }

    public static List<UserMealWithExcess> filteredByCycles(List<UserMeal> meals,
                                                            LocalTime startTime,
                                                            LocalTime endTime,
                                                            int caloriesPerDay) {
        Map<LocalDate, Integer> sumCaloriesPerDay = new HashMap<>();
        for (UserMeal meal : meals) {
            sumCaloriesPerDay.put(
                    meal.getDateTime().toLocalDate(),
                    sumCaloriesPerDay.get(meal.getDateTime().toLocalDate()) == null ?
                            meal.getCalories() :
                            sumCaloriesPerDay.get(meal.getDateTime().toLocalDate()) + meal.getCalories());
        }
        List<UserMealWithExcess> resultMeals = new ArrayList<>();
        meals.forEach(meal -> {
            if (TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime)) {
                resultMeals.add(
                        new UserMealWithExcess(
                                meal.getDateTime(),
                                meal.getDescription(),
                                meal.getCalories(),
                                (int) sumCaloriesPerDay.get(meal.getDateTime().toLocalDate()) > caloriesPerDay
                        )
                );
            }
        });
        return resultMeals;
    }

    public static List<UserMealWithExcess> filteredByStreams(List<UserMeal> meals,
                                                             LocalTime startTime,
                                                             LocalTime endTime,
                                                             int caloriesPerDay) {
        Map<LocalDate, Integer> sumCaloriesPerDay = meals.stream()
                .collect(
                        Collectors.groupingBy(
                                meal->meal.getDateTime().toLocalDate(),
                                Collectors.summingInt(UserMeal::getCalories)));

        return meals.stream()
                .filter(meal -> TimeUtil.isBetweenHalfOpen(meal.getDateTime().toLocalTime(), startTime, endTime))
                .map(meal -> new UserMealWithExcess(
                        meal.getDateTime(),
                        meal.getDescription(),
                        meal.getCalories(),
                        (int) sumCaloriesPerDay.get(meal.getDateTime().toLocalDate()) > caloriesPerDay)
                )
                .collect(Collectors.toList());
    }
}
