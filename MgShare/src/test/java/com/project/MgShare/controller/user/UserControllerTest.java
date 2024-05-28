package com.project.MgShare.controller.user;

import com.project.MgShare.dto.user.UserInfoDTO;
import com.project.MgShare.service.user.UserInfoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserInfoService userInfoService;

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testLoginPass() throws Exception {
        mockMvc.perform(get("/user/main"))
                .andExpect(status().isOk())
                .andExpect(view().name("/main_page"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testCheckList() throws Exception {
        mockMvc.perform(get("/user/return"))
                .andExpect(status().isOk())
                .andExpect(view().name("return_page"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testMyPage() throws Exception {
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        userInfoDTO.setUsername("testuser");
        userInfoDTO.setUserEmail("testuser@mirineglobal.com");
        userInfoDTO.setPhoneNumber("1234567890");

        when(userInfoService.getCurrentUser()).thenReturn(userInfoDTO);

        mockMvc.perform(get("/user/myPage"))
                .andExpect(status().isOk())
                .andExpect(view().name("my_page"))
                .andExpect(model().attributeExists("user"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testUpdateUserInfo() throws Exception {
        mockMvc.perform(post("/user/update")
                        .with(csrf())  // CSRF 토큰 추가
                        .param("username", "newuser")
                        .param("phoneNumber", "0987654321")
                        .param("userEmail", "newuser@mirineglobal.com")
                        .param("password", "newpassword"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testDeleteUser() throws Exception {
        mockMvc.perform(post("/user/delete")
                        .with(csrf()))  // CSRF 토큰 추가
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testConfirmPassword() throws Exception {
        when(userInfoService.confirmCurrentPassword("password")).thenReturn(true);

        mockMvc.perform(post("/user/confirmPassword")
                        .with(csrf())  // CSRF 토큰 추가
                        .contentType("application/json")
                        .content("{\"currentPassword\":\"password\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true));
    }
}