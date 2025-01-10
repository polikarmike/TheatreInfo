-- Таблица спектаклей
CREATE TABLE performance (
    performance_id SERIAL PRIMARY KEY,
    title VARCHAR(150) NOT NULL,
    description TEXT,
    duration INT NOT NULL,
    poster_url VARCHAR(255),
    genre_id INT REFERENCES genre(genre_id) ON DELETE RESTRICT
);

-- Таблица ролей в спектаклях (роли привязаны к спектаклям)
CREATE TABLE performance_role (
    role_id SERIAL PRIMARY KEY,
    performance_id INT REFERENCES performance(performance_id) ON DELETE CASCADE,
    role_name VARCHAR(100) NOT NULL
);