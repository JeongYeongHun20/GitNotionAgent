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
            String recentCommits = githubService.getRecentCommits( "WebSocketChat");
            System.out.println(recentCommits);
        }catch (IOException e){
            log.error("e: ", e);
        }

    }

    @Test
    void getCommitFiles(){
        try{
            String commitDiff = githubService.getCommitFiles( "WebSocketChat","b6a53ffc9969aa7b3527aa64a241d13eebfc93ef");
            System.out.println(commitDiff);
        } catch (IOException e) {
            log.error("e: "+e);
        }
    }

}