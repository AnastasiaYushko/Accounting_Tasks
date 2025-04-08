import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';

const Login = ({ login }) => {
  const [loginValue, setLoginValue] = useState('');
  const [password, setPassword] = useState('');
  const navigate = useNavigate();
  const [error, setError] = useState('');

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    try {
      await login({ login: loginValue, password });
      navigate('/');
    } catch (err) {
      if (err.response && err.response.data && err.response.data.message) {
        setError(err.response.data.message);
      } else {
        setError('Ошибка входа. Пожалуйста, попробуйте еще раз.');
      }
      console.error('Ошибка входа:', err);
    }
  };

  const handleClose = () => {
    navigate(-1); // Возвращаемся на предыдущую страницу
  };

  return (
    <div className="auth-container">
      <button className="close-button" onClick={handleClose}>
        &times;
      </button>
      <h1>Вход</h1>
      <form onSubmit={handleSubmit}>
        {error && <div className="error-message">{error}</div>}
        <div className="form-group">
          <label htmlFor="login">Логин:</label>
          <input
            type="text"
            id="login"
            value={loginValue}
            onChange={(e) => setLoginValue(e.target.value)}
            required
            maxLength="255"
          />
        </div>
        <div className="form-group">
          <label htmlFor="password">Пароль:</label>
          <input
            type="password"
            id="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
            required
            maxLength="255"
          />
        </div>
        <button type="submit">Войти</button>
      </form>
    </div>
  );
};

export default Login;