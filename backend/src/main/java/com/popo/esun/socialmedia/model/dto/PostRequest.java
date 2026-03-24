package com.popo.esun.socialmedia.model.dto;

import com.popo.esun.socialmedia.model.Comment;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class PostRequest {

    @NotNull(message = "使用者ID不能為空")
    private Integer userId;

    @NotBlank(message = "發佈內容不能為空")
    @Size(max = 2000, message = "發文內容過長")
    private String content;

    private String image;
    private List<Comment> comments;
}