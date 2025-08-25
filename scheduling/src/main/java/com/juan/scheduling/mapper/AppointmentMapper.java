package com.juan.scheduling.mapper;

import com.juan.scheduling.dto.AppointmentRequestDTO;
import com.juan.scheduling.dto.AppointmentResponseDTO;
import com.juan.scheduling.model.Appointment;
import com.juan.scheduling.model.Doctor;
import com.juan.scheduling.model.Patient;
import org.springframework.stereotype.Component;

@Component
public class AppointmentMapper {

    /**
     * Utiliza o padrão de projeto Mapper para converter entidades em DTOs e vice-versa.
     * @param requestDTO O DTO com os dados da requisição.
     * @param patient A entidade Patient completa, já buscada no banco.
     * @param doctor A entidade Doctor completa, já buscada no banco.
     * @return Uma nova entidade Appointment, pronta para ser salva.
     */
    public Appointment toEntity(AppointmentRequestDTO requestDTO, Patient patient, Doctor doctor) {
        return Appointment.builder()
                .patient(patient)
                .doctor(doctor)
                .appointmentDateTime(requestDTO.datetime())
                .build();
    }

    /**
     * Converte uma entidade Appointment em um DTO de resposta.
     * @param appointment A entidade salva no banco
     * @return um DTO com os dados a serem exibidos na resposta da API.
     */
    public AppointmentResponseDTO toResponseDTO(Appointment appointment){
        return new AppointmentResponseDTO(
                appointment.getId(),
                appointment.getPatient().getName(),
                appointment.getDoctor().getName(),
                appointment.getAppointmentDateTime(),
                appointment.getStatus()
        );
    }
}
