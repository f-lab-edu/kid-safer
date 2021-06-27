package com.flab.kidsafer.controller;

import com.flab.kidsafer.config.auth.LoginUser;
import com.flab.kidsafer.config.auth.dto.SessionUser;
import com.flab.kidsafer.dto.PostLikeDTO;
import com.flab.kidsafer.service.PostLikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts/{postId}/likes")
public class PostLikeController {

    @Autowired
    private PostLikeService postLikeService;

    @PostMapping
    public ResponseEntity<PostLikeDTO> addLike(@PathVariable int postId,
        @LoginUser SessionUser user) {
        PostLikeDTO postLikeDTO = postLikeService.like(postId, user.getId());
        return ResponseEntity.ok(postLikeDTO);
    }

    @DeleteMapping
    public void unLike(@PathVariable int postId,
        @LoginUser SessionUser user) {
        postLikeService.unlike(postId, user.getId());
    }
}
