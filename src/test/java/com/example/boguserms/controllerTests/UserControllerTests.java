package com.example.boguserms.controllerTests;

import com.example.boguserms.controller.UserController;
import com.example.boguserms.dto.UserRequestDTO;
import com.example.boguserms.dto.UserResponseDTO;
import com.example.boguserms.security.config.SecurityConfig;
import com.example.boguserms.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
//@WebMvcTest(value = UserController.class)
public class UserControllerTests {

    //@MockBean
    private UserService userService;
    private UserController userController;
    //@Autowired
    private MockMvc mockMvc;
    private final ObjectMapper mapper = new ObjectMapper();

    public UserControllerTests() {
        userService = Mockito.mock(UserService.class);
        userController = new UserController(userService);
    }

    @Before
    public void init() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(userController)
                .build();
    }

    @Test
    public void givenUserRequestDTO_whenCreateUser_thenReturnUserResponseDTOWithHttp201() throws Exception {
        UserRequestDTO testUserRequest = UserRequestDTO.builder()
                .login("validLogin")
                .email("validEmail@gmail.com")
                .password("validPassword!@1")
                .firstName("Validname")
                .lastName("Validlastname")
                .build();
        UserResponseDTO expectedResult = UserResponseDTO.builder()
                .userId("d8ad87ab-04a4-4438-9ff6-34bad41a8a31")
                .build();

        doReturn(expectedResult).when(userService).createUser(testUserRequest);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/user")
                .content(mapper.writeValueAsString(testUserRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());

    }

}
