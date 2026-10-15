package com.refdata.allergy;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AllergyRepository extends JpaRepository<Allergy, UUID> {

}
