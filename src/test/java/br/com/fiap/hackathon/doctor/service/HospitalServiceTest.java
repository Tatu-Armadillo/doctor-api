package br.com.fiap.hackathon.doctor.service;

import br.com.fiap.hackathon.doctor.exception.BusinessException;
import br.com.fiap.hackathon.doctor.model.Hospital;
import br.com.fiap.hackathon.doctor.repository.HospitalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class HospitalServiceTest {
    @Mock
    private HospitalRepository hospitalRepository;

    @InjectMocks
    private HospitalService hospitalService;

    private Hospital hospital;

    @BeforeEach
    void setUp() {
        hospital = new Hospital();
        hospital.setName("General Hospital");
    }

    @Test
    void shouldFindHospitalByNameSuccessfully() {
        when(hospitalRepository.findHospitalByName("General Hospital")).thenReturn(Optional.of(hospital));

        Hospital foundHospital = hospitalService.findHospitalByName("General Hospital");

        assertNotNull(foundHospital);
        assertEquals("General Hospital", foundHospital.getName());

        verify(hospitalRepository, times(1)).findHospitalByName("General Hospital");
    }

    @Test
    void shouldThrowExceptionWhenHospitalNotFound() {
        when(hospitalRepository.findHospitalByName("Unknown Hospital")).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class,
                () -> hospitalService.findHospitalByName("Unknown Hospital"));

        assertEquals("Not found hospital with name Unknown Hospital", exception.getMessage());

        verify(hospitalRepository, times(1)).findHospitalByName("Unknown Hospital");
    }
}