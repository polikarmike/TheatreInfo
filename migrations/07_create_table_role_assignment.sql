-- Таблица назначения работников на роли
CREATE TABLE role_assignment (
    assignment_id SERIAL PRIMARY KEY,
    schedule_id INT REFERENCES schedule(schedule_id) ON DELETE CASCADE,
    role_id INT REFERENCES performance_role(role_id) ON DELETE CASCADE,
    worker_id INT REFERENCES worker(worker_id) ON DELETE CASCADE
);
