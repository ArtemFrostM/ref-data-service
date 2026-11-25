package com.refdata.allergy.controller;

import com.refdata.allergy.dto.AllergyCreateRequest;
import com.refdata.allergy.dto.AllergyResponse;
import com.refdata.allergy.dto.AllergyUpdateRequest;
import com.refdata.allergy.service.AllergyService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.NO_CONTENT;

@RestController
@RequestMapping("/api/v1/allergies")
@RequiredArgsConstructor
public class AllergyController {

    private final AllergyService service;

    @PreAuthorize("hasAuthority('SCOPE_allergy:read')")
    @GetMapping
    public Page<AllergyResponse> findAll(@PageableDefault(size = 20, sort = "name") Pageable pageable) {
        return service.findAll(pageable);
    }

    @PreAuthorize("hasAuthority('SCOPE_allergy:read')")
    @GetMapping("/{id}")
    public AllergyResponse findById(@PathVariable UUID id) {
        return service.findById(id);
    }

    @PreAuthorize("hasAuthority('SCOPE_allergy:read')")
    @GetMapping("/lookup")
    public List<AllergyResponse> findAllByIds(@RequestParam @NotEmpty @Size(max = 100) Set<UUID> ids) {
        return service.findAllByIds(ids);
    }

    @PreAuthorize("hasAuthority('SCOPE_allergy:write')")
    @PostMapping
    @ResponseStatus(CREATED)
    public AllergyResponse save(@Valid @RequestBody AllergyCreateRequest createRequest) {
        return service.save(createRequest);
    }

    @PreAuthorize("hasAuthority('SCOPE_allergy:write')")
    @PutMapping("/{id}")
    public AllergyResponse update(@PathVariable UUID id, @Valid @RequestBody AllergyUpdateRequest createRequest) {
        return service.update(id, createRequest);
    }

    @PreAuthorize("hasAuthority('SCOPE_allergy:write')")
    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void delete(@PathVariable UUID id) {
        service.delete(id);
    }
}
