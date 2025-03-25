package com.jpacourse.persistance.entity;

import jakarta.persistence.*;
import com.jpacourse.persistance.enums.TreatmentType;

@Entity
@Table(name = "medical_treatment")
public class MedicalTreatmentEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String description;
	private String type;

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
}
