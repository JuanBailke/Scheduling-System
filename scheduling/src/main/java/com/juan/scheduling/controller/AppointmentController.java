package com.juan.scheduling.controller;

import com.juan.scheduling.dto.AppointmentRequestDTO;
import com.juan.scheduling.dto.AppointmentResponseDTO;
import com.juan.scheduling.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping("/schedule")
    public ResponseEntity<AppointmentResponseDTO> scheduleAppointment(@Valid @RequestBody AppointmentRequestDTO object) {
        return ResponseEntity.status(HttpStatus.CREATED).body(appointmentService.scheduleAppointment(object));
    }

    @GetMapping("/all")
    public ResponseEntity<List<AppointmentResponseDTO>> getAllAppointments() {
        return ResponseEntity.ok(appointmentService.getAllAppointments());
    }
}
