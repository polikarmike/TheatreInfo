package edu.fa.model;

import edu.fa.model.entities.worker.JobRole;
import edu.fa.model.entities.worker.Worker;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "production_team")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProductionTeam {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long teamId;

    @ManyToOne
    @JoinColumn(name = "schedule_id", nullable = false)
    private Schedule schedule;

    @ManyToOne
    @JoinColumn(name = "worker_id", nullable = false)
    private Worker worker;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private JobRole role;
}
