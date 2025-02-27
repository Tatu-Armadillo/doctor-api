package br.com.fiap.hackathon.doctor.model;

import java.util.Set;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "doctor", schema = "hackaton")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Doctor {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "active_crm", unique = true)
    private String crm;
    
    @Column(name = "user_identifier")
    private String userIdentifier;
    
    @Column(name = "professional_contact")
    private String professionalContact;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(schema = "hackaton", name = "speciality_doctor", 
        joinColumns = @JoinColumn(name = "id_doctor", foreignKey = @ForeignKey(name = "fk_speciality_doctor_doctor")), 
        inverseJoinColumns = @JoinColumn(name = "id_speciality", foreignKey = @ForeignKey(name = "fk_speciality_doctor_speciality"))
    )
    private Set<Speciality> specialities;

    
}
