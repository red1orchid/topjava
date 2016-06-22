package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.ModelMatcher;
import ru.javawebinar.topjava.model.UserMeal;

import java.time.LocalDateTime;

import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

/**
 * GKislin
 * 13.03.2015.
 */
public class MealTestData {
    public static final ModelMatcher<UserMeal, String> MATCHER = new ModelMatcher<>(UserMeal::toString);

    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;
    public static final int MEAL1_ID = START_SEQ + 2;
    public static final int MEAL2_ID = START_SEQ + 3;
    public static final int MEAL3_ID = START_SEQ + 4;

    public static final UserMeal MEAL1 = new UserMeal(MEAL1_ID, LocalDateTime.of(2016, 6, 21, 9, 0), "breakfast", 900);
    public static final UserMeal MEAL2 = new UserMeal(MEAL2_ID, LocalDateTime.of(2016, 6, 21, 14, 0), "lunch", 1200);
    public static final UserMeal MEAL3 = new UserMeal(MEAL3_ID, LocalDateTime.of(2016, 6, 21, 20, 0), "dinner", 700);
}
