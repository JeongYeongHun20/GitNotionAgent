package com.gitnotionagent.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class NotionServiceTest {


    @Autowired
    private NotionService notionService; // 테스트할 대상


    @Test
    void createCommitLogPage() {
    }

    @Test
    @DisplayName("실제 노션 서버에 DB(표)가 생성되는지 확인한다")
    void real_createDatabase_Test() {

        String createdDbid = notionService.createNewCommitLogDatabase();

        // 3. Then: 실제 DB ID가 생성되었는지 검증
        System.out.println(">>> 생성된 노션 DB ID: " + createdDbid);
        assertNotNull(createdDbid);
    }


}