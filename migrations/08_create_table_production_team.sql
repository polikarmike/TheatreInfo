-- Таблица постановщиков
CREATE TABLE production_team (
     team_id SERIAL PRIMARY KEY,
     schedule_id INT REFERENCES schedule(schedule_id) ON DELETE CASCADE,
     worker_id INT REFERENCES worker(worker_id) ON DELETE CASCADE
);
