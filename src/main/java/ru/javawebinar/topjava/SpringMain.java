package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.repository.memory.InMemoryUserMealRepositoryImpl;
import ru.javawebinar.topjava.repository.memory.InMemoryUserRepositoryImpl;
import ru.javawebinar.topjava.service.UserMealService;
import ru.javawebinar.topjava.service.UserMealServiceImpl;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.service.UserServiceImpl;
import ru.javawebinar.topjava.web.meal.UserMealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;

/**
 * User: gkislin
 * Date: 22.08.2014
 */
public class SpringMain {
    public static void main(String[] args) {
        // java 7 Automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println(Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            UserMealRestController userMealRestController = appCtx.getBean(UserMealRestController.class);
            System.out.println(adminUserController.create(new User(1, "userName", "email", "password", Role.ROLE_ADMIN)));
            System.out.println(adminUserController.getAll());

            System.out.println("=====");
            System.out.println(userMealRestController.getAllWithExceed());
            System.out.println(userMealRestController.save(new UserMeal(1, LocalDateTime.of(2016, Month.MAY, 30, 10, 0), "Завтрак", 500)));
            System.out.println(userMealRestController.getAllWithExceed());
            System.out.println(userMealRestController.getFilteredWithExceed(LocalDate.of(2016, 1, 1), null, LocalTime.of(8, 0), LocalTime.of(10, 0)));
        }
    }
}
