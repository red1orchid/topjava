package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.util.exception.ExceptionUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.util.List;

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

    public List<UserMeal> getAll(int userId) {
        return (List) repository.getAll(userId);
    }

    private boolean isAuthorized(UserMeal userMeal, int userId) {
        return userMeal != null && userMeal.getUserId() == userId;
    }
}
