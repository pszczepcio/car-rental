package com.kodilla.carrental.controller;
import com.google.gson.*;
import com.kodilla.carrental.dto.UpdateCarAndEquipment;
import com.kodilla.carrental.localdateadapter.LocalDateAdapter;
import com.kodilla.carrental.dao.CarDao;
import com.kodilla.carrental.domain.Car;
import com.kodilla.carrental.dto.CarDto;
import com.kodilla.carrental.dto.CreateCarDto;
import com.kodilla.carrental.dto.GetCarDto;
import com.kodilla.carrental.mapper.CarMapper;
import com.kodilla.carrental.service.CarService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.*;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CarController.class)
public class CarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CarService carService;

    @MockBean
    private CarMapper carMapper;

    @MockBean
    private UpdateCarAndEquipment updateCarAndEquipment;

    @Test
    public void shouldFetchEmptyCarsList() throws Exception {
        //Given
        List<CarDto> carDtoList = new ArrayList<>();

        when(carMapper.getCarsDtoList(carService.getCarsList())).thenReturn(carDtoList);

        //When & Then
        mockMvc.perform(get("/v1/cars").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(200))
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void shouldFetchCarsList() throws Exception {
        //Given
        List<CarDto> carDtoList = new ArrayList<>();
        carDtoList.add(new CarDto(2L, "Basic", "Sedan",
                                    "Ford", "Focus", LocalDate.now().minusYears(7),
                                    350.0, "blue", 5, true));

        when(carMapper.getCarsDtoList(carService.getCarsList())).thenReturn(carDtoList);

        //When & Then
        mockMvc.perform(get("/v1/cars").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                //.andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].id", is(2)))
                .andExpect(jsonPath("$[0].carClass", is("Basic")))
                .andExpect(jsonPath("$[0].typeOfCar", is("Sedan")))
                .andExpect(jsonPath("$[0].producer", is("Ford")))
                .andExpect(jsonPath("$[0].model", is("Focus")))
                .andExpect(jsonPath("$[0].dayOfProduction", is(LocalDate.now().minusYears(7).toString())))
                .andExpect(jsonPath("$[0].pricePerDay", is(350.0)))
                .andExpect(jsonPath("$[0].color", is("blue")))
                .andExpect(jsonPath("$[0].numberOfSeats", is(5)))
                .andExpect(jsonPath("$[0].availability", is(true)));
    }

    @Test
    public void shouldGetCarDto() throws Exception {
        //Given
        Car car = new Car().builder()
                .id(3L)
                .carClass("Premium")
                .typeOfCar("Sedan")
                .producer("Ford")
                .model("Focus")
                .availability(true)
                .numberOfSeats(5)
                .color("white")
                .pricePerDay(350)
                .dayOfProduction(LocalDate.of(2010, 1, 1))
                .build();

        GetCarDto getCarDto = new GetCarDto(3L, "Premium", "Sedan",
                                            "Ford", "Focus", LocalDate.of(2010, 1, 1),
                                    350, "white", 5, true, new ArrayList<>());

        when(carMapper.mapToGetCarDto(any(Car.class))).thenReturn(getCarDto);
        when(carService.getCar(car.getId())).thenReturn(Optional.ofNullable(car));

        //When & Then
        mockMvc.perform(get("/v1/cars/3").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(3)))
                .andExpect(jsonPath("$.carClass", is("Premium")))
                .andExpect(jsonPath("$.typeOfCar", is("Sedan")))
                .andExpect(jsonPath("$.producer", is("Ford")))
                .andExpect(jsonPath("$.model", is("Focus")))
                .andExpect(jsonPath("$.dayOfProduction", is(LocalDate.of(2010, 1, 1).toString())))
                .andExpect(jsonPath("$.pricePerDay", is(350.0)))
                .andExpect(jsonPath("$.color", is("white")))
                .andExpect(jsonPath("$.numberOfSeats", is(5)))
                .andExpect(jsonPath("$.availability", is(true)))
                .andExpect(jsonPath("$.additionalEquipmentId", is(new ArrayList())));
    }

    @Test
    public void shouldCreateCar() throws Exception {
//        //Given
        CreateCarDto createCarDto = new CreateCarDto(
                "Premium",
                "Sedan",
                "Skoda",
                "Superb",
                LocalDate.of(2015, 10, 23),
                450,
                "Black",
                5,
                true
                 );

        Car car = new Car().builder()
                .id(3L)
                .carClass("Premium")
                .typeOfCar("Sedan")
                .producer("Skoda")
                .model("Superb")
                .availability(true)
                .numberOfSeats(5)
                .color("Black")
                .pricePerDay(450)
                .equipments(new ArrayList<>())
                .dayOfProduction(LocalDate.of(2015, 10, 23))
                .build();

        when(carService.saveCar(carMapper.mapToCar(createCarDto))).thenReturn(car);

        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
                .create();

        String jsonContent = gson.toJson(car);

        //when & Then
        mockMvc.perform(post("/v1/cars")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id",is(3)))
                .andExpect(jsonPath("$.carClass",is("Premium")))
                .andExpect(jsonPath("$.typeOfCar",is("Sedan")))
                .andExpect(jsonPath("$.producer",is("Skoda")))
                .andExpect(jsonPath("$.model",is("Superb")))
                .andExpect(jsonPath("$.availability",is(true)))
                .andExpect(jsonPath("$.numberOfSeats",is(5)))
                .andExpect(jsonPath("$.color",is("Black")))
                .andExpect(jsonPath("$.pricePerDay",is(450.0)))
                .andExpect(jsonPath("$.dayOfProduction",is(LocalDate.of(2015, 10, 23).toString())));
    }

    @Test
    public void shouldUpdateCarStatus() throws Exception {
        //Given
        GetCarDto getCarDto = new GetCarDto(1L, "Premium", "Sedan",
                "Ford", "Focus", LocalDate.of(2015, 10, 23),
                450, "Black", 5, true, new ArrayList<>());
        Long carId = 1L;
        boolean status = true;

        Car car = new Car().builder()
                .id(1L)
                .carClass("Premium")
                .typeOfCar("Sedan")
                .producer("Ford")
                .model("Focus")
                .availability(true)
                .numberOfSeats(5)
                .color("Black")
                .pricePerDay(450)
                .dayOfProduction(LocalDate.of(2015, 10, 23))
                .build();

        when(carMapper.mapToGetCarDto(any(Car.class))).thenReturn(getCarDto);
        when(carService.updateStatus(carId, status)).thenReturn(car);

        //When & Then

        mockMvc.perform(put("/v1/cars/1/status/true")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id",is(1)))
                .andExpect(jsonPath("$.carClass",is("Premium")))
                .andExpect(jsonPath("$.typeOfCar",is("Sedan")))
                .andExpect(jsonPath("$.producer",is("Ford")))
                .andExpect(jsonPath("$.model",is("Focus")))
                .andExpect(jsonPath("$.availability",is(true)))
                .andExpect(jsonPath("$.numberOfSeats",is(5)))
                .andExpect(jsonPath("$.color",is("Black")))
                .andExpect(jsonPath("$.pricePerDay",is(450.0)))
                .andExpect(jsonPath("$.dayOfProduction",is(LocalDate.of(2015, 10, 23).toString())));

    }

    @Test
    public void shouldUpdateCarEquipment() throws Exception {
        //Given
        List<Long> equipmentId = new ArrayList<>();
        equipmentId.add(1L);
        equipmentId.add(2L);

        UpdateCarAndEquipment updateCarAndEquipment = new UpdateCarAndEquipment(
               equipmentId,
                1L
        );

        doNothing().when(carService).addEquipmentToCar(updateCarAndEquipment);

        Gson gson = new Gson();
        String jsonContent = gson.toJson(updateCarAndEquipment);

        //When & Then
        mockMvc.perform(put("/v1/car")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDeleteCar() throws Exception {
        //Given

        //When & Then
        mockMvc.perform(delete("/v1/cars/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void shouldUpdatePrize() throws Exception {
        //Given
        GetCarDto getCarDto = new GetCarDto(1L, "Premium", "Sedan",
                "Ford", "Focus", LocalDate.of(2015, 10, 23),
                500.0, "Black", 5, true, new ArrayList<>());

        Long carId = 1L;
        double prize = 500.0;

        when(carMapper.mapToGetCarDto(carService.updatePrize(carId,prize))).thenReturn(getCarDto);

        //When & Then
        mockMvc.perform(put("/v1/cars/1/prize/500")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pricePerDay",is(500.0)));
    }

    @Test
    public void shouldReturningTheCar() throws Exception {
        //Given
        GetCarDto getCarDto = new GetCarDto(1L, "Premium", "Sedan",
                "Ford", "Focus", LocalDate.of(2015, 10, 23),
                500.0, "Black", 5, true, new ArrayList<>());

        Long carId = 1L;

        when(carMapper.mapToGetCarDto(carService.returnCarToRental(carId))).thenReturn(getCarDto);

        //When & Then
        mockMvc.perform(put("/v1/cars/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.additionalEquipmentId",is(new ArrayList())));
    }

}