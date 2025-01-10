import React, { useState, useEffect } from 'react';
import '../../styles/schedule/AssignRoleModal.css';

function AssignRoleModal({ roles, workers, onAssign, onClose, selectedAssignments }) {
    const [localAssignments, setLocalAssignments] = useState({});

    // Инициализируем localAssignments значениями, переданными через пропс
    useEffect(() => {
        setLocalAssignments(selectedAssignments);
    }, [selectedAssignments]);

    // Обработчик изменения выбранного работника для каждой роли
    const handleWorkerChange = (roleId, workerId) => {
        setLocalAssignments(prevAssignments => ({
            ...prevAssignments,
            [roleId]: workerId,
        }));
    };

    // Сохраняем все изменения
    const handleSave = () => {
        const updatedAssignments = Object.keys(localAssignments).map(roleId => ({
            roleId: Number(roleId),  // Преобразуем roleId в число (если нужно)
            workerId: Number(localAssignments[roleId]),  // Преобразуем workerId в число (если нужно)
        }));

        onAssign(updatedAssignments);  // Передаем массив назначений в родительский компонент
        onClose();
    };


    return (
        <div className="modal">
            <div className="modal-header">
                <h2>Назначить роль</h2>
                <button onClick={onClose} className="modal-closeButton">×</button>
            </div>
            <div className="modal-body">
                <div className="roles-workers-container">
                    {roles.map(role => (
                        <div key={role.roleId} className="roles-workers-row">
                            <div className="role-item">{role.roleName}</div>
                            <div className="worker-item">
                                <select
                                    onChange={e => handleWorkerChange(role.roleId, e.target.value)}
                                    className="worker-select"
                                    value={localAssignments[role.roleId] || ''}  // Тут сохраняется выбранный работник для каждой роли
                                >
                                    <option value="">Выберите сотрудника</option>
                                    {workers.map(worker => (
                                        <option key={worker.workerId} value={worker.workerId}>
                                            {worker.firstName} {worker.lastName}
                                        </option>
                                    ))}
                                </select>
                            </div>
                        </div>
                    ))}
                </div>
            </div>
            <div className="modal-actions">
                <button onClick={handleSave} className="modal-saveButton">
                    Сохранить
                </button>
            </div>
        </div>
    );
}

export default AssignRoleModal;
