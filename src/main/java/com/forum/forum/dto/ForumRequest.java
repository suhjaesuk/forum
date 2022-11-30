package com.forum.forum.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ForumRequest { //요청
    private String username;
    private String password;
    private String title;
    private String contents;
}
