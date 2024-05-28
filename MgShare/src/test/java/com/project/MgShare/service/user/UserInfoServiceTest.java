package com.project.MgShare.service.user;

import com.project.MgShare.dto.user.UserInfoDTO;
import com.project.MgShare.model.user.UserEntity;
import com.project.MgShare.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserInfoServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private Authentication authentication;

    @Mock
    private SecurityContext securityContext;

    @InjectMocks
    private UserInfoService userInfoService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        SecurityContextHolder.setContext(securityContext);
    }

    @Test
    public void testGetCurrentUser() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUsername("Test User");
        userEntity.setUserEmail("testuser@mirineglobal.com");
        userEntity.setPhoneNumber("1234567890");

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn(new Object());
        when(authentication.getName()).thenReturn("testuser@mirineglobal.com");
        when(userRepository.findByUserEmail("testuser@mirineglobal.com")).thenReturn(Optional.of(userEntity));

        UserInfoDTO currentUser = userInfoService.getCurrentUser();

        assertNotNull(currentUser);
        assertEquals("Test User", currentUser.getUsername());
        assertEquals("testuser@mirineglobal.com", currentUser.getUserEmail());
        assertEquals("1234567890", currentUser.getPhoneNumber());
    }

    @Test
    public void testConfirmCurrentPassword() {
        String currentPassword = "password";
        UserEntity userEntity = new UserEntity();
        userEntity.setUserEmail("testuser@mirineglobal.com");
        userEntity.setPassword(new BCryptPasswordEncoder().encode(currentPassword));

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn(new Object());
        when(authentication.getName()).thenReturn("testuser@mirineglobal.com");
        when(userRepository.findByUserEmail("testuser@mirineglobal.com")).thenReturn(Optional.of(userEntity));
        when(bCryptPasswordEncoder.matches(currentPassword, userEntity.getPassword())).thenReturn(true);

        boolean isPasswordValid = userInfoService.confirmCurrentPassword(currentPassword);

        assertTrue(isPasswordValid);
    }

    @Test
    public void testUpdateUserInfo() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserEmail("testuser@mirineglobal.com");
        userEntity.setPassword(new BCryptPasswordEncoder().encode("password"));

        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setUsername("Updated User");
        userInfoDTO.setPhoneNumber("0987654321");
        userInfoDTO.setUserEmail("updateduser@mirineglobal.com");
        userInfoDTO.setPassword("newpassword");

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn(new Object());
        when(authentication.getName()).thenReturn("testuser@mirineglobal.com");
        when(userRepository.findByUserEmail("testuser@mirineglobal.com")).thenReturn(Optional.of(userEntity));

        userInfoService.updateUserInfo(userInfoDTO);

        verify(userRepository).save(any(UserEntity.class));
    }

    @Test
    public void testDeleteUser() {
        UserEntity userEntity = new UserEntity();
        userEntity.setUserEmail("testuser@mirineglobal.com");

        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.isAuthenticated()).thenReturn(true);
        when(authentication.getPrincipal()).thenReturn(new Object());
        when(authentication.getName()).thenReturn("testuser@mirineglobal.com");
        when(userRepository.findByUserEmail("testuser@mirineglobal.com")).thenReturn(Optional.of(userEntity));

        userInfoService.deleteUser();

        verify(userRepository).delete(any(UserEntity.class));
    }
}
