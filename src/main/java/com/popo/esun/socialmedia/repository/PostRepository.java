package com.popo.esun.socialmedia.repository;


import com.popo.esun.socialmedia.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    // 呼叫新增發文的 SP
    @Modifying
    @Query(value = "CALL sp_create_post(:userId, :content, :image)", nativeQuery = true)
    void callCreatePost(
            @Param("userId") Integer userId,
            @Param("content") String content,
            @Param("image") String image
    );
    // 呼叫編輯發文的 SP
    @Modifying
    @Query(value = "CALL sp_update_post(:postId, :userId, :content, :image)", nativeQuery = true)
    void callUpdatePost(
            @Param("postId") Integer postId,
            @Param("userId") Integer userId,
            @Param("content") String content,
            @Param("image") String image
    );

    // 呼叫刪除發文的 SP
    @Modifying
    @Query(value = "CALL sp_delete_post(:postId, :userId)", nativeQuery = true)
    void callDeletePost(
            @Param("postId") Integer postId,
            @Param("userId") Integer userId
    );

    // 呼叫查詢所有發文的 Function
    // 回傳 List<Map<String, Object>> 可以很彈性地接住 Function 回傳的多個欄位 (包含發文者名稱等)
    @Query(value = "SELECT * FROM fn_get_all_posts()", nativeQuery = true)
    List<Map<String, Object>> callGetAllPosts();
}