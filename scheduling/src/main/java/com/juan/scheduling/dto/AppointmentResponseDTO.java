package com.juan.scheduling.dto;

import com.juan.scheduling.model.enums.AppointmentStatus;

import java.time.LocalDateTime;

public record AppointmentResponseDTO(
        Long appointmentId,
        String patientName,
        String doctorName,
        LocalDateTime dateTime,
        AppointmentStatus status
) {
}
