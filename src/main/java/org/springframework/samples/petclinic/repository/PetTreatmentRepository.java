package org.springframework.samples.petclinic.repository;

import jakarta.annotation.Nonnull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.samples.petclinic.entity.PetTreatment;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface PetTreatmentRepository extends JpaRepository<PetTreatment, Integer> {
	Optional<List<PetTreatment>> findByOwnerId(@Nonnull Integer Owner_Id);
}
