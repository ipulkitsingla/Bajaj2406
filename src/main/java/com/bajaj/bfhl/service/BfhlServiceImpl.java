package com.bajaj.bfhl.service;

import com.bajaj.bfhl.dto.BfhlRequest;
import com.bajaj.bfhl.dto.BfhlResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BfhlServiceImpl implements BfhlService {

    private static final String USER_ID = "john_doe_17091999";
    private static final String EMAIL = "john@xyz.com";
    private static final String ROLL_NUMBER = "ABCD123";

    @Override
    public BfhlResponse processBfhlRequest(BfhlRequest request) {
        BfhlResponse response = new BfhlResponse();
        response.setUserId(USER_ID);
        // Requirement specifies full name must be lowercase, which it is.
        response.setEmail(EMAIL);
        response.setRollNumber(ROLL_NUMBER);

        List<String> oddNumbers = new ArrayList<>();
        List<String> evenNumbers = new ArrayList<>();
        List<String> alphabets = new ArrayList<>();
        List<String> specialCharacters = new ArrayList<>();
        
        long sum = 0;
        StringBuilder concatBuilder = new StringBuilder();

        List<String> data = request.getData();
        if (data == null) {
            data = new ArrayList<>();
        }

        for (String item : data) {
            if (item == null || item.isEmpty()) continue;

            if (item.matches("^[0-9]+$")) {
                try {
                    long num = Long.parseLong(item);
                    if (num % 2 == 0) {
                        evenNumbers.add(item);
                    } else {
                        oddNumbers.add(item);
                    }
                    sum += num;
                } catch (NumberFormatException e) {
                    // In case of extremely large numbers, treat as special string or handle accordingly
                    specialCharacters.add(item);
                }
            } else if (item.matches("^[a-zA-Z]+$")) {
                alphabets.add(item.toUpperCase());
                concatBuilder.append(item);
            } else {
                specialCharacters.add(item);
            }
        }

        String combinedAlphabets = concatBuilder.toString();
        StringBuilder reversed = new StringBuilder(combinedAlphabets).reverse();
        StringBuilder concatString = new StringBuilder();
        boolean upper = true;
        
        for (int i = 0; i < reversed.length(); i++) {
            char c = reversed.charAt(i);
            if (upper) {
                concatString.append(Character.toUpperCase(c));
            } else {
                concatString.append(Character.toLowerCase(c));
            }
            upper = !upper;
        }

        response.setOddNumbers(oddNumbers);
        response.setEvenNumbers(evenNumbers);
        response.setAlphabets(alphabets);
        response.setSpecialCharacters(specialCharacters);
        response.setSum(String.valueOf(sum));
        response.setConcatString(concatString.toString());
        response.setSuccess(true);

        return response;
    }
}
