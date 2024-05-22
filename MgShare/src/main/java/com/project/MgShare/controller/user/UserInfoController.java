package com.project.MgShare.controller.user;

import com.project.MgShare.dto.user.RegisterDTO;
import com.project.MgShare.dto.user.UserInfoDTO;
import com.project.MgShare.service.user.UserInfoService;
import com.project.MgShare.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@Controller
public class UserInfoController {

    private final UserInfoService userInfoService;

    @GetMapping("/user/myPage")
    public  String myPage(Model model) {

        UserInfoDTO currentUser = userInfoService.getCurrentUser();
        if(currentUser != null) {

            model.addAttribute("user", currentUser);
            return "my_page";
        }
        return  "redirect:/user/login";
    }

    @PostMapping("/user/update")
    @ResponseBody
    public Map<String, Object> updateUserInfo(@RequestParam("username") String username,
                                              @RequestParam("phoneNumber") String phoneNumber,
                                              @RequestParam("userEmail") String userEmail,
                                              @RequestParam(value = "password", required = false) String password) {
        Map<String, Object> response = new HashMap<>();
        try {
            UserInfoDTO userInfoDTO = new UserInfoDTO();
            userInfoDTO.setUsername(username);
            userInfoDTO.setPhoneNumber(phoneNumber);
            userInfoDTO.setUserEmail(userEmail);
            userInfoDTO.setPassword(password);
            userInfoService.updateUserInfo(userInfoDTO);
            response.put("success", true);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
        }
        return response;
    }

}
