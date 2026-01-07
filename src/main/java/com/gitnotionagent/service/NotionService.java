package com.gitnotionagent.service;

import com.gitnotionagent.dto.CommitAnalysis;
import com.gitnotionagent.util.NotionUtil;
import lombok.RequiredArgsConstructor;
import notion.api.v1.model.blocks.Block;
import notion.api.v1.model.common.OptionColor;
import notion.api.v1.model.databases.*;
import notion.api.v1.model.pages.PageParent;
import notion.api.v1.model.pages.PageParentType;
import notion.api.v1.request.databases.CreateDatabaseRequest;
import org.springframework.stereotype.Service;


import notion.api.v1.NotionClient;
import notion.api.v1.model.pages.Page;
import notion.api.v1.model.pages.PageProperty;
import notion.api.v1.request.pages.CreatePageRequest;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotionService {

    private final NotionClient client;

    @Value("${notion.pageId}")
    private String pageId;




    //초기 생성된 DB(표)에 실제 값들을 입력해주는 함수
    public String createCommitLogPage(CommitAnalysis analysis, String dbId) {
        Map<String, PageProperty> properties = Map.of(
                "Name", NotionUtil.createTitle(analysis.getSummary()),
                "Category", NotionUtil.createSelect(analysis.getCategory()),
                "Date", NotionUtil.createDate(analysis.getCommitDate()),
                "Commit Hash", NotionUtil.createUrl(analysis.getCommitUrl())
        );

        List<Block> contents = List.of(
                NotionUtil.h2("📝 상세 변경 내역 (Detailed Changes)"),
                NotionUtil.p(analysis.getAiImpactAnalysis()),

                NotionUtil.h2("📂 수정된 파일 목록"),
                NotionUtil.p(analysis.getFileList())

        );

        Page response = client.createPage(new CreatePageRequest(
                new PageParent(PageParentType.PageId,dbId),
                properties,
                contents
        ));

        return response.getUrl();
    }

    //지정 페이지에 DB(표) 생성 함수
    public String createNewCommitLogDatabase() {
        Map<String, DatabasePropertySchema> schema = Map.of(
                "Name", new TitlePropertySchema(),
                "Category", new SelectPropertySchema(
                       List.of(
                                new SelectOptionSchema("✨ Feature", OptionColor.Blue,null),
                                new SelectOptionSchema("🐛 Fix", OptionColor.Red,null),
                                new SelectOptionSchema("♻️ Refactor", OptionColor.Green,null)
                        )),
                "Date", new DatePropertySchema(),
                "Commit Hash", new URLPropertySchema()
        );

        Database newDb = client.createDatabase(new CreateDatabaseRequest(
                new DatabaseParent(DatabaseParentType.PageId,pageId),
                NotionUtil.createDBRichTextList("GitNotion Commit Logs"),
                schema
        ));


        return newDb.getId();
    }

}
