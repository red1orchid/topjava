DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password');

INSERT INTO users (name, email, password)
VALUES ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (user_id, meal_date, calories, description)
VALUES (100000, '2016-06-21 09:00:00', 900, 'breakfast');

INSERT INTO meals (user_id, meal_date, calories, description)
VALUES (100000, '2016-06-21 14:00:00', 1200, 'lunch');

INSERT INTO meals (user_id, meal_date, calories, description)
VALUES (100000, '2016-06-21 20:00:00', 700, 'dinner');