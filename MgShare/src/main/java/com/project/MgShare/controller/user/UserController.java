package com.project.MgShare.controller.user;

import com.project.MgShare.dto.user.UserInfoDTO;
import com.project.MgShare.service.user.UserInfoService;
import org.springframework.ui.Model;
import com.project.MgShare.dto.user.RegisterDTO;
import com.project.MgShare.model.user.UserEntity;
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
public class UserController {

    private final UserService userService;
    private final UserInfoService userInfoService;

    @GetMapping("/")
    public String First() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String)) {
            System.out.println("login中");
            return "redirect:/user/main";
        }

        return "login_page";
    }

    @GetMapping("/login")
    public String login() {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated() && !(authentication.getPrincipal() instanceof String)) {
            System.out.println("login中");
            return "redirect:/user/main";
        }
        return "login_page";
    }

    @GetMapping("/user/main")
    public String loginPass() {

        return "/main_page";
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
