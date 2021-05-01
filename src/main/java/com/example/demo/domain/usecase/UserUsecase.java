package com.example.demo.domain.usecase;

import com.example.demo.domain.model.User;

public interface UserUsecase {

  public String findOneUser(String useId);

  public String findAllUser();

  public User createUser(User user);

  public User updateUser(User user);

  public Boolean deleteOneUser(String userId);

  public Boolean deleteAllUser();

}
