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
	@JoinColumn(name = "doctor_id", referencedColumnName = "id")
	private DoctorEntity doctor;

	@ManyToOne
	@JoinColumn(name = "patient_id", referencedColumnName = "id")
	private PatientEntity patient;

	@ManyToMany
	@JoinTable(
			name = "visit_medical_treatment",
			joinColumns = @JoinColumn(name = "visit_id"),
			inverseJoinColumns = @JoinColumn(name = "medical_treatment_id")
	)
	private List<MedicalTreatmentEntity> medicalTreatment;

	// Новий геттер для PatientMapper
	public List<MedicalTreatmentEntity> getTreatments() {
		return medicalTreatment;
	}

	// Стандартні гетери/сетери
	public List<MedicalTreatmentEntity> getMedicalTreatment() {
		return medicalTreatment;
	}

	public void setMedicalTreatment(List<MedicalTreatmentEntity> medicalTreatment) {
		this.medicalTreatment = medicalTreatment;
	}

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

	public DoctorEntity getDoctor() {
		return doctor;
	}

	public void setDoctor(DoctorEntity doctor) {
		this.doctor = doctor;
	}

	public PatientEntity getPatient() {
		return patient;
	}

	public void setPatient(PatientEntity patient) {
		this.patient = patient;
	}
}
