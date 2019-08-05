package com.kodilla.carrental.controller;

import com.google.gson.Gson;
import com.kodilla.carrental.domain.AdditionalEquipment;
import com.kodilla.carrental.dto.CreateEquipmentDto;
import com.kodilla.carrental.dto.EquipmentDto;
import com.kodilla.carrental.dto.GetEquipmentDto;
import com.kodilla.carrental.mapper.AdditionalEquipmentMapper;
import com.kodilla.carrental.service.AdditionalEquipmentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(AdditionalEquipmentController.class)
public class AdditionalEquipmentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdditionalEquipmentService additionalEquipmentService;

    @MockBean
    private AdditionalEquipmentMapper additionalEquipmentMapper;

    @Test
    public void shouldFetchEmptyEquipmentListTest() throws Exception {
        //Given
        List<GetEquipmentDto> getEquipmentDtoList = new ArrayList<>();
        List<AdditionalEquipment> additionalEquipmentList = new ArrayList<>();
        when(additionalEquipmentMapper.mapToEquipmentDtoList(additionalEquipmentService.getEquipmentList())).thenReturn(getEquipmentDtoList);

        //When & Then
        mockMvc.perform(get("/v1/equipments").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void shouldGetEquipmentDtoListTest() throws Exception {
        //Given
        List<GetEquipmentDto> getEquipmentDtoList = new ArrayList<>();
        getEquipmentDtoList.add(new GetEquipmentDto(1L, "cb-radio", 25.0));

        when(additionalEquipmentMapper.mapToEquipmentDtoList(additionalEquipmentService.getEquipmentList())).thenReturn(getEquipmentDtoList);

        //When & Then
        mockMvc.perform(get("/v1/equipments").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].equipment", is("cb-radio")))
                .andExpect(jsonPath("$[0].prize", is(25.0)));
    }

    @Test
    public void shouldGetEquipmentDtoTest() throws Exception {
        //Given
        EquipmentDto equipmentDto = new EquipmentDto(
                1L,
                "cb-radio",
                35.0,
                new ArrayList<>()
        );

        AdditionalEquipment additionalEquipment = new AdditionalEquipment(
                1L,
                "cb-radio",
                35.0,
                new ArrayList<>()
        );

        when(additionalEquipmentMapper.mapToEquipmentDto(any(AdditionalEquipment.class))).thenReturn(equipmentDto);
        when(additionalEquipmentService.getEquipment(equipmentDto.getId())).thenReturn(Optional.of(additionalEquipment));

        //When & Then
        mockMvc.perform(get("/v1/equipments/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.equipment", is("cb-radio")))
                .andExpect(jsonPath("$.prize", is(35.0)));
    }

    @Test
    public void shouldCreateEquipmentTest() throws Exception {
        //Given
        CreateEquipmentDto createEquipmentDto = new CreateEquipmentDto(
                1L,
                "cb-radio",
                30.0,
                new ArrayList<>()
        );

        AdditionalEquipment additionalEquipment = new AdditionalEquipment(
                1L,
                "cb-radio",
                30.0,
                new ArrayList<>()
        );
        when(additionalEquipmentService.saveEquipment(additionalEquipmentMapper.mapToAdditionalEquipment(createEquipmentDto))).thenReturn(additionalEquipment);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(createEquipmentDto);

        //when & Then
        mockMvc.perform(post("/v1/equipments")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(jsonPath("$.id",is(1)))
                .andExpect(jsonPath("$.equipment",is("cb-radio")))
                .andExpect(jsonPath("$.prize",is(30.0)));
    }

    @Test
    public void shouldDeleteEquipmentTest() throws Exception {
        //Given

        //When & Then
        mockMvc.perform(delete("/v1/equipments/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}