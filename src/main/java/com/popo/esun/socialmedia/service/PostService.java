package com.popo.esun.socialmedia.service;


import com.popo.esun.socialmedia.repository.CommentRepository;
import com.popo.esun.socialmedia.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final CommentRepository commentRepository;

    /**
     * 新增發文
     */
    @Transactional(rollbackFor = Exception.class)
    public void createPost(Integer userId, String content, String image) {
        postRepository.callCreatePost(userId, content, image);
    }

    /**
     * 編輯發文
     */
    @Transactional(rollbackFor = Exception.class)
    public void updatePost(Integer postId, Integer userId, String content, String image) {
        postRepository.callUpdatePost(postId, userId, content, image);
    }

    /**
     * 刪除發文
     */
    @Transactional(rollbackFor = Exception.class)
    public void deletePost(Integer postId, Integer userId) {
        postRepository.callDeletePost(postId, userId);
    }

    /**
     * 取得所有發文 (查詢不需要 Transactional，或者可以設為唯讀)
     */
    public List<Map<String, Object>> getAllPosts() {

        List<Map<String, Object>> rawPosts = postRepository.callGetAllPosts();
        List<Map<String, Object>> resultPosts = new java.util.ArrayList<>();

        for (Map<String, Object> rawPost : rawPosts) {
            Map<String, Object> modifiablePost = new java.util.HashMap<>(rawPost);
            Object idObj = modifiablePost.get("post_id");

            if (idObj != null) {
                Integer postId = ((Number) idObj).intValue();

                List<Map<String, Object>> comments = commentRepository.findCommentsByPostId(postId);
                modifiablePost.put("comments", comments);
            }

            resultPosts.add(modifiablePost);
        }

        return resultPosts;
    }
}