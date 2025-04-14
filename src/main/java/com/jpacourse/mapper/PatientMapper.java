package com.jpacourse.mapper;

import com.jpacourse.dto.PatientTO;
import com.jpacourse.dto.VisitTO;
import com.jpacourse.persistance.entity.PatientEntity;
import com.jpacourse.persistance.entity.VisitEntity;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class PatientMapper {

    public static PatientTO mapToTO(PatientEntity patientEntity) {
        if (patientEntity == null) {
            return null;
        }

        PatientTO patientTO = new PatientTO();
        patientTO.setId(patientEntity.getId());
        patientTO.setFirstName(patientEntity.getFirstName());
        patientTO.setLastName(patientEntity.getLastName());
        patientTO.setDateOfBirth(patientEntity.getDateOfBirth());
        patientTO.setDateOfRegistration(patientEntity.getDateOfRegistration());
        patientTO.setTelephoneNumber(patientEntity.getTelephoneNumber());
        patientTO.setEmail(patientEntity.getEmail());
        patientTO.setPatientNumber(patientEntity.getPatientNumber());
        patientTO.setIsInsured(patientEntity.getIsInsured()); // Мапінг поля isInsured

        if (patientEntity.getVisits() != null && !patientEntity.getVisits().isEmpty()) {
            List<VisitTO> visitTOs = patientEntity.getVisits().stream()
                    .map(PatientMapper::mapVisitToTO)
                    .collect(Collectors.toList());
            patientTO.setVisits(visitTOs);
        } else {
            patientTO.setVisits(Collections.emptyList());
        }

        return patientTO;
    }

    public static PatientEntity mapToEntity(PatientTO patientTO) {
        if (patientTO == null) {
            return null;
        }

        PatientEntity patientEntity = new PatientEntity();
        patientEntity.setId(patientTO.getId());
        patientEntity.setFirstName(patientTO.getFirstName());
        patientEntity.setLastName(patientTO.getLastName());
        patientEntity.setDateOfBirth(patientTO.getDateOfBirth());
        patientEntity.setDateOfRegistration(patientTO.getDateOfRegistration());
        patientEntity.setTelephoneNumber(patientTO.getTelephoneNumber());
        patientEntity.setEmail(patientTO.getEmail());
        patientEntity.setPatientNumber(patientTO.getPatientNumber());
        patientEntity.setIsInsured(patientTO.getIsInsured()); // Мапінг поля isInsured

        return patientEntity;
    }

    private static VisitTO mapVisitToTO(VisitEntity visitEntity) {
        if (visitEntity == null) {
            return null;
        }

        VisitTO visitTO = new VisitTO();
        visitTO.setId(visitEntity.getId());
        visitTO.setTime(visitEntity.getTime());
        visitTO.setDescription(visitEntity.getDescription());

        if (visitEntity.getDoctor() != null) {
            visitTO.setDoctorFirstName(visitEntity.getDoctor().getFirstName());
            visitTO.setDoctorLastName(visitEntity.getDoctor().getLastName());
        } else {
            visitTO.setDoctorFirstName(null);
            visitTO.setDoctorLastName(null);
        }

        if (visitEntity.getTreatments() != null && !visitEntity.getTreatments().isEmpty()) {
            List<String> treatments = visitEntity.getTreatments().stream()
                    .map(treatment -> treatment.getName())
                    .collect(Collectors.toList());
            visitTO.setTreatmentTypes(treatments);
        } else {
            visitTO.setTreatmentTypes(Collections.emptyList());
        }

        return visitTO;
    }
}
