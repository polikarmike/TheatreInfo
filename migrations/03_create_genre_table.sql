-- Таблица жанров
CREATE TABLE genre (
    genre_id SERIAL PRIMARY KEY,
    genre_name VARCHAR(100) UNIQUE NOT NULL
);

INSERT INTO genre (genre_name) VALUES ('Драма');
INSERT INTO genre (genre_name) VALUES ('Комедия');
INSERT INTO genre (genre_name) VALUES ('Трагедия');
INSERT INTO genre (genre_name) VALUES ('Мюзикл');
INSERT INTO genre (genre_name) VALUES ('Фарс');
INSERT INTO genre (genre_name) VALUES ('Трагикомедия');
INSERT INTO genre (genre_name) VALUES ('Опера');
INSERT INTO genre (genre_name) VALUES ('Балет');
INSERT INTO genre (genre_name) VALUES ('Мелодрама');
INSERT INTO genre (genre_name) VALUES ('Фэнтези');
INSERT INTO genre (genre_name) VALUES ('Сатирическая комедия');
INSERT INTO genre (genre_name) VALUES ('Историческая драма');
INSERT INTO genre (genre_name) VALUES ('Детектив');
INSERT INTO genre (genre_name) VALUES ('Абсурдистская драма');
INSERT INTO genre (genre_name) VALUES ('Монодрама');
INSERT INTO genre (genre_name) VALUES ('Мистика');
INSERT INTO genre (genre_name) VALUES ('Сказка');
INSERT INTO genre (genre_name) VALUES ('Философская драма');
INSERT INTO genre (genre_name) VALUES ('Научная фантастика');
