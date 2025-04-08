import React, { useState, useRef, useEffect } from "react";
import { DateTime } from 'luxon';
import { useLocation, useNavigate } from 'react-router-dom';

const EditTaskPage = ({ handlerChangeTask }) => {
    const location = useLocation();
    const task = location.state?.taskToEdit;
    const [error, setError] = useState("");
    const textareaRef = useRef(null);
    const [newTaskText, setNewTaskText] = useState(task?.title || "");
    const [toggleDate, setToggleDate] = useState(!!task?.date);
    const [toggleTime, setToggleTime] = useState(!!task?.time);
    const [dateTime, setDateTime] = useState({ date: task?.date || '', time: task?.time || '' });

    const getOmskDate = () => {
        const omskDate = DateTime.now().setZone('Asia/Omsk');
        return omskDate.toISODate();
    };

    const defaultDate = getOmskDate();
    const defaultTime = "00:00";
    const navigate = useNavigate();

    useEffect(() => {
        const task = location.state?.taskToEdit;
        if (task) {
            setNewTaskText(task.title || "");
            setToggleDate(!!task.date);
            setToggleTime(!!task.time);
            setDateTime({ date: task.date || '', time: task.time || '' });
        }
    }, [task]);

    useEffect(() => {
        if (!dateTime) {
            setDateTime({ date: defaultDate, time: defaultTime });
        } else {
            if (!dateTime.date) {
                setDateTime(prev => ({ ...prev, date: defaultDate }));
            }
            if (!dateTime.time) {
                setDateTime(prev => ({ ...prev, time: defaultTime }));
            }
        }
    }, [dateTime, defaultDate, defaultTime, setNewTaskText, setDateTime]);

    // Функция для автоматического изменения высоты textarea
    const autoResize = () => {
        textareaRef.current.style.height = 'auto'; // Сбрасываем высоту
        textareaRef.current.style.height = textareaRef.current.scrollHeight + 'px'; // Устанавливаем новую высоту
    };

    // Обработчик изменения текста
    const handleTextChange = (e) => {
        setNewTaskText(e.target.value);
        autoResize(); // Вызываем функцию изменения размера
    };

    // Обработка нажатия кнопки "Изменить"
    const handleEditClick = (e) => {
        e.preventDefault();
        setError("");

        if (!newTaskText.trim()) {
            setError("Ошибка: Введите текст задачи!");
            return;
        }

        const currentDate = new Date();
        const selectedDate = new Date(dateTime.date);

        const omtskDate = new Date(
            selectedDate.toLocaleString("en-US", { timeZone: "Asia/Omsk" })
        );

        if (omtskDate < currentDate.setHours(0, 0, 0, 0)) {
            setError("Ошибка: Дата не может быть в прошлом!");
            return;
        }

        if (toggleDate && toggleTime && dateTime.date && dateTime.time) {
            const [selectedHours, selectedMinutes] = dateTime.time.split(":");
            const selectedTime = new Date(
                selectedDate.getFullYear(),
                selectedDate.getMonth(),
                selectedDate.getDate(),
                selectedHours,
                selectedMinutes,
                0,
                0
            );

            if (selectedTime < currentDate) {
                setError("Ошибка: Время не может быть в прошлом!");
                return;
            }
        }
        const task = location.state?.taskToEdit;
        const updatedTask = {
            ...task, // Сохраняем существующие свойства задачи
            title: newTaskText, // Обновляем название
            date: toggleDate ? dateTime.date : null,
            time: toggleTime ? dateTime.time : null,
        };
        handlerChangeTask(updatedTask);

        setNewTaskText("");
        setToggleDate(false);
        setToggleTime(false);
        setDateTime({ date: '', time: '' });
        setError("");

        const from = location.state?.from || '/task-list';
        navigate(from, { replace: true });
    };

    const handleToggleDate = () => {
        setToggleDate(!toggleDate);

        if (!toggleDate) {
            setDateTime((prev) => ({ ...prev, date: defaultDate }));
        }

        if (toggleDate) {
            setToggleTime(false);
            setDateTime((prev) => ({ ...prev, time: defaultTime }));
        }
    };

    const handleToggleTime = () => {
        if (!toggleTime) {
            setDateTime((prev) => ({ ...prev, time: defaultTime }));
        }
        setToggleTime(!toggleTime);
    };

    const handleCloseClick = () => {
        const previousPath = sessionStorage.getItem('previousPath') || '/task-list';
        sessionStorage.removeItem('previousPath');
        navigate(previousPath, { replace: true });
    };

    return (
        <div className="modal">
            <div className="modal-content">
                <h2>Изменение задачи</h2>
                <br></br>
                <div className="add-task-page">
                    <span className="close" onClick={handleCloseClick}> { }
                        &times; { }
                    </span>
                    <div className="textarea-container">
                        <label htmlFor="taskText" className="textarea-label" style={{ fontSize: '18px' }}>Введите задачу:</label>
                        <textarea
                            id="taskText"
                            ref={textareaRef}
                            value={newTaskText}
                            onChange={handleTextChange}
                            placeholder="Мне нужно сделать..."
                            className="textarea-input"
                            maxLength="255"
                            spellCheck="false"
                        />
                    </div>
                    <div>
                        <label>Установить дату:</label>
                        <input
                            type="checkbox"
                            checked={toggleDate}
                            onChange={handleToggleDate}
                        />
                        {toggleDate && (
                            <input
                                type="date"
                                value={dateTime.date}
                                onChange={(e) => setDateTime({ ...dateTime, date: e.target.value })}
                            />
                        )}
                    </div>
                    <div>
                        <label>Установить время:</label>
                        <input
                            type="checkbox"
                            checked={toggleTime}
                            onChange={handleToggleTime}
                            disabled={!toggleDate}
                        />
                        {toggleTime && (
                            <input
                                type="time"
                                value={dateTime.time}
                                onChange={(e) => setDateTime({ ...dateTime, time: e.target.value })}
                            />
                        )}
                    </div>

                    {error && <div className="error-message">{error}</div>}

                    <div className="add-button-container">
                        <button onClick={handleEditClick}>Изменить</button>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default EditTaskPage;