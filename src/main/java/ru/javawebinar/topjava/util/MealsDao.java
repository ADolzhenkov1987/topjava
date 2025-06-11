package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;


public interface MealsDao {
    void addOrEdit(int mealId,
                   LocalDateTime mealDate,
                   String description,
                   int calories);

    void delete(int mealId);

    Meal getMeal(int mealId);

    List<Meal> getMeals();
}