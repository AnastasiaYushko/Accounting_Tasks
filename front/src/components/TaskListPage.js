import React, { useState, useEffect } from "react";
import { isPast } from 'date-fns';
import { useLocation, useNavigate } from 'react-router-dom'; 

const TaskListPage = ({ tasks, toggleComplete, filter, isLoading, fetchTasks, deleteTask }) => { 
    //Текущая страница для пагинации
    const [currentPage, setCurrentPage] = useState(1);
    //Массив задач, которые должны отображаться на текущей странице.
    const [visibleTasks, setVisibleTasks] = useState([]);
    //Происходит ли завершение задачи. Это нужно для блокировки кнопок.
    const [isTaskBeingCompleted, setIsTaskBeingCompleted] = useState(false);
    const [editingTaskId, setEditingTaskId] = useState(null);

    const tasksPerPage = 3;
    const navigate = useNavigate();
    const location = useLocation();

    useEffect(() => {
        fetchTasks('all')
    }, [fetchTasks]);

    useEffect(() => {
        // Обновляем visibleTasks при изменении tasks
        setVisibleTasks(tasks || []);
    }, [tasks]);

    // Завершение задачи с задержкой
    const handleCompleteWithDelay = (taskId) => {
        // Устанавливаем состояние, что задача завершена
        setIsTaskBeingCompleted(true);

        setVisibleTasks((prevTasks) =>
            prevTasks.map((task) =>
                task.id === taskId ? { ...task, isMarkingComplete: true } : task
            )
        );

        // Через 2 секунд переводим задачу в completed
        setTimeout(() => {
            toggleComplete(taskId, 'all'); // Обновляем состояние задачи глобально
            setIsTaskBeingCompleted(false); // Сбрасываем состояние завершения
        }, 2000);
    };

    // Пагинация
    const indexOfLastTask = currentPage * tasksPerPage;
    const indexOfFirstTask = indexOfLastTask - tasksPerPage;
    const currentTasks = visibleTasks.slice(indexOfFirstTask, indexOfLastTask);

    const nextPage = () => {
        if (indexOfLastTask < visibleTasks.length) {
            setCurrentPage(currentPage + 1);
        }
    };

    const prevPage = () => {
        if (currentPage > 1) {
            setCurrentPage(currentPage - 1);
        }
    };

    // Эффект для корректной работы пагинации после удаления задачи
    useEffect(() => {
        // Если на текущей странице нет задач, то переходим на предыдущую
        if (currentPage > 1 && currentTasks.length === 0) {
            setCurrentPage(currentPage - 1);
        }
    }, [currentTasks, currentPage]);

    const deleteTaskHandler = (taskId) => {
        deleteTask(taskId);
    };

    const getDateTime = (task) => {
        if (!task.date || !task.time) {
            return null;
        }
        const [year, month, day] = task.date.split('-').map(Number);
        const [hours, minutes] = task.time.split(':').map(Number);
        return new Date(year, month - 1, day, hours, minutes);
    };

    const handleEditClick = (task) => {
        setEditingTaskId(task.id);
        sessionStorage.setItem('previousPath', location.pathname);
        navigate('/edit-task', { state: { taskToEdit: task, from: location.pathname } });
    };

    return (
        <div className="task-list">
            <h2>{filter === "completed" ? "Завершенные задачи" : "Все задачи"}</h2>
            {isLoading ? (
                <p>Загрузка...</p>
            ) : (
                <>
                    {visibleTasks.length === 0 ? (
                        <div className="empty-list-all">
                            <p>Список задач пуст</p>
                        </div>
                    ) : (
                        currentTasks.map((task) => (
                            <div
                                key={task.id}
                                className={`task-item ${task.isMarkingComplete ? "completed" : ""} ${getDateTime(task) && isPast(getDateTime(task)) ? "overdue" : ""
                                    }`}
                            >
                                <div
                                    className={`task-text ${task.isMarkingComplete ? "completed" : ""}`}
                                >
                                    {task.title} { }
                                </div>
                                <div
                                    className={`dateTime ${task.isMarkingComplete ? "completed" : ""}`}
                                >
                                    {task.date && task.time && (
                                        <span>
                                            Выполнить: {task.date} {task.time}
                                        </span>
                                    )}
                                </div>
                                <div className="task-actions">
                                    <button
                                        onClick={() => handleEditClick(task)}
                                        className={`button ${getDateTime(task) && isPast(getDateTime(task)) ? "overdue-button" : ""}`}
                                    >
                                        Изменить
                                    </button>
                                    <button
                                        onClick={() => deleteTaskHandler(task.id)}
                                        className={`button ${getDateTime(task) && isPast(getDateTime(task)) ? "overdue-button" : ""}`}
                                    >
                                        Удалить
                                    </button>
                                    <button
                                        onClick={() => handleCompleteWithDelay(task.id)}
                                        disabled={task.isMarkingComplete || isTaskBeingCompleted}
                                        className={`button ${task.isMarkingComplete || isTaskBeingCompleted ? "disabled" : ""} ${getDateTime(task) && isPast(getDateTime(task)) ? "overdue-button" : ""}`}
                                    >
                                        {task.isMarkingComplete ? "Завершается..." : "Завершить"}
                                    </button>
                                </div>
                            </div>
                        ))
                    )}
                    {/* Кнопки для пагинации */}
                    {visibleTasks.length > tasksPerPage && (
                        <div className="pagination">
                            <button onClick={prevPage} disabled={currentPage === 1}>
                                Назад
                            </button>
                            <button
                                onClick={nextPage}
                                disabled={indexOfLastTask >= visibleTasks.length}
                            >
                                Вперед
                            </button>
                        </div>
                    )}
                </>
            )}
        </div>
    );
};

export default TaskListPage;