package com.forum.forum.service;

import com.forum.forum.dto.ForumListResponse;
import com.forum.forum.dto.ForumRequest;
import com.forum.forum.dto.ForumResponse;
import com.forum.forum.entity.Forum;
import com.forum.forum.repository.ForumRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ForumService {
    private final ForumRepository forumRepository;
    @Transactional
    public ForumResponse createForum(ForumRequest forumRequest) {
        Forum forum = new Forum(forumRequest);
        forumRepository.save(forum);
        return new ForumResponse("게시글 작성 완료", HttpStatus.OK.value());
    }

    @Transactional(readOnly = true) //유지보수 측면 생각해보기
    public List<ForumListResponse> getForum() {
        List<ForumListResponse> forumList = new ArrayList<>();
        List<Forum> forums  = forumRepository.findAllByOrderByModifiedAtDesc();
        for(Forum forum : forums){
            forumList.add(new ForumListResponse(forum.getCreatedAt(), forum.getUsername(), forum.getTitle(), forum.getContents()));
        }
        return forumList;
    }

    @Transactional(readOnly = true) //유지보수 측면 생각해보기
    public ForumListResponse getForum(Long id){
        Forum forum = checkForum(forumRepository, id);
        return new ForumListResponse(forum.getCreatedAt(), forum.getUsername(), forum.getTitle(), forum.getContents());
    }

    @Transactional //예외 처리 어떻게할까?
    public ForumResponse updateForum(Long id,ForumRequest forumRequest) {
        Forum forum = checkForum(forumRepository, id);
        if(checkPassword(forumRequest, forum)){
            forum.update(forumRequest);
        }else{
            throw new IllegalArgumentException("올바르지 않은 비밀번호입니다.");
        }
        return new ForumResponse("게시글 변경 완료", HttpStatus.OK.value());
    }
    @Transactional //예외 처리 어떻게할까?
    public ForumResponse deleteForum(Long id, ForumRequest forumRequest) {
        Forum forum = checkForum(forumRepository, id);
        if(checkPassword(forumRequest, forum)){
            forumRepository.deleteById(id);
        }else{
            throw new IllegalArgumentException("올바르지 않은 비밀번호입니다.");
        }
        return new ForumResponse("게시글 삭제 완료", HttpStatus.OK.value());
    }


    private static boolean checkPassword(ForumRequest forumRequest, Forum forum) {
        return forum.getPassword().equals(forumRequest.getPassword());
    }

    private Forum checkForum(ForumRepository forumRepository, Long id){
        return forumRepository.findById(id).orElseThrow(
                ()->new IllegalArgumentException("게시물이 존재하지 않습니다.")
        );
    }
}
