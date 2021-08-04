package com.flab.kidsafer.controller;

import com.flab.kidsafer.config.auth.LoginUser;
import com.flab.kidsafer.config.auth.dto.SessionUser;
import com.flab.kidsafer.dto.PostCommentDTO;
import com.flab.kidsafer.service.CommentService;
import java.util.List;
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
public class CommentController {

    @Autowired
    private CommentService commentService;

    @GetMapping("/{postId}/comments")
    public List<PostCommentDTO> getCommentsByPostNo(@PathVariable int postId,
        @LoginUser SessionUser user) {
        return commentService.getCommentsByPostId(postId, user.getId());
    }

    @PostMapping("/{postId}/comments")
    public ResponseEntity<PostCommentDTO> saveComment(@RequestBody @Valid PostCommentDTO comment,
        @LoginUser SessionUser user) {
        commentService.saveNewComments(comment, user);

        return ResponseEntity.ok(comment);
    }

    @DeleteMapping("{postId}/comments/{commentId}")
    public void deleteComment(@PathVariable int commentId, @LoginUser SessionUser user) {
        commentService.deleteComment(commentId, user.getId());
    }

    @PutMapping("/{postId}/comments/{commentId}")
    public void updateComment(@RequestBody @Valid PostCommentDTO comment,
        @LoginUser SessionUser user) {
        commentService.updateComment(comment, user.getId());
    }
}
