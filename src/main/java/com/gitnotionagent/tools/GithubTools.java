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
            description = "특정 GitHub 레포지토리의 최신 작업 내용(커밋 및 코드 변경점)을 가져옵니다.")
    public String getMyWork(
            @McpToolParam(description = "GitHub 사용자명 (예: YeongHun)") String owner,
            @McpToolParam(description = "레포지토리 이름 (예: GitNotionAgent)") String repo) {
        try {
            var commits = githubService.getRecentCommits(owner, repo);
            if (commits.isEmpty()) return "최근 커밋 내역이 없습니다.";

            // 가장 최신 커밋의 상세 내용 분석
            return githubService.getCommitDiffSummary(commits.get(0));
        } catch (IOException e) {
            return "오류 발생: " + e.getMessage();
        }
    }
}
