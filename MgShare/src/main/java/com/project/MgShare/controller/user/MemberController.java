package com.project.MgShare.controller.user;

import com.project.MgShare.dto.user.RegisterDTO;
import com.project.MgShare.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@RequiredArgsConstructor
@Controller
public class MemberController {

    private final UserService userService;

    @GetMapping("/")
    public String First() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String)) {
            System.out.println("login中");

            //ROLE CHECK
            boolean isAdmin = authentication.getAuthorities().stream()
                    .anyMatch(authority -> authority.getAuthority().equals("ROLE_ADMIN"));
            boolean isUser = authentication.getAuthorities().stream()
                    .anyMatch(authority -> authority.getAuthority().equals("ROLE_USER"));

            if (isAdmin) {
                System.out.println("admin");
                return "redirect:/admin/userList";
            } else if (isUser) {
                System.out.println("user");
                return "redirect:/user/main";
            }
        }

        return "login_page";
    }

    @GetMapping("/login")
    public String login() {

        return "redirect:/";
    }


    @GetMapping("/register") //会員登録
    public String register() {

        return "register_page";
    }

    @PostMapping("/register/save") //会員登録完了
    public String registerPass(@ModelAttribute RegisterDTO registerDTO) {

            userService.UserRegisterSave(registerDTO);

            return "redirect:/login";
    }

 }
