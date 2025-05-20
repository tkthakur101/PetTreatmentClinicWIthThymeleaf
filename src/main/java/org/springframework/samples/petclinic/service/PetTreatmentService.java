package org.springframework.samples.petclinic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.entity.PetTreatment;
import org.springframework.samples.petclinic.owner.Owner;
import org.springframework.samples.petclinic.owner.OwnerRepository;
import org.springframework.samples.petclinic.repository.PetTreatmentRepository;
import org.springframework.samples.petclinic.service.util.LocalDateFormater;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PetTreatmentService {
	@Autowired
	private PetTreatmentRepository petTreatmentRepository;

	@Autowired
	private OwnerRepository ownerRepository;

	public PetTreatment saveTreatment(PetTreatment treatment) {
		return petTreatmentRepository.save(treatment);
	}

	public List<PetTreatment> getTreatmentDetails() {
		return petTreatmentRepository.findAll();
	}

	public PetTreatment saveTreatmentDetails(PetTreatment petTreatment) {
		PetTreatment petTreatmentDetails = null;
		Integer owner_Id = petTreatment.getOwnerId();
		if (owner_Id != null) {
			Optional<Owner> optionalOwner = ownerRepository.findById(owner_Id);
			if (optionalOwner.isPresent()) {
				if (petTreatment.getDate() == null) {
					petTreatment.setDate(LocalDateFormater.getLocalDate());
				}
				petTreatmentDetails = petTreatmentRepository.save(petTreatment);
			} else {
				Owner owner1 = optionalOwner.orElseThrow(() -> new IllegalArgumentException(
					"Owner not found with id: " + owner_Id + ". Please ensure the ID is correct "));
			}
		}
		return petTreatmentDetails;
	}

	public List<PetTreatment> FindPetTreatmentDetails(Integer owner_Id) {
		Optional<List<PetTreatment>> petTreatmentDetails = petTreatmentRepository.findByOwnerId(owner_Id);
		List<PetTreatment> tempPetTreatmentDetails = null;
		if (petTreatmentDetails.isPresent())
			tempPetTreatmentDetails = petTreatmentDetails.get();
		return tempPetTreatmentDetails;
	}

	public void deleteTreatmentRecord(Integer id) {
		petTreatmentRepository.deleteById(id);
		System.out.println("Pet Treatment record is deleted for Record Id: " + id);
	}

	public PetTreatment updateTreatmentRecord(Integer id, PetTreatment petTreatment) {
		Optional<PetTreatment> currentPetTreatment = petTreatmentRepository.findById(id);
		PetTreatment tempPetTreatmentRecord = null;
		PetTreatment updatedPetTreatment = null;
		if (currentPetTreatment.isPresent()) {
			tempPetTreatmentRecord = currentPetTreatment.get();
			tempPetTreatmentRecord.setTreatment(petTreatment.getTreatment());
			tempPetTreatmentRecord.setCost(petTreatment.getCost());
			tempPetTreatmentRecord.setOwnerId(petTreatment.getOwnerId());
			tempPetTreatmentRecord.setPet_type_id(petTreatment.getPet_type_id());
			System.out.println("Before update PetTreatmentRecord =>   Treatment : " + tempPetTreatmentRecord.getTreatment() +
				" Cost : " + tempPetTreatmentRecord.getCost());

			updatedPetTreatment = petTreatmentRepository.save(tempPetTreatmentRecord);
		}
		return updatedPetTreatment;
	}

	public PetTreatment findPetTreatmentRecord(Integer id) {
		Optional<PetTreatment> petTreatment = petTreatmentRepository.findById(id);
		PetTreatment petTreatment1 = null;
		if (petTreatment.isPresent())
			petTreatment1 = petTreatment.get();

		return petTreatment1;
	}
}
