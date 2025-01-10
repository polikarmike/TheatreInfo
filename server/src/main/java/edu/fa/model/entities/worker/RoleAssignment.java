package edu.fa.model;

import edu.fa.model.entities.worker.Worker;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "role_assignment")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleAssignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long assignmentId;

    @ManyToOne
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private PerformanceRole role;

    @ManyToOne
    @JoinColumn(name = "worker_id", nullable = false)
    private Worker worker;
}
