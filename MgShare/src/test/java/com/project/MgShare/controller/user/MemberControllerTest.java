package com.project.MgShare.controller.user;

import com.project.MgShare.dto.user.RegisterDTO;
import com.project.MgShare.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MemberController.class)
public class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testFirst_UserRole() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/user/main"));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"ADMIN"})
    public void testFirst_AdminRole() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/admin/userList"));
    }

    @Test
    public void testLogin() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/"));
    }

    @Test
    public void testRegister() throws Exception {
        mockMvc.perform(get("/register"))
                .andExpect(status().isOk())
                .andExpect(view().name("register_page"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testRegisterPass() throws Exception {
        RegisterDTO registerDTO = new RegisterDTO();
        registerDTO.setUsername("Test User");
        registerDTO.setPhoneNumber("1234567890");
        registerDTO.setUserEmail("testuser@mirineglobal.com");
        registerDTO.setPassword("password");

        mockMvc.perform(post("/register/save")
                        .flashAttr("registerDTO", registerDTO)
                        .with(csrf())) // CSRF 토큰 추가
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrlPattern("**/login"));

        verify(userService).UserRegisterSave(registerDTO);
    }
}
