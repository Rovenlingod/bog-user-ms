package com.example.boguserms.serviceTests;

import com.example.boguserms.domain.User;
import com.example.boguserms.dto.*;
import com.example.boguserms.exception.InvalidUUIDException;
import com.example.boguserms.exception.UserAlreadyExistsException;
import com.example.boguserms.mapper.UserMapper;
import com.example.boguserms.repository.UserRepository;
import com.example.boguserms.service.UserService;
import com.example.boguserms.service.UserServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.doReturn;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTests {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;

    @Test
    public void givenValidExistentId_whenFindByUserId_thenReturnUserResponseDTO() {
        String testId = "d8ad87ab-04a4-4438-9ff6-34bad41a8a31";

        UserResponseDTO expectedResult = UserResponseDTO.builder()
                .userId(testId).build();
        User testUser = User.builder()
                .userId(UUID.fromString(testId))
                .build();

        doReturn(Optional.of(testUser)).when(userRepository).findById(UUID.fromString(testId));
        doReturn(expectedResult).when(userMapper).userToUserResponseDTO(testUser);

        UserResponseDTO actualResult = userService.findByUserId(testId);

        assertEquals(expectedResult, actualResult);
    }

    @Test(expected = ResponseStatusException.class)
    public void givenValidNonExistentId_whenFindByUserId_thenThrowResponseStatusException() {
        String testId = "d8ad87ab-04a4-4438-9ff6-34bad41a8a31";

        doReturn(Optional.empty()).when(userRepository).findById(UUID.fromString(testId));

        userService.findByUserId(testId);
    }

    @Test(expected = InvalidUUIDException.class)
    public void givenInvalidId_whenFindByUserId_thenThrowInvalidUUIDException() {
        String testId = "testId";
        userService.findByUserId(testId);
    }

    @Test(expected = InvalidUUIDException.class)
    public void givenNullAsAStringId_whenFindByUserId_thenThrowInvalidUUIDException() {
        String testId = "null";
        userService.findByUserId(testId);
    }

    @Test(expected = InvalidUUIDException.class)
    public void givenEmptyStringId_whenFindByUserId_thenThrowInvalidUUIDException() {
        userService.findByUserId("");
    }

    @Test(expected = InvalidUUIDException.class)
    public void givenNullId_whenFindByUserId_thenThrowInvalidUUIDException() {
        userService.findByUserId(null);
    }

    @Test(expected = InvalidUUIDException.class)
    public void givenSqlInjectionId_whenFindByUserId_thenThrowInvalidUUIDException() {
        String testId = "d8ad87ab-04a4-4438-9ff6-34bad41a8a31 OR 1 = 1";
        userService.findByUserId(testId);
    }

    @Test
    public void givenValidExistentLogin_whenFindByLogin_thenReturnLoginSearchResponseDTO() {
        String testLogin = "validExistentLogin";

        LoginSearchResponseDTO expectedResult = LoginSearchResponseDTO.builder()
                .login("validExistentLogin")
                .build();
        User testUser = User.builder()
                .login("validExistentLogin")
                .build();

        doReturn(Optional.of(testUser)).when(userRepository).findByLogin(testLogin);
        doReturn(expectedResult).when(userMapper).toLoginDTO(testUser);

        LoginSearchResponseDTO actualResult = userService.findByUserLogin(testLogin);

        assertEquals(expectedResult, actualResult);
    }

    @Test(expected = ResponseStatusException.class)
    public void givenValidNonExistentLogin_whenFindByUserLogin_thenThrowResponseStatusException() {
        String testLogin = "validNonExistentLogin";

        doReturn(Optional.empty()).when(userRepository).findByLogin(testLogin);

        userService.findByUserLogin(testLogin);
    }

    @Test(expected = ResponseStatusException.class)
    public void givenNullAsAStringLogin_whenFindByUserLogin_thenThrowResponseStatusException() {
        String testLogin = "null";
        userService.findByUserLogin(testLogin);
    }

    @Test(expected = ResponseStatusException.class)
    public void givenEmptyStringLogin_whenFindByUserLogin_thenThrowResponseStatusException() {
        userService.findByUserLogin("");
    }

    @Test(expected = ResponseStatusException.class)
    public void givenNullLogin_whenFindByUserLogin_thenThrowResponseStatusException() {
        userService.findByUserLogin(null);
    }

    @Test
    public void givenValidNonExistentUserRequestDTO_whenCreateUser_thenReturnUserResponseDTO() {
        UserRequestDTO testUserRequest = UserRequestDTO.builder()
                .login("validLogin")
                .email("validEmail@gmail.com")
                .build();

        UserResponseDTO expectedResult = UserResponseDTO.builder()
                .userId("d8ad87ab-04a4-4438-9ff6-34bad41a8a31")
                .build();
        User testUserBeforeCreation = User.builder()
                .login("validLogin")
                .email("validEmail@gmail.com")
                .build();
        User testUserAfterCreation = User.builder()
                .userId(UUID.fromString("d8ad87ab-04a4-4438-9ff6-34bad41a8a31"))
                .login("validLogin")
                .email("validEmail@gmail.com")
                .build();

        doReturn(testUserAfterCreation).when(userRepository).save(testUserBeforeCreation);
        doReturn(expectedResult).when(userMapper).userToUserResponseDTO(testUserAfterCreation);
        doReturn(testUserBeforeCreation).when(userMapper).userRequestDTOToUser(testUserRequest);

        UserResponseDTO actualResult = userService.createUser(testUserRequest);

        assertEquals(expectedResult, actualResult);
    }

    @Test(expected = UserAlreadyExistsException.class)
    public void givenValidExistentUserRequestDTO_whenCreateUser_thenThrowUserAlreadyExistsException() {
        UserRequestDTO testUserRequest = UserRequestDTO.builder()
                .login("validTakenLogin")
                .email("validTakenEmail@gmail.com")
                .build();

        doReturn(Optional.of(User.builder()
                .login("validTakenLogin")
                .email("validTakenEmail@gmail.com")
                .build())).when(userRepository).findByLogin(testUserRequest.getLogin());

        userService.createUser(testUserRequest);
    }

    @Test(expected = NullPointerException.class)
    public void givenNullUserRequestDTO_whenCreateUser_thenThrowNullPointerException() {
        userService.createUser(null);
    }

    @Test
    public void givenValidNonExistentOAuthUserDTO_whenUpdateOauthUser_thenReturnUserResponseDTO() {
        OAuthUserDTO testOAuthUser = OAuthUserDTO.builder()
                .name("validName")
                .email("validEmail@gmail.com")
                .build();
        User testUserAfterCreation = User.builder()
                .userId(UUID.fromString("d8ad87ab-04a4-4438-9ff6-34bad41a8a31"))
                .login("validEmail@gmail.com")
                .email("validEmail@gmail.com")
                .firstName("validName")
                .build();
        User testUserBeforeCreation = User.builder()
                .login("validEmail@gmail.com")
                .email("validEmail@gmail.com")
                .firstName("validName")
                .build();
        UserResponseDTO expectedResult = UserResponseDTO.builder()
                .userId("d8ad87ab-04a4-4438-9ff6-34bad41a8a31")
                .build();
        doReturn(Optional.empty()).when(userRepository).findByLogin(testOAuthUser.getEmail());
        doReturn(testUserAfterCreation).when(userRepository).save(testUserBeforeCreation);
        doReturn(testUserBeforeCreation).when(userMapper).oAuthDTOToUser(testOAuthUser);
        doReturn(expectedResult).when(userMapper).userToUserResponseDTO(testUserAfterCreation);

        UserResponseDTO actualResult = userService.updateOauthUser(testOAuthUser);

        assertEquals(expectedResult, actualResult);
    }

    @Test(expected = NullPointerException.class)
    public void givenNullOAuthUserDTO_whenUpdateOauthUser_thenThrowNullPointerException() {
        userService.updateOauthUser(null);
    }

    @Test
    public void givenValidExistentLogin_whenFindUserDetailsByLogin_thenReturnUserDetailsDTO() {
        String testLogin = "validExistentLogin";

        UserDetailsDTO expectedResult = UserDetailsDTO.builder()
                .username("validExistentLogin")
                .build();
        User testUser = User.builder()
                .login("validExistentLogin")
                .build();

        doReturn(Optional.of(testUser)).when(userRepository).findByLogin(testLogin);
        doReturn(expectedResult).when(userMapper).userToUserDetailsDTO(testUser);

        UserDetailsDTO actualResult = userService.findUserDetailsByLogin(testLogin);

        assertEquals(expectedResult, actualResult);
    }

    @Test(expected = ResponseStatusException.class)
    public void givenValidNonExistentLogin_whenFindUserDetailsByLogin_thenThrowResponseStatusException() {
        String testLogin = "validNonExistentLogin";

        doReturn(Optional.empty()).when(userRepository).findByLogin(testLogin);

        userService.findUserDetailsByLogin(testLogin);
    }

    @Test(expected = ResponseStatusException.class)
    public void givenNullAsAStringLogin_whenFindUserDetailsByLogin_thenThrowResponseStatusException() {
        String testLogin = "null";
        userService.findUserDetailsByLogin(testLogin);
    }

    @Test(expected = ResponseStatusException.class)
    public void givenEmptyStringLogin_whenFindUserDetailsByLogin_thenThrowResponseStatusException() {
        userService.findUserDetailsByLogin("");
    }

    @Test(expected = ResponseStatusException.class)
    public void givenNullLogin_whenFindUserDetailsByLogin_thenThrowResponseStatusException() {
        userService.findUserDetailsByLogin(null);
    }
}
