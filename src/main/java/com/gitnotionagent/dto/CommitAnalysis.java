package com.gitnotionagent.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CommitAnalysis {
    private String summary;
    private String category;
    private String commitDate;
    private String commitUrl;
    private String aiImpactAnalysis;
    private String fileList;
}
