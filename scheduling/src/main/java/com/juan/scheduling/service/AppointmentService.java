package com.juan.scheduling.service;

import com.juan.scheduling.dto.AppointmentRequestDTO;
import com.juan.scheduling.dto.AppointmentResponseDTO;
import com.juan.scheduling.exception.ResourceNotFoundException;
import com.juan.scheduling.mapper.AppointmentMapper;
import com.juan.scheduling.model.enums.AppointmentStatus;
import com.juan.scheduling.repository.AppointmentRepository;
import com.juan.scheduling.repository.DoctorRepository;
import com.juan.scheduling.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    //Padrão Facade implementado

    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final AppointmentMapper appointmentMapper;

    /**
     * Agendar uma nova consulta
     * @param request para receber os dados da consulta no padrão DTO
     * @return o DTO de resposta com os dados da consulta agendada
     */
    public AppointmentResponseDTO scheduleAppointment(AppointmentRequestDTO request){
        var patient = patientRepository.findById(request.patientId())
                .orElseThrow(() -> new ResourceNotFoundException("Paciente com id " + request.patientId() + " não encontrado"));
        var doctor = doctorRepository.findById(request.doctorId())
                .orElseThrow(() -> new ResourceNotFoundException("Médico com id " + request.doctorId() + " não encontrado"));

        var newAppointment = appointmentMapper.toEntity(request, patient, doctor);
        newAppointment.setStatus(AppointmentStatus.SCHEDULED);

        var savedAppointment = appointmentRepository.save(newAppointment);

        return appointmentMapper.toResponseDTO(savedAppointment);
    }

    public List<AppointmentResponseDTO> getAllAppointments() {
        var appointments = appointmentRepository.findAll();
        return appointmentMapper.toResponseDTOList(appointments);
    }
}
