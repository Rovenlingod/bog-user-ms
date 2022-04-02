package com.example.boguserms.controllerTests;

import com.example.boguserms.controller.UserController;
import com.example.boguserms.dto.UserRequestDTO;
import com.example.boguserms.dto.UserResponseDTO;
import com.example.boguserms.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.doReturn;

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
    public void givenUserRequestDTO_whenCreateUser_thenReturnHttp201() throws Exception {
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

    @Test
    public void givenEmptyLogin_whenCreateUser_thenReturnHttp400() throws Exception {
        UserRequestDTO testUserRequest = UserRequestDTO.builder()
                .login("")
                .email("validEmail@gmail.com")
                .password("validPassword!@1")
                .firstName("Validname")
                .lastName("Validlastname")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/user")
                .content(mapper.writeValueAsString(testUserRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    public void givenEmptyPassword_whenCreateUser_thenReturnHttp400() throws Exception {
        UserRequestDTO testUserRequest = UserRequestDTO.builder()
                .login("validLogin")
                .email("validEmail@gmail.com")
                .password("")
                .firstName("Validname")
                .lastName("Validlastname")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/user")
                .content(mapper.writeValueAsString(testUserRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    public void givenPasswordWithoutDigits_whenCreateUser_thenReturnHttp400() throws Exception {
        UserRequestDTO testUserRequest = UserRequestDTO.builder()
                .login("validLogin")
                .email("validEmail@gmail.com")
                .password("invalidPassword@")
                .firstName("Validname")
                .lastName("Validlastname")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/user")
                .content(mapper.writeValueAsString(testUserRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    public void givenPasswordWithoutSymbols_whenCreateUser_thenReturnHttp400() throws Exception {
        UserRequestDTO testUserRequest = UserRequestDTO.builder()
                .login("validLogin")
                .email("validEmail@gmail.com")
                .password("invalidPassword1")
                .firstName("Validname")
                .lastName("Validlastname")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/user")
                .content(mapper.writeValueAsString(testUserRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    public void givenPasswordWithoutLetters_whenCreateUser_thenReturnHttp400() throws Exception {
        UserRequestDTO testUserRequest = UserRequestDTO.builder()
                .login("validLogin")
                .email("validEmail@gmail.com")
                .password("@@!!!@@@123")
                .firstName("Validname")
                .lastName("Validlastname")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/user")
                .content(mapper.writeValueAsString(testUserRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    public void givenPasswordWithLessThan6Characters_whenCreateUser_thenReturnHttp400() throws Exception {
        UserRequestDTO testUserRequest = UserRequestDTO.builder()
                .login("validLogin")
                .email("validEmail@gmail.com")
                .password("abc1@")
                .firstName("Validname")
                .lastName("Validlastname")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/user")
                .content(mapper.writeValueAsString(testUserRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    public void givenInvalidEmail1_whenCreateUser_thenReturnHttp400() throws Exception {
        UserRequestDTO testUserRequest1 = UserRequestDTO.builder()
                .login("validLogin")
                .email("invalidEmailgmail.com")
                .password("validPassword!@1")
                .firstName("Validname")
                .lastName("Validlastname")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/user")
                .content(mapper.writeValueAsString(testUserRequest1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void givenInvalidEmail2_whenCreateUser_thenReturnHttp400() throws Exception {
        UserRequestDTO testUserRequest2 = UserRequestDTO.builder()
                .login("validLogin")
                .email("invalidEmail@gmail")
                .password("validPassword!@1")
                .firstName("Validname")
                .lastName("Validlastname")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/user")
                .content(mapper.writeValueAsString(testUserRequest2))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void givenInvalidEmail3_whenCreateUser_thenReturnHttp400() throws Exception {
        UserRequestDTO testUserRequest3 = UserRequestDTO.builder()
                .login("validLogin")
                .email("invalidEmail@gmail.")
                .password("validPassword!@1")
                .firstName("Validname")
                .lastName("Validlastname")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/user")
                .content(mapper.writeValueAsString(testUserRequest3))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void givenInvalidEmail4_whenCreateUser_thenReturnHttp400() throws Exception {
        UserRequestDTO testUserRequest4 = UserRequestDTO.builder()
                .login("validLogin")
                .email("@gmail.com")
                .password("validPassword!@1")
                .firstName("Validname")
                .lastName("Validlastname")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/user")
                .content(mapper.writeValueAsString(testUserRequest4))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void givenEmptyEmail_whenCreateUser_thenReturnHttp400() throws Exception {
        UserRequestDTO testUserRequest = UserRequestDTO.builder()
                .login("validLogin")
                .email("")
                .password("validPassword!@1")
                .firstName("Validname")
                .lastName("Validlastname")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/user")
                .content(mapper.writeValueAsString(testUserRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    public void givenEmptyFirstName_whenCreateUser_thenReturnHttp400() throws Exception {
        UserRequestDTO testUserRequest = UserRequestDTO.builder()
                .login("validLogin")
                .email("validEmail@gmail.com")
                .password("validPassword!@1")
                .firstName("")
                .lastName("Validlastname")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/user")
                .content(mapper.writeValueAsString(testUserRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    public void givenInvalidFirstName_whenCreateUser_thenReturnHttp400() throws Exception {
        UserRequestDTO testUserRequest = UserRequestDTO.builder()
                .login("validLogin")
                .email("validEmail@gmail.com")
                .password("validPassword!@1")
                .firstName("invalidname")
                .lastName("Validlastname")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/user")
                .content(mapper.writeValueAsString(testUserRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    public void givenEmptyLastName_whenCreateUser_thenReturnHttp400() throws Exception {
        UserRequestDTO testUserRequest = UserRequestDTO.builder()
                .login("validLogin")
                .email("validEmail@gmail.com")
                .password("validPassword!@1")
                .firstName("Validname")
                .lastName("")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/user")
                .content(mapper.writeValueAsString(testUserRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    public void givenInvalidLastName_whenCreateUser_thenReturnHttp400() throws Exception {
        UserRequestDTO testUserRequest = UserRequestDTO.builder()
                .login("validLogin")
                .email("validEmail@gmail.com")
                .password("validPassword!@1")
                .firstName("Validname")
                .lastName("invlaidlastname")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/user")
                .content(mapper.writeValueAsString(testUserRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    public void givenValidStringUUID_whenGetUserById_thenReturnUserResponseDTOWithHttp201() throws Exception {
        String validId = "d8ad87ab-04a4-4438-9ff6-34bad41a8a31";
        UserResponseDTO expectedResult = UserResponseDTO.builder()
                .userId("d8ad87ab-04a4-4438-9ff6-34bad41a8a31")
                .build();

        doReturn(expectedResult).when(userService).findByUserId(validId);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/{id}", validId))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.userId").value(validId));

    }

    @Test
    public void givenEmptyUUID_whenGetUserById_thenReturnHttp400() throws Exception {
        String id = "";

        mockMvc.perform(MockMvcRequestBuilders.get("/api/user/{id}", id))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void givenUserRequestDTO_whenUpdateUser_thenReturnHttp202() throws Exception {
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

        doReturn(expectedResult).when(userService).updateUser(testUserRequest);

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/user")
                .content(mapper.writeValueAsString(testUserRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isAccepted());

    }

    @Test
    public void givenEmptyLogin_whenUpdateUser_thenReturnHttp400() throws Exception {
        UserRequestDTO testUserRequest = UserRequestDTO.builder()
                .login("")
                .email("validEmail@gmail.com")
                .password("validPassword!@1")
                .firstName("Validname")
                .lastName("Validlastname")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/user")
                .content(mapper.writeValueAsString(testUserRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    public void givenEmptyPassword_whenUpdateUser_thenReturnHttp400() throws Exception {
        UserRequestDTO testUserRequest = UserRequestDTO.builder()
                .login("validLogin")
                .email("validEmail@gmail.com")
                .password("")
                .firstName("Validname")
                .lastName("Validlastname")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/user")
                .content(mapper.writeValueAsString(testUserRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    public void givenPasswordWithoutDigits_whenUpdateUser_thenReturnHttp400() throws Exception {
        UserRequestDTO testUserRequest = UserRequestDTO.builder()
                .login("validLogin")
                .email("validEmail@gmail.com")
                .password("invalidPassword@")
                .firstName("Validname")
                .lastName("Validlastname")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/user")
                .content(mapper.writeValueAsString(testUserRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    public void givenPasswordWithoutSymbols_whenUpdateUser_thenReturnHttp400() throws Exception {
        UserRequestDTO testUserRequest = UserRequestDTO.builder()
                .login("validLogin")
                .email("validEmail@gmail.com")
                .password("invalidPassword1")
                .firstName("Validname")
                .lastName("Validlastname")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/user")
                .content(mapper.writeValueAsString(testUserRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    public void givenPasswordWithoutLetters_whenUpdateUser_thenReturnHttp400() throws Exception {
        UserRequestDTO testUserRequest = UserRequestDTO.builder()
                .login("validLogin")
                .email("validEmail@gmail.com")
                .password("@@!!!@@@123")
                .firstName("Validname")
                .lastName("Validlastname")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/user")
                .content(mapper.writeValueAsString(testUserRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    public void givenPasswordWithLessThan6Characters_whenUpdateUser_thenReturnHttp400() throws Exception {
        UserRequestDTO testUserRequest = UserRequestDTO.builder()
                .login("validLogin")
                .email("validEmail@gmail.com")
                .password("abc1@")
                .firstName("Validname")
                .lastName("Validlastname")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/user")
                .content(mapper.writeValueAsString(testUserRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    public void givenInvalidEmail1_whenUpdateUser_thenReturnHttp400() throws Exception {
        UserRequestDTO testUserRequest1 = UserRequestDTO.builder()
                .login("validLogin")
                .email("invalidEmailgmail.com")
                .password("validPassword!@1")
                .firstName("Validname")
                .lastName("Validlastname")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/user")
                .content(mapper.writeValueAsString(testUserRequest1))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void givenInvalidEmail2_whenUpdateUser_thenReturnHttp400() throws Exception {
        UserRequestDTO testUserRequest2 = UserRequestDTO.builder()
                .login("validLogin")
                .email("invalidEmail@gmail")
                .password("validPassword!@1")
                .firstName("Validname")
                .lastName("Validlastname")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/user")
                .content(mapper.writeValueAsString(testUserRequest2))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void givenInvalidEmail3_whenUpdateUser_thenReturnHttp400() throws Exception {
        UserRequestDTO testUserRequest3 = UserRequestDTO.builder()
                .login("validLogin")
                .email("invalidEmail@gmail.")
                .password("validPassword!@1")
                .firstName("Validname")
                .lastName("Validlastname")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/user")
                .content(mapper.writeValueAsString(testUserRequest3))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void givenInvalidEmail4_whenUpdateUser_thenReturnHttp400() throws Exception {
        UserRequestDTO testUserRequest4 = UserRequestDTO.builder()
                .login("validLogin")
                .email("@gmail.com")
                .password("validPassword!@1")
                .firstName("Validname")
                .lastName("Validlastname")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/user")
                .content(mapper.writeValueAsString(testUserRequest4))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    public void givenEmptyEmail_whenUpdateUser_thenReturnHttp400() throws Exception {
        UserRequestDTO testUserRequest = UserRequestDTO.builder()
                .login("validLogin")
                .email("")
                .password("validPassword!@1")
                .firstName("Validname")
                .lastName("Validlastname")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/user")
                .content(mapper.writeValueAsString(testUserRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    public void givenEmptyFirstName_whenUpdateUser_thenReturnHttp400() throws Exception {
        UserRequestDTO testUserRequest = UserRequestDTO.builder()
                .login("validLogin")
                .email("validEmail@gmail.com")
                .password("validPassword!@1")
                .firstName("")
                .lastName("Validlastname")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/user")
                .content(mapper.writeValueAsString(testUserRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    public void givenInvalidFirstName_whenUpdateUser_thenReturnHttp400() throws Exception {
        UserRequestDTO testUserRequest = UserRequestDTO.builder()
                .login("validLogin")
                .email("validEmail@gmail.com")
                .password("validPassword!@1")
                .firstName("invalidname")
                .lastName("Validlastname")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/user")
                .content(mapper.writeValueAsString(testUserRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    public void givenEmptyLastName_whenUpdateUser_thenReturnHttp400() throws Exception {
        UserRequestDTO testUserRequest = UserRequestDTO.builder()
                .login("validLogin")
                .email("validEmail@gmail.com")
                .password("validPassword!@1")
                .firstName("Validname")
                .lastName("")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/user")
                .content(mapper.writeValueAsString(testUserRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

    @Test
    public void givenInvalidLastName_whenUpdateUser_thenReturnHttp400() throws Exception {
        UserRequestDTO testUserRequest = UserRequestDTO.builder()
                .login("validLogin")
                .email("validEmail@gmail.com")
                .password("validPassword!@1")
                .firstName("Validname")
                .lastName("invlaidlastname")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.patch("/api/user")
                .content(mapper.writeValueAsString(testUserRequest))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());

    }

}
