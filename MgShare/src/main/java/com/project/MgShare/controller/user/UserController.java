package com.project.MgShare.controller.user;

import com.project.MgShare.dto.user.RegisterDTO;
import com.project.MgShare.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class UserController {

    private final UserService userService;

//    @GetMapping("/")
//    public String first() {
//        return "login_page";
//    }

    @GetMapping("/login")
    public String login() {
        return "login_page";
    }

    @GetMapping("/register")
    public String register() {
        return "register_page";
    }

    @PostMapping("/registerP")
    public String registerP(@ModelAttribute RegisterDTO registerDTO) {

        userService.UserRegisterSave(registerDTO);

        return "redirect:/login";
    }

    @PostMapping("/main")
    public String loginP() {



        return "main_page";
    }
}
