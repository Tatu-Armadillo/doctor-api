package br.com.fiap.hackathon.doctor.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fiap.hackathon.doctor.model.Appointment;
import br.com.fiap.hackathon.doctor.record.MakeAppointmentDto;
import br.com.fiap.hackathon.doctor.service.AppointmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

@Tag(name = "Appointment", description = "Endpoints for Managing appointments")
@AllArgsConstructor
@SecurityRequirement(name = "bearer-key")
@RestController
@RequestMapping("/appointment")
public class AppointmentController {

    private final AppointmentService appointmentService;

    @Operation(tags = { "Doctor" }, summary = "Create a new doctor")
    @PostMapping("/scheduled")
    @Transactional
    public ResponseEntity<Appointment> create(@RequestBody final MakeAppointmentDto data) {
        final var response = this.appointmentService.makeScheduled(data.toEntity());
        return ResponseEntity.ok(response);
    }

}
