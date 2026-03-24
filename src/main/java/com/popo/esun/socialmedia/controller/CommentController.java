package com.popo.esun.socialmedia.controller;


import com.popo.esun.socialmedia.model.dto.CommentRequest;
import com.popo.esun.socialmedia.service.CommentService; // 需自行建立對應的 Service
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
     * 新增留言 (HTTP POST /api/comments)
     */
    @PostMapping
    public ResponseEntity<String> createComment(@Valid @RequestBody CommentRequest request) {
        commentService.createComment(request.getUserId(), request.getPostId(), request.getContent());
        return ResponseEntity.status(HttpStatus.CREATED).body("留言成功");
    }
}