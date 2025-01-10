
CREATE TABLE users (
   id SERIAL PRIMARY KEY,
   username VARCHAR(150) UNIQUE NOT NULL,
   password VARCHAR(150) NOT NULL,
   first_name VARCHAR(150) NOT NULL,
   last_name VARCHAR(150) NOT NULL,
   email VARCHAR(150) UNIQUE NOT NULL
);

-- Таблица типов пользователей
CREATE TABLE user_types (
    id SERIAL PRIMARY KEY,
    name VARCHAR(100) UNIQUE NOT NULL
);


CREATE TABLE user_user_types (
     user_id INT REFERENCES users(id) ON DELETE CASCADE,
     user_type_id INT REFERENCES user_types(id) ON DELETE CASCADE,
     PRIMARY KEY (user_id, user_type_id)
);
