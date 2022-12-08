package com.forum.forum.controller;

import com.forum.forum.dto.ForumRequest;
import com.forum.forum.dto.ForumResponse;
import com.forum.forum.dto.StatusResponse;
import com.forum.forum.service.ForumService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/forum")
public class ForumController {
    private final ForumService forumService;
    @PostMapping
    public ResponseEntity<StatusResponse> createForum(@RequestBody ForumRequest forumRequest, HttpServletRequest request){
        StatusResponse forum = forumService.createForum(forumRequest, request);
        return new ResponseEntity<>(forum, HttpStatus.valueOf(forum.getStatusCode()));
    }

    @GetMapping
    public List<ForumResponse> getForum(){
        return forumService.getForum();
    }

    @GetMapping("/{id}")
    public ForumResponse getForum(@PathVariable Long id){
        return forumService.getForum(id);
    }

    @PatchMapping("/{id}")
    public ForumResponse updateForum(@PathVariable Long id, @RequestBody ForumRequest forumRequest,HttpServletRequest request){
        return forumService.updateForum(id,forumRequest,request);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<StatusResponse> deleteForum(@PathVariable Long id, HttpServletRequest request){
        StatusResponse forum = forumService.deleteForum(id,request);
        return new ResponseEntity<>(forum, HttpStatus.valueOf(forum.getStatusCode()));
    }
}

