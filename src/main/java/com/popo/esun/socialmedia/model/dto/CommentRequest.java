package com.popo.esun.socialmedia.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentRequest {

    @NotNull(message = "使用者ID不能為空")
    private Integer userId;

    @NotNull(message = "發文ID不能為空")
    private Integer postId;

    @NotBlank(message = "留言內容不能為空")
    @Size(max = 500, message = "留言內容過長")
    private String content;
}