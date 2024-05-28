package com.project.MgShare.controller.user;

import com.project.MgShare.config.user.SecurityConfig;
import com.project.MgShare.dto.user.RegisterDTO;
import com.project.MgShare.service.user.UserSecurityService;
import com.project.MgShare.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.verify;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(MemberController.class)
@Import(SecurityConfig.class) // Import your SecurityConfig class
public class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @MockBean
    private UserSecurityService userSecurityService; // Add this line to mock UserSecurityService

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFirst_NotAuthenticated() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("login_page"));
    }

    @Test
    @WithMockUser(username = "user@mirineglobal.com", roles = {"USER"})
    public void testFirst_UserAuthenticated() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/user/main"));
    }

    @Test
    @WithMockUser(username = "admin@mirineglobal.com", roles = {"ADMIN"})
    public void testFirst_AdminAuthenticated() throws Exception {
        mockMvc.perform(get("/"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/admin/userList"));
    }

    @Test
    public void testLogin() throws Exception {
        mockMvc.perform(get("/login"))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));
    }

    @Test
    @WithMockUser(username = "user", roles = {"USER"})
    public void testRegisterPage() throws Exception {
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
                        .with(csrf())) // CSRF
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/login"));

        verify(userService).UserRegisterSave(registerDTO);
    }
}