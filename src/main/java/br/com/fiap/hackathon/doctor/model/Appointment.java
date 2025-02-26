package br.com.fiap.hackathon.doctor.model;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "appointment", schema = "hackaton")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Appointment {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "scheduled")
    private LocalDateTime scheduled;

    @Column(name = "canceled")
    private Boolean canceled;

    @Column(name = "patient")
    private String patient;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "doctor", foreignKey = @ForeignKey(name = "fk_doctor_appointment"))
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hospital", foreignKey = @ForeignKey(name = "fk_hospital_appointment"))
    private Hospital hospital;

}
