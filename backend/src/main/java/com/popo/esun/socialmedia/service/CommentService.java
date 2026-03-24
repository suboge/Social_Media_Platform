package com.popo.esun.socialmedia.service;

import com.popo.esun.socialmedia.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final CommentRepository commentRepository;

    /**
     * 新增留言
     * 滿足條件：使用者可以針對發文新增留言
     */
    @Transactional(rollbackFor = Exception.class)
    public void createComment(Integer userId, Integer postId, String content) {
        commentRepository.callCreateComment(userId, postId, content);
    }
}