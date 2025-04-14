package com.jpacourse.persistance.dao;

import com.jpacourse.persistance.entity.PatientEntity;
import com.jpacourse.persistance.entity.VisitEntity;
import com.jpacourse.persistance.entity.DoctorEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

@Repository
@Transactional
public class PatientDao {

    @PersistenceContext
    private EntityManager entityManager;

    public void addVisitToPatient(Long patientId, Long doctorId, LocalDateTime visitTime, String description) {
        PatientEntity patient = entityManager.find(PatientEntity.class, patientId);
        DoctorEntity doctor = entityManager.find(DoctorEntity.class, doctorId);

        if (patient == null || doctor == null) {
            throw new IllegalArgumentException("Patient or Doctor not found");
        }

        VisitEntity visit = new VisitEntity();
        visit.setDescription(description);
        visit.setTime(visitTime);
        visit.setDoctor(doctor);
        visit.setPatient(patient);

        patient.getVisits().add(visit);
        entityManager.merge(patient);
    }
}
