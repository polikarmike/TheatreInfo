CREATE TABLE schedule (
    schedule_id SERIAL PRIMARY KEY,
    performance_id INT REFERENCES performance(performance_id) ON DELETE CASCADE,
    show_date DATE NOT NULL,
    show_time TIME NOT NULL,
    hall_id INT REFERENCES hall(hall_id) ON DELETE CASCADE
);

