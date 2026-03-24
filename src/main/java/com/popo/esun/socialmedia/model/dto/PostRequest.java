package com.popo.esun.socialmedia.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PostRequest {

    @NotNull(message = "使用者ID不能為空")
    private Integer userId;
    // 實務上，安全度較高的做法是從登入後的 Token (例如 JWT) 中解析出 userId。
    // 但作為簡易實作，我們先允許前端傳入，後續可以視實作時間決定是否升級為 JWT 驗證。

    @NotBlank(message = "發佈內容不能為空")
    @Size(max = 2000, message = "發文內容過長")
    private String content;

    private String image;
}