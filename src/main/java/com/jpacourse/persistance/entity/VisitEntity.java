package com.jpacourse.persistance.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "visit")
public class VisitEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String description;
	private LocalDateTime time;

	@ManyToOne
	@JoinColumn(name = "patient_id", referencedColumnName = "id")
	private PatientEntity patient;

	@ManyToOne
	@JoinColumn(name = "medical_treatment_id", referencedColumnName = "id")
	private MedicalTreatmentEntity medicalTreatment;

	// Getters and Setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDateTime getTime() {
		return time;
	}

	public void setTime(LocalDateTime time) {
		this.time = time;
	}

	public PatientEntity getPatient() {
		return patient;
	}

	public void setPatient(PatientEntity patient) {
		this.patient = patient;
	}

	public MedicalTreatmentEntity getMedicalTreatment() {
		return medicalTreatment;
	}

	public void setMedicalTreatment(MedicalTreatmentEntity medicalTreatment) {
		this.medicalTreatment = medicalTreatment;
	}
}
