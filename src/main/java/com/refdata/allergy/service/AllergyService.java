package com.refdata.allergy.service;

import com.refdata.allergy.Allergy;
import com.refdata.allergy.AllergyRepository;
import com.refdata.allergy.dto.AllergyCreateRequest;
import com.refdata.allergy.dto.AllergyResponse;
import com.refdata.allergy.dto.AllergyUpdateRequest;
import com.refdata.allergy.mapper.AllergyMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AllergyService {

    private final AllergyMapper mapper;
    private final AllergyRepository repository;

    public Page<AllergyResponse> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(mapper::toResponse);
    }

    public List<AllergyResponse> findAllByIds(Set<UUID> ids) {
        return repository.findAllById(ids).stream()
                .map(mapper::toResponse)
                .toList();
    }

    public AllergyResponse findById(UUID id) {
        return repository.findById(id)
                .map(mapper::toResponse)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Allergy not found: " + id));
    }

    @Transactional
    public AllergyResponse save(AllergyCreateRequest createRequest) {
        Allergy allergy = mapper.toEntity(createRequest);
        return mapper.toResponse(repository.save(allergy));
    }

    @Transactional
    public AllergyResponse update(UUID id, AllergyUpdateRequest updateRequest) {
        Allergy allergy = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Allergy not found: " + id));
        mapper.updateEntity(allergy, updateRequest);
        return mapper.toResponse(allergy);
    }

    @Transactional
    public void delete(UUID id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Allergy not found: " + id);
        }
        repository.deleteById(id);
    }
}
