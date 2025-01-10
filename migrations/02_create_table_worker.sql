-- Таблица работников
CREATE TABLE worker (
                        worker_id SERIAL PRIMARY KEY,
                        first_name VARCHAR(100) NOT NULL,
                        last_name VARCHAR(100) NOT NULL,
                        biography TEXT,
                        role_id INT REFERENCES job_role(role_id) ON DELETE SET NULL,  -- Внешний ключ на роль
                        photo_url VARCHAR(255)  -- Путь к фото
);