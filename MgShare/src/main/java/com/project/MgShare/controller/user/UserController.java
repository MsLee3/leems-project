package com.project.MgShare.controller.user;

import com.project.MgShare.dto.user.UserInfoDTO;
import com.project.MgShare.service.user.UserInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
@RequestMapping("/user")
@Controller
public class UserController {

    private final UserInfoService userInfoService;

    @GetMapping("/main")
    public String loginPass() {

        return "/main_page";
    }

    @GetMapping("/return")
    public String checkList() {

        return "return_page";
    }

    @GetMapping("/myPage")
    public  String myPage(Model model) {

        UserInfoDTO currentUser = userInfoService.getCurrentUser();
        if(currentUser != null) {

            model.addAttribute("user", currentUser);
            return "my_page";
        }
        return  "redirect:/login";
    }

    @PostMapping("/update")
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

    @PostMapping("/delete")
    @ResponseBody
    public Map<String, Object> deleteUser() {
        Map<String, Object> response = new HashMap<>();
        try {
            userInfoService.deleteUser();
            response.put("success", true);
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", e.getMessage());
        }
        return response;
    }

    @PostMapping("/confirmPassword")
    @ResponseBody
    public Map<String, Object> confirmPassword(@RequestBody Map<String, String> request) {
        Map<String, Object> response = new HashMap<>();
        String currentPassword = request.get("currentPassword");
        boolean isPasswordValid = userInfoService.confirmCurrentPassword(currentPassword);

        response.put("success", isPasswordValid);
        return response;
    }
}
