package br.com.fiap.hackathon.doctor.record;

import java.time.LocalDateTime;

import br.com.fiap.hackathon.doctor.model.Appointment;
import br.com.fiap.hackathon.doctor.model.Doctor;
import br.com.fiap.hackathon.doctor.model.Hospital;

public record MakeAppointmentDto(
        LocalDateTime scheduledDate,
        String patientIdentifier,
        String crm,
        String hospitalName) {

    public Appointment toEntity() {
        final var entity = new Appointment();
        entity.setScheduled(this.scheduledDate());
        entity.setPatient(this.patientIdentifier());

        final var doctor = new Doctor();
        doctor.setCrm(this.crm());
        entity.setDoctor(doctor);

        final var hospital = new Hospital();
        hospital.setName(this.hospitalName());
        entity.setHospital(hospital);
        
        return entity;
    }

}
