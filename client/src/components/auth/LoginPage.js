import { useState } from 'react';
import { useAuth } from './AuthContext';
import { login as apiLogin } from './authService';
import {Link, useLocation, useNavigate} from 'react-router-dom';
import './LoginPage.css';

const LoginPage = () => {
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const { login, authError, authSuccess, setAuthError } = useAuth();
    const location = useLocation();
    const navigate = useNavigate();

    const from = location.state?.from?.pathname || '/';

    const handleSubmit = async (e) => {
        e.preventDefault();

        try {
            const data = await apiLogin(username, password);
            login(data.username, data.role);
            navigate(from, { replace: true });
        } catch (error) {
            setAuthError("Неверный логин или пароль.");
        }
    };

    return (
        <div className="loginPage-container">
            <form onSubmit={handleSubmit} className="loginPage-mainForm">
                <h2 className="loginPage-title">Вход</h2>
                <label className="loginPage-label">Логин</label>
                <input
                    type="text"
                    value={username}
                    onChange={(e) => setUsername(e.target.value)}
                    placeholder="Username"
                    className="loginPage-input"
                />
                <label className="loginPage-label">Пароль</label>
                <input
                    type="password"
                    value={password}
                    onChange={(e) => setPassword(e.target.value)}
                    placeholder="Password"
                    className="loginPage-input"
                />
                <div className="loginPage-buttons">
                    <button type="submit" className="loginPage-submitButton">Войти</button>
                </div>
            </form>

            <div className="loginPage-footer">
                <p>Нет аккаунта? <Link to="/register" className="loginPage-registerLink">Зарегистрируйтесь</Link></p>
            </div>

            {/* Показываем сообщения об ошибке или успехе */}
            {authSuccess && <div className="success-message">{authSuccess}</div>}
            {authError && <div className="error-message">{authError}</div>}
        </div>
    );
};

export default LoginPage;
