package br.com.fiap.hackathon.doctor.service;

import br.com.fiap.hackathon.doctor.exception.BusinessException;
import br.com.fiap.hackathon.doctor.model.Doctor;
import br.com.fiap.hackathon.doctor.model.Speciality;
import br.com.fiap.hackathon.doctor.repository.DoctorRepository;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DoctorServiceTest {
    @Mock
    private DoctorRepository doctorRepository;

    @Mock
    private SpecialityService specialityService;

    @InjectMocks
    private DoctorService doctorService;

    private Doctor doctor;
    private Speciality cardiology;
    private Speciality neurology;

    @BeforeEach
    void setUp() {
        cardiology = new Speciality();
        cardiology.setName("Cardiology");

        neurology = new Speciality();
        neurology.setName("Neurology");

        doctor = new Doctor();
        doctor.setCrm("123456");
        doctor.setSpecialities(Set.of(cardiology, neurology));
    }


    // TODO: AJUSTAR
    @Test
    void shouldCreateDoctorSuccessfully() {
        UserDetails userDetails = User.builder()
                .username("testUser")
                .password("password")
                .authorities("ROLE_USER")
                .build();

        final var token = JWT.create()
                .withIssuer("authorization_hackaton")
                .withClaim("identifier", "123456")
                .withSubject("user")
                .sign(Algorithm.HMAC256("secret"));

        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());

        SecurityContext securityContext = SecurityContextHolder.createEmptyContext();
        securityContext.setAuthentication(authentication);
        SecurityContextHolder.setContext(securityContext);

        when(specialityService.findSpecialitiesByNames(anySet())).thenReturn(Set.of(cardiology, neurology));
        when(doctorRepository.save(any(Doctor.class))).thenReturn(doctor);

        Doctor createdDoctor = doctorService.create(doctor);

        assertNotNull(createdDoctor);
        assertEquals("123456", createdDoctor.getCrm());
        assertEquals(2, createdDoctor.getSpecialities().size());

        verify(specialityService, times(1)).findSpecialitiesByNames(anySet());
        verify(doctorRepository, times(1)).save(any(Doctor.class));

        SecurityContextHolder.clearContext();
    }

    @Test
    void shouldFindDoctorByCrmSuccessfully() {
        when(doctorRepository.findDoctorByCrm("123456")).thenReturn(Optional.of(doctor));

        Doctor foundDoctor = doctorService.findDoctorByCrm("123456");

        assertNotNull(foundDoctor);
        assertEquals("123456", foundDoctor.getCrm());

        verify(doctorRepository, times(1)).findDoctorByCrm("123456");
    }

    @Test
    void shouldThrowExceptionWhenDoctorNotFoundByCrm() {
        when(doctorRepository.findDoctorByCrm("999999")).thenReturn(Optional.empty());

        BusinessException exception = assertThrows(BusinessException.class,
                () -> doctorService.findDoctorByCrm("999999"));

        assertEquals("Not found a doctor with CRM = 999999", exception.getMessage());

        verify(doctorRepository, times(1)).findDoctorByCrm("999999");
    }
}