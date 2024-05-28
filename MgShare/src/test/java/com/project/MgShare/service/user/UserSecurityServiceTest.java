package com.project.MgShare.service.user;

import com.project.MgShare.role.UserRole;
import com.project.MgShare.model.user.UserEntity;
import com.project.MgShare.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserSecurityServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserSecurityService userSecurityService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoadUserByUsername_UserExists() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserEmail("user@mirineglobal.com");
        userEntity.setPassword(new BCryptPasswordEncoder().encode("password"));
        userEntity.setRole(UserRole.USER.getValue());

        when(userRepository.findByUserEmail("user@mirineglobal.com")).thenReturn(Optional.of(userEntity));

        org.springframework.security.core.userdetails.UserDetails userDetails = userSecurityService.loadUserByUsername("user@mirineglobal.com");

        assertNotNull(userDetails);
        assertEquals("user@mirineglobal.com", userDetails.getUsername());
        assertEquals(userEntity.getPassword(), userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals(UserRole.USER.getValue())));
    }

    @Test
    public void testLoadUserByUsername_AdminExists() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserEmail("admin@mirineglobal.com");
        userEntity.setPassword(new BCryptPasswordEncoder().encode("adminPassword"));
        userEntity.setRole(UserRole.ADMIN.getValue());

        when(userRepository.findByUserEmail("admin@mirineglobal.com")).thenReturn(Optional.of(userEntity));

        org.springframework.security.core.userdetails.UserDetails userDetails = userSecurityService.loadUserByUsername("admin@mirineglobal.com");

        assertNotNull(userDetails);
        assertEquals("admin@mirineglobal.com", userDetails.getUsername());
        assertEquals(userEntity.getPassword(), userDetails.getPassword());
        assertTrue(userDetails.getAuthorities().stream().anyMatch(auth -> auth.getAuthority().equals(UserRole.ADMIN.getValue())));
    }

    @Test
    public void testLoadUserByUsername_UserNotFound() {
        when(userRepository.findByUserEmail("nonexistent@mirineglobal.com")).thenReturn(Optional.empty());

        assertThrows(UsernameNotFoundException.class, () -> {
            userSecurityService.loadUserByUsername("nonexistent@mirineglobal.com");
        });
    }
}
