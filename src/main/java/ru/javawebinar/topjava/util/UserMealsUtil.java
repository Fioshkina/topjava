package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;

public class UserMealsUtil {
    public static void main(String[] args) {
        List<UserMeal> mealList = Arrays.asList(
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,10,0), "Завтрак", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,13,0), "Обед", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 30,20,0), "Ужин", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,10,0), "Завтрак", 1000),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,13,0), "Обед", 500),
                new UserMeal(LocalDateTime.of(2015, Month.MAY, 31,20,0), "Ужин", 510)
        );

        for (UserMealWithExceed mealWithExceed : getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12,0), 2000)) {
            System.out.println(mealWithExceed);
        }
//        .toLocalDate();
//        .toLocalTime();
    }

    //HW0
    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with correctly exceeded field
        HashMap<LocalDate, Integer> dateCalories = new HashMap<>();
        HashMap<UserMeal, LocalDate> dateMeals = new HashMap<>();
        mealList.forEach(userMeal -> {
            LocalTime mealTime = userMeal.getDateTime().toLocalTime();
            LocalDate mealDate = userMeal.getDateTime().toLocalDate();
            if (dateCalories.containsKey(mealDate)) {
                dateCalories.replace(mealDate, dateCalories.get(mealDate) + userMeal.getCalories());
            } else {
                dateCalories.put(mealDate, userMeal.getCalories());
            }
            if (TimeUtil.isBetween(mealTime, startTime, endTime)) {
                dateMeals.put(userMeal, mealDate);
            }
        });

        List<UserMealWithExceed> mealWithExceedsList = new ArrayList<>();
        dateMeals.forEach((k, v) -> mealWithExceedsList.add(new UserMealWithExceed(k.getDateTime(), k.getDescription(), k.getCalories(), (caloriesPerDay < dateCalories.get(v)))));

        return mealWithExceedsList;
    }
}
