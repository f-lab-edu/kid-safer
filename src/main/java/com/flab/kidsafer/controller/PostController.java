package com.flab.kidsafer.controller;

import com.flab.kidsafer.dto.PostDTO;
import com.flab.kidsafer.service.PostService;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/{postId}")
    public ResponseEntity<PostDTO> getOnePost(@PathVariable int postId) {
        PostDTO post = postService.getPostInfoByPostId(postId);
        return ResponseEntity.ok(post);
    }

    @PostMapping
    public void registerPost(@RequestBody @Valid PostDTO postDTO) {
        postService.registerPost(postDTO);
    }

    @PutMapping("/{postId}")
    public void modifyPostInfo(@PathVariable int postId, @RequestBody @Valid PostDTO postDTO,
        HttpSession httpSession) {
        if (postId != postDTO.getId()) {
            throw new RuntimeException("수정하려는 post가 유효하지 않습니다."); // TODO : 차후 Merge할 예외 사용 예정
        }

        postService.modifyPostsInfo(postDTO, (Integer) httpSession
            .getAttribute("MEMBER_ID")); // TODO : httpSession이 아닌 애노테이션으로 세션 정보 가져오도록 개선 예정
    }

    @DeleteMapping("/{postId}")
    public void deletePost(@PathVariable int postId, HttpSession httpSession) {
        postService.deletePosts(postId, (Integer) httpSession
            .getAttribute("MEMBER_ID"));   // TODO : httpSession이 아닌 애노테이션으로 세션 정보 가져오도록 개선 예정
    }
}
