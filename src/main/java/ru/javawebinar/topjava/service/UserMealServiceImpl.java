package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.to.UserMealWithExceed;
import ru.javawebinar.topjava.util.TimeUtil;
import ru.javawebinar.topjava.util.exception.ExceptionUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static ru.javawebinar.topjava.util.UserMealsUtil.createWithExceed;

/**
 * GKislin
 * 06.03.2015.
 */
@Service
public class UserMealServiceImpl implements UserMealService {
    @Autowired
    private UserMealRepository repository;

    public UserMeal get(int id, int userId) throws NotFoundException {
        UserMeal userMeal = repository.get(id);
        ExceptionUtil.checkNotFoundWithId(isAuthorized(userMeal, userId), id);
        return userMeal;
    }

    public void update(UserMeal userMeal, int userId) throws NotFoundException {
        if (userMeal.getId() != null) {
            get(userMeal.getId(), userId);
        }
        repository.save(userMeal);
    }

    public UserMeal save(UserMeal userMeal, int userId) throws NotFoundException {
        if (userMeal.getId() != null) {
            get(userMeal.getId(), userId);
        }
        return repository.save(userMeal);
    }

    public void delete(int id, int userId) throws NotFoundException {
        get(id, userId);
        repository.delete(id);
    }

    public List<UserMealWithExceed> getAllWithExceed(int userId, int caloriesPerDay) {
        return getWithExceeded(repository.getAll(userId), caloriesPerDay);
    }

    public List<UserMealWithExceed> getFilteredWithExceed(int userId, int caloriesPerDay, LocalDate startDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        return getFilteredWithExceeded(repository.getFiltered(userId, startDate, endDate), caloriesPerDay, startTime, endTime);
    }

    private boolean isAuthorized(UserMeal userMeal, int userId) {
        return userMeal != null && userMeal.getUserId() == userId;
    }

    private Map<LocalDate, Integer> getCaloriesByDates(Collection<UserMeal> mealList) {
        return mealList.stream()
                .collect(
                        Collectors.groupingBy(um -> um.getDateTime().toLocalDate(),
                                Collectors.summingInt(UserMeal::getCalories))
                );
    }

    private List<UserMealWithExceed> getWithExceeded(Collection<UserMeal> mealList, int caloriesPerDay) {
        return mealList.stream()
                .map(um -> createWithExceed(um, getCaloriesByDates(mealList).get(um.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }

    private List<UserMealWithExceed> getFilteredWithExceeded(Collection<UserMeal> mealList, int caloriesPerDay, LocalTime startTime, LocalTime endTime) {
        return mealList.stream()
                .filter(um -> TimeUtil.isBetween(um.getDateTime().toLocalTime(), startTime, endTime))
                .map(um -> createWithExceed(um, getCaloriesByDates(mealList).get(um.getDateTime().toLocalDate()) > caloriesPerDay))
                .collect(Collectors.toList());
    }
}
