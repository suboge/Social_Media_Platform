package com.popo.esun.socialmedia.model.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterRequest {

    @NotBlank(message = "手機號碼不能為空")
    @Pattern(regexp = "^09\\d{8}$", message = "手機號碼格式錯誤，必須為 09 開頭的 10 碼數字")
    private String phoneNumber;

    @NotBlank(message = "使用者名稱不能為空")
    @Size(max = 50, message = "使用者名稱長度不能超過 50 個字元")
    private String userName;

    @NotBlank(message = "密碼不能為空")
    @Size(min = 6, max = 20, message = "密碼長度必須介於 6 到 20 碼之間")
    private String password;

    @Email(message = "電子郵件格式錯誤")
    private String email;

    private String coverImage;

    @Size(max = 500, message = "自我介紹長度不能超過 500 個字元")
    private String biography;
}