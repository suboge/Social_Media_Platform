package com.popo.esun.socialmedia.service;


import com.popo.esun.socialmedia.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor // Lombok 自動幫我們產生帶有 final 屬性的建構子，完成依賴注入
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 註冊使用者
     */
    @Transactional(rollbackFor = Exception.class) // 只要發生任何 Exception 就 Rollback
    public void registerUser(String phone, String name, String rawPassword, String email, String cover, String bio) {

        // 1. 密碼加鹽並經雜湊處理
        String hashedPassword = passwordEncoder.encode(rawPassword);

        // 2. 呼叫底層 Repository 的 Stored Procedure
        userRepository.callRegisterUser(phone, name, hashedPassword, email, cover, bio);
    }
}