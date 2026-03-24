package com.popo.esun.socialmedia.controller;

import com.popo.esun.socialmedia.model.dto.CommentRequest;
import com.popo.esun.socialmedia.service.CommentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    /**
     * 新增留言 (對應前端 axios.post('/api/comments', ...))
     */
    @PostMapping
    public ResponseEntity<String> createComment( @RequestBody CommentRequest request) {
        System.out.println("收到前端請求: userId=" + request.getUserId() +
                ", postId=" + request.getPostId() +
                ", content=" + request.getContent());
        commentService.createComment(
                request.getUserId(),
                request.getPostId(),
                request.getContent()
        );

        return ResponseEntity.status(HttpStatus.CREATED).body("留言成功");
    }
}