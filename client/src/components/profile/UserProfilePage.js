import React, { useState, useEffect } from 'react';
import './UserProfilePage.css';
import EditProfile from './EditProfile';
import ChangePassword from './ChangePassword';

const UserProfilePage = () => {
    const [userInfo, setUserInfo] = useState({});
    const [bookings, setBookings] = useState([]);
    const [loading, setLoading] = useState(true);
    const [error, setError] = useState(null);
    const [successMessage, setSuccessMessage] = useState(null);

    const [isEditProfileOpen, setIsEditProfileOpen] = useState(false);
    const [isChangePasswordOpen, setIsChangePasswordOpen] = useState(false);

    useEffect(() => {
        const fetchUserProfile = async () => {
            try {
                const token = localStorage.getItem('token');
                if (!token) {
                    setError('Токен не найден');
                    return;
                }

                const response = await fetch('/api/user/profile', {
                    method: 'GET',
                    headers: { 'Authorization': `Bearer ${token}`, 'Content-Type': 'application/json' },
                });
                if (!response.ok) {
                    setError('Ошибка при загрузке профиля пользователя');
                    return;
                }

                const data = await response.json();
                setUserInfo(data);
            } catch (error) {
                setError(error.message);
            } finally {
                setLoading(false);
            }
        };

        const fetchBookings = async () => {
            try {
                const token = localStorage.getItem('token');
                if (!token) {
                    setError('Токен не найден');
                    return;
                }

                const response = await fetch('/api/bookings/user', {
                    method: 'GET',
                    headers: { 'Authorization': `Bearer ${token}`, 'Content-Type': 'application/json' },
                });
                if (!response.ok) {
                    setError('Ошибка при загрузке бронирований');
                    return;
                }

                const data = await response.json();
                setBookings(data);
            } catch (error) {
                setError(error.message);
            } finally {
                setLoading(false);
            }
        };

        fetchUserProfile().then();
        fetchBookings().then();
    }, []);

    const handleCancelBooking = async (scheduleId) => {
        try {
            const token = localStorage.getItem('token');
            if (!token) {
                setError('Токен не найден');
                return;
            }

            const response = await fetch(`/api/bookings/cancel/schedule/${scheduleId}`, {
                method: 'DELETE',
                headers: { 'Authorization': `Bearer ${token}`, 'Content-Type': 'application/json' },
            });
            if (!response.ok) {
                setError('Ошибка при отмене бронирования');
                return;
            }

            setBookings((prevBookings) => prevBookings.filter(booking => booking.scheduleId !== scheduleId));
            setSuccessMessage("Все бронирования по расписанию успешно отменены!");
        } catch (error) {
            setError(error.message);
        }
    };

    if (loading) return <div className="loading">Загрузка...</div>;
    if (error) return <div className="error">{error}</div>;

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
        <div className="userProfile-container">
            <h2 className="userProfile-title">Мой профиль</h2>
            <div className="userProfile-info">
                <div className="profile-data">
                    <p><strong>Имя:</strong> {userInfo.firstName}</p>
                    <p><strong>Фамилия:</strong> {userInfo.lastName}</p>
                    <p><strong>Email:</strong> {userInfo.email}</p>
                </div>
                <div className="profile-button-container">
                    <button
                        onClick={() => setIsEditProfileOpen(true)}
                        className="profile-btn-primary profile-editButton">
                        <i className="fas fa-edit"></i> Редактировать профиль
                    </button>
                    <button
                        onClick={() => setIsChangePasswordOpen(true)}
                        className="profile-btn-primary profile-passwordButton">
                        <i className="fas fa-key"></i> Изменить пароль
                    </button>
                </div>
            </div>

            <h3 className="userProfile-subtitle">Мои бронирования</h3>
            <ul className="userProfile-bookingList">
                {bookings.length > 0 ? (
                    bookings.map(booking => (
                        <li key={booking.scheduleId} className="userProfile-bookingItem">
                            <div className="booking-details">
                                <h4>{booking.performanceTitle}</h4>
                                <p>Дата: {formatDate(booking.showDate)}</p>
                                <p>Время: {formatTime(booking.showTime)}</p>
                                <p>Места: {booking.seatInfo}</p>
                            </div>
                            <button onClick={() => handleCancelBooking(booking.scheduleId)}
                                    className="profile-btn-danger">Отменить
                            </button>
                        </li>
                    ))
                ) : (
                    <li className="no-bookings">У вас нет активных бронирований в данный момент.</li>
                )}
            </ul>
            {successMessage && <div className="success-message">{successMessage}</div>}

            {isEditProfileOpen && (
                <>
                    <div className="modal-overlay" onClick={() => setIsEditProfileOpen(false)}></div>
                    <div className="modal">
                        <button onClick={() => setIsEditProfileOpen(false)} className="close-button">×</button>
                        <EditProfile userInfo={userInfo} setSuccessMessage={setSuccessMessage} />
                    </div>
                </>
            )}

            {isChangePasswordOpen && (
                <>
                    <div className="modal-overlay" onClick={() => setIsChangePasswordOpen(false)}></div>
                    <div className="modal">
                        <button onClick={() => setIsChangePasswordOpen(false)} className="close-button">×</button>
                        <ChangePassword setIsChangePasswordOpen={setIsChangePasswordOpen} />
                    </div>
                </>
            )}

        </div>
    );
};

export default UserProfilePage;
