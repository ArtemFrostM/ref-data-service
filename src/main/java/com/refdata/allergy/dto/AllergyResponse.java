package com.refdata.allergy.dto;

import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder(builderMethodName = "allergyResponse")
public record AllergyResponse(
        UUID id,
        String name,
        Instant createdAt,
        String createdBy,
        Instant updatedAt,
        String updatedBy
) {

}
