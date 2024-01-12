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
        // Arrange
        UserDTO userDTO = new UserDTO(1000L, "testUser", "password", "test@email.com");
        User user = new User();
        user.setUsername(userDTO.getUsername());
        user.setPassword(userDTO.getPassword());
        user.setEmail(userDTO.getEmail());

        when(userService.createUser(any(User.class))).thenReturn(user);

        // Act
        ResponseEntity<UserDTO> response = userController.createUser(userDTO);

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("testUser", response.getBody().getUsername());
        assertEquals("test@email.com", response.getBody().getEmail());

        verify(userService, times(1)).createUser(any(User.class));
    }

    @Test
    public void getUserById_ExistingUser_ReturnsUser() {
        // Arrange
        Long userId = 1000L;
        User user = new User(userId,"testUser", "password", "test@email.com");
        user.setId(userId);
        when(userService.getUserById(userId)).thenReturn(user);

        // Act
        ResponseEntity<UserDTO> response = userController.getUserById(userId);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(userId, response.getBody().getId());
    }

    @Test
    public void getUserById_NonExistingUser_ReturnsNotFound() {
        // Arrange
        Long userId = 1000L;
        when(userService.getUserById(userId)).thenReturn(null);

        // Act
        ResponseEntity<UserDTO> response = userController.getUserById(userId);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    public void getAllUsers_UsersExist_ReturnsUserList() {
        // Arrange
        List<User> users = Arrays.asList(new User(1000L,"testUser", "password", "test@email.com"),
                                         new User(1002L,"testUser2", "password2", "test2@email.com"));
        when(userService.getAllUsers()).thenReturn(users);

        // Act
        ResponseEntity<List<UserDTO>> response = userController.getAllUsers();

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(users.size(), response.getBody().size());
    }



}
