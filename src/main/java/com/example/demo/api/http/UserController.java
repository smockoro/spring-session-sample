package com.example.demo.api.http;

import com.example.demo.domain.model.User;
import com.example.demo.domain.usecase.UserUsecase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
public class UserController {

  private final UserUsecase userUsecase;

  @GetMapping("/users")
  public User getUser() {
    User user = new User("00001", "alice", 12);
    return user;
  }
}
