package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class MealsDaoProcessingInMemory implements MealsDao {

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


    @Override
    public void addOrEdit(int mealId,
                          LocalDateTime mealDate,
                          String description,
                          int calories) {
        int newMealId;

        if (mealId != -1) {
            newMealId = mealId;
            delete(newMealId);
        } else {
            newMealId = counterMealId.incrementAndGet();
        }
        meals.add(new Meal(
                newMealId,
                mealDate,
                description,
                calories
        ));
    }

    @Override
    public void delete(int mealId) {
        meals.removeIf(meal -> meal.getMealId().equals(mealId));
    }

    @Override
    public Meal getMeal(int mealId) {
        return meals.stream()
                .filter(meal -> meal.getMealId().equals(mealId))
                .findFirst().orElse(null);
    }

    @Override
    public List<Meal> getMeals() {
        return meals;
    }
}