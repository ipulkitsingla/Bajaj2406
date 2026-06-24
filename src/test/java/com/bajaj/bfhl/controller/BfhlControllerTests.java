package com.bajaj.bfhl.controller;

import com.bajaj.bfhl.dto.BfhlRequest;
import com.bajaj.bfhl.dto.BfhlResponse;
import com.bajaj.bfhl.service.BfhlService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BfhlController.class)
class BfhlControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BfhlService bfhlService;

    @Test
    void testPostBfhlEndpoint() throws Exception {
        BfhlResponse mockResponse = new BfhlResponse();
        mockResponse.setSuccess(true);
        mockResponse.setUserId("john_doe_17091999");
        mockResponse.setEmail("john@xyz.com");
        mockResponse.setRollNumber("ABCD123");
        
        when(bfhlService.processBfhlRequest(any(BfhlRequest.class))).thenReturn(mockResponse);

        String requestJson = "{\"data\": [\"a\", \"1\", \"334\"]}";

        mockMvc.perform(post("/bfhl")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.is_success").value(true))
                .andExpect(jsonPath("$.user_id").value("john_doe_17091999"));
    }

    @Test
    void testBadRequest() throws Exception {
        // Missing data property (empty request body causes 400 Bad Request directly from Spring)
        mockMvc.perform(post("/bfhl")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }
}
