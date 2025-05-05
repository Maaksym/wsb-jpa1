package com.jpacourse.persistance.dao;

import com.jpacourse.persistance.entity.PatientEntity;
import com.jpacourse.persistance.entity.VisitEntity;
import com.jpacourse.persistance.entity.DoctorEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

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

    /**
     * 1. Знайти пацієнтів за прізвищем
     */
    public List<PatientEntity> findPatientsByLastName(String lastName) {
        TypedQuery<PatientEntity> query = entityManager.createQuery(
                "SELECT p FROM PatientEntity p WHERE p.lastName = :lastName", PatientEntity.class);
        query.setParameter("lastName", lastName);
        return query.getResultList();
    }

    /**
     * 2. Знайти всі візити пацієнта за його ID
     */
    public List<VisitEntity> findVisitsByPatientId(Long patientId) {
        TypedQuery<VisitEntity> query = entityManager.createQuery(
                "SELECT v FROM VisitEntity v WHERE v.patient.id = :patientId", VisitEntity.class);
        query.setParameter("patientId", patientId);
        return query.getResultList();
    }

    /**
     * 3. Знайти пацієнтів, які мали більше ніж X візитів
     */
    public List<PatientEntity> findPatientsWithMoreThanXVisits(int minVisits) {
        TypedQuery<PatientEntity> query = entityManager.createQuery(
                "SELECT p FROM PatientEntity p WHERE SIZE(p.visits) > :minVisits", PatientEntity.class);
        query.setParameter("minVisits", minVisits);
        return query.getResultList();
    }

    /**
     * 4. Знайти пацієнтів за доданим полем `isInsured` (фільтрація за статусом страхування)
     */
    public List<PatientEntity> findPatientsByInsuranceStatus(boolean insuredStatus) {
        TypedQuery<PatientEntity> query = entityManager.createQuery(
                "SELECT p FROM PatientEntity p WHERE p.isInsured = :insuredStatus", PatientEntity.class);
        query.setParameter("insuredStatus", insuredStatus);
        return query.getResultList();
    }
}
