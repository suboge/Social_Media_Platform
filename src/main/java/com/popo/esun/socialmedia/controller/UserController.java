package com.popo.esun.socialmedia.controller;


import com.popo.esun.socialmedia.model.dto.RegisterRequest;
import com.popo.esun.socialmedia.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // 標示這是一個 REST API Controller，回傳的資料會自動轉成 JSON
@RequestMapping("/api/users") // 定義共用的基礎網址路徑
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 註冊 API (對應 HTTP POST)
     */
    @PostMapping("/register")
    public ResponseEntity<String> register(@Valid @RequestBody RegisterRequest request) {
        // @Valid: 觸發 DTO 裡面的驗證規則
        // @RequestBody: 將前端傳來的 JSON 對應到 RegisterRequest 物件

        userService.registerUser(
                request.getPhoneNumber(),
                request.getUserName(),
                request.getPassword(),
                request.getEmail(),
                request.getCoverImage(),
                request.getBiography()
        );

        // RESTful 慣例：新增成功回傳 HTTP 201 Created
        return ResponseEntity.status(HttpStatus.CREATED).body("註冊成功");
    }
}