package com.kodilla.carrental.controller;

import com.google.gson.Gson;
import com.kodilla.carrental.domain.User;
import com.kodilla.carrental.dto.CreateUserDto;
import com.kodilla.carrental.dto.UserDto;
import com.kodilla.carrental.dto.UserDtoList;
import com.kodilla.carrental.mapper.UserMapper;
import com.kodilla.carrental.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import java.util.*;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;



@RunWith(SpringRunner.class)
@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserMapper userMapper;

    @Test
    public void shouldGetUsers() throws Exception {
        //Given
        List<UserDtoList> userDtoLists = new ArrayList<>();
        userDtoLists.add(new UserDtoList(1L, "name", "lastname",
                                        111111111, "email@email.com", false));

        when(userMapper.getUsersDtoList(userService.getUsersList())).thenReturn(userDtoLists);

        //When & then
        mockMvc.perform(get("/v1/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].name", is("name")))
                .andExpect(jsonPath("$[0].surname", is("lastname")))
                .andExpect(jsonPath("$[0].phone", is(111111111)))
                .andExpect(jsonPath("$[0].email", is("email@email.com")))
                .andExpect(jsonPath("$[0].loginStatus", is(false)));
    }

    @Test
    public void getUserDto() throws Exception {
        //Given
        UserDto userDto = new UserDto(
                1L, "name", "surname",
                123456789, "email@email.com",
                true, new ArrayList<>() , new ArrayList<>()
        );

        Long userId = 1L;

        when(userMapper.mapToUserDto(userService.getUser(userId))).thenReturn(userDto);

        //When & Then
        mockMvc.perform(get("/v1/users/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("name")))
                .andExpect(jsonPath("$.surname", is("surname")))
                .andExpect(jsonPath("$.phone", is(123456789)))
                .andExpect(jsonPath("$.email", is("email@email.com")))
                .andExpect(jsonPath("$.loginStatus", is(true)));
    }

    @Test
    public void shouldCreateUser() throws Exception {
        //Given
        CreateUserDto createUserDto = new CreateUserDto(
                "name", "surname", 123456789,
                "email@email.com", "password"
        );

        User user = new User(
                1L,
                "name",
                "surname",
                123456789,
                "email@email.com",
                "password",
                false,
                new ArrayList<>(),
                new ArrayList<>()
        );

        when(userService.saveUser(userMapper.mapToUser(createUserDto))).thenReturn(user);
        Gson gson = new Gson();
        String jsonContent = gson.toJson(createUserDto);

        //When & Then
        mockMvc.perform(post("/v1/users")
                .contentType(MediaType.APPLICATION_JSON)
                .characterEncoding("UTF-8")
                .content(jsonContent))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("name")))
                .andExpect(jsonPath("$.surname", is("surname")))
                .andExpect(jsonPath("$.phone", is(123456789)))
                .andExpect(jsonPath("$.email", is("email@email.com")))
                .andExpect(jsonPath("$.password", is("password")))
                .andExpect(jsonPath("$.loginStatus", is(false)))
                .andExpect(jsonPath("$.orderList", is(new ArrayList())))
                .andExpect(jsonPath("$.invoiceList", is(new ArrayList())));
    }

    @Test
    public void createUser() {
    }

    @Test
    public void shouldDeleteUser() throws Exception {
        //Given

        //When & Then
            mockMvc.perform(delete("/v1/users/1")
                    .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(status().isOk());

    }
}