package com.popo.esun.socialmedia.service;


import com.popo.esun.socialmedia.model.User;
import com.popo.esun.socialmedia.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    /**
     * 註冊使用者
     */
    @Transactional(rollbackFor = Exception.class)
    public void registerUser(String phone, String name, String rawPassword, String email, String cover, String bio) {


        String hashedPassword = passwordEncoder.encode(rawPassword);


        userRepository.callRegisterUser(phone, name, hashedPassword, email, cover, bio);
    }

    public Map<String, Object> login(String phone, String rawPassword) {

        User user = userRepository.findByPhoneNumber(phone)
                .orElseThrow(() -> new RuntimeException("使用者不存在"));


        if (passwordEncoder.matches(rawPassword, user.getPasswordHash())) {
            Map<String, Object> result = new HashMap<>();
            result.put("userId", user.getUserId());
            result.put("userName", user.getUserName());
            return result;
        } else {
            throw new RuntimeException("密碼錯誤");
        }
    }
}