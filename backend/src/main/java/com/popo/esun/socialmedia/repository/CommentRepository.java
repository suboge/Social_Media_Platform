package com.popo.esun.socialmedia.repository;


import com.popo.esun.socialmedia.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    // 呼叫新增留言的 SP
    @Modifying
    @Query(value = "CALL sp_create_comment(:userId, :postId, :content)", nativeQuery = true)
    void callCreateComment(
            @Param("userId") Integer userId,
            @Param("postId") Integer postId,
            @Param("content") String content
    );
    @Query(value = "SELECT c.comment_id, c.content, c.created_at, u.user_name " +
            "FROM comments c " +
            "JOIN users u ON c.user_id = u.user_id " +
            "WHERE c.post_id = :postId " +
            "ORDER BY c.created_at ASC", nativeQuery = true)
    List<Map<String, Object>> findCommentsByPostId(@Param("postId") Integer postId);
}