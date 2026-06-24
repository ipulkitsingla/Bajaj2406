package com.bajaj.bfhl.controller;

import com.bajaj.bfhl.dto.BfhlRequest;
import com.bajaj.bfhl.dto.BfhlResponse;
import com.bajaj.bfhl.service.BfhlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/bfhl")
public class BfhlController {

    private final BfhlService bfhlService;

    @Autowired
    public BfhlController(BfhlService bfhlService) {
        this.bfhlService = bfhlService;
    }

    @PostMapping
    public ResponseEntity<BfhlResponse> processData(@RequestBody(required = false) BfhlRequest request) {
        if (request == null || request.getData() == null) {
            BfhlResponse errorResponse = new BfhlResponse();
            errorResponse.setSuccess(false);
            return ResponseEntity.badRequest().body(errorResponse);
        }

        try {
            BfhlResponse response = bfhlService.processBfhlRequest(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            BfhlResponse errorResponse = new BfhlResponse();
            errorResponse.setSuccess(false);
            return ResponseEntity.internalServerError().body(errorResponse);
        }
    }
}
