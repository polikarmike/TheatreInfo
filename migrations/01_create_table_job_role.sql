-- Таблица допустимых ролей
CREATE TABLE job_role (
                          role_id SERIAL PRIMARY KEY,
                          role_name VARCHAR(50) UNIQUE NOT NULL  -- Название роли, например, 'Режиссёр', 'Сценограф'
);

-- Добавление ролей в таблицу job_role
INSERT INTO job_role (role_name) VALUES ('Режиссёр');
INSERT INTO job_role (role_name) VALUES ('Сценограф');
INSERT INTO job_role (role_name) VALUES ('Актёр');
INSERT INTO job_role (role_name) VALUES ('Хореограф');
INSERT INTO job_role (role_name) VALUES ('Композитор');
INSERT INTO job_role (role_name) VALUES ('Звукорежиссёр');
INSERT INTO job_role (role_name) VALUES ('Светорежиссёр');
INSERT INTO job_role (role_name) VALUES ('Художник по костюмам');
INSERT INTO job_role (role_name) VALUES ('Продюсер');
INSERT INTO job_role (role_name) VALUES ('Техник');
INSERT INTO job_role (role_name) VALUES ('Помощник режиссёра');