import React, { useState, useEffect, useCallback } from 'react';
import { BrowserRouter as Router, Route, Routes, Link, Navigate, useLocation, useNavigate } from 'react-router-dom';
import axios from 'axios';
import { DateTime } from 'luxon';
import TodayPage from './components/TodayPage';
import TaskListPage from './components/TaskListPage';
import CompletedPage from './components/CompletedPage';
import AddTaskPage from './components/AddTaskPage';
import Login from './components/Login';
import Register from './components/Register';
import EditTaskPage from './components/EditTaskPage';

const App = () => {
    const [tasks, setTasks] = useState([]);
    const [newTaskText, setNewTaskText] = useState("");
    const [toggleTime, setToggleTime] = useState(false);
    const [toggleDate, setToggleDate] = useState(false);
    const [dateTime, setDateTime] = useState({ date: "", time: "" });

    // Состояние аутентификации
    const [isLoggedIn, setIsLoggedIn] = useState(false);
    const [userName, setUserName] = useState("");
    const [userId, setUserId] = useState("");

    // Состояние загрузки
    const [isLoading, setIsLoading] = useState(false);

    // Состояние диалогового окна
    const [isDeleteConfirmationOpen, setIsDeleteConfirmationOpen] = useState(false);

    // Функция для сохранения состояния аутентификации в Local Storage
    const saveAuthData = (userName, userId) => {
        localStorage.setItem('isLoggedIn', 'true');
        localStorage.setItem('userName', userName);
        localStorage.setItem('userId', userId.toString()); // Преобразуем в строку для хранения
    };

    // Функция для загрузки состояния аутентификации из Local Storage
    const loadAuthData = useCallback(async () => {
        const storedIsLoggedIn = localStorage.getItem('isLoggedIn');
        const storedUserName = localStorage.getItem('userName');
        const storedUserId = localStorage.getItem('userId'); // Получаем как строку

        if (storedIsLoggedIn === 'true' && storedUserName && storedUserId) {
            setUserName(storedUserName);
            setUserId(parseInt(storedUserId, 10)); // Преобразуем обратно в число
            setIsLoggedIn(true);
        }
    }, []);

    useEffect(() => {
        loadAuthData();
    }, [loadAuthData]);

    const addTask = async (newTask) => {
        try {
            const taskData = {
                user_id: userId,
                title: newTask.text,
                date: newTask.dateTime.date,
                time: newTask.dateTime.time
            };
            const response = await axios.post("http://localhost:8080/api/tasks/addTask", taskData);
            if (response.status !== 201) {
                console.error("Ошибка при добавлении задачи:", response);
                alert("Ошибка при добавлении задачи. Пожалуйста, попробуйте позже.");
            }
        } catch (error) {
            console.error("Ошибка при добавлении задачи:", error);
            alert("Ошибка при добавлении задачи. Пожалуйста, попробуйте позже.");
        }
    };

    const login = async (credentials) => {
        try {
            const response = await axios.post("http://localhost:8080/api/user/getUser", credentials);
            if (response.status === 200) {
                setUserName(response.data.name);
                setUserId(response.data.user_id);
                setIsLoggedIn(true);
                saveAuthData(response.data.name, response.data.user_id);  // Сохраняем данные после успешного входа
            } else {
                console.error("Ошибка входа:", response);
                throw new Error("Неожиданный код ответа: " + response.status);
            }
        } catch (error) {
            console.error("Ошибка входа:", error);
            throw error;
        }
    };

    const register = async (userData) => {
        try {
            const response = await axios.post("http://localhost:8080/api/user/addUser", userData);
            return response;
        } catch (error) {
            console.error("Ошибка регистрации:", error);
            throw error;
        }
    };

    // Выход
    const logout = () => {
        setIsLoggedIn(false);
        setUserName("");
        setUserId("");
        setTasks([]); // Очищаем список задач при выходе
        localStorage.removeItem('isLoggedIn');
        localStorage.removeItem('userName');
        localStorage.removeItem('userId');
    };

    // Функция открытия диалогового окна удаления пользователя
    const openDeleteConfirmation = () => {
        setIsDeleteConfirmationOpen(true);
    };

    // Функция закрытия диалогового окна удаления пользователя
    const closeDeleteConfirmation = () => {
        setIsDeleteConfirmationOpen(false);
    };
    // Функция удаления пользователя
    const deleteUser = async () => {
        try {
            //  Проверка наличия userId
            if (!userId) {
                console.error("Пользователь не найден");
                closeDeleteConfirmation(); // Закрываем диалоговое окно
                return;
            }
            const response = await axios.delete(`http://localhost:8080/api/user/deleteUser/${userId}`);

            if (response.status === 200) {
                //  Успешное удаление
                logout(); //  Очищаем данные пользователя
                closeDeleteConfirmation(); // Закрываем диалоговое окно
            } else {
                console.error("Ошибка удаления пользователя:", response);
                alert("Ошибка при удалении пользователя. Пожалуйста, попробуйте позже.");
                closeDeleteConfirmation(); //  Закрываем диалоговое окно в случае ошибки
            }
        } catch (error) {
            console.error("Ошибка удаления пользователя:", error);
            alert("Ошибка при удалении пользователя. Пожалуйста, попробуйте позже.");
            closeDeleteConfirmation();  // Закрываем диалоговое окно в случае ошибки
        }
    };

    // Удаление завершенных
    const clearCompleted = async () => {
        setIsLoading(true);
        try {
            // Отправляем запрос на сервер для удаления всех завершенных задач для текущего пользователя
            const response = await axios.delete(`http://localhost:8080/api/tasks/deleteCompletedTasks/${userId}`);

            if (response.status === 200) {
                // Обновляем состояние задач после успешного удаления
                fetchTasks('completed'); // Обновляем список завершенных задач
            } else {
                console.error("Ошибка при очистке завершенных задач:", response);
                alert("Ошибка при очистке завершенных задач. Пожалуйста, попробуйте позже.");
            }
        } catch (error) {
            console.error("Ошибка при очистке завершенных задач:", error);
            alert("Ошибка при очистке завершенных задач. Пожалуйста, попробуйте позже.");
        } finally {
            setIsLoading(false);
        }
    };

    // 3 tasks
    const fetchTasks = useCallback(async (category) => {
        if (!userId) {
            return; // Не делаем запрос, если userId не определен
        }
        setIsLoading(true); // Устанавливаем isLoading в true перед началом запроса
        try {
            let url;
            if (category === 'today') {
                url = `http://localhost:8080/api/tasks/getTodayTasks/${userId}`;
            } else if (category === 'completed') {
                url = `http://localhost:8080/api/tasks/getCompletedTasks/${userId}`;
            } else if (category === 'all') {
                url = `http://localhost:8080/api/tasks/getAllTasks/${userId}`;
            }
            const response = await axios.get(url);
            if (response.status === 200) {
                console.log("Data from server:", response.data);
                setTasks(response.data);
                console.log("Tasks state after set:", tasks);
            } else {
                console.error(`Ошибка при получении задач (${category || 'все'}):`, response);
                alert(`Ошибка при получении задач (${category || 'все'}). Пожалуйста, попробуйте позже.`);
            }
        } catch (error) {
            console.error(`Ошибка при получении задач (${category || 'все'}):`, error);
            alert(`Ошибка при получении задач (${category || 'все'}). Пожалуйста, попробуйте позже.`);
        } finally {
            setIsLoading(false); // Устанавливаем isLoading в false после завершения запроса
        }
    }, [userId]);

    // Функция для обновления статуса задачи (завершена/не завершена) и отправки запроса на сервер
    const toggleComplete = async (taskId, source) => {
        setIsLoading(true);
        try {
            // Отправляем PUT-запрос на сервер для обновления статуса задачи
            const response = await axios.put(`http://localhost:8080/api/tasks/completeTask/${taskId}/${userId}`);

            if (response.status === 200) {
                // Если запрос успешен, обновляем состояние задач
                setTasks((prevTasks) =>
                    prevTasks.map((task) =>
                        task.id === taskId ? { ...task, completed: !task.completed } : task
                    )
                );
            } else {
                console.error("Ошибка при обновлении статуса задачи:", response);
                alert("Ошибка при обновлении статуса задачи. Пожалуйста, попробуйте позже.");
            }
            fetchTasks(source);
        } catch (error) {
            console.error("Ошибка при обновлении статуса задачи:", error);
            alert("Ошибка при обновлении статуса задачи. Пожалуйста, попробуйте позже.");
        } finally {
            setIsLoading(false);
        }
    };

    // Возврат task из завершенного
    const returnTask = async (taskId) => {
        setIsLoading(true);
        try {
            const response = await axios.put(`http://localhost:8080/api/tasks/returnCompletedTask/${taskId}/${userId}`);

            if (response.status === 200) {
                fetchTasks('completed');
            } else {
                console.error("Ошибка при возврате задачи:", response);
                alert("Ошибка при возврате задачи. Пожалуйста, попробуйте позже.");
            }
        } catch (error) {
            console.error("Ошибка при возврате задачи:", error);
            alert("Ошибка при возврате задачи. Пожалуйста, попробуйте позже.");
        } finally {
            setIsLoading(false);
        }
    };

    // Удаление task
    const deleteTask = async (taskId) => {
        setIsLoading(true);
        try {
            const response = await axios.delete(`http://localhost:8080/api/tasks/deleteTask/${taskId}/${userId}`);

            if (response.status === 200) {
                fetchTasks('completed');
            } else {
                console.error("Ошибка при удалении задачи:", response);
                alert("Ошибка при удалении задачи. Пожалуйста, попробуйте позже.");
            }
        } catch (error) {
            console.error("Ошибка при удалении задачи:", error);
            alert("Ошибка при удалении задачи. Пожалуйста, попробуйте позже.");
        } finally {
            setIsLoading(false);
        }
    };

    const handlerChangeTask = (task) => {
        changeTask(task);
    }

    // Изменение task
    const changeTask = async (task) => {
        try {
            const requestBody = {
                user_id: userId,
                task_id: task.id,
                title: task.title,
                date: task.date,
                time: task.time
            };

            const response = await axios.put("http://localhost:8080/api/tasks/changeTask", requestBody);

            if (response.status === 200) {
                // Задача успешно изменена
                console.log("Задача успешно изменена:", response.data);
                fetchTasks('all'); // Обновляем список задач 
                alert("Задача успешно изменена!");
            }
        } catch (error) {
            if (error.response && error.response.status === 304) {
                console.error("Сначала внесите изменения");
                alert("Сначала внесите изменения");
            }
            else {// Обработка ошибок сети или ошибок на сервере
                console.error("Ошибка при изменении задачи:", error);
                alert("Ошибка при изменении задачи. Пожалуйста, попробуйте позже.");
            }
        }
    };

    return (
        <Router>
            <>
                {isLoggedIn && (
                    <div className="logout-container">
                        <button onClick={logout} className="logout-button">
                            Выйти
                        </button>
                        <button onClick={openDeleteConfirmation} className="delete-user-button">
                            Удалить пользователя
                        </button>
                    </div>
                )}

                {isDeleteConfirmationOpen && (
                    <>
                        <div className="overlay"></div>
                        <div className="confirmation-dialog">
                            <p>Вы уверены, что хотите удалить пользователя?</p>
                            <button onClick={deleteUser}>Да</button>
                            <button onClick={closeDeleteConfirmation}>Нет</button>
                        </div>
                    </>
                )}

                <div className="app">
                    <Routes>
                        { }
                        <Route
                            path="/login"
                            element={isLoggedIn ? <Navigate to="/" /> : <Login login={login} />}
                        />
                        <Route
                            path="/register"
                            element={isLoggedIn ? <Navigate to="/" /> : <Register register={register} />}
                        />
                        { }
                        <Route
                            path="/"
                            element={
                                isLoggedIn ? (
                                    <div className="dashboard-container">
                                        <h1>Учет домашних дел и задач</h1>
                                        <div className="task-categories">
                                            <Link to="/today">
                                                <button onClick={() => fetchTasks('today')}>Сегодня</button>
                                            </Link>
                                            <Link to="/task-list">
                                                <button onClick={() => fetchTasks('all')}>Все</button>
                                            </Link>
                                            <Link to="/completed">
                                                <button onClick={() => fetchTasks('completed')}>Завершенные</button>
                                            </Link>
                                        </div>
                                        <div className="add-task-button">
                                            <Link to="/add-task">
                                                <button>Добавить задачу</button>
                                            </Link>
                                        </div>
                                    </div>
                                ) : (
                                    <div className="auth-container"> { }
                                        <h1>Добро пожаловать!</h1>
                                        <Link to="/login">
                                            <button className="auth-button">Войти</button> { }
                                        </Link>
                                        <Link to="/register">
                                            <button className="auth-button">Регистрация</button> { }
                                        </Link>
                                    </div>
                                )
                            }
                        />
                        { }
                        <Route
                            path="/add-task"
                            element={
                                isLoggedIn ? (
                                    <AddTaskPage
                                        addTask={addTask}
                                        setNewTaskText={setNewTaskText}
                                        newTaskText={newTaskText}
                                        toggleDate={toggleDate}
                                        toggleTime={toggleTime}
                                        setToggleDate={setToggleDate}
                                        setToggleTime={setToggleTime}
                                        dateTime={dateTime}
                                        setDateTime={setDateTime}
                                    />
                                ) : (
                                    <Navigate to="/" />
                                )
                            }
                        />
                        <Route
                            path="/task-list"
                            element={
                                isLoggedIn ? (
                                    <TaskListPage
                                        tasks={tasks}
                                        toggleComplete={toggleComplete}
                                        isLoading={isLoading}
                                        fetchTasks={fetchTasks}
                                        deleteTask={deleteTask} />
                                ) : (
                                    <Navigate to="/" />
                                )
                            }
                        />
                        <Route
                            path="/edit-task"
                            element={
                                isLoggedIn ? (
                                    <EditTaskPage
                                        handlerChangeTask={handlerChangeTask}
                                    />
                                ) : (
                                    <Navigate to="/" />
                                )
                            }
                        />
                        <Route
                            path="/today"
                            element={
                                isLoggedIn ? (
                                    <TodayPage
                                        tasks={tasks}
                                        toggleComplete={toggleComplete}
                                        userId={userId}
                                        isLoading={isLoading}
                                        fetchTasks={fetchTasks}
                                        deleteTask={deleteTask} />
                                ) : (
                                    <Navigate to="/" />
                                )
                            }
                        />
                        <Route
                            path="/completed"
                            element={
                                isLoggedIn ? (
                                    <CompletedPage
                                        tasks={tasks}
                                        clearCompleted={clearCompleted}
                                        returnTask={returnTask}
                                        deleteTask={deleteTask}
                                        isLoading={isLoading}
                                        fetchTasks={fetchTasks}
                                    />
                                ) : (
                                    <Navigate to="/" />
                                )
                            }
                        />
                    </Routes>
                </div>
            </>
        </Router>
    );
};

export default App;