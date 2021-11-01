package com.saltpay.test.integration;

import com.saltpay.test.Controllers.CustomerController;
import com.saltpay.test.DTO.CustomerDTO;
import com.saltpay.test.models.Customer;
import com.saltpay.test.services.CustomerService;
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
import java.util.List;

import static org.hamcrest.Matchers.notNullValue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
@ContextConfiguration()
public class CustomerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    private WebApplicationContext webApplicationContext;

    @MockBean
    private CustomerService customerService;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    CustomerDTO customer = new CustomerDTO(1L,"Arisha Barron", "arisha@gmail.com", 123456);
    CustomerDTO customer1 = new CustomerDTO(2L,"Branden Gibson", "branden@gmail.com", 13456);

    @Test
    public void testGetCustomers() throws Exception{
        List<CustomerDTO> customerDTOList = new ArrayList<>(Arrays.asList(customer,customer1));
        Mockito.when(customerService.getAllCustomers()).thenReturn(customerDTOList);
        mockMvc.perform(MockMvcRequestBuilders.
                get("/api/v1/customers").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)));

    }
    @Test
    public void testPostCustomer() throws Exception{
        Customer customer1 = new Customer(1L,"Branden Gibson", "branden@gmail.com", 13456);
        Mockito.when(customerService.save(ArgumentMatchers.any()))
                .thenReturn(customer1);
        String json = "{\"customerName\":\"Branden Gibson\",\"customerEmail\":\"Branden@gmail.com\",\"customerPhone\":1237789}";
        mockMvc.perform(MockMvcRequestBuilders
                .post("/api/v1/customers").contentType(MediaType.APPLICATION_JSON)
                .content(json)
                .accept(MediaType.APPLICATION_JSON)).andExpect(status().isCreated())
                .andExpect(jsonPath("$.customerId", Matchers.equalTo(1)))
                .andExpect(jsonPath("$.customerName", Matchers.equalTo("Branden Gibson")));

    }

    @Test
    public void testGetCustomerById() throws Exception{
        CustomerDTO customer = new CustomerDTO(1L,"Arisha Barron", "arisha@gmail.com", 123456);

        Mockito.when(customerService.getCustomerById(1L)).thenReturn(customer);

        mockMvc.perform(MockMvcRequestBuilders
                .get("/api/v1/customer/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.data.customerName", Matchers.equalTo("Arisha Barron")));
    }

}
