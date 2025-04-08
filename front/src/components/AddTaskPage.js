import React, { useState, useRef, useEffect } from "react";
import { DateTime } from 'luxon';

const AddTaskPage = ({
    addTask,
    setNewTaskText,
    newTaskText,
    toggleDate,
    toggleTime,
    setToggleDate,
    setToggleTime,
    dateTime,
    setDateTime,
}) => {
    const [error, setError] = useState("");
    const textareaRef = useRef(null);

    const getOmskDate = () => {
        const omskDate = DateTime.now().setZone('Asia/Omsk');
        return omskDate.toISODate();
    };

    const defaultDate = getOmskDate();
    const defaultTime = "00:00";

    useEffect(() => {
        // Сброс состояния 
        return () => {
            setNewTaskText("");
            setToggleDate(false);
            setToggleTime(false);
            setDateTime({ date: defaultDate, time: defaultTime });
            setError("");
        };
    }, []);

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
    }, [dateTime, defaultDate, defaultTime, setDateTime]);

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

    // Обработка нажатия кнопки "Добавить"
    const handleAddClick = (e) => {
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

        const newTask = {
            text: newTaskText,
            completed: false,
            dateTime: dateTime,
        };

        addTask(newTask);

        setNewTaskText("");
        setToggleDate(false);
        setToggleTime(false);
        setDateTime({ date: defaultDate, time: defaultTime });
        setError("");
        textareaRef.current.style.height = 'auto'; // Сбрасываем высоту после добавления задачи
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

    return (
        <div className="add-task-page">
            <h2>Добавление задачи</h2>
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
                <button onClick={handleAddClick}>Добавить</button>
            </div>
        </div>
    );
};

export default AddTaskPage;