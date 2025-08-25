package com.juan.scheduling.controller;

import com.juan.scheduling.dto.AppointmentRequestDTO;
import com.juan.scheduling.dto.AppointmentResponseDTO;
import com.juan.scheduling.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/appointments")
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;

    @PostMapping("/schedule")
    public ResponseEntity<AppointmentResponseDTO> scheduleAppointment(@RequestBody AppointmentRequestDTO object) {
        try {
            return ResponseEntity.ok(appointmentService.scheduleAppointment(object));
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
