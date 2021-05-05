package com.example.demo.store.repository;

import com.example.demo.domain.model.User;
import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMysqlMapper {

  @Select("SELECT * FROM users")
  List<User> select();

  @Select("SELECT * FROM users WHERE id = #{id}")
  User selectById(Long id);

  @Insert("INSERT INTO users (name, age, address, phone_number, email_address, password) "
      + "VALUES (#{name}, #{age}, #{address}, #{phoneNumber}, #{emailAddress}, #{password})")
  @Options(useGeneratedKeys = true, keyProperty = "id")
  Boolean insert(User user);

}
