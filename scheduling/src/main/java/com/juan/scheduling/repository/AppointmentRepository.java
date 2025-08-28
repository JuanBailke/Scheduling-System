package com.juan.scheduling.repository;

import com.juan.scheduling.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    Optional<List<Appointment>> findByDoctorId(Long doctorId);

    Optional<List<Appointment>> findByPatientId(Long patientId);
}
