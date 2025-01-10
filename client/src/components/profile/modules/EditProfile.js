import React, { useState } from 'react';
import './UserProfile-Modal.css'

const EditProfile = ({ userInfo, setSuccessMessage }) => {
    const [firstName, setFirstName] = useState(userInfo.firstName);
    const [lastName, setLastName] = useState(userInfo.lastName);
    const [email, setEmail] = useState(userInfo.email);
    const [error, setError] = useState(null);

    const validateEmail = (email) => {
        const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/;
        return emailRegex.test(email);
    };

    const handleProfileUpdate = async (e) => {
        e.preventDefault();

        if (!validateEmail(email)) {
            setError('Некорректный email адрес');
            return;
        }

        try {
            const token = localStorage.getItem('token');

            if (!token) {
                throw new Error('Токен не найден');
            }

            const response = await fetch('/api/user/profile', {
                method: 'PUT',
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ firstName, lastName, email }),
            });

            if (!response.ok) {
                throw new Error('Ошибка при обновлении профиля');
            }

            const data = await response.json();
            alert('Профиль успешно обновлен!');
            window.location.reload();
        } catch (error) {
            setError(error.message);
        }
    };

    return (
        <form onSubmit={handleProfileUpdate} className="editProfile-form">
            <h3>Изменить данные профиля</h3>
            <div className="form-group">
                <label>Имя:</label>
                <input
                    type="text"
                    value={firstName}
                    onChange={(e) => setFirstName(e.target.value)}
                />
            </div>
            <div className="form-group">
                <label>Фамилия:</label>
                <input
                    type="text"
                    value={lastName}
                    onChange={(e) => setLastName(e.target.value)}
                />
            </div>
            <div className="form-group">
                <label>Email:</label>
                <input
                    type="email"
                    value={email}
                    onChange={(e) => setEmail(e.target.value)}
                />
            </div>
            <button type="submit" className="btn-primary">Обновить профиль</button>
            {error && <div className="error">{error}</div>}
        </form>
    );
};

export default EditProfile;
