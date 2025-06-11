package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class MealsProcessing implements MealsDao {

    private List<Meal> meals = new ArrayList<>(Arrays.asList(
            new Meal(1, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
            new Meal(2, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
            new Meal(3, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
            new Meal(4, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
            new Meal(5, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
            new Meal(6, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
            new Meal(7, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
    ));

    private final AtomicInteger counterMealId = new AtomicInteger(7);

    public void addOrEditMeal(String mealId,
                              String mealDate,
                              String description,
                              String calories) {
        int newMealId;

        if (!Objects.equals(mealId, "")) {
            newMealId = Integer.parseInt(mealId);
            deleteMeal(newMealId);
        }
        else{
            newMealId = counterMealId.incrementAndGet();
        }
        meals.add(new Meal(
                newMealId,
                LocalDateTime.parse(mealDate),
                description,
                Integer.parseInt(calories)
        ));
    }

    public void deleteMeal(int mealId) {
        meals.removeIf(meal -> meal.getMealId().equals(mealId));
    }

    public Meal getMeal(String mealId) {
        return meals.stream()
                .filter(meal -> meal.getMealId().equals(Integer.parseInt(mealId)))
                .findFirst().orElse(null);
    }

    public List<Meal> getMeals() {
        return meals;
    }
}