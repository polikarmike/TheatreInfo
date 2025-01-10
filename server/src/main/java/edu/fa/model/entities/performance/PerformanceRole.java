package edu.fa.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "performance_role")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PerformanceRole {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roleId;

    @ManyToOne
    @JoinColumn(name = "performance_id", nullable = false)
    @JsonBackReference
    private Performance performance;

    @Column(name = "role_name", nullable = false, length = 100)
    private String roleName;
}

