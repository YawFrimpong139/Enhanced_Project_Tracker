package com.codewithzea.myprojecttracker.test;



import com.codewithzea.myprojecttracker.user.Role;
import com.codewithzea.myprojecttracker.user.UserEntity;
import com.codewithzea.myprojecttracker.user.dto.UserDTO;
import com.codewithzea.myprojecttracker.user.dto.UserMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
class UserMapperTest {

    private final UserMapper userMapper = new UserMapper();

    @Test
    void toDTO_ShouldMapAllFieldsCorrectly() {
        // Arrange
        UserEntity user = UserEntity.builder()
                .id(2L)
                .firstName("Jane")
                .lastName("Smith")
                .email("jane.smith@example.com")
                .password("password123")
                .role(Role.ROLE_MANAGER)
                .build();

        // Act
        UserDTO result = userMapper.toDTO(user);

        // Assert
        assertAll(
                () -> assertEquals(2L, result.getId()),
                () -> assertEquals("Jane", result.getFirstName()),
                () -> assertEquals("Smith", result.getLastName()),
                () -> assertEquals("jane.smith@example.com", result.getEmail()),
                () -> assertEquals(Role.ROLE_MANAGER, result.getRole())
        );
    }
}
