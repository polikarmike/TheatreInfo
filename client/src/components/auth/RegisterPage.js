import React, { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import './RegisterPage.css'; // Импортируем стили

const RegisterPage = () => {
    const [formData, setFormData] = useState({
        username: '',
        password: '',
        firstName: '',
        lastName: '',
        email: '',
    });

    const [errors, setErrors] = useState({});
    const [success, setSuccess] = useState('');
    const navigate = useNavigate();

    const handleChange = (e) => {
        const { name, value } = e.target;
        setFormData({
            ...formData,
            [name]: value,
        });
    };

    const validate = () => {
        const errors = {};
        if (!formData.username) {
            errors.username = 'Имя пользователя является обязательным';
        } else if (formData.username.length < 3 || formData.username.length > 50) {
            errors.username = 'Имя пользователя должно быть от 3 до 50 символов';
        }

        if (!formData.password) {
            errors.password = 'Пароль является обязательным';
        } else if (formData.password.length < 8) {
            errors.password = 'Пароль должен быть не менее 8 символов';
        }

        if (!formData.firstName) {
            errors.firstName = 'Имя является обязательным';
        }

        if (!formData.lastName) {
            errors.lastName = 'Фамилия является обязательной';
        }

        if (!formData.email) {
            errors.email = 'Электронная почта является обязательной';
        } else if (!/\S+@\S+\.\S+/.test(formData.email)) {
            errors.email = 'Электронная почта должна быть валидной';
        }

        return errors;
    };

    const handleSubmit = async (e) => {
        e.preventDefault();
        const validationErrors = validate();
        if (Object.keys(validationErrors).length > 0) {
            setErrors(validationErrors);
            return;
        }

        try {
            const response = await fetch('/api/auth/register', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(formData),
            });

            if (!response.ok) {
                const errorData = await response.text();
                setErrors({ apiError: errorData });
                return;
            }

            setSuccess('Пользователь успешно зарегистрирован');
            setFormData({
                username: '',
                password: '',
                firstName: '',
                lastName: '',
                email: '',
            });

            // Редирект на страницу входа после успешной регистрации
            setTimeout(() => {
                navigate('/login');
            }, 2000);
        } catch (err) {
            setErrors({ apiError: 'Ошибка при регистрации' });
        }
    };

    return (
        <div className="registerPage-container">
            <form onSubmit={handleSubmit} className="registerPage-mainForm">
                <h2 className="registerPage-title">Регистрация</h2>
                <label className="registerPage-label">Имя пользователя</label>
                <input
                    type="text"
                    name="username"
                    value={formData.username}
                    onChange={handleChange}
                    placeholder="Username"
                    className="registerPage-input"
                />
                {errors.username && <div className="error-message">{errors.username}</div>}

                <label className="registerPage-label">Пароль</label>
                <input
                    type="password"
                    name="password"
                    value={formData.password}
                    onChange={handleChange}
                    placeholder="Password"
                    className="registerPage-input"
                />
                {errors.password && <div className="error-message">{errors.password}</div>}

                <label className="registerPage-label">Имя</label>
                <input
                    type="text"
                    name="firstName"
                    value={formData.firstName}
                    onChange={handleChange}
                    placeholder="First Name"
                    className="registerPage-input"
                />
                {errors.firstName && <div className="error-message">{errors.firstName}</div>}

                <label className="registerPage-label">Фамилия</label>
                <input
                    type="text"
                    name="lastName"
                    value={formData.lastName}
                    onChange={handleChange}
                    placeholder="Last Name"
                    className="registerPage-input"
                />
                {errors.lastName && <div className="error-message">{errors.lastName}</div>}

                <label className="registerPage-label">Электронная почта</label>
                <input
                    type="email"
                    name="email"
                    value={formData.email}
                    onChange={handleChange}
                    placeholder="Email"
                    className="registerPage-input"
                />
                {errors.email && <div className="error-message">{errors.email}</div>}

                <div className="registerPage-buttons">
                    <button type="submit" className="registerPage-submitButton">Зарегистрироваться</button>
                </div>
            </form>

            <div className="registerPage-footer">
                <p>Уже есть аккаунт? <Link to="/login" className="registerPage-loginLink">Войти</Link></p>
            </div>

            {success && <div className="success-message">{success}</div>}
            {errors.apiError && <div className="error-message">{errors.apiError}</div>}
        </div>
    );
};

export default RegisterPage;
