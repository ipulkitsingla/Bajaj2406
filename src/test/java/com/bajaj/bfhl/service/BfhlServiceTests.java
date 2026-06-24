package com.bajaj.bfhl.service;

import com.bajaj.bfhl.dto.BfhlRequest;
import com.bajaj.bfhl.dto.BfhlResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class BfhlServiceTests {

    private BfhlServiceImpl bfhlService;

    @BeforeEach
    void setUp() {
        bfhlService = new BfhlServiceImpl();
    }

    @Test
    void testExampleA() {
        BfhlRequest request = new BfhlRequest(Arrays.asList("a", "1", "334", "4", "R", "$"));
        BfhlResponse response = bfhlService.processBfhlRequest(request);

        assertTrue(response.isSuccess());
        assertEquals("john_doe_17091999", response.getUserId());
        assertEquals("john@xyz.com", response.getEmail());
        assertEquals("ABCD123", response.getRollNumber());
        assertEquals(Arrays.asList("1"), response.getOddNumbers());
        assertEquals(Arrays.asList("334", "4"), response.getEvenNumbers());
        assertEquals(Arrays.asList("A", "R"), response.getAlphabets());
        assertEquals(Arrays.asList("$"), response.getSpecialCharacters());
        assertEquals("339", response.getSum());
        assertEquals("Ra", response.getConcatString());
    }

    @Test
    void testExampleB() {
        BfhlRequest request = new BfhlRequest(Arrays.asList("2", "a", "y", "4", "&", "-", "*", "5", "92", "b"));
        BfhlResponse response = bfhlService.processBfhlRequest(request);

        assertTrue(response.isSuccess());
        assertEquals(Arrays.asList("5"), response.getOddNumbers());
        assertEquals(Arrays.asList("2", "4", "92"), response.getEvenNumbers());
        assertEquals(Arrays.asList("A", "Y", "B"), response.getAlphabets());
        assertEquals(Arrays.asList("&", "-", "*"), response.getSpecialCharacters());
        assertEquals("103", response.getSum());
        assertEquals("ByA", response.getConcatString());
    }

    @Test
    void testExampleC() {
        BfhlRequest request = new BfhlRequest(Arrays.asList("A", "ABCD", "DOE"));
        BfhlResponse response = bfhlService.processBfhlRequest(request);

        assertTrue(response.isSuccess());
        assertTrue(response.getOddNumbers().isEmpty());
        assertTrue(response.getEvenNumbers().isEmpty());
        assertEquals(Arrays.asList("A", "ABCD", "DOE"), response.getAlphabets());
        assertTrue(response.getSpecialCharacters().isEmpty());
        assertEquals("0", response.getSum());
        assertEquals("EoDdCbAa", response.getConcatString());
    }
}
