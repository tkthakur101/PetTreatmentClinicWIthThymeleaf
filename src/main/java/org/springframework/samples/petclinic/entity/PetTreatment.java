package org.springframework.samples.petclinic.entity;

import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
@Table(name = "pet_treatments")
public class PetTreatment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "owner_id")
	private Integer ownerId;

	@Column(name = "pet_type_id")
	private Integer pet_type_id;

	@Column(name = "length_in_cm")
	private double length_in_cm;

	@Column(name = "weight_kg")
	private double weight_kg;

	@Column(name = "visit_date")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private LocalDate date;

	@Column(name = "cost")
	private double cost;

	@Column(name = "treatment")
	private String treatment;

	public PetTreatment() {
	}

	public PetTreatment(Integer ownerId, Integer pet_type_id, double length_in_cm, double weight_kg, LocalDate date, double cost, String treatment) {
		this.ownerId = ownerId;
		this.pet_type_id = pet_type_id;
		this.length_in_cm = length_in_cm;
		this.weight_kg = weight_kg;
		this.date = date;
		this.cost = cost;
		this.treatment = treatment;
	}

	public Integer getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(Integer ownerId) {
		this.ownerId = ownerId;
	}

	public Integer getPet_type_id() {
		return pet_type_id;
	}

	public void setPet_type_id(Integer pet_type_id) {
		this.pet_type_id = pet_type_id;
	}

	public double getLength_in_cm() {
		return length_in_cm;
	}

	public void setLength_in_cm(double length_in_cm) {
		this.length_in_cm = length_in_cm;
	}

	public double getWeight_kg() {
		return weight_kg;
	}

	public void setWeight_kg(double weight_kg) {
		this.weight_kg = weight_kg;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public String getTreatment() {
		return treatment;
	}

	public void setTreatment(String treatment) {
		this.treatment = treatment;
	}
}
