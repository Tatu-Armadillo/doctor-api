package br.com.fiap.hackathon.doctor.record;

import java.util.List;
import java.util.stream.Collectors;

import br.com.fiap.hackathon.doctor.model.Doctor;
import br.com.fiap.hackathon.doctor.model.Speciality;

public record CreateDockerDto(
        String crm,
        String professionalContact,
        List<String> specialities) {

    public static Doctor toEntity(final CreateDockerDto dto) {
        final var entity = new Doctor();
        entity.setCrm(dto.crm());
        entity.setProfessionalContact(dto.professionalContact());
        entity.setSpecialities(
                dto.specialities().stream().map(s -> new Speciality(null, s)).collect(Collectors.toSet()));
        return entity;
    }

}
