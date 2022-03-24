package com.example.boguserms.repositoryTests;

import com.example.boguserms.domain.User;
import com.example.boguserms.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;
import java.util.UUID;

import static org.junit.Assert.*;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    private User initUser;

    @Before
    public void init() {
        User user = User.builder()
                .email("testInitEmail@gmail.com")
                .login("testInitLogin").build();

        initUser = userRepository.save(user);
    }

    @Test
    public void givenValidUser_whenSave_thenReturnUser() {
        User user = User.builder()
                .email("testEmail@gmail.com")
                .login("testLogin").build();

        user = userRepository.save(user);

        assertEquals(userRepository.findById(user.getUserId()).get(), user);
    }

    @Test
    public void givenValidLogin_whenFindByLogin_thenReturnPresentOptionalUser() {
        String testLogin = "testInitLogin";

        assertEquals(userRepository.findByLogin(testLogin).get(), initUser);
    }

    @Test
    public void givenNullLogin_whenFindByLogin_thenReturnEmptyOptionalResult() {
        assertTrue(userRepository.findByLogin(null).isEmpty());
    }

    @Test
    public void givenEmptyLogin_whenFindByLogin_thenReturnEmptyOptionalResult() {
        assertTrue(userRepository.findByLogin("").isEmpty());
    }

    @Test(expected = DataIntegrityViolationException.class)
    public void givenExistentUser_whenSave_thenThrowsDataIntegrityViolationException() {
        User user = User.builder()
                .email("testInitEmail@gmail.com")
                .login("testInitLogin")
                .build();

        userRepository.save(user);
        userRepository.findByLogin("testInitLogin");
    }

}
