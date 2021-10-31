package com.saltpay.test.integration;

import com.saltpay.test.Controllers.AccountController;
import com.saltpay.test.DTO.AccountDTO;
import com.saltpay.test.models.Account;
import com.saltpay.test.models.Customer;
import com.saltpay.test.services.AccountService;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AccountController.class)
@ContextConfiguration()

public class AccountControllerTest {
    @Autowired
    MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private AccountService accountService;

    @MockBean
    Customer customer;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testCreateAccount() throws Exception{
        Account account = new Account("Savings", "Nairobi", 1000, new Customer(1L));
        Mockito.when(accountService.saveAccount(ArgumentMatchers.any()))
                .thenReturn(account);
        String json = "{\"accName\":\"Savings\",\"accBranch\":\"Nairobi\",\"minBalance\":1000,\"customer\":{\"customerId\":1}}";
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/accounts").contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()))

        ;
    }

}
