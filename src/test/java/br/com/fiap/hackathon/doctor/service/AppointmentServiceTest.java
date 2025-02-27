package br.com.fiap.hackathon.doctor.service;

import br.com.fiap.hackathon.doctor.exception.BusinessException;
import br.com.fiap.hackathon.doctor.model.Appointment;
import br.com.fiap.hackathon.doctor.model.Doctor;
import br.com.fiap.hackathon.doctor.model.Hospital;
import br.com.fiap.hackathon.doctor.repository.AppointmentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {
    @Mock
    private AppointmentRepository appointmentRepository;

    @Mock
    private DoctorService doctorService;

    @Mock
    private HospitalService hospitalService;

    @InjectMocks
    private AppointmentService appointmentService;

    private Appointment appointment;
    private Doctor doctor;
    private Hospital hospital;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        doctor = new Doctor("123456", "Dr. Smith");
        hospital = new Hospital("Hospital A");

        appointment = new Appointment();
        appointment.setScheduled(LocalDateTime.now().plusDays(1));
        appointment.setDoctor(doctor);
        appointment.setHospital(hospital);
    }

    // TODO: AJUSTAR
    @Test
    void shouldMakeScheduledAppointmentSuccessfully() {
        when(doctorService.findDoctorByCrm(anyString())).thenReturn(doctor);
        when(hospitalService.findHospitalByName(anyString())).thenReturn(hospital);

        when(appointmentRepository.existsAppointmentWithScheduledAndDoctor(
                any(), any(), anyString())).thenReturn(Collections.emptyList());

        Appointment createdAppointment = appointmentService.makeScheduled(appointment);

        assertNotNull(createdAppointment);
        assertEquals(doctor, createdAppointment.getDoctor());
        assertEquals(hospital, createdAppointment.getHospital());
        assertFalse(createdAppointment.getCanceled());

        verify(appointmentRepository, times(1)).save(any(Appointment.class));
        verify(doctorService, times(1)).findDoctorByCrm(anyString());
        verify(hospitalService, times(1)).findHospitalByName(anyString());
    }

    // TODO: AJUSTAR
    @Test
    void shouldThrowExceptionIfScheduledDateIsBeforeToday() {
        appointment.setScheduled(LocalDateTime.now().minusDays(1));

        BusinessException thrown = assertThrows(BusinessException.class, () -> {
            appointmentService.makeScheduled(appointment);
        });

        assertEquals("m=scheduledDateDontBeforeToday Schduled date don't schedule after today", thrown.getMessage());
    }

    // TODO: AJUSTAR
    @Test
    void shouldThrowExceptionIfDoctorHasAppointmentAtTheSameTime() {
        when(appointmentRepository.existsAppointmentWithScheduledAndDoctor(
                any(), any(), eq(doctor.getCrm()))).thenReturn(List.of(new Appointment()));

        BusinessException thrown = assertThrows(BusinessException.class, () -> {
            appointmentService.makeScheduled(appointment);
        });

        assertEquals("m=scheduledDateDontBeforeToday Schduled date don't schedule after today", thrown.getMessage());
    }

}