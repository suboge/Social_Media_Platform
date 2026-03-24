package com.popo.esun.socialmedia;

import com.popo.esun.socialmedia.config.SecurityConfig;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(SecurityConfig.class) // <--- 強制載入你的自定義安全設定
class ESumSocialMediaPlatformApplicationTests {

    @Test
    void contextLoads() {
    }

}
