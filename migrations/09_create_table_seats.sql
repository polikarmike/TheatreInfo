-- Таблица мест
CREATE TABLE seat (
    seat_id SERIAL PRIMARY KEY,
    row_number VARCHAR(5) NOT NULL,
    seat_number INT NOT NULL,
    seat_type VARCHAR(50) NOT NULL,
    price DECIMAL(10, 2) NOT NULL
);

-- Таблица состояния мест
CREATE TABLE seat_status (
    status_id SERIAL PRIMARY KEY,
    schedule_id INT REFERENCES schedule(schedule_id) ON DELETE CASCADE,
    seat_id INT REFERENCES seat(seat_id) ON DELETE CASCADE,
    is_occupied BOOLEAN DEFAULT FALSE
);
