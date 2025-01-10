import React, { useState, useEffect } from 'react';
import { Link } from 'react-router-dom';
import '../WorkerPage.css'; // Импортируем CSS файл

function WorkerPage() {
    const [actors, setActors] = useState([]); // Состояние для списка актёров
    const [searchQuery, setSearchQuery] = useState(''); // Состояние для поиска

    // Получение списка актёров с сервера
    useEffect(() => {
        fetchActors();
    }, []);

    const fetchActors = async () => {
        try {
            const response = await fetch('/api/worker'); // URL замените на соответствующий
            const data = await response.json();
            console.log(data);
            setActors(data);
        } catch (error) {
            console.error('Ошибка при получении списка актёров:', error);
        }
    };

    // Обработчик поиска
    const handleSearchChange = (event) => {
        setSearchQuery(event.target.value);
    };

    // Фильтрация актёров на основе поиска
    const filteredActors = actors.filter((actor) =>
        `${actor.first_name} ${actor.last_name}`
            .toLowerCase()
            .includes(searchQuery.toLowerCase())
    );

    return (
        <div className="container">
            <h1 className="title">Список актёров</h1>
            <div className="controls">
                <input
                    type="text"
                    placeholder="Поиск по имени"
                    value={searchQuery}
                    onChange={handleSearchChange}
                    className="searchBox"
                />
                <button className="addButton">
                    <Link to="/add-actor" className="linkButton">
                        Добавить нового актёра
                    </Link>
                </button>
            </div>
            <ul className="actorList">
                {filteredActors.map((actor) => (
                    <li key={actor.workerId} className="actorItem">
                        <img src={actor.photoUrl} alt="Фото" className="photo" />
                        <div className="actorInfo">
                            <h3 className="actorName">{actor.firstName} {actor.lastName}</h3>
                            <p className="actorBio"><strong>Биография:</strong> {actor.biography}</p>
                            {actor.role && <p className="actorRole"><strong>Роль:</strong> {actor.role.roleName}</p>}
                        </div>
                    </li>
                ))}
            </ul>
        </div>
    );
}

export default WorkerPage;
