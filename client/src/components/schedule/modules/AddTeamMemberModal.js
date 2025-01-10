import React, { useState } from 'react';
import '../../styles/schedule/AddTeamMemberModal.css'; // Подключаем стили

function AddTeamMemberModal({ roles, workers, onSave, onClose, initialTeam = [] }) {
    const [teamMembers, setTeamMembers] = useState(initialTeam);
    const [selectedRoleId, setSelectedRoleId] = useState('');
    const [selectedWorkerId, setSelectedWorkerId] = useState('');

    // Убедимся, что все ID числовые
    const parsedRoles = roles.map((role) => ({
        ...role,
        roleId: Number(role.roleId),
    }));

    const parsedWorkers = workers.map((worker) => ({
        ...worker,
        workerId: Number(worker.workerId),
    }));

    const handleAddMember = () => {
        if (selectedRoleId && selectedWorkerId) {
            const isDuplicate = teamMembers.some(
                (member) => member.roleId === Number(selectedRoleId) && member.workerId === Number(selectedWorkerId)
            );

            if (isDuplicate) {
                alert('Этот сотрудник уже добавлен на эту должность.');
                return;
            }

            const newMember = {
                roleId: Number(selectedRoleId),
                workerId: Number(selectedWorkerId),
            };

            setTeamMembers([...teamMembers, newMember]);
            setSelectedRoleId('');
            setSelectedWorkerId('');
        } else {
            alert('Пожалуйста, выберите и должность, и сотрудника.');
        }
    };

    const handleRemoveMember = (index) => {
        setTeamMembers(teamMembers.filter((_, i) => i !== index));
    };

    const handleSave = () => {
        onSave(teamMembers);
        onClose();
    };

    return (
        <div className="modal-overlay">
            <div className="modal-content">
                <button className="modal-closeButton" onClick={onClose}>
                    &times;
                </button>
                <h2 className="modal-title">Добавить команду</h2>
                <div className="modal-row">
                    <select
                        value={selectedRoleId}
                        onChange={(e) => setSelectedRoleId(e.target.value)}
                        className="modal-select"
                    >
                        <option value="">Выберите должность</option>
                        {parsedRoles.map((role) => (
                            <option key={role.roleId} value={role.roleId}>
                                {role.roleName}
                            </option>
                        ))}
                    </select>
                    <select
                        value={selectedWorkerId}
                        onChange={(e) => setSelectedWorkerId(e.target.value)}
                        className="modal-select"
                    >
                        <option value="">Выберите сотрудника</option>
                        {parsedWorkers.map((worker) => (
                            <option key={worker.workerId} value={worker.workerId}>
                                {worker.firstName} {worker.lastName}
                            </option>
                        ))}
                    </select>
                    <button onClick={handleAddMember} className="modal-addButton">
                        Добавить
                    </button>
                </div>

                <ul className="modal-teamList">
                    {teamMembers.map((member, index) => {
                        const role = parsedRoles.find((r) => r.roleId === member.roleId);
                        const worker = parsedWorkers.find((w) => w.workerId === member.workerId);
                        return (
                            <li key={index} className="modal-teamListItem">
                                <span className="modal-role">{role ? role.roleName : 'Неизвестная должность'}</span>
                                {'  '}
                                <span className="modal-worker">
                                    {worker ? `${worker.firstName} ${worker.lastName}` : 'Неизвестный сотрудник'}
                                </span>
                                <button
                                    onClick={() => handleRemoveMember(index)}
                                    className="modal-removeButton"
                                >
                                    Удалить
                                </button>
                            </li>
                        );
                    })}
                </ul>

                <div className="modal-footer">
                    <button onClick={handleSave} className="modal-saveButton">
                        Сохранить
                    </button>
                    <button onClick={onClose} className="modal-cancelButton">
                        Отменить
                    </button>
                </div>
            </div>
        </div>
    );
}

export default AddTeamMemberModal;
