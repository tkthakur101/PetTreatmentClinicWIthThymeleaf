package org.springframework.samples.petclinic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.entity.PetTreatment;
import org.springframework.samples.petclinic.service.PetTreatmentService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Controller
@RequestMapping("/petTreatment")
public class PetTreatmentController {

	@Autowired
	PetTreatmentService petTreatmentService;

	//  ----   Get All the records -----------
	@GetMapping("/allTreatmentRecords")
	public ResponseEntity<List<PetTreatment>> getPetTreatmentDetails() {
		List<PetTreatment> petTreatmentDetails = new ArrayList<>();
		try {
			petTreatmentDetails = petTreatmentService.getTreatmentDetails();
			petTreatmentDetails.forEach(pet -> System.out.println("owner ID : " + pet.getOwnerId() +
				" Pet Type ID : " + pet.getPet_type_id() + " Treatment : " + pet.getTreatment() + " Cost : " + pet.getCost()));
		} catch (Exception e) {
		}

		return ResponseEntity.ok(petTreatmentDetails);
	}

	//--------  Save the petTreatmentDetails ------
	@PostMapping("/saveTreatmentDetails")
	public ResponseEntity<PetTreatment> saveTreatmentDetails(@RequestBody PetTreatment petTreatment) {
		return ResponseEntity.ok(petTreatmentService.saveTreatmentDetails(petTreatment));
	}

	//--------  Find petTreatmentDetails records for Owner ------
	@GetMapping("/findTreatmentDetailsForOwnerId/{owner_Id}")
	public ResponseEntity<List<PetTreatment>> findPetTreatmentDetails(@PathVariable Integer owner_Id) {
		List<PetTreatment> petTreatmentDetails = petTreatmentService.FindPetTreatmentDetails(owner_Id);

		petTreatmentDetails.forEach(petTreatment -> {
			System.out.println("Owner ID :" + petTreatment.getOwnerId() + " PetTypeId : " + petTreatment.getPet_type_id() +
				" visiting Date : " + petTreatment.getDate() + "Treatment : " + petTreatment.getTreatment());
		});
		return ResponseEntity.ok(petTreatmentDetails);
	}

	//--------  Delete the petTreatmentDetails record based on ID ------
	@DeleteMapping("/deleteTreatmentRecord/{id}")
	public void deleteTreatmentRecord(@PathVariable Integer id) {
		petTreatmentService.deleteTreatmentRecord(id);
	}

	//--------  Update the petTreatmentRecord based on ID ------
	@PutMapping("/updateTreatmentRecord/{id}")
	public ResponseEntity<PetTreatment> updateTreatmentRecord(@PathVariable Integer id, @RequestBody PetTreatment updatedPetTreatmentDetails) {
		return ResponseEntity.ok(petTreatmentService.updateTreatmentRecord(id,updatedPetTreatmentDetails));
	}

	//  Used Spring HATEOAS for get the record based on ID
	@GetMapping("/findTreatmentRecordForHateoas/{id}")
	public ResponseEntity<EntityModel<PetTreatment>> getTreatmentRecord(@PathVariable Integer id) {
		PetTreatment petTreatment = petTreatmentService.findPetTreatmentRecord(id);
		EntityModel<PetTreatment> petTreatementModel1 = EntityModel.of(petTreatment);
		WebMvcLinkBuilder linkTo = linkTo(methodOn(PetTreatmentController.class).getTreatmentRecord(id));
		petTreatementModel1.add(linkTo.withSelfRel());
		return ResponseEntity.ok(petTreatementModel1);
	}
}
