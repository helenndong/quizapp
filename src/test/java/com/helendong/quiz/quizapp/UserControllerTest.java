package com.helendong.quiz.quizapp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

import com.helendong.quiz.quizapp.controller.UserController;
import com.helendong.quiz.quizapp.dto.UserDTO;
import com.helendong.quiz.quizapp.model.User;
import com.helendong.quiz.quizapp.service.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

public class UserControllerTest {

    private static final Long USER_ID = 1000L;

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void createUser_SuccessfulCreation_ReturnsCreatedUser() {
        UserDTO userDTO = new UserDTO(USER_ID, "testUser", "password", "test@email.com");
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());

        when(userService.createUser(any(User.class))).thenReturn(user);

        ResponseEntity<UserDTO> response = userController.createUser(userDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("testUser", response.getBody().getUsername());
        assertEquals("test@email.com", response.getBody().getEmail());

        verify(userService, times(1)).createUser(any(User.class));
    }

    @Test
    public void getUserById_ExistingUser_ReturnsUser() {
        User user = new User(USER_ID,"testUser", "password", "test@email.com");
        user.setId(USER_ID);
        when(userService.getUserById(USER_ID)).thenReturn(user);

        ResponseEntity<UserDTO> response = userController.getUserById(USER_ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(USER_ID, response.getBody().getId());
    }

    @Test
    public void getUserById_NonExistingUser_ReturnsNotFound() {
        when(userService.getUserById(USER_ID)).thenReturn(null);

        ResponseEntity<UserDTO> response = userController.getUserById(USER_ID);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void getAllUsers_UsersExist_ReturnsUserList() {
        List<User> users = Arrays.asList(new User(1000L,"testUser", "password", "test@email.com"),
                                         new User(1002L,"testUser2", "password2", "test2@email.com"));
        when(userService.getAllUsers()).thenReturn(users);

        ResponseEntity<List<UserDTO>> response = userController.getAllUsers();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(users.size(), response.getBody().size());
    }

    @Test
    public void updateUser_ExistingUser_ReturnsUpdatedUser() {

        UserDTO userDTO = new UserDTO(USER_ID, "updatedUser", "newPassword", "updated@email.com");
        User updatedUser = new User();
        updatedUser.setId(USER_ID);
        updatedUser.setUsername("updatedUser");
        updatedUser.setPassword("newPassword");
        updatedUser.setEmail("updated@email.com");
        when(userService.updateUser(eq(USER_ID), any(User.class))).thenReturn(updatedUser);

        ResponseEntity<UserDTO> response = userController.updateUser(USER_ID, userDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("updatedUser", response.getBody().getUsername());
    }

    @Test
    public void deleteUser_ExistingUser_ReturnsOkStatus() {
        doNothing().when(userService).deleteUser(USER_ID);

        ResponseEntity<Void> response = userController.deleteUser(USER_ID);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        verify(userService, times(1)).deleteUser(USER_ID);
    }

    @Test
    public void authenticateUser_WithValidCredentials_ReturnsSuccessMessage() {
        UserDTO userDTO = new UserDTO(USER_ID,"username", "password", null);
        when(userService.authenticateUser("username", "password")).thenReturn(true);

        ResponseEntity<String> response = userController.authenticateUser(userDTO);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Login successful!", response.getBody());
    }

    @Test
    public void authenticateUser_WithInvalidCredentials_ReturnsFailureMessage() {
        UserDTO userDTO = new UserDTO(USER_ID, "username", "wrongpassword", null);
        when(userService.authenticateUser("username", "wrongpassword")).thenReturn(false);

        ResponseEntity<String> response = userController.authenticateUser(userDTO);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertEquals("Login failed. Invalid username or password.", response.getBody());
    }




}
