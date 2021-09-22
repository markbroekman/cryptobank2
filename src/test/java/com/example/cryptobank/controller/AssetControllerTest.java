package com.example.cryptobank.controller;

import com.example.cryptobank.domain.Asset;
import com.example.cryptobank.domain.CryptoCurrencyRate;
import com.example.cryptobank.service.AssetService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.assertj.core.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

@WebMvcTest(AssetController.class)
public class AssetControllerTest {

    private final Logger logger = LoggerFactory.getLogger(AssetControllerTest.class);

    @MockBean
    private AssetService assetService;

    private MockMvc mockMvc;

    @Autowired
    public AssetControllerTest(MockMvc mockMvc) {
        super();
        this.mockMvc = mockMvc;
        logger.info("New AssetControllerTest");
    }

    @Test
    void showAssets() {
    }

    @Test
    void showSpecificAssetTest() {
        CryptoCurrencyRate rate = new CryptoCurrencyRate("ADA", 2.65, LocalDateTime.now());
        Mockito.when(assetService.getByAbbreviation("ADA")).thenReturn(new Asset("ADA", "Cardano",
                "ADA", rate));
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders.get("/assets/ADA");
        try {
            ResultActions actions = mockMvc.perform(request);
            MockHttpServletResponse response = actions.andExpect(status().isOk()).andDo(print()).andReturn().getResponse();
            System.out.println(response.getContentAsString());
            assertThat(response.getContentType()).isEqualTo("application/json");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    void showListAbbreviations() {
    }
}
