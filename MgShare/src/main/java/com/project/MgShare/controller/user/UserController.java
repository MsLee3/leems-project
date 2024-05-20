package com.project.MgShare.controller.user;

import com.project.MgShare.dto.user.RegisterDTO;
import com.project.MgShare.model.user.UserEntity;
import com.project.MgShare.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

    @GetMapping("/register") //会員登録
    public String register() {

        return "register_page";
    }

    @PostMapping("/registerPass") //会員登録完了
    public String registerPass(@ModelAttribute RegisterDTO registerDTO) {

            userService.UserRegisterSave(registerDTO);

            return "redirect:/login";
    }

    @GetMapping("/login")
    public String login() {

        return "login_page";
    }

    @PostMapping("/user/main")
    public String loginP() {



        return "main_page";
    }
}
