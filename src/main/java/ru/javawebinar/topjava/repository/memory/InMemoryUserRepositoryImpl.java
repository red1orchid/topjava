package ru.javawebinar.topjava.repository.memory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;
import ru.javawebinar.topjava.util.UserMealsUtil;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * GKislin
 * 15.06.2015.
 */
@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {
    private static final Logger LOG = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);
    private Map<Integer, User> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        UserMealsUtil.USERS_LIST.forEach(this::save);
    }

    @Override
    public boolean delete(int id) {
        LOG.info("delete " + id);
        return repository.remove(id) != null;
    }

    @Override
    public User save(User user) {
        LOG.info("save " + user);
        if (user.isNew()) {
            user.setId(counter.incrementAndGet());
        }
        repository.put(user.getId(), user);

        return user;
    }

    @Override
    public User get(int id) {
        LOG.info("get " + id);
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        LOG.info("getAll");
        return repository.entrySet().stream()
                .map(Map.Entry::getValue)
                .sorted(Comparator.comparing(User::getName))
                .collect(Collectors.toList());
    }

    @Override
    public User getByEmail(String email) {
        LOG.info("getByEmail " + email);
        Optional<User> user = repository.entrySet().stream()
                                .map(Map.Entry::getValue)
                                .filter(u -> u.getEmail().equals(email))
                                .findFirst();
        if (user.isPresent()) {
            return user.get();
        }  else {
            return null;
        }
    }
}
