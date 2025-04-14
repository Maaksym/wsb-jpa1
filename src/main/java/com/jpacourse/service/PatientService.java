package com.jpacourse.service;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.mapper.PatientMapper;
import com.jpacourse.persistance.entity.PatientEntity;
import com.jpacourse.repository.PatientRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Optional;

@Service
public class PatientService {

    private final PatientRepository patientRepository;

    public PatientService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    public PatientTO getPatientById(Long id) {
        Optional<PatientEntity> optionalPatientEntity = patientRepository.findById(id);

        if (optionalPatientEntity.isEmpty()) {
            throw new RuntimeException("Пацієнт із ID " + id + " не знайдений");
        }

        PatientEntity patientEntity = optionalPatientEntity.get();

        // Перевірка та ініціалізація visits, якщо вони null
        if (patientEntity.getVisits() == null) {
            patientEntity.setVisits(Collections.emptyList());
        }

        return PatientMapper.mapToTO(patientEntity);
    }

    public void deletePatient(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new RuntimeException("Пацієнт із ID " + id + " не існує");
        }
        patientRepository.deleteById(id);
    }
}
