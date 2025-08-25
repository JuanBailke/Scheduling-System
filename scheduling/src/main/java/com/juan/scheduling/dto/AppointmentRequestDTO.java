package com.juan.scheduling.dto;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record AppointmentRequestDTO(
        @NotNull(message = "O ID do paciente é obrigatório.")
        Long patientId,
        @NotNull(message = "O ID do médico é obrigatório.")
        Long doctorId,
        @NotNull(message = "A data e hora do agendamento são obrigatórias.")
        @FutureOrPresent(message = "A data e hora do agendamento não pode ser no passado.")
        LocalDateTime datetime
) {
}
