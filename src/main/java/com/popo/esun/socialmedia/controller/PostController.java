package com.popo.esun.socialmedia.controller;


import com.popo.esun.socialmedia.model.dto.PostRequest;
import com.popo.esun.socialmedia.model.dto.UpdatePostRequest;
import com.popo.esun.socialmedia.repository.CommentRepository;
import com.popo.esun.socialmedia.repository.PostRepository;
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
    private final CommentRepository commentRepository;
    private final PostRepository postRepository;

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
     */
    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(
            @PathVariable Integer postId,
            @RequestParam Integer userId) {

        postService.deletePost(postId, userId);
        return ResponseEntity.ok("發文刪除成功");
    }

    @GetMapping
    public ResponseEntity<List<Map<String, Object>>> callgetAllPosts() {
        return ResponseEntity.ok(postService.getAllPosts());
    }

    /**
     * 取得所有發文 (HTTP GET /api/posts)
     */
    public List<Map<String, Object>> getAllPosts() {
        List<Map<String, Object>> posts = postRepository.callGetAllPosts();
        for (Map<String, Object> post : posts) {
            Integer postId = (Integer) post.get("post_id");

            List<Map<String, Object>> comments = commentRepository.findCommentsByPostId(postId);

            post.put("comments", comments);
        }

        return posts;
    }
}