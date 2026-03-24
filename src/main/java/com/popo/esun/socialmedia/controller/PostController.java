package com.popo.esun.socialmedia.controller;


import com.popo.esun.socialmedia.model.dto.PostRequest;
import com.popo.esun.socialmedia.model.dto.UpdatePostRequest;
import com.popo.esun.socialmedia.service.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/posts")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    /**
     * 新增發文 (HTTP POST /api/posts)
     */
    @PostMapping
    public ResponseEntity<String> createPost(@Valid @RequestBody PostRequest request) {
        postService.createPost(request.getUserId(), request.getContent(), request.getImage());
        return ResponseEntity.status(HttpStatus.CREATED).body("發文成功");
    }
    /**
     * 編輯發文 (HTTP PUT /api/posts/{postId})
     */
    @PutMapping("/{postId}")
    public ResponseEntity<String> updatePost(
            @PathVariable Integer postId,
            @Valid @RequestBody UpdatePostRequest request) {

        postService.updatePost(postId, request.getUserId(), request.getContent(), request.getImage());
        return ResponseEntity.ok("發文更新成功");
    }

    /**
     * 刪除發文 (HTTP DELETE /api/posts/{postId}?userId={userId})
     * 註：刪除通常不需要 RequestBody，因此 userId 用 Query Parameter 傳遞
     */
    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(
            @PathVariable Integer postId,
            @RequestParam Integer userId) {

        postService.deletePost(postId, userId);
        return ResponseEntity.ok("發文刪除成功");
    }

    /**
     * 取得所有發文 (HTTP GET /api/posts)
     */
    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> getAllPosts() {
        // RESTful 慣例：查詢成功回傳 HTTP 200 OK
        return ResponseEntity.ok(postService.getAllPosts());
    }
}