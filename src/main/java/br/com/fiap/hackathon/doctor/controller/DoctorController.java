package br.com.fiap.hackathon.doctor.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.hackathon.doctor.model.Doctor;
import br.com.fiap.hackathon.doctor.service.DoctorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Tag(name = "Doctor", description = "Endpoints for Managing doctors")
@AllArgsConstructor
@SecurityRequirement(name = "bearer-key")
@RestController
public class DoctorController {

    private final DoctorService doctorService;

    @Operation(tags = { "Doctor" }, summary = "Create a new doctor")
    @PostMapping("/create")
    @Transactional
    public ResponseEntity<Doctor> create(@RequestBody final Doctor data) {
        final var response = this.doctorService.create(data);
        return ResponseEntity.ok(response);
    }
    


}
