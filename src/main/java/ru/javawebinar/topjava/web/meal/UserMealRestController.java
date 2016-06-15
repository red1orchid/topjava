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

    public List<UserMeal> getAll() {
        LOG.info("getAll");
        return service.getAll(LoggedUser.id());
    }

    public List<UserMeal> getFiltred(LocalDate stratDate, LocalDate endDate, LocalTime startTime, LocalTime endTime) {
        LOG.info("getFiltred");
        return service.getAll(LoggedUser.id()).stream()
                .filter(m -> TimeUtil.isBetween(m.getDateTime().toLocalDate(), stratDate, endDate) && TimeUtil.isBetween(m.getDateTime().toLocalTime(), startTime, endTime))
                .collect(Collectors.toList());
    }

    public UserMeal get(int id) {
        LOG.info("get " + id);
        return service.get(id, LoggedUser.id());
    }

    public UserMeal create(UserMeal userMeal) {
        LOG.info("create " + userMeal);
        return service.save(userMeal, LoggedUser.id());
    }

    public void delete(int id) {
        LOG.info("delete " + id);
        service.delete(id, LoggedUser.id());
    }

    public void update(UserMeal userMeal, int id) {
        userMeal.setId(id);
        LOG.info("update " + userMeal);
        service.update(userMeal, LoggedUser.id());
    }
}
