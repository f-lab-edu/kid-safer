package com.flab.kidsafer.domain;

public class SignInResponse {

  private SignInStatus status;
  User user;

  public SignInResponse(SignInStatus status, User user) {
    this.status = status;
    this.user = user;
  }

  public SignInStatus getStatus() {
    return status;
  }

  public User getUser() {
    return user;
  }
}
