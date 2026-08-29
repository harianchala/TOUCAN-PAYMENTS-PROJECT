package com.example.transactionstarter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.transactionstarter.controller.TransactionController;
import com.example.transactionstarter.service.TransactionService;

@WebMvcTest(TransactionController.class)
class TransactionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TransactionService transactionService;

    @Test
    void invalidTransactionRejectedByValidation() throws Exception {

        String invalidJson = """
                {
                    "transactionId": "",
                    "customerId": "",
                    "amount": -100,
                    "currency": "",
                    "transactionType": null,
                    "status": null
                }
                """;

        mockMvc.perform(
                post("/transaction/createTransaction")
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(invalidJson)
            )
            .andExpect(status().isBadRequest());
    }
}