package com.forum.forum.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ForumResponse { //응답
    private String massage;
    private int statusCode;

    public ForumResponse(String massage, int statusCode) {
        this.massage = massage;
        this.statusCode = statusCode;
    }


}
