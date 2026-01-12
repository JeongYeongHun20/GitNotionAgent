package com.gitnotionagent; // 사용자님의 실제 패키지 경로에 맞게 수정하세요

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component // 이 어노테이션 덕분에 별도의 Config 파일이 없어도 작동합니다!
public class McpStreamFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        filterChain.doFilter(request, response);

        if (request.getRequestURI().contains("mcp")) {
            response.setHeader("X-Accel-Buffering", "no");

            response.setHeader("Cache-Control", "no-cache");
            response.setHeader("Connection", "keep-alive");


        }
    }
}