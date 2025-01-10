import React, { useState, useEffect } from 'react';

function AddWorkerPage() {
    const [firstName, setFirstName] = useState('');
    const [lastName, setLastName] = useState('');
    const [biography, setBiography] = useState('');
    const [photoFile, setPhotoFile] = useState(null);  // Для хранения выбранного файла
    const [roleId, setRoleId] = useState('');
    const [roles, setRoles] = useState([]); // Список ролей

    // Получаем список ролей при загрузке страницы
    useEffect(() => {
        fetchRoles();
    }, []);

    const fetchRoles = async () => {
        try {
            const response = await fetch('/api/roles');
            const data = await response.json();
            const rolesArray = data.roles || data;  // data.roles если это объект, иначе data
            console.log("Привет");
            console.log(data);  // Временный вывод данных в консоль
            setRoles(rolesArray);
        } catch (error) {
            console.error('Ошибка при получении списка ролей:', error);
        }
    };

    const handleSubmit = async (e) => {
        e.preventDefault();

        // Создаем объект FormData для отправки файла и других данных
        const formData = new FormData();
        formData.append('firstName', firstName);
        formData.append('lastName', lastName);
        formData.append('biography', biography);
        formData.append('roleId', roleId);

        if (photoFile) {
            formData.append('photo', photoFile);  // Добавляем файл
        }

        try {
            const response = await fetch('/api/worker', {
                method: 'POST',
                body: formData,  // Отправляем FormData
            });

            if (response.ok) {
                alert('Сотрудник успешно добавлен!');
                // Очистка полей формы после успешного добавления
                setFirstName('');
                setLastName('');
                setBiography('');
                setPhotoFile(null);
                setRoleId('');
            } else {
                alert('Ошибка при добавлении сотрудника');
            }
        } catch (error) {
            console.error('Ошибка при добавлении сотрудника:', error);
            alert('Ошибка при добавлении сотрудника');
        }
    };

    return (
        <div style={styles.container}>
            <h1 style={styles.title}>Добавить нового сотрудника</h1>
            <form onSubmit={handleSubmit} style={styles.form}>
                <label style={styles.label}>
                    Имя:
                    <input
                        type="text"
                        value={firstName}
                        onChange={(e) => setFirstName(e.target.value)}
                        required
                        style={styles.input}
                    />
                </label>
                <label style={styles.label}>
                    Фамилия:
                    <input
                        type="text"
                        value={lastName}
                        onChange={(e) => setLastName(e.target.value)}
                        required
                        style={styles.input}
                    />
                </label>
                <label style={styles.label}>
                    Роль:
                    <select
                        value={roleId}
                        onChange={(e) => setRoleId(e.target.value)}
                        required
                        style={styles.select}
                    >
                        <option value="">Выберите роль</option>
                        {roles.map((role) => (
                            <option key={role.roleId} value={role.roleId}>
                                {role.roleName} {/* Отображаем только название роли */}
                            </option>
                        ))}
                    </select>
                </label>
                <label style={styles.label}>
                    Биография:
                    <textarea
                        value={biography}
                        onChange={(e) => setBiography(e.target.value)}
                        style={styles.textarea}
                    />
                </label>
                <label style={styles.label}>
                    Фото:
                    <input
                        type="file"
                        accept="image/*"  // Ограничиваем выбор только изображениями
                        onChange={(e) => setPhotoFile(e.target.files[0])}  // Сохраняем файл
                        style={styles.input}
                    />
                </label>

                <button type="submit" style={styles.submitButton}>Добавить актёра</button>
            </form>
        </div>
    );
}

const styles = {
    container: {
        maxWidth: '500px',
        margin: '0 auto',
        padding: '20px',
        backgroundColor: '#f4f4f4',
        borderRadius: '10px',
        boxShadow: '0 0 10px rgba(0, 0, 0, 0.1)',
    },
    title: {
        textAlign: 'center',
        fontSize: '24px',
        marginBottom: '20px',
    },
    form: {
        display: 'flex',
        flexDirection: 'column',
    },
    label: {
        marginBottom: '15px',
    },
    input: {
        padding: '10px',
        fontSize: '16px',
        borderRadius: '5px',
        border: '1px solid #ccc',
        width: '100%',
        boxSizing: 'border-box',
    },
    textarea: {
        padding: '10px',
        fontSize: '16px',
        borderRadius: '5px',
        border: '1px solid #ccc',
        width: '100%',
        height: '80px',
        boxSizing: 'border-box',
    },
    select: {
        padding: '10px',
        fontSize: '16px',
        borderRadius: '5px',
        border: '1px solid #ccc',
        width: '100%',
        boxSizing: 'border-box',
    },
    submitButton: {
        padding: '10px',
        fontSize: '16px',
        backgroundColor: '#6200ea',
        color: 'white',
        border: 'none',
        borderRadius: '5px',
        cursor: 'pointer',
        transition: 'background-color 0.3s',
    },
    submitButtonHover: {
        backgroundColor: '#3700b3',
    },
};

export default AddWorkerPage;
