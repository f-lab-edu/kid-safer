package com.flab.kidsafer.controller;

import com.flab.kidsafer.config.auth.LoginUser;
import com.flab.kidsafer.config.auth.dto.SessionUser;
import com.flab.kidsafer.dto.PostRequestDTO;
import com.flab.kidsafer.service.PostRequestService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts/{postId}/requests")
public class PostRequestController {

  @Autowired
  private PostRequestService postRequestService;

  @GetMapping("/mine")
  public ResponseEntity<PostRequestDTO> getSingleRequest(@PathVariable int postId,
      @LoginUser SessionUser user) {
    return ResponseEntity.ok(postRequestService.getSingleRequest(postId, user.getId()));
  }

  @GetMapping("/all")
  public ResponseEntity<List<PostRequestDTO>> getAllRequests(@PathVariable int postId) {
    return ResponseEntity.ok(postRequestService.getAllRequests(postId));
  }

  @PutMapping
  public void applyRequest(@ModelAttribute PostRequestDTO postRequestDTO, @PathVariable int postId,
      @LoginUser SessionUser user) {
    postRequestService.applyRequest(postRequestDTO);
  }

  @DeleteMapping("/mine")
  public void cancelRequest(@PathVariable int postId, @LoginUser SessionUser user) {
    postRequestService.cancelRequest(postId, user.getId());
  }
}
