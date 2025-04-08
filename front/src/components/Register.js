import React, { useState, useEffect } from 'react';
import { useNavigate } from 'react-router-dom';

const Register = ({ register }) => {
  const [loginValue, setLoginValue] = useState('');
  const [password, setPassword] = useState('');
  const [name, setName] = useState('');
  const navigate = useNavigate();
  const [error, setError] = useState('');
  const [successMessage, setSuccessMessage] = useState('');

  // Функция для сброса значений полей
  const resetForm = () => {
    setLoginValue('');
    setPassword('');
    setName('');
  };

  useEffect(() => {
    resetForm();
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();
    setError('');
    setSuccessMessage('');

    try {
      const response = await register({ login: loginValue, password, name });
      if (response.status === 201) {
        setSuccessMessage('Регистрация прошла успешно!');
        resetForm();
      } else {
        setError('Произошла ошибка при регистрации.');
      }
    } catch (err) {
      if (err.response && err.response.data && err.response.data.message) {
        setError(err.response.data.message);
      } else {
        setError('Ошибка регистрации. Пожалуйста, попробуйте еще раз.');
      }
      console.error('Ошибка регистрации:', err);
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
      <h1>Регистрация</h1>
      <form onSubmit={handleSubmit}>
        {successMessage && <div className="success-message">{successMessage}</div>}
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
          <label htmlFor="name">Имя:</label>
          <input
            type="text"
            id="name"
            value={name}
            onChange={(e) => setName(e.target.value)}
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
        <button type="submit">Зарегистрироваться</button>
      </form>
    </div>
  );
};

export default Register;