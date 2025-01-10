package edu.fa.model;


import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
public class Hall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long hallId;

    @Column(nullable = false)
    private String hallName;

    @Column(nullable = false)
    private int rowCount;
}
