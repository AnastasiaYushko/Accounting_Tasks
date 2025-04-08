import React, { useState, useEffect } from "react";
import { useLocation, useNavigate } from 'react-router-dom';

const TodayPage = ({ tasks, toggleComplete, userId, isLoading, fetchTasks, deleteTask }) => {
  const [currentPage, setCurrentPage] = useState(1);
  const [visibleTasks, setVisibleTasks] = useState([]);
  const [isTaskBeingCompleted, setIsTaskBeingCompleted] = useState(false);
  const [editingTaskId, setEditingTaskId] = useState(null);

  const tasksPerPage = 3;
  const navigate = useNavigate();
  const location = useLocation();

  useEffect(() => {
    // Загружаем данные
    fetchTasks('today')
  }, [fetchTasks]);

  useEffect(() => {
    // Обновляем visibleTasks при изменении tasks
    setVisibleTasks(tasks || []);
  }, [tasks]);

  const handleCompleteWithDelay = (taskId) => {
    setIsTaskBeingCompleted(true);

    // Обновляем локальное состояние, чтобы показать, что задача в процессе завершения
    setVisibleTasks((prevTasks) =>
      prevTasks.map((task) =>
        task.id === taskId ? { ...task, isMarkingComplete: true } : task
      )
    );

    // Через 2 секунды переводим задачу в completed
    setTimeout(() => {
      // Обновляем глобальное состояние задачи
      toggleComplete(taskId, 'today');

      // Фильтруем завершенные задачи из списка для отображения
      setVisibleTasks((prevTasks) =>
        prevTasks.filter((task) => task.id !== taskId) //  Убираем завершенную задачу из списка
      );

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
    if (currentPage > 1 && currentTasks.length === 0) {
      setCurrentPage(currentPage - 1);
    }
  }, [currentTasks, currentPage]);

  const deleteTaskHandler = (taskId) => {
    deleteTask(taskId);
  };
  const handleEditClick = (task) => {
    setEditingTaskId(task.id);
    sessionStorage.setItem('previousPath', location.pathname);
    navigate('/edit-task', { state: { taskToEdit: task, from: location.pathname } });
  };


  return (
    <div className="task-list">
      <h2>Сегодня</h2>
      {isLoading ? (
        <p>Загрузка...</p>
      ) : (
        <>
          {visibleTasks && visibleTasks.length === 0 ? (
            <div className="empty-list-all">
              <p>Задач на сегодня нет</p>
            </div>
          ) : (
            currentTasks.map((task) => (
              <div
                key={task.id}
                className={`task-item ${task.isMarkingComplete ? "completed" : ""}`}
              >
                <div
                  className={`task-text ${task.isMarkingComplete ? "completed" : ""}`}
                >
                  {task.title} {}
                </div>
                <div
                  className={`dateTime ${task.isMarkingComplete ? "completed" : ""}`}
                >
                  {task.date && task.time && (
                    <span>
                      Выполнить: {task.date} {task.time} { }
                    </span>
                  )}
                </div>
                <div className="task-actions">
                  <button onClick={() => handleEditClick(task)} className="button">
                    Изменить
                  </button>
                  <button onClick={() => deleteTaskHandler(task.id)} className="button">
                    Удалить
                  </button>
                  <button
                    onClick={() => handleCompleteWithDelay(task.id)}
                    disabled={task.isMarkingComplete || isTaskBeingCompleted} // Блокируем кнопки
                    className={task.isMarkingComplete || isTaskBeingCompleted ? "disabled" : ""}
                  >
                    {task.isMarkingComplete ? "Завершается..." : "Завершить"}
                  </button>
                </div>
              </div>
            ))
          )}

          {/* Кнопки для пагинации */}
          {visibleTasks && visibleTasks.length > tasksPerPage && (
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

export default TodayPage;