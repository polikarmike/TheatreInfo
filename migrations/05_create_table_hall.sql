CREATE TABLE hall (
    hall_id SERIAL PRIMARY KEY,
    hall_name VARCHAR(100) NOT NULL,
    row_count INT NOT NULL,
    seat_per_row INT NOT NULL
);
