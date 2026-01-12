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

    public String getRecentCommits(String repo) throws IOException {

            GHRepository repository = getGHRepository( repo);
            List<GHCommit> commits = repository.listCommits().toList();
            if (commits.isEmpty()) {
                throw new IOException("### 결과\n" + repo + " 레포지토리에 커밋 내역이 없습니다.");
            }

            StringBuilder sb = new StringBuilder();
            sb.append("### 📂 ").append("/").append(repo).append(" 최신 커밋 목록\n\n");

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

    public String getCommitFiles(String repo, String sha) throws IOException {
        GHRepository repository = getGHRepository(repo);
        GHCommit commit = repository.getCommit(sha);
        List<String> filePaths = new java.util.ArrayList<>();
        if (commit==null){
            throw new IOException("### 결과\\n" + repo + " 레포지토리에 커밋 내역이 없습니다.");
        }
        StringBuilder sb = new StringBuilder();
        sb.append("커밋 메시지: ").append(commit.getCommitShortInfo().getMessage()).append("\n");
        sb.append("변경된 파일 목록:\n");
        
        for (GHCommit.File file : commit.getFiles()) {
            filePaths.add(file.getFileName());
            sb.append("* ").append(file.getFileName())
                    .append(" (").append(file.getStatus()).append(")\n");
            if (file.getPatch() != null) {
                sb.append("  Patch: ").append(file.getPatch()).append("\n");
            }
        }
        sb.append("파일 경로 목록:\n");
        filePaths.forEach((p->sb.append("-").append(p).append("\n")));

        return sb.toString();
    }
    public String getCommitFile(String repo, String sha, String path) throws IOException {
        if (path==null){
            throw new IOException("이 파일은 변경 내용을 텍스트로 표시할 수 없습니다");
        }

        GHRepository ghRepository = getGHRepository(repo);
        GHCommit commit = ghRepository.getCommit(sha);
        GHCommit.File file = null;
        for (GHCommit.File selectedFile : commit.getFiles()) {
            if (selectedFile.getFileName().equals(path)) {
                file=selectedFile;
            }
        }
        if (file==null){
            throw new IOException("아래 파일 경로는 변경되지 않은 파일입니다\n"+path);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("커밋 메시지: ").append(commit.getCommitShortInfo().getMessage()).append("\n");
        sb.append("변경된 파일:\n");
        sb.append("* ").append(file.getFileName())
                .append(" (").append(file.getStatus()).append(")\n");
        if (file.getPatch() != null) {
            sb.append("  Patch: ").append(file.getPatch()).append("\n");
        }

        return sb.toString();
    }


    private GHRepository getGHRepository(String repo) throws IOException {
        String token=tokenProvider.getToken();
        GitHub github = new GitHubBuilder().withOAuthToken(token).build();
        String login = github.getMyself().getLogin();

        return github.getRepository(login + "/" + repo);

    }

}
