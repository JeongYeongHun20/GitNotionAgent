package com.gitnotionagent.service;

import org.kohsuke.github.GHCommit;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
public class GithubService {

    private final String token;

    public GithubService(@Value("${github.token}") String token) {
        this.token=token;
    }

    public List<GHCommit> getRecentCommits(String owner, String repoName) throws IOException{
        GitHub github = new GitHubBuilder().withOAuthToken(token).build();
        GHRepository repository = github.getRepository(owner + "/" + repoName);
        return repository.listCommits().toList().subList(0, 5);
    }

    public String getCommitDiffSummary(GHCommit commit) throws IOException {
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

}
