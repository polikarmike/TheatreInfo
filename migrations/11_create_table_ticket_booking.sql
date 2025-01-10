CREATE TABLE ticket_booking (
    booking_id SERIAL PRIMARY KEY,
    status_id INT REFERENCES seat_status(status_id) ON DELETE CASCADE,
    user_id INT REFERENCES users(id) ON DELETE CASCADE,
    is_paid BOOLEAN DEFAULT FALSE
);
