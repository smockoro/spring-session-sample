package com.example.demo.domain.usecase;

import com.example.demo.domain.model.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class UserInteractor implements UserUsecase {

  @Override
  public String findOneUser(String useId) {
    return null;
  }

  @Override
  public String findAllUser() {
    return null;
  }

  @Override
  public User createUser(User user) {
    return null;
  }

  @Override
  public User updateUser(User user) {
    return null;
  }

  @Override
  public Boolean deleteOneUser(String userId) {
    return null;
  }

  @Override
  public Boolean deleteAllUser() {
    return null;
  }
}
