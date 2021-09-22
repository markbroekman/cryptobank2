package com.example.cryptobank.controller;

import com.example.cryptobank.domain.Customer;
import com.example.cryptobank.dto.CustomerDto;
import com.example.cryptobank.dto.RegistrationDto;
import com.example.cryptobank.service.CustomerService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    private  MockMvc mockMvc;

    @MockBean
    private CustomerService customerServiceTest;

    @Autowired
    public CustomerControllerTest( MockMvc mockMvc) {
        this.mockMvc = mockMvc;
    }

    @Test
    public void registerTest() throws Exception {
//        RegistrationDto customerRegistrationDto = new RegistrationDto("Branko",
//                "Beunhaas123", "Branko",
//                "Visser", LocalDate.parse("1991-08-01"),
//                365478912, "Lisstraat",
//                "3037RC", 45,
//                "a", "Rotterdam",
//                "visserbrankie@gmail.com");
//        Customer customerToRegister = new Customer(customerRegistrationDto.getUsername().toLowerCase(),
//                customerRegistrationDto.getPassword(), customerRegistrationDto.getFirstName(),
//                customerRegistrationDto.getLastName(), customerRegistrationDto.getDateOfBirth(),
//                customerRegistrationDto.getSocialSecurityNumber(), customerRegistrationDto.getStreet(),
//                customerRegistrationDto.getZipcode(), customerRegistrationDto.getHousenumber(),
//                customerRegistrationDto.getAddition(), customerRegistrationDto.getCity(),
//                customerRegistrationDto.getEmail().toLowerCase());

     Customer klantje =   new Customer("Branko",
                "Beunhaas123", "Branko",
                "Visser", LocalDate.parse("1991-08-01"),
                365478912, "Lisstraat",
                "3037RC", 45,
                "a", "Rotterdam",
                "visserbrankie@gmail.com");

            Mockito.when(customerServiceTest.register(klantje)).thenReturn(klantje);
            MockHttpServletRequestBuilder request = MockMvcRequestBuilders.post("/register");
            request.content(klantje.toString());

            try {
                ResultActions resultActions = mockMvc.perform(request);
                MockHttpServletResponse response = resultActions
                        .andExpect(status().isOk())
                        .andDo(print())
                        .andReturn().getResponse();
                System.out.println(response.getContentAsString());
                assertThat(response.getContentType()).isEqualTo("application/json");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }

