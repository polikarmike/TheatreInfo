import React, { useState, useEffect } from 'react';
import { useParams } from 'react-router-dom';
import '../../styles/schedule/ViewSchedulePage.css';

function ViewSchedulePage() {
    const { scheduleId } = useParams();
    const [schedule, setSchedule] = useState(null);
    const [teamMembers, setTeamMembers] = useState([]);
    const [roleAssignments, setRoleAssignments] = useState([]);

    useEffect(() => {
        const fetchData = async () => {
            await fetchSchedule();
            await fetchProductionTeam();
            await fetchRoleAssignments();
        };

        fetchData();
    }, []);

    const fetchSchedule = async () => {
        try {
            const response = await fetch(`/api/schedules/${scheduleId}`);
            const data = await response.json();
            setSchedule(data);
        } catch (error) {
            console.error('Ошибка при получении расписания:', error);
        }
    };

    const fetchProductionTeam = async () => {
        try {
            const response = await fetch(`/api/schedules/${scheduleId}/production-team`);
            const data = await response.json();
            setTeamMembers(data);
        } catch (error) {
            console.error('Ошибка при получении команды:', error);
        }
    };

    const fetchRoleAssignments = async () => {
        try {
            const response = await fetch(`/api/schedules/${scheduleId}/role-assignments`);
            const data = await response.json();
            setRoleAssignments(data);
        } catch (error) {
            console.error('Ошибка при получении назначений ролей:', error);
        }
    };

    if (!schedule) {
        return <div>Загрузка...</div>;
    }

    const { performance, showDate, showTime, hall } = schedule;
    const { title, description, duration, posterUrl, genre } = performance;

    return (
        <div className="viewSchedulePage-container">
            <h1 className="viewSchedulePage-title">{title}</h1>

            {/* Спектакль */}
            <div className="viewSchedulePage-details">
                <div className="viewSchedulePage-poster">
                    <img src={posterUrl} alt={title} className="viewSchedulePage-posterImage" />
                </div>
                <div className="viewSchedulePage-info">
                    <p className="viewSchedulePage-description">{description}</p>
                    <p><strong>Продолжительность:</strong> {duration} мин</p>
                    <p><strong>Жанр:</strong> {genre.genreName}</p>
                    <p><strong>Дата:</strong> {showDate}</p>
                    <p><strong>Время:</strong> {showTime}</p>
                    <p><strong>Зал:</strong> {hall.hallName}</p>
                </div>
            </div>

            <div className="viewSchedulePage-segment">
                {/* Команда постановщиков */}
                <div className="viewSchedulePage-productionTeam">
                    <h3>Команда постановщиков</h3>
                    <div className="viewSchedulePage-cards">
                        {teamMembers.map((member) => (
                            <div key={member.teamId} className="viewSchedulePage-card">
                                <img src={member.worker.photoUrl} alt={member.worker.firstName} className="viewSchedulePage-cardImage" />
                                <div className="viewSchedulePage-cardContent">
                                    <h4>{member.worker.firstName} {member.worker.lastName}</h4>
                                    <p><strong>Роль:</strong> {member.role.roleName}</p>
                                    <p><strong>Биография:</strong> {member.worker.biography}</p>
                                </div>
                            </div>
                        ))}
                    </div>
                </div>

                {/* Роли и актёры */}
                <div className="viewSchedulePage-roleAssignments">
                    <h3>Роли и актёры</h3>
                    <div className="viewSchedulePage-cards">
                        {roleAssignments.map((assignment) => (
                            <div key={assignment.assignmentId} className="viewSchedulePage-card">
                                <img src={assignment.worker.photoUrl} alt={assignment.worker.firstName} className="viewSchedulePage-cardImage" />
                                <div className="viewSchedulePage-cardContent">
                                    <h4>{assignment.worker.firstName} {assignment.worker.lastName}</h4>
                                    <p><strong>Роль:</strong> {assignment.role.roleName}</p>
                                    <p><strong>Биография:</strong> {assignment.worker.biography}</p>
                                </div>
                            </div>
                        ))}
                    </div>
                </div>
            </div>
        </div>
    );
}

export default ViewSchedulePage;
