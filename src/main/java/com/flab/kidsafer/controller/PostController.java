package com.flab.kidsafer.controller;

import com.flab.kidsafer.config.auth.LoginUser;
import com.flab.kidsafer.config.auth.dto.SessionUser;
import com.flab.kidsafer.domain.enums.UserType;
import com.flab.kidsafer.dto.PostDTO;
import com.flab.kidsafer.error.exception.OperationNotAllowedException;
import com.flab.kidsafer.service.PostService;
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
    public void registerPost(@RequestBody @Valid PostDTO postDTO, @LoginUser SessionUser user) {
        if(user.getType() != UserType.PARENT)
            throw new OperationNotAllowedException();
        postService.registerPost(postDTO);
    }

    @PutMapping("/{postId}")
    public void modifyPostInfo(@PathVariable int postId, @RequestBody @Valid PostDTO postDTO,
        @LoginUser SessionUser user) {
        if (postId != postDTO.getId()) {
            throw new OperationNotAllowedException();
        }

        postService.modifyPostsInfo(postDTO, user.getId());
    }

    @DeleteMapping("/{postId}")
    public void deletePost(@PathVariable int postId, @LoginUser SessionUser user) {
        postService.deletePosts(postId, user.getId());
    }
}
