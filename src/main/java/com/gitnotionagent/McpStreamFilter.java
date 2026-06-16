package com.gitnotionagent; 

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
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
