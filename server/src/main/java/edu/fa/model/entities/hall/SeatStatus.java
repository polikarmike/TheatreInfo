package edu.fa.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SeatStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long statusId;

    @Column(name = "schedule_id", nullable = false)
    private Long scheduleId;

    @Column(name = "seat_id", nullable = false)
    private Long seatId;

    @Column(name = "is_occupied", nullable = false)
    private Boolean isOccupied = false;

    @Column(name = "seat_type", nullable = false)
    private String seatType;

    @Column(name = "price", nullable = false)
    private Double price;

    // Дополнительный конструктор без statusId
    public SeatStatus(Long scheduleId, Long seatId, String seatType, Double price, Boolean isOccupied) {
        this.scheduleId = scheduleId;
        this.seatId = seatId;
        this.seatType = seatType;
        this.price = price;
        this.isOccupied = isOccupied;
    }
}
