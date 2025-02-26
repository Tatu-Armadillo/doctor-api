package br.com.fiap.hackathon.doctor.model;

import java.util.Set;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "speciality", schema = "hackaton")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Speciality {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(schema = "hackaton", name = "speciality_doctor", 
        joinColumns = @JoinColumn(name = "id_speciality", foreignKey = @ForeignKey(name = "fk_speciality_doctor_speciality")), 
        inverseJoinColumns = @JoinColumn(name = "id_doctor", foreignKey = @ForeignKey(name = "fk_speciality_doctor_doctor"))
    )
    private Set<Doctor> doctors;

}
