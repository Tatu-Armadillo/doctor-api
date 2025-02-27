package br.com.fiap.hackathon.doctor.service;

import br.com.fiap.hackathon.doctor.exception.BusinessException;
import br.com.fiap.hackathon.doctor.model.Speciality;
import br.com.fiap.hackathon.doctor.repository.SpecialityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class SpecialityServiceTest {
    @Mock
    private SpecialityRepository specialityRepository;

    @InjectMocks
    private SpecialityService specialityService;

    private Speciality speciality1;
    private Speciality speciality2;

    @BeforeEach
    void setUp() {
        speciality1 = new Speciality();
        speciality1.setName("Cardiology");

        speciality2 = new Speciality();
        speciality2.setName("Neurology");
    }

    @Test
    void shouldFindSpecialityByNameSuccessfully() {
        when(specialityRepository.findSpecialityByName("Cardiology")).thenReturn(Optional.of(speciality1));

        Speciality foundSpeciality = specialityService.findSpecialityByName("Cardiology");

        assertNotNull(foundSpeciality);
        assertEquals("Cardiology", foundSpeciality.getName());
        verify(specialityRepository).findSpecialityByName("Cardiology");
    }

    @Test
    void shouldThrowExceptionWhenSpecialityNotFoundByName() {
        when(specialityRepository.findSpecialityByName("Unknown")).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class, () -> specialityService.findSpecialityByName("Unknown"));

        assertEquals("Speciality not found = Unknown", exception.getMessage());
        verify(specialityRepository).findSpecialityByName("Unknown");
    }

    @Test
    void shouldFindMultipleSpecialitiesByNamesSuccessfully() {
        Set<Speciality> inputSpecialities = Set.of(speciality1, speciality2);

        when(specialityRepository.findSpecialityByName("Cardiology")).thenReturn(Optional.of(speciality1));
        when(specialityRepository.findSpecialityByName("Neurology")).thenReturn(Optional.of(speciality2));

        Set<Speciality> foundSpecialities = specialityService.findSpecialitiesByNames(inputSpecialities);

        assertNotNull(foundSpecialities);
        assertEquals(2, foundSpecialities.size());
        assertTrue(foundSpecialities.containsAll(inputSpecialities));

        verify(specialityRepository).findSpecialityByName("Cardiology");
        verify(specialityRepository).findSpecialityByName("Neurology");
    }

    @Test
    void shouldThrowExceptionWhenOneSpecialityNotFound() {
        Set<Speciality> inputSpecialities = Set.of(speciality1, speciality2);

        when(specialityRepository.findSpecialityByName("Cardiology")).thenReturn(Optional.of(speciality1));
        when(specialityRepository.findSpecialityByName("Neurology")).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class, () -> specialityService.findSpecialitiesByNames(inputSpecialities));

        assertEquals("Speciality not found = Neurology", exception.getMessage());

        verify(specialityRepository, times(1)).findSpecialityByName("Cardiology");
        verify(specialityRepository, times(1)).findSpecialityByName("Neurology");
    }
}