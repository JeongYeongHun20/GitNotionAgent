package com.gitnotionagent.service;


import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;

@SpringBootTest
class GithubServiceTest {

    private static final Logger log = LoggerFactory.getLogger(GithubServiceTest.class);
    @Autowired
    private GithubService githubService;
    @Test
    void getRecentCommits() {
        try{
            String recentCommits = githubService.getRecentCommits("JeongYeongHun20", "WebSocketChat2");
            System.out.println(recentCommits);
        }catch (IOException e){
            log.error("e: ", e);
        }

    }
}