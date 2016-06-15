package ru.javawebinar.topjava.service;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

/**
 * GKislin
 * 15.06.2015.
 */
public interface UserMealService {
    UserMeal get(int id, int userId) throws NotFoundException;

    void update(UserMeal userMeal, int userId) throws NotFoundException;

    UserMeal save(UserMeal userMeal, int userId) throws NotFoundException;

    void delete(int id, int userId) throws NotFoundException;

    List<UserMeal> getAll(int userId);
}
