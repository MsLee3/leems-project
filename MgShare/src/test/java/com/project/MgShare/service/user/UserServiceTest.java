package com.project.MgShare.service.user;

import com.project.MgShare.dto.user.RegisterDTO;
import com.project.MgShare.model.user.UserEntity;
import com.project.MgShare.repository.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    public void testUserRegisterSave_Success() {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setUsername("Test User");
        registerDTO.setPhoneNumber("1234567890");
        registerDTO.setUserEmail("testuser@mirineglobal.com");
        registerDTO.setPassword("password");

        when(userRepository.existsUserEntitiesByUserEmail(registerDTO.getUserEmail())).thenReturn(false);
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn("encodedPassword");

        userService.UserRegisterSave(registerDTO);

        verify(userRepository, times(1)).save(any(UserEntity.class));
    }

    @Test
    public void testUserRegisterSave_InvalidName() {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setUsername("Invalid@Name");
        registerDTO.setPhoneNumber("1234567890");
        registerDTO.setUserEmail("testuser@mirineglobal.com");
        registerDTO.setPassword("password");

        assertThrows(IllegalArgumentException.class, () -> {
            userService.UserRegisterSave(registerDTO);
        });
    }

    @Test
    public void testUserRegisterSave_InvalidPhoneNumber() {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setUsername("Test User");
        registerDTO.setPhoneNumber("InvalidPhoneNumber");
        registerDTO.setUserEmail("testuser@mirineglobal.com");
        registerDTO.setPassword("password");

        assertThrows(IllegalArgumentException.class, () -> {
            userService.UserRegisterSave(registerDTO);
        });
    }

    @Test
    public void testUserRegisterSave_InvalidEmailDomain() {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setUsername("Test User");
        registerDTO.setPhoneNumber("1234567890");
        registerDTO.setUserEmail("testuser@invalid.com");
        registerDTO.setPassword("password");

        assertThrows(IllegalArgumentException.class, () -> {
            userService.UserRegisterSave(registerDTO);
        });
    }

    @Test
    public void testUserRegisterSave_EmailAlreadyExists() {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setUsername("Test User");
        registerDTO.setPhoneNumber("1234567890");
        registerDTO.setUserEmail("testuser@mirineglobal.com");
        registerDTO.setPassword("password");

        when(userRepository.existsUserEntitiesByUserEmail(registerDTO.getUserEmail())).thenReturn(true);

        assertThrows(IllegalArgumentException.class, () -> {
            userService.UserRegisterSave(registerDTO);
        });
    }
}
