package com.example.vvsdemo;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
public class ViewControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("GetHomePage -> status 200")
    public void whenGetHomePage_then200() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/")).andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("index"));

    }

    @Test
    @DisplayName("GetAddPiecePage -> status 200")
    public void whenGetAddPiecePage_then200() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/new")).andExpect(status().isOk())
                .andExpect(content().contentType("text/html;charset=UTF-8"))
                .andExpect(view().name("new_piece"));

    }

    @Test
    @DisplayName("SavePage -> status 302")
    public void whenPostSaveUrl_then302() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/save")).andExpect(status().isFound())
                .andExpect(view().name("redirect:/"));

    }

    @Test
    @DisplayName("WrongPage -> status 404")
    public void whenGetWrongPage_then404() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.get("/wrong")).andExpect(status().isNotFound());

    }

}
