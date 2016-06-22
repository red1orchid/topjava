package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

import static ru.javawebinar.topjava.MealTestData.*;

import static org.junit.Assert.*;

/**
 * Created by PerevalovaMA on 22.06.2016.
 */
@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
public class UserMealServiceTest {

    @Autowired
    protected UserMealService service;

    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @Test
    public void get() throws Exception {
        UserMeal meal = service.get(MEAL2_ID, USER_ID);
        MATCHER.assertEquals(MEAL2, meal);
    }

    @Test(expected = NotFoundException.class)
    public void getNotYours() throws Exception {
        service.get(MEAL2_ID, ADMIN_ID);
    }

    @Test
    public void delete() throws Exception {
        service.delete(MEAL1_ID, USER_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(MEAL3, MEAL2), service.getAll(USER_ID));
    }

    @Test(expected = NotFoundException.class)
    public void deleteNotYours() throws Exception {
        service.delete(MEAL1_ID, ADMIN_ID);
    }

    @Test
    public void getBetweenDates() throws Exception {
        UserMeal meal = new UserMeal(LocalDateTime.of(2016, 6, 20, 0, 0), "new meal", 2000);
        service.save(meal, USER_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(MEAL3, MEAL2, MEAL1), service.getBetweenDates(LocalDate.of(2016, 6, 21), LocalDate.of(2016, 6, 22), USER_ID));
    }

    @Test
    public void getBetweenDateTimes() throws Exception {
        MATCHER.assertCollectionEquals(Collections.singletonList(MEAL3), service.getBetweenDateTimes(LocalDateTime.of(2016, 6, 21, 15, 0), LocalDateTime.of(2016, 6, 21, 22, 0), USER_ID));
    }

    @Test
    public void getAll() throws Exception {
        MATCHER.assertCollectionEquals(Arrays.asList(MEAL3, MEAL2, MEAL1), service.getAll(USER_ID));
    }

    @Test
    public void update() throws Exception {
        UserMeal meal = new UserMeal(MEAL1_ID, LocalDateTime.of(2016, 6, 21, 0, 0), "new meal", 2000);
        MATCHER.assertEquals(service.update(meal, USER_ID), meal);
    }

    @Test(expected = NotFoundException.class)
    public void updateNotYours() throws Exception {
        UserMeal meal = new UserMeal(MEAL1_ID, LocalDateTime.of(2016, 6, 21, 0, 0), "new meal", 2000);
        MATCHER.assertEquals(service.update(meal, ADMIN_ID), meal);
    }

    @Test
    public void save() throws Exception {
        UserMeal meal = new UserMeal(MEAL1_ID, LocalDateTime.of(2016, 6, 21, 0, 0), "new meal", 2000);
        MATCHER.assertEquals(service.save(meal, USER_ID), meal);
    }
}