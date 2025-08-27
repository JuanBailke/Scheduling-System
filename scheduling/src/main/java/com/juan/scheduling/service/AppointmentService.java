package com.juan.scheduling.service;

import com.juan.scheduling.dto.AppointmentRequestDTO;
import com.juan.scheduling.dto.AppointmentResponseDTO;
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

    public AppointmentResponseDTO scheduleAppointment(AppointmentRequestDTO request){
        var patient = patientRepository.findById(request.patientId())
                .orElseThrow(() -> new RuntimeException("Paciente não encontrado"));
        var doctor = doctorRepository.findById(request.doctorId())
                .orElseThrow(() -> new RuntimeException("Médico não encontrado"));

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
