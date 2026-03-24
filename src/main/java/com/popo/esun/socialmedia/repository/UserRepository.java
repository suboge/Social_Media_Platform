package com.popo.esun.socialmedia.repository;



import com.popo.esun.socialmedia.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    // 呼叫註冊使用者的 SP
    @Modifying
    @Query(value = "CALL sp_register_user(:phone, :name, :password, :email, :cover, :bio)", nativeQuery = true)
    void callRegisterUser(
            @Param("phone") String phone,
            @Param("name") String name,
            @Param("password") String passwordHash,
            @Param("email") String email,
            @Param("cover") String coverImage,
            @Param("bio") String biography
    );

    // 為了後續 Spring Security 登入驗證使用
    Optional<User> findByPhoneNumber(String phoneNumber);
}