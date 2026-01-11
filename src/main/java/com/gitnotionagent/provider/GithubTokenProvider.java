package com.gitnotionagent.provider;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GithubTokenProvider {
    private final HttpServletRequest request; // 현재 HTTP 요청 정보를 가져옴

    @Value("${github.token}")
    private String token;

    private String getGitHubTokenFromHeader(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7); // "Bearer " 이후의 토큰값만 추출
        }
        return null;
    }

    public String getToken(){
        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return token;
        }else {
            return getGitHubTokenFromHeader(authHeader);
        }
    }




}
