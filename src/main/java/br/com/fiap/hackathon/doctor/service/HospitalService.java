package br.com.fiap.hackathon.doctor.service;

import org.springframework.stereotype.Service;

import br.com.fiap.hackathon.doctor.exception.BusinessException;
import br.com.fiap.hackathon.doctor.model.Hospital;
import br.com.fiap.hackathon.doctor.repository.HospitalRepository;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class HospitalService {

    private final HospitalRepository hospitalRepository;

    public Hospital findHospitalByName(final String name) {
        return this.hospitalRepository.findHospitalByName(name)
                .orElseThrow(() -> new BusinessException("Not found hospital with name " + name));
    }

}
