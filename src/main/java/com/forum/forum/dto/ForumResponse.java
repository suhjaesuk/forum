package com.forum.forum.dto;

import com.forum.forum.entity.Forum;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class ForumResponse {
    private LocalDateTime createdAt;
    private String username;
    private String title;
    private String content;
    private  List<CommentResponse> commentList;

    public ForumResponse(Forum forum){
        this.createdAt = forum.getCreatedAt();
        this.username = forum.getUsername();
        this.title = forum.getTitle();
        this.content = forum.getContent();
        commentList=new ArrayList<>();
    }
}
