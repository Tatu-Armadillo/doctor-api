package br.com.fiap.hackathon.doctor.service;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.fiap.hackathon.doctor.exception.BusinessException;
import br.com.fiap.hackathon.doctor.model.Speciality;
import br.com.fiap.hackathon.doctor.repository.SpecialityRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SpecialityService {

    private final SpecialityRepository specialityRepository;

    public Speciality findSpecialityByName(final String name) {
        return this.specialityRepository.findSpecialityByName(name)
                .orElseThrow(() -> new BusinessException("Speciality not found = " + name));
    }

    public Set<Speciality> findSpecialitiesByNames(final Set<Speciality> specialities) {
        return specialities.stream()
                .map(s -> this.findSpecialityByName(s.getName()))
                .collect(Collectors.toSet());
    }

}
