import React, { useState, useEffect, useLayoutEffect, useRef } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import './BookingPage.css';
import { useAuth } from '../AuthContext';

function BookingPage() {
    const { role } = useAuth();
    const navigate = useNavigate();
    const seatingAreaRef = useRef(null);
    const { scheduleId } = useParams();
    const [schedule, setSchedule] = useState(null);
    const [seats, setSeats] = useState([]);
    const [selectedSeats, setSelectedSeats] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [dataLoaded, setDataLoaded] = useState(false);

    useEffect(() => {
        const fetchData = async () => {
            try {
                setLoading(true);
                const scheduleResponse = await fetch(`/api/schedules/${scheduleId}`);
                const scheduleData = await scheduleResponse.json();
                setSchedule(scheduleData);

                const seatStatusesResponse = await fetch(`/api/seat-status/schedule/${scheduleId}`);
                const seatStatusesData = await seatStatusesResponse.json();
                setSeats(seatStatusesData);

                setDataLoaded(true);
            } catch (error) {
                setError('Ошибка при загрузке данных. Попробуйте позже.');
                console.error('Ошибка при загрузке данных:', error);
            } finally {
                setLoading(false);
            }
        };

        fetchData();
    }, [scheduleId]);

    useLayoutEffect(() => {
        if (dataLoaded && seatingAreaRef.current) {
            const seatingArea = seatingAreaRef.current;
            const parentWidth = 850; // Ширина родителя
            const desiredWidth = schedule.hall.seatPerRow * 80;
            const scaleCoefficient = parentWidth / desiredWidth;

            const desiredHeight = schedule.hall.rowCount * 70 * scaleCoefficient;
            seatingArea.style.setProperty('--scale-coefficient', scaleCoefficient);
            seatingArea.style.setProperty('--height', `${desiredHeight}px`);
        }
    }, [dataLoaded, schedule]);

    const handleSeatSelection = (statusId) => {
        setSelectedSeats((prevSelectedSeats) =>
            prevSelectedSeats.includes(statusId)
                ? prevSelectedSeats.filter((id) => id !== statusId)
                : [...prevSelectedSeats, statusId]
        );
    };

    const handleBooking = async () => {
        if (selectedSeats.length === 0) {
            alert('Пожалуйста, выберите хотя бы одно место!');
            return;
        }

        try {
            const response = await fetch('/api/bookings/book-seats', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ seatStatusIds: selectedSeats }),
            });

            if (response.ok) {
                alert('Бронирование успешно выполнено!');
                setSelectedSeats([]);
            } else {
                const errorData = await response.json();
                alert('Ошибка при бронировании: ' + errorData.message);
            }
        } catch (error) {
            console.error('Ошибка при бронировании:', error);
            alert('Ошибка при бронировании мест.');
        }
    };

    const totalAmount = selectedSeats.reduce((sum, statusId) => {
        const seat = seats.find((s) => s.statusId === statusId);
        return sum + (seat ? seat.price : 0);
    }, 0);

    if (loading) {
        return <div className="loading">Загрузка...</div>;
    }

    if (error) {
        return <div className="error">{error}</div>;
    }

    const { performance, showDate, showTime } = schedule;
    const groupedSeats = Array.from(new Set(seats.map((seat) => seat.rowNumber)))
        .map((rowNumber) => ({
            rowNumber,
            seats: seats
                .filter((seat) => seat.rowNumber === rowNumber)
                .sort((a, b) => a.seatNumber - b.seatNumber),
        }));

    const formatDate = (dateString) => {
        const options = { day: 'numeric', month: 'long' };
        const date = new Date(dateString);
        return date.toLocaleDateString('ru-RU', options);
    };

    const formatTime = (timeString) => {
        const date = new Date(`1970-01-01T${timeString}Z`);
        return date.toLocaleTimeString('ru-RU', { hour: '2-digit', minute: '2-digit', timeZone: 'UTC' });
    };

    return (
        <div className="bookingPage-theatre-hall">
            <header className="bookingPage-theatre-header">
                <h1>{performance.title}</h1>
                <p>
                    <span>Дата: <strong>{formatDate(showDate)} </strong></span>
                    <span>Время: <strong>{formatTime(showTime)}</strong></span>
                </p>
            </header>

            <div className="theatre-hall-container">
                {loading ? (
                    <div className="loading-message">Загрузка...</div>
                ) : (
                    <>
                        <div className="bookingPage-screen">Сцена</div>
                        <div ref={seatingAreaRef} className="bookingPage-seating-area">
                            {groupedSeats.map(({rowNumber, seats}) => (
                                <div key={rowNumber} className="bookingPage-seat-row">
                                    <span className="bookingPage-row-label">{rowNumber}</span>
                                    {seats.map((seat) => (
                                        <button
                                            key={seat.statusId}
                                            className={`bookingPage-seat ${seat.occupied
                                                ? 'bookingPage-occupied-seat'
                                                : selectedSeats.includes(seat.statusId)
                                                    ? 'bookingPage-selected-seat'
                                                    : seat.seatType === 'VIP'
                                                        ? 'bookingPage-vip-seat'
                                                        : 'bookingPage-regular-seat'}`}
                                            onClick={() => !seat.occupied && handleSeatSelection(seat.statusId)}
                                            disabled={seat.occupied}
                                            title={`Ряд: ${rowNumber}, Место: ${seat.seatNumber}, Цена: ${seat.price} ₽`}
                                        ></button>
                                    ))}
                                    <span className="bookingPage-row-label">{rowNumber}</span>
                                </div>
                            ))}
                        </div>
                    </>
                )}
            </div>

            <footer className="cinema-footer">
                <div className="legend">
                    <div className="legend-item">
                        <span className="legend-color legend-regular"></span>
                        Обычное место
                    </div>
                    <div className="legend-item">
                        <span className="legend-color legend-vip"></span>
                        VIP место
                    </div>
                    <div className="legend-item">
                        <span className="legend-color legend-selected"></span>
                        Выбранное место
                    </div>
                    <div className="legend-item">
                        <span className="legend-color legend-occupied"></span>
                        Занятое место
                    </div>
                </div>
                <div className="actions">
                    <div className="total-amount">Итого: {totalAmount} ₽</div>
                    <button onClick={handleBooking} className="booking-button">
                        Забронировать
                    </button>
                </div>
            </footer>
        </div>
    );
}

export default BookingPage;
