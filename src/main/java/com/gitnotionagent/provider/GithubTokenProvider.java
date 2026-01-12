package com.gitnotionagent.provider;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GithubTokenProvider {
    private final HttpServletRequest request;
//
//    @Value("${github.token}")
//    private String token;

    private String getGitHubTokenFromHeader(String authHeader) {
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            return authHeader.substring(7);
        }
        return null;
    }

    public String getToken(){
        String authHeader = request.getHeader("Authorization");

//        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
//            return token;
//        }else {
            return getGitHubTokenFromHeader(authHeader);
//        }
    }




}
