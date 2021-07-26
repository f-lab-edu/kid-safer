package com.flab.kidsafer.controller;

import com.flab.kidsafer.config.auth.LoginUser;
import com.flab.kidsafer.config.auth.dto.SessionUser;
import com.flab.kidsafer.domain.enums.UserType;
import com.flab.kidsafer.dto.PostRequestDTO;
import com.flab.kidsafer.error.exception.OperationNotAllowedException;
import com.flab.kidsafer.error.exception.UserNotSignInException;
import com.flab.kidsafer.service.PostRequestService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    checkUserSignIn(user);
    return ResponseEntity.ok(postRequestService.getSingleRequest(postId, user.getId()));
  }

  @GetMapping
  public ResponseEntity<List<PostRequestDTO>> getAllRequests(@PathVariable int postId,
      @LoginUser SessionUser user) {
    checkUserSignIn(user);
    return ResponseEntity.ok(postRequestService.getAllRequests(postId));
  }

  @PostMapping
  public void applyRequest(@RequestBody PostRequestDTO postRequestDTO, @PathVariable int postId,
      @LoginUser SessionUser user) {
    if (user.getType() != UserType.SAFER) {
      throw new OperationNotAllowedException();
    }
    postRequestService.applyRequest(postRequestDTO);
  }

  @DeleteMapping
  public void cancelRequest(@PathVariable int postId, @LoginUser SessionUser user) {
    if (user.getType() != UserType.SAFER) {
      throw new OperationNotAllowedException();
    }
    postRequestService.cancelRequest(postId, user.getId());
  }

  public void checkUserSignIn(SessionUser user) {
    if (user == null) {
      throw new UserNotSignInException();
    }
  }
}
