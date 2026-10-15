package com.refdata.allergy.mapper;


import com.refdata.allergy.Allergy;
import com.refdata.allergy.dto.AllergyCreateRequest;
import com.refdata.allergy.dto.AllergyResponse;
import com.refdata.allergy.dto.AllergyUpdateRequest;
import org.springframework.stereotype.Component;

@Component
public class AllergyMapper {

    public AllergyResponse toResponse(Allergy allergy) {
        return AllergyResponse.allergyResponse()
                .id(allergy.getId())
                .name(allergy.getName())
                .createdAt(allergy.getCreatedAt())
                .createdBy(allergy.getCreatedBy())
                .updatedAt(allergy.getUpdatedAt())
                .updatedBy(allergy.getUpdatedBy())
                .build();
    }

    public Allergy toEntity(AllergyCreateRequest request) {
        Allergy allergy = new Allergy();
        allergy.setName(request.name());
        return allergy;
    }

    public Allergy updateEntity(Allergy entity, AllergyUpdateRequest request) {
        entity.setName(request.name());
        return entity;
    }
}
