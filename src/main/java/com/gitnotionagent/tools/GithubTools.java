package com.gitnotionagent.tools;


import com.gitnotionagent.service.GithubService;
import lombok.RequiredArgsConstructor;
import org.springaicommunity.mcp.annotation.McpTool;
import org.springaicommunity.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Component;

import java.io.IOException;


@Component
@RequiredArgsConstructor
public class GithubTools {

    private final GithubService githubService;

    @McpTool(name = "get_my_latest_work",
            description = """
                   지정한 레포지토리의 최근 커밋 목록을 보여줍니다.\s
                   이 도구를 호출한 후에는 반드시 다음 단계를 수행하세요:
                   1. 반환된 커밋 리스트를 사용자에게 보여준다.
                   2. 특정 커밋을 상세 분석하고 싶다면 리스트의 'SHA' 값을 알려달라고 사용자에게 친절하게 제안한다.
                   \s""")
    public String getMyCommits(
            @McpToolParam(description = "GitHub 사용자명 (예: YeongHun)") String owner,
            @McpToolParam(description = "레포지토리 이름 (예: GitNotionAgent)") String repo) {
        try {
            return githubService.getRecentCommits(owner, repo);
        }catch (IOException e) {
            return "화살표 뒤의 에러코드와 메시지를보고 분석하여 사용자에게 전달해 ->" + e.getMessage();
        }


    }
    
    @McpTool(name = "analyze_commit",
            description = """
                   지정한 커밋을 분석하여 보여줍니다\s
                   이 도구를 호출한 후에는 반드시 다음 단계를 수행하세요:
                   1. 반환된 커밋을 분석하여 커밋의도(기능추가, 에러해결, 리팩토링)와 커밋한 이유에 대해서 분석하여 사용자에게 보여줍니다
                   2. 사용자의 의도를 물어봅니다.
                   \s""")
    public String analyzeCommit(
            @McpToolParam(description = "GitHub 사용자명 (예: YeongHun)") String owner,
            @McpToolParam(description = "레포지토리 이름 (예: GitNotionAgent)") String repo,
            @McpToolParam(description = "SHA 값(예: 5s4fe21fw5e6q)") String SHA
    ){
        try{
            return githubService.getCommitDiffSummary(owner, repo, SHA);
        }catch (IOException e){
            return "IOException"+e.getMessage();
        }

    }
    @McpTool(
            name = "get_commit_code",
            description = "지정한 커밋의 상세 코드를 그대로 보여줍니다" +
                    "1. 바뀌귀전 코드를 먼저 보여주세요" +
                    "2. 바뀐후의 코드를 보여주세요"
    )
    public String getCommit(
            @McpToolParam(description = "GitHub 사용자명 (예: YeongHun)") String owner,
            @McpToolParam(description = "레포지토리 이름 (예: GitNotionAgent)") String repo,
            @McpToolParam(description = "SHA 값(예: 5s4fe21fw5e6q)") String SHA
    ){
        
        return "";
    }

}
