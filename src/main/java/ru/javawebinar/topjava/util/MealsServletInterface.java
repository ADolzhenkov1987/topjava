package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;

import javax.servlet.http.HttpServletRequest;
import java.util.*;


interface MealsServletInterface {
    static List<MealTo> getMeals(List<Meal> meal) {
        return null;
    }

    static void addOrEditMeal(List<Meal> meal, HttpServletRequest request) {

    }

    static void deleteMeal(List<Meal> meals, int mealId) {

    }

    static Meal getMeal(List<Meal> meals, String mealId) {
        return null;
    }
}