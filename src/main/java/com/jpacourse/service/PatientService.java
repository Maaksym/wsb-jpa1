package com.jpacourse.service;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.dto.VisitTO;
import com.jpacourse.mapper.PatientMapper;
import com.jpacourse.mapper.VisitMapper;
import com.jpacourse.persistance.dao.PatientDao;
import com.jpacourse.persistance.entity.PatientEntity;
import com.jpacourse.persistance.entity.VisitEntity;
import com.jpacourse.repository.PatientRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PatientService {

    private final PatientRepository patientRepository;
    private final PatientDao patientDao;

    public PatientService(PatientRepository patientRepository, PatientDao patientDao) {
        this.patientRepository = patientRepository;
        this.patientDao = patientDao;
    }

    /**
     * Отримати пацієнта за ID
     */
    public PatientTO getPatientById(Long id) {
        Optional<PatientEntity> optionalPatientEntity = patientRepository.findById(id);

        if (optionalPatientEntity.isEmpty()) {
            throw new RuntimeException("Пацієнт із ID " + id + " не знайдений");
        }

        PatientEntity patientEntity = optionalPatientEntity.get();

        return PatientMapper.mapToTO(patientEntity);
    }

    /**
     * Видалити пацієнта за ID (з обробкою помилок)
     */
    @Transactional
    public void deletePatient(Long id) {
        if (!patientRepository.existsById(id)) {
            throw new RuntimeException("Пацієнт із ID " + id + " не існує");
        }
        patientRepository.deleteById(id);
    }

    /**
     * 2. Знайти всі візити пацієнта за його ID (JPQL-запит)
     */
    public List<VisitTO> findVisitsByPatientId(Long patientId) {
        List<VisitEntity> visits = patientDao.findVisitsByPatientId(patientId);
        return visits.stream().map(VisitMapper::mapToTO).collect(Collectors.toList());
    }

    /**
     * 3. Знайти пацієнтів, які мали більше ніж X візитів
     */
    public List<PatientTO> findPatientsWithMoreThanXVisits(int minVisits) {
        List<PatientEntity> patients = patientDao.findPatientsWithMoreThanXVisits(minVisits);
        return patients.stream().map(PatientMapper::mapToTO).collect(Collectors.toList());
    }

    /**
     * 4. Знайти пацієнтів за статусом страхування (isInsured)
     */
    public List<PatientTO> findPatientsByInsuranceStatus(boolean insuredStatus) {
        List<PatientEntity> patients = patientDao.findPatientsByInsuranceStatus(insuredStatus);
        return patients.stream().map(PatientMapper::mapToTO).collect(Collectors.toList());
    }
}
