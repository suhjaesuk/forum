package com.forum.forum.entity;

import com.forum.forum.dto.ForumRequest;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@Entity
@NoArgsConstructor
public class Forum extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable =false)
    private String title;

    @Column(nullable = false)
    private String contents;

    public Forum(ForumRequest forumRequest) {
        this.username = forumRequest.getUsername();
        this.password = forumRequest.getPassword();
        this.title = forumRequest.getTitle();
        this.contents = forumRequest.getContents();
    }
    public void update(ForumRequest forumRequest) {
        this.username = forumRequest.getUsername();
        this.title = forumRequest.getTitle();
        this.contents=forumRequest.getContents();
    }
}
