package com.juan.scheduling.service;

import com.juan.scheduling.model.Doctor;
import com.juan.scheduling.repository.DoctorRepository;

public class DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public void addDoctor(Doctor doctor){
        doctorRepository.save(doctor);
    }
}
