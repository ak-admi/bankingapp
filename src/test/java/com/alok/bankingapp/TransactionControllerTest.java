package com.alok.bankingapp;

import com.alok.bankingapp.dto.responses.TransactionResponse;
import com.alok.bankingapp.entity.TransactionType;
import com.alok.bankingapp.entity.Transactions;
import com.alok.bankingapp.security.CustomUserDetailsService;
import com.alok.bankingapp.security.JwtService;
import com.alok.bankingapp.service.TransactionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(Transactions.class)
public class TransactionControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TransactionService transactionService;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private CustomUserDetailsService userDetailsService;

    @Test
    @WithMockUser(username = "alok", roles = "USER")
    void getTransaction_asUSer_succeeds() throws Exception {
        when(transactionService.getById(1L)).thenReturn(sampleTransactionDto());
        mockMvc.perform(get("/api/transactions/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(username = "alok", roles = "USER")
    void deleteTransaction_asUser_isForbidden() throws Exception {
        mockMvc.perform(delete("/api/transactions/1"))
                .andExpect(status().isForbidden());
    }

    @Test
    void getTransaction_unauthenticated_isUnauthorized() throws Exception {
        mockMvc.perform(get("/api/transactions/1"))
                .andExpect(status().isUnauthorized());
    }

    private TransactionResponse sampleTransactionDto() {
        return new TransactionResponse(
                1L,
                new BigDecimal("500.00"),
                TransactionType.CREDIT,
                LocalDate.now()
        );
    }
}
