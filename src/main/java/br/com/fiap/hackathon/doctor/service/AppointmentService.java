package br.com.fiap.hackathon.doctor.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import br.com.fiap.hackathon.doctor.configuration.security.LoggedUser;
import br.com.fiap.hackathon.doctor.exception.BusinessException;
import br.com.fiap.hackathon.doctor.model.Appointment;
import br.com.fiap.hackathon.doctor.repository.AppointmentRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorService doctorService;
    private final HospitalService hospitalService;

    public Appointment makeScheduled(final Appointment appointment) {
        scheduledDateDontBeforeToday(appointment.getScheduled());
        scheduledWithDoctorAlreadyExists(appointment);

        final var doctor = this.doctorService.findDoctorByCrm(appointment.getDoctor().getCrm());
        appointment.setDoctor(doctor);

        final var hospital = this.hospitalService.findHospitalByName(appointment.getHospital().getName());
        appointment.setHospital(hospital);

        appointment.setCanceled(false);
        appointment.setPatient(LoggedUser.get().getUserIdentifier());
        return this.appointmentRepository.save(appointment);
    }

    private void scheduledDateDontBeforeToday(final LocalDateTime scheduled) {
        if (scheduled.isAfter(LocalDateTime.now())) {
            throw new BusinessException("m=scheduledDateDontBeforeToday Schduled date don't schedule after today");
        }
    }

    private void scheduledWithDoctorAlreadyExists(final Appointment appointment) {
        final var appointments = this.appointmentRepository.existsAppointmentWithScheduledAndDoctor(
                appointment.getScheduled().minusHours(1),
                appointment.getScheduled().plusHours(1),
                appointment.getDoctor().getCrm());

        if (!appointments.isEmpty()) {
            throw new BusinessException(
                    "m=scheduledWithDoctorAlreadyExists Schduled don't have hours by this scheduled");
        }
    }

}
