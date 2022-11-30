package com.forum.forum.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class ForumListResponse {
    private LocalDateTime createdAt;
    private String username;
    private String title;
    private String contents;

    public ForumListResponse(LocalDateTime createdAt, String username, String title, String contents){
        this.createdAt = createdAt;
        this.username = username;
        this.title = title;
        this.contents = contents;
    }
}
