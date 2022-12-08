package com.forum.forum.service;

import com.forum.forum.dto.CommentResponse;
import com.forum.forum.dto.ForumRequest;
import com.forum.forum.dto.ForumResponse;
import com.forum.forum.dto.StatusResponse;
import com.forum.forum.entity.Comment;
import com.forum.forum.entity.Forum;
import com.forum.forum.entity.User;
import com.forum.forum.jwt.JwtUtil;
import com.forum.forum.repository.CommentRepository;
import com.forum.forum.repository.ForumRepository;
import com.forum.forum.repository.UserRepository;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

import static com.forum.forum.entity.Role.ADMIN;

@Service
@RequiredArgsConstructor
public class ForumService {
    private final ForumRepository forumRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;
    private final JwtUtil jwtUtil;


    @Transactional
    public StatusResponse createForum(ForumRequest forumRequest, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;

        if (token != null) {
            if (jwtUtil.validateToken(token)) {claims = jwtUtil.getUserInfoFromToken(token);} else {throw new IllegalArgumentException("Token Error");}

            Forum forum = forumRequest.toEntity(claims.getSubject());
            forum.confirmWriter(userRepository.findByUsername(claims.getSubject()).orElseThrow(() -> new IllegalArgumentException("게시물이 존재하지 않습니다.")));
            forumRepository.save(forum);

            return new StatusResponse("게시글 작성 완료", HttpStatus.OK.value());} else {return null;}
    }


    @Transactional(readOnly = true)
    public List<ForumResponse> getForum() {
        List<ForumResponse> forumResponseList = new ArrayList<>();
        List<Forum> forums = forumRepository.findAllByOrderByModifiedAtDesc();

        for (Forum forum : forums) {
            ForumResponse forumResponse = new ForumResponse(forum);
            List<Comment> comments = commentRepository.findAllByForum_IdOrderByModifiedAtDesc(forum.getId());
            for (Comment comment : comments) {
                forumResponse.getCommentList().add(new CommentResponse(comment));
            }
            forumResponseList.add(forumResponse);
        }
        return forumResponseList;
    }

    @Transactional(readOnly = true)
    public ForumResponse getForum(Long id) {

        Forum forum = checkForum(id);
        ForumResponse forumResponse = new ForumResponse(forum);
        List<Comment> comments = commentRepository.findAllByForum_IdOrderByModifiedAtDesc(id);
        for (Comment comment : comments) {
            forumResponse.getCommentList().add(new CommentResponse(comment));
        }
        return forumResponse;
    }

    @Transactional
    public ForumResponse updateForum(Long id, ForumRequest forumRequest, HttpServletRequest request) {
        String token = jwtUtil.resolveToken(request);
        Claims claims;
        Forum forum = checkForum(id);
        if (token != null) {
            if (jwtUtil.validateToken(token)) {claims = jwtUtil.getUserInfoFromToken(token);} else {throw new IllegalArgumentException("Token Error");}

            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(() -> new IllegalArgumentException("등록된 사용자가 없습니다."));

            if (user.getRole().equals(ADMIN)) {forum.update(forumRequest);

            }else{
                if(claims.getSubject().equals(forum.getUsername())) {forum.update(forumRequest);
                }else{throw new IllegalArgumentException("접근 권한이 없습니다.");}

            }return new ForumResponse(forum);} else {return null;}
    }

    @Transactional
    public StatusResponse deleteForum(Long id, HttpServletRequest request) {
        Forum forum = checkForum(id);
        String token = jwtUtil.resolveToken(request);
        Claims claims;
        if (token != null) {
            if (jwtUtil.validateToken(token)) {claims = jwtUtil.getUserInfoFromToken(token);} else {throw new IllegalArgumentException("Token Error");}

            User user = userRepository.findByUsername(claims.getSubject()).orElseThrow(() -> new IllegalArgumentException("등록된 사용자가 없습니다."));

            if (user.getRole().equals(ADMIN)) {forumRepository.deleteById(id);
            }else{
                if (claims.getSubject().equals(forum.getUsername())) {forumRepository.deleteById(id);
                }else{throw new IllegalArgumentException("접근 권한이 없습니다.");}

            }return new StatusResponse("게시글 삭제 완료", HttpStatus.OK.value()); }else{return null;}
        }

        private Forum checkForum (Long id){
            return forumRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("게시물이 존재하지 않습니다."));
        }
    }
