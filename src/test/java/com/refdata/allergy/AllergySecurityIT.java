package com.refdata.allergy;

import com.refdata.BaseIntegrationTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.JwtRequestPostProcessor;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Verifies the per-endpoint read/write rules of {@code SecurityConfig}.
 *
 * <p>Tokens are faked with {@code jwt()}: the request is treated as already authenticated with the
 * given authorities, bypassing signature validation. So no Keycloak is needed here: we test our
 * access rules, not token signing.
 */
class AllergySecurityIT extends BaseIntegrationTest {

    private static final String BASE_URL = "/api/v1/allergies";
    private static final String CREATE_BODY = "{\"name\":\"Soy\"}";

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AllergyRepository repository;

    @AfterEach
    void cleanUp() {
        repository.deleteAll();
    }

    private static JwtRequestPostProcessor readToken() {
        return jwt().authorities(new SimpleGrantedAuthority("SCOPE_allergy:read"));
    }

    private static JwtRequestPostProcessor writeToken() {
        return jwt().authorities(new SimpleGrantedAuthority("SCOPE_allergy:write"));
    }

    @Test
    @DisplayName("No token -> 401")
    void noToken() throws Exception {
        mockMvc.perform(get(BASE_URL))
                .andExpect(status().isUnauthorized());
    }

    @Test
    @DisplayName("GET with allergy:read -> 200")
    void readWithReadScope() throws Exception {
        mockMvc.perform(get(BASE_URL).with(readToken()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET with only allergy:write -> 403")
    void readWithWriteScopeOnly() throws Exception {
        mockMvc.perform(get(BASE_URL).with(writeToken()))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("POST with only allergy:read -> 403 (patient-service cannot modify reference data)")
    void createWithReadScope() throws Exception {
        mockMvc.perform(post(BASE_URL).with(readToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CREATE_BODY))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("POST with allergy:write -> 201")
    void createWithWriteScope() throws Exception {
        mockMvc.perform(post(BASE_URL).with(writeToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CREATE_BODY))
                .andExpect(status().isCreated());
    }

    @Test
    @DisplayName("PUT with only allergy:read -> 403")
    void updateWithReadScope() throws Exception {
        mockMvc.perform(put(BASE_URL + "/" + UUID.randomUUID()).with(readToken())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CREATE_BODY))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("DELETE with only allergy:read -> 403")
    void deleteWithReadScope() throws Exception {
        mockMvc.perform(delete(BASE_URL + "/" + UUID.randomUUID()).with(readToken()))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Unknown id with valid token -> 404, not 403 (/error is permitted)")
    void notFoundIsNotMaskedAsForbidden() throws Exception {
        mockMvc.perform(get(BASE_URL + "/" + UUID.randomUUID()).with(readToken()))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Actuator health is public (Docker HEALTHCHECK calls it without a token)")
    void healthIsPublic() throws Exception {
        mockMvc.perform(get("/actuator/health"))
                .andExpect(status().isOk());
    }
}
