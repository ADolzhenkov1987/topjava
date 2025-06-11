package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;

import java.util.ArrayList;
import java.util.List;


interface MealsDao {
    List<Meal> meals = new ArrayList<>();

    default void addOrEditMeal() {

    }

    default void deleteMeal() {

    }

    default Meal getMeal() {
        return null;
    }

    default List<Meal> getMeals() {
        return null;
    }
}