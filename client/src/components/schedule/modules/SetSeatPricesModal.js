import React, { useState, useEffect, useCallback } from 'react';
import { MdEventSeat } from 'react-icons/md';
import '../../styles/schedule/SetSeatPricesModal.css';

function SetSeatPricesModal({ hallId, seatStatuses = [], onClose, onSave }) {
    const [seats, setSeats] = useState([]);
    const [loading, setLoading] = useState(true);

    useEffect(() => {
        fetchSeats();
    }, [hallId]);

    useEffect(() => {
        if (seatStatuses.length > 0) {
            const mappedSeats = seatStatuses.map(seat => ({
                ...seat,
                isOccupied: seat.isOccupied ?? false,
                seatType: seat.seatType || 'Regular',
                price: seat.price || 0,
                // rowNumber: seat.rowNumber || 1, // Здесь возможно проблема
            }));
            console.log("Mapped Seat Statuses: ", mappedSeats);
            setSeats(prevSeats => mergeSeatData(prevSeats, mappedSeats));
            setLoading(false);
        }
    }, [seatStatuses]);

    const fetchSeats = async () => {
        setLoading(true);
        try {
            const response = await fetch(`/api/seats?hallId=${hallId}`);
            const data = await response.json();
            const mappedSeats = data.map(seat => ({
                ...seat,
                seatType: 'Regular',
                price: 0,
                isOccupied: false,
                rowNumber: seat.rowNumber, // Убедимся, что rowNumber корректно считывается
            }));
            console.log("Fetched Seats: ", mappedSeats);
            setSeats(prevSeats => mergeSeatData(mappedSeats, prevSeats));
            setLoading(false);
        } catch (error) {
            console.error('Error fetching seat data:', error);
            setLoading(false);
        }
    };

    const mergeSeatData = (apiSeats, statusSeats) => {
        const seatMap = new Map();

        apiSeats.forEach(seat => {
            seatMap.set(seat.seatId, seat);
        });

        statusSeats.forEach(seat => {
            seatMap.set(seat.seatId, { ...seatMap.get(seat.seatId), ...seat });
        });

        const mergedSeats = Array.from(seatMap.values());
        console.log("Merged Seats: ", mergedSeats); // Проверим, что данные сливаются корректно
        return mergedSeats;
    };

    const handleSeatChange = (seatId, field, value) => {
        setSeats(prevSeats =>
            prevSeats.map(seat => (seat.seatId === seatId ? { ...seat, [field]: value } : seat))
        );
    };

    const handleRowChange = (rowNumber, field, value) => {
        setSeats(prevSeats =>
            prevSeats.map(seat =>
                seat.rowNumber === rowNumber ? { ...seat, [field]: value } : seat
            )
        );
    };

    const handleSave = useCallback(() => {
        const updatedStatuses = seats.map(seat => ({
            seatId: seat.seatId,
            seatType: seat.seatType,
            price: seat.price,
            isOccupied: seat.isOccupied,
            rowNumber: seat.rowNumber,
        }));

        onSave(updatedStatuses);
        onClose();
    }, [seats, onSave, onClose]);

    const groupedSeats = Array.from(new Set(seats.map(seat => seat.rowNumber)))
        .map(rowNumber => ({
            rowNumber,
            seats: seats.filter(seat => seat.rowNumber === rowNumber),
        }));

    console.log("Grouped Seats: ", groupedSeats); // Проверим групировку данных по рядам

    return (
        <>
            <div className="modal-overlay" onClick={onClose}></div>
            <div className="modal">
                <div className="modal-header">
                    <h2>Назначить цену, тип мест и занятость</h2>
                    <button onClick={onClose} className="modal-closeButton">×</button>
                </div>
                {loading ? (
                    <p className="loading">Загрузка...</p>
                ) : (
                    <div className="theater-layout">
                        {groupedSeats.map(({ rowNumber, seats: rowSeats }) => (
                            <div key={rowNumber} className="row">
                                <div className="row-header">
                                    <span className="row-label">Ряд {rowNumber}</span>
                                    <select
                                        value={rowSeats[0]?.seatType || 'Regular'}
                                        onChange={e =>
                                            handleRowChange(rowNumber, 'seatType', e.target.value)
                                        }
                                        className="row-select"
                                    >
                                        <option value="Regular">Обычное</option>
                                        <option value="VIP">VIP</option>
                                    </select>
                                    <input
                                        type="number"
                                        placeholder="Цена для ряда"
                                        value={rowSeats[0]?.price || 0}
                                        className="row-input"
                                        onChange={e =>
                                            handleRowChange(rowNumber, 'price', parseFloat(e.target.value) || 0)
                                        }
                                    />
                                    <label className="row-checkbox">
                                        <input
                                            type="checkbox"
                                            checked={rowSeats.every(seat => seat.isOccupied)}
                                            onChange={e =>
                                                handleRowChange(rowNumber, 'isOccupied', e.target.checked)
                                            }
                                        />
                                        Занятый ряд
                                    </label>
                                </div>
                                <div className="row-seats">
                                    {rowSeats.map(seat => (
                                        <button
                                            key={seat.seatId}
                                            className={`seat ${seat.isOccupied
                                                ? 'occupied-seat'
                                                : seat.seatType === 'VIP'
                                                    ? 'vip-seat'
                                                    : 'regular-seat'}`}
                                            onClick={() =>
                                                handleSeatChange(seat.seatId, 'isOccupied', !seat.isOccupied)
                                            }
                                        >
                                            <MdEventSeat />
                                            <span className="seat-number">{seat.seatNumber}</span>
                                            <input
                                                type="number"
                                                className="seat-price"
                                                value={seat.price}
                                                onChange={e =>
                                                    handleSeatChange(seat.seatId, 'price', parseFloat(e.target.value) || 0)
                                                }
                                            />
                                            <label className="seat-checkbox">
                                                <input
                                                    type="checkbox"
                                                    checked={seat.isOccupied}
                                                    onChange={e =>
                                                        handleSeatChange(seat.seatId, 'isOccupied', e.target.checked)
                                                    }
                                                />
                                                Занято
                                            </label>
                                        </button>
                                    ))}
                                </div>
                            </div>
                        ))}
                    </div>
                )}
                <div className="modal-actions">
                    <button onClick={handleSave} className="modal-saveButton">Сохранить</button>
                    <button onClick={onClose} className="modal-cancelButton">Закрыть</button>
                </div>
            </div>
        </>
    );
}

export default SetSeatPricesModal;
