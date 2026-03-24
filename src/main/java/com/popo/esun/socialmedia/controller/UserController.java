package com.popo.esun.socialmedia.controller;

import com.popo.esun.socialmedia.model.dto.RegisterRequest;
import com.popo.esun.socialmedia.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 註冊 API
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request) {
        userService.registerUser(
                request.getPhoneNumber(),
                request.getUserName(),
                request.getPassword(),
                request.getEmail(),
                request.getCoverImage(),
                request.getBiography()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body("註冊成功");
    }

    /**
     * 登入 API (新增的部分)
     */
    @PostMapping("/login")
    public ResponseEntity<Map<String, Object>> login(@RequestBody Map<String, String> loginData) {
        String phone = loginData.get("phone");
        String password = loginData.get("password");

        Map<String, Object> userInfo = userService.login(phone, password);

        return ResponseEntity.ok(userInfo);
    }
}