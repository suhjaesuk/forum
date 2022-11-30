package com.forum.forum.controller;

import com.forum.forum.dto.ForumListResponse;
import com.forum.forum.dto.ForumRequest;
import com.forum.forum.dto.ForumResponse;
import com.forum.forum.service.ForumService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ForumController {

    private final ForumService forumService;

    @PostMapping("/forum")
    public ForumResponse createForum(@RequestBody ForumRequest forumRequest){
        return forumService.createForum(forumRequest);
    }

    @GetMapping("/forum")
    public List<ForumListResponse> getForum(){
        return forumService.getForum();
    }

    @GetMapping("/forum/{id}")
    public ForumListResponse getForum(@PathVariable Long id){
        return forumService.getForum(id);
    }

    @PutMapping("/forum/{id}")
    public ForumResponse updateForum(@PathVariable Long id, @RequestBody ForumRequest forumRequest){
        return forumService.updateForum(id, forumRequest);
    }

    @DeleteMapping("forum/{id}")
    public ForumResponse deleteForum(@PathVariable Long id, @RequestBody ForumRequest forumRequest){
        return forumService.deleteForum(id, forumRequest);
    }
}
