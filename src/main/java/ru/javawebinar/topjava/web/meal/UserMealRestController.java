package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.LoggedUser;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.service.UserMealService;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.to.UserMealWithExceed;
import ru.javawebinar.topjava.util.TimeUtil;
import ru.javawebinar.topjava.util.UserMealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * GKislin
 * 06.03.2015.
 */
@Controller
public class UserMealRestController {
    protected final Logger LOG = LoggerFactory.getLogger(getClass());
    @Autowired
    private UserMealService service;

    public UserMeal get(int id) {
        LOG.info("get " + id);
        return service.get(id, LoggedUser.id());
    }

    public UserMeal save(UserMeal userMeal) {
        LOG.info("save " + userMeal);
        userMeal.setUserId(LoggedUser.id());
        return service.save(userMeal, LoggedUser.id());
    }

    public void delete(int id) {
        LOG.info("delete " + id);
        service.delete(id, LoggedUser.id());
    }

    public List<UserMealWithExceed> getAllWithExceed() {
        LOG.info("getAll");
        return service.getAllWithExceed(LoggedUser.id(), LoggedUser.getCaloriesPerDay());
    }

    public List<UserMealWithExceed> getFilteredWithExceed(LocalDate stratDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        LOG.info("getFiltred");
        return service.getFilteredWithExceed(LoggedUser.id(), LoggedUser.getCaloriesPerDay(), stratDate, endDate, startTime, endTime);
    }
}
