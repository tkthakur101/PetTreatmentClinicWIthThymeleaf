package org.springframework.samples.petclinic.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.samples.petclinic.entity.PetTreatment;
import org.springframework.samples.petclinic.service.PetTreatmentService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	@GetMapping("/allTreatmentRecords1")
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

	// Thymeleaf - Get all records
	@GetMapping("/allTreatmentRecords")
	public String getTreatmentRecords(Model model) {
		model.addAttribute("allPetTreatmentList", petTreatmentService.getTreatmentDetails());
		return "petTreatment/petTreatmentDetails";
	}

	// Thymeleaf - add new record
	@GetMapping("/addNew")
	public String addPetTreatmentDetails(Model model)  {
		PetTreatment petTreatment = new PetTreatment();
		model.addAttribute("petTreatment", petTreatment);
		return "petTreatment/addPetTreatmentDetails";
	}

	//--------  Save the petTreatmentDetails ------
	@PostMapping("/saveTreatmentDetails")
	public ResponseEntity<PetTreatment> saveTreatmentDetails(@RequestBody PetTreatment petTreatment) {
		return ResponseEntity.ok(petTreatmentService.saveTreatmentDetails(petTreatment));
	}

	// Thymeleaf - Save the record
	@PostMapping("/save")
	public String saveTreatmentDetail(@ModelAttribute("petTreatment") PetTreatment petTreatment) {
		petTreatmentService.saveTreatmentDetails(petTreatment);
		return "redirect:/petTreatment/allTreatmentRecords";
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

	// Thymeleaf - delete the record
	@GetMapping("/deletePetTreatmentReport/{id}")
	public String deleteTreatmentRecord1(@PathVariable (value = "id") Integer id) {
		petTreatmentService.deleteTreatmentRecord(id);
		return "redirect:/petTreatment/allTreatmentRecords";
	}

	//--------  Update the petTreatmentRecord based on ID ------
	@PutMapping("/updateTreatmentRecord/{id}")
	public ResponseEntity<PetTreatment> updateTreatmentRecord(@PathVariable Integer id, @RequestBody PetTreatment updatedPetTreatmentDetails) {
		return ResponseEntity.ok(petTreatmentService.updateTreatmentRecord(id,updatedPetTreatmentDetails));
	}

	@PostMapping("/updateTreatmentRecord/{id}")
	public String updateTreatmentRecord1(@PathVariable Integer id, @ModelAttribute PetTreatment updatedPetTreatmentDetails) {
		 petTreatmentService.updateTreatmentRecord(id,updatedPetTreatmentDetails);
		return "redirect:/petTreatment/allTreatmentRecords";
	}

	// Thymeleaf - update the record
	@GetMapping("/showPetTreatmentDetailsForUpdate/{id}")
	public String updateForm(@PathVariable(value = "id") Integer id, Model model) {
		 PetTreatment petTreatment = petTreatmentService.findPetTreatmentRecord(id);
		model.addAttribute("petTreatment", petTreatment);
		return "petTreatment/petTreatmentReportUpdate";
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
