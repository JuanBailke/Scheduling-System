package com.juan.scheduling.service;

import com.juan.scheduling.model.Patient;
import com.juan.scheduling.repository.PatientRepository;

public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public void addDoctor(Patient patient){
        patientRepository.save(patient);
    }
}
