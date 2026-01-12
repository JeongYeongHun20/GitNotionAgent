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

    @McpTool(name = "get_my_latest_commit",
            description = """
                   지정한 레포지토리의 최근 커밋 목록을 보여줍니다.\s
                   이 도구를 호출한 후에는 반드시 다음 단계를 수행하세요:
                   1. 반환된 커밋 메시지 리스트를 SHA값을 제외하고 최신순으로 사용자에게 보여준다.
                   2. 특정 커밋을 상세 분석하고 싶다면 리스트 번호를 알려달라고 사용자에게 친절하게 제안한다.
                   \s""")
    public String getMyCommits(
            @McpToolParam(description = "레포지토리 이름 (예: GitNotionAgent)") String repo) {
        try {
            return githubService.getRecentCommits(repo);
        }catch (IOException e) {
            return e.getMessage();
        }


    }
    
    @McpTool(name = "analyze_commit",
            description = """
                   \s
                   이 도구를 호출한 후에는 반드시 다음 단계를 수행하세요:
                   1. 지정한 커밋을 분석하여 보여줍니다.
                   2. 반환된 커밋을 분석하여 커밋의도(기능추가, 에러해결, 리팩토링)와 커밋한 이유에 대해서 분석하여 사용자에게 보여줍니다
                   \s""")
    public String analyzeCommit(
            @McpToolParam(description = "레포지토리 이름 (예: GitNotionAgent)") String repo,
            @McpToolParam(description = "SHA 값(예: 5s4fe21fw5e6q)") String sha
    ){
        try{
            return githubService.getCommitFiles(repo, sha);
        }catch (IOException e){
            return e.getMessage();
        }

    }
    @McpTool(
            name = "get_commit_code",
            description ="""
                    지정한 커밋의 전체 변경사항을 보여줍니다\s
                    아래 지정한 양식을 무조건 따라서 보여줘
                    1. 변경한 파일명을 보여줘
                    2. 바뀐후의 코드를 보여줘
                    \s""")
    public String getCommit(
            @McpToolParam(description = "레포지토리 이름 (예: GitNotionAgent)") String repo,
            @McpToolParam(description = "SHA 값(예: 5s4fe21fw5e6q)") String sha
    ){
        try{
            return githubService.getCommitFiles(repo,sha);
        }catch (IOException e){
            return e.getMessage();
        }
    }

    @McpTool(
            name = "get_commit_file",
            description = """
                    지정한 특정한 파일의 변경 사항을 보여줍니다\s
                    아래 지정한 양식을 무조건 따라서 보여줘
                    1. 변경한 파일명을 보여줘
                    2. 바뀌귀전 코드를 보여줘
                    3. 바뀐후의 코드를 보여줘
                    \s""")
    public String getCommitFile(
            @McpToolParam(description = "레포지토리 이름 (예: GitNotionAgent)") String repo,
            @McpToolParam(description = "SHA 값(예: 5s4fe21fw5e6q)") String sha,
            @McpToolParam(description = "파일 경로값(예: src/main/resources/static")String path){

        try{
            return githubService.getCommitFile(repo, sha, path);
        }catch (IOException e){
            return e.getMessage();
        }
    }

}
