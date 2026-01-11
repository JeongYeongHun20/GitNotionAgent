package com.gitnotionagent.service;


import com.gitnotionagent.provider.GithubTokenProvider;
import lombok.RequiredArgsConstructor;
import org.kohsuke.github.*;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;


@Service
@RequiredArgsConstructor
public class GithubService {

    private final GithubTokenProvider tokenProvider;

    public String getRecentCommits(String owner, String repoName) throws IOException {

            GHRepository repository = getGhRepository(owner, repoName);
            List<GHCommit> commits = repository.listCommits().toList();
            if (commits.isEmpty()) {
                throw new IOException("### 결과\n" + owner + "/" + repoName + " 레포지토리에 커밋 내역이 없습니다.");
            }

            StringBuilder sb = new StringBuilder();
            sb.append("### 📂 ").append(owner).append("/").append(repoName).append(" 최신 커밋 목록\n\n");

            commits.stream().limit(5).forEach(c -> {
                try {
                    String fullSha = c.getSHA1();
                    String message = c.getCommitShortInfo().getMessage().trim(); // 앞뒤 공백 제거
                    String author = c.getAuthor() != null ? c.getAuthor().getLogin() : "unknown";
                    String date = c.getCommitDate().toString();

                    sb.append(" **커밋 메시지:** ").append(message).append("\n");
                    sb.append("- **작성자:** ").append(author).append("\n");
                    sb.append("- **날짜:** ").append(date).append("\n");
                    sb.append("- **SHA:** `").append(fullSha).append("`\n\n");
                    sb.append("---\n\n"); // 각 커밋 사이에 구분선 추가

                } catch (IOException e) {
                }
            });

            return sb.toString();

    }

    public String getCommitDiffSummary(String owner, String repoName, String sha) throws IOException {
        GHRepository repository = getGhRepository(owner, repoName);
        GHCommit commit = repository.getCommit(sha);
        if (commit==null){
            throw new IOException("### 결과\\n" + owner + "/" + repoName + " 레포지토리에 커밋 내역이 없습니다.");
        }
        StringBuilder sb = new StringBuilder();
        sb.append("커밋 메시지: ").append(commit.getCommitShortInfo().getMessage()).append("\n");
        sb.append("변경된 파일 목록:\n");

        for (GHCommit.File file : commit.getFiles()) {
            sb.append("- ").append(file.getFileName())
                    .append(" (").append(file.getStatus()).append(")\n");
            if (file.getPatch() != null) {
                sb.append("  Patch: ").append(file.getPatch()).append("\n");
            }
        }
        return sb.toString();
    }

    private GHRepository getGhRepository(String owner, String repoName) throws IOException {
        String token=tokenProvider.getToken();
        GitHub github = new GitHubBuilder().withOAuthToken(token).build();
        return github.getRepository(owner + "/" + repoName);
    }

}
