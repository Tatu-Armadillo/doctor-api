package br.com.fiap.hackathon.doctor.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import br.com.fiap.hackathon.doctor.model.Appointment;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    @Query("SELECT appointment FROM Appointment appointment "
            + "WHERE appointment.doctor.crm = :crm "
            + "AND appointment.scheduled BETWEEN :minusScheduled AND :plusScheduled")
    List<Appointment> existsAppointmentWithScheduledAndDoctor(
            @Param("plusScheduled") LocalDateTime plusScheduled,
            @Param("minusScheduled") LocalDateTime minusScheduled,
            @Param("crm") String crm);
}
