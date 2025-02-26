package br.com.fiap.hackathon.doctor.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.fiap.hackathon.doctor.model.Speciality;

@Repository
public interface SpecialityRepository extends JpaRepository<Speciality, Long>{
    
    Optional<Speciality> findSpecialityByName(@Param("name") String name);

}
