package ru.javawebinar.topjava.repository.memory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.repository.UserMealRepository;
import ru.javawebinar.topjava.util.TimeUtil;
import ru.javawebinar.topjava.util.UserMealsUtil;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * GKislin
 * 15.09.2015.
 */
@Repository
public class InMemoryUserMealRepositoryImpl implements UserMealRepository {
    private Map<Integer, UserMeal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        UserMealsUtil.MEAL_LIST.forEach(this::save);
    }

    @Override
    public UserMeal save(UserMeal userMeal) {
        if (userMeal.isNew()) {
            userMeal.setId(counter.incrementAndGet());
        }
        repository.put(userMeal.getId(), userMeal);
        return userMeal;
    }

    @Override
    public void delete(int id) {
        repository.remove(id);
    }

    @Override
    public UserMeal get(int id) {
        return repository.get(id);
    }

    @Override
    public Collection<UserMeal> getAll(int userId) {
        return repository.entrySet().stream()
                .map(Map.Entry::getValue)
                .filter(m -> m.getUserId() == userId)
                .sorted(Collections.reverseOrder(Comparator.comparing(UserMeal::getDateTime)))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<UserMeal> getFiltered(int userId, LocalDate startDate, LocalDate endDate) {
        return repository.entrySet().stream()
                .map(Map.Entry::getValue)
                .filter(m -> m.getUserId() == userId && TimeUtil.isBetween(m.getDateTime().toLocalDate(), startDate, endDate))
                .sorted(Collections.reverseOrder(Comparator.comparing(UserMeal::getDateTime)))
                .collect(Collectors.toList());
    }
}

