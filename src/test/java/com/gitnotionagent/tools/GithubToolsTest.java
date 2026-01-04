package com.gitnotionagent.tools;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class GithubToolsTest {
    @Autowired
    private GithubTools githubTools;

    @Test
    void toolExecuteTest(){
        String result = githubTools.getMyWork("JeongYeongHun20", "WebSocketChat");
        System.out.println("AI에게 전달될 결과값: " + result);
        assertThat(result).contains("커밋 메시지");
    }

}