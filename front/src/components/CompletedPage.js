import React, { useState, useEffect } from "react";

const CompletedPage = ({ tasks, clearCompleted, returnTask, deleteTask, isLoading, fetchTasks }) => {
    //Состояние для текущей страницы пагинации.
    const [currentPage, setCurrentPage] = useState(1);
    //Ограничение на количество задач на странице пагинации
    const tasksPerPage = 3;
    const [visibleTasks, setVisibleTasks] = useState([]);

    useEffect(() => {
        // Загружаем данные 
        fetchTasks('completed');
    }, [fetchTasks]);

    useEffect(() => {
        // Обновляем visibleTasks при изменении tasks
        setVisibleTasks(tasks || []);
    }, [tasks]);

    //Индекс последней задачи на текущей странице.
    const indexOfLastTask = currentPage * tasksPerPage;
    //Индекс первой задачи на текущей странице
    const indexOfFirstTask = indexOfLastTask - tasksPerPage;
    //Выбираются задачи, которые будут отображаться на страницу
    const currentTasks = visibleTasks.slice(indexOfFirstTask, indexOfLastTask);

    // Переход на следующую страницу
    const nextPage = () => {
        if (indexOfLastTask < visibleTasks.length) {
            setCurrentPage(currentPage + 1);
        }
    };

    // Переход на предыдущую страницу
    const prevPage = () => {
        if (currentPage > 1) {
            setCurrentPage(currentPage - 1);
        }
    };

    const returnTaskHandler = (taskId) => {
        returnTask(taskId);
    };

    const deleteTaskHandler = (taskId) => {
        deleteTask(taskId);
    };

    return (
        <div className="task-list-page">
            <h2>Завершенные задачи</h2>
            {isLoading ? (
                <p>Загрузка...</p>
            ) : (
                <>
                    {visibleTasks.length === 0 ? (
                        <div className="empty-list">
                            <p>Нет завершенных задач</p>
                        </div>
                    ) : (
                        currentTasks.map((task) => (
                            <div key={task.id} className="task-item completed">
                                <div className="task-text">
                                    {task.title}
                                </div>
                                <div className="dateTime">
                                    {task.date && task.time && (
                                        <span>
                                            <span>Запланировано: {task.date} {task.time}</span>
                                            <br />
                                            <span>Выполнено: {task.completeDate} {task.completeTime}</span>
                                        </span>
                                    )}
                                </div>
                                <div className="task-actions">
                                    <button onClick={() => returnTaskHandler(task.id)} className="button">
                                        Вернуть
                                    </button>
                                    <button onClick={() => deleteTaskHandler(task.id)} className="button">
                                        Удалить
                                    </button>
                                </div>
                            </div>
                        ))
                    )}
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
                    {visibleTasks.length > 0 && (
                        <button className="clear-completed" onClick={clearCompleted}>
                            Очистить завершенные
                        </button>
                    )}
                </>
            )}
        </div>
    );
};

export default CompletedPage;