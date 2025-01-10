import React, { useState } from 'react';
import './UserProfile-Modal.css';

const ChangePassword = ({ setIsChangePasswordOpen }) => {
    const [oldPassword, setOldPassword] = useState('');
    const [newPassword, setNewPassword] = useState('');
    const [error, setError] = useState(null);

    const validatePassword = (password) => {
        const minLength = 8;
        const hasUpperCase = /[A-Z]/.test(password);
        const hasLowerCase = /[a-z]/.test(password);
        const hasNumber = /[0-9]/.test(password);
        const hasSpecialChar = /[!@#$%^&*(),.?":{}|<>]/.test(password);

        if (password.length < minLength) {
            return 'Пароль должен быть не короче 8 символов';
        }
        if (!hasUpperCase) {
            return 'Пароль должен содержать как минимум одну заглавную букву';
        }
        if (!hasLowerCase) {
            return 'Пароль должен содержать как минимум одну строчную букву';
        }
        if (!hasNumber) {
            return 'Пароль должен содержать как минимум одну цифру';
        }
        if (!hasSpecialChar) {
            return 'Пароль должен содержать как минимум один специальный символ';
        }
        return null;
    };

    const handleChangePassword = async (e) => {
        e.preventDefault();
        const validationError = validatePassword(newPassword);
        if (validationError) {
            setError(validationError);
            return;
        }

        try {
            const token = localStorage.getItem('token');

            if (!token) {
                throw new Error('Токен не найден');
            }

            const response = await fetch('/api/user/profile/change-password', {
                method: 'PUT',
                headers: {
                    'Authorization': `Bearer ${token}`,
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({ oldPassword, newPassword }),
            });

            if (!response.ok) {
                throw new Error('Ошибка при изменении пароля');
            }
            alert('Пароль успешно изменен!');
            setOldPassword('');
            setNewPassword('');
            setIsChangePasswordOpen(false);
        } catch (error) {
            setError(error.message);
        }
    };

    return (
        <form onSubmit={handleChangePassword} className="changePassword-form">
            <h3>Изменить пароль</h3>
            <div className="form-group">
                <label>Старый пароль:</label>
                <input
                    type="password"
                    value={oldPassword}
                    onChange={(e) => setOldPassword(e.target.value)}
                    required
                />
            </div>
            <div className="form-group">
                <label>Новый пароль:</label>
                <input
                    type="password"
                    value={newPassword}
                    onChange={(e) => setNewPassword(e.target.value)}
                    required
                />
            </div>
            <button type="submit" className="btn-primary">Изменить пароль</button>
            {error && <div className="error">{error}</div>}
        </form>
    );
};

export default ChangePassword;
