package br.com.fiap.hackathon.doctor.service;

import org.springframework.stereotype.Service;

import br.com.fiap.hackathon.doctor.configuration.security.LoggedUser;
import br.com.fiap.hackathon.doctor.exception.BusinessException;
import br.com.fiap.hackathon.doctor.model.Doctor;
import br.com.fiap.hackathon.doctor.repository.DoctorRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final SpecialityService specialityService;

    public Doctor create(final Doctor entity) {

        final var specialities = this.specialityService.findSpecialitiesByNames(entity.getSpecialities());
        entity.setSpecialities(specialities);
        entity.setUserIdentifier(LoggedUser.get().getUserIdentifier());

        return this.doctorRepository.save(entity);
    }

    public Doctor findDoctorByCrm(final String crm) {
        return this.doctorRepository.findDoctorByCrm(crm)
                .orElseThrow(() -> new BusinessException("Not found a doctor with CRM = " + crm));
    }

}
