package com.saltpay.test.integration;

import com.saltpay.test.Controllers.TransactionalController;
import com.saltpay.test.DTO.AccountTransactionDTO;
import com.saltpay.test.DTO.TransactionDTO;
import com.saltpay.test.models.TransactionType;
import com.saltpay.test.services.TransactionService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Tests Transaction Controller
 */
@WebMvcTest(TransactionalController.class)
public class TransactionControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private TransactionService transactionService;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    TransactionDTO transaction = new TransactionDTO(UUID.randomUUID(),1000, TransactionType.DEPOSIT);

    /**
     * Test get transaction history via account number
     * @throws Exception
     */
    @Test
    public void testGetTransactionsByAccNo() throws Exception{
        List<TransactionDTO> transactionDTOList = new ArrayList<>(Collections.singletonList(transaction));
        Mockito.when(transactionService.findTransactionsByAccount(1000L)).thenReturn(transactionDTOList);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/transactions/1000")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.message", Matchers.equalTo("Success")));
    }

    /**
     * Test deposit to own personal account
     * @throws Exception
     */
    @Test
    public void testDepositToOwnAccount() throws  Exception{
        AccountTransactionDTO account = new AccountTransactionDTO(1000L, 500);
        Mockito.when(transactionService.depositToOwnAccount(ArgumentMatchers.any()))
                .thenReturn(account);
        String json = "{\"senderAccNo\":1000,\"transactionAmount\":310}";
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/transaction/deposit").contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
                .andExpect(jsonPath("$.message", Matchers.equalTo("Success")));

    }

    /**
     * Test customer/account to customer/account transfer
     * @throws Exception
     */
    @Test
    public void testAccountToAccountTransfer() throws Exception{
        AccountTransactionDTO transactionDTO = new AccountTransactionDTO(1000L,250,1101L);
        Mockito.when(transactionService.accountToAccountTransfer(ArgumentMatchers.any()))
                .thenReturn(transactionDTO);
        String json = "{\"accNo\":1000,\"receiverAccNo\":1101,\"transactionAmount\":490}";
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/transaction/transfer").contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
                .andExpect(jsonPath("$.message", Matchers.equalTo("Success")));
    }
}
