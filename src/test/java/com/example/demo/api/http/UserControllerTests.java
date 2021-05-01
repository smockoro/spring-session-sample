/**
 * @formatter:off
 *      MIT License
 *
 *      Copyright (c) 2021 mockoro
 *
 *      Permission is hereby granted, free of charge, to any person obtaining a copy
 *      of this software and associated documentation files (the "Software"), to deal
 *      in the Software without restriction, including without limitation the rights
 *      to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 *      copies of the Software, and to permit persons to whom the Software is
 *      furnished to do so, subject to the following conditions:
 *
 *      The above copyright notice and this permission notice shall be included in all
 *      copies or substantial portions of the Software.
 *
 *      THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 *      IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 *      FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 *      AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 *      LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *      OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 *      SOFTWARE.
 * @formatter:on
 */
package com.example.demo.api.http;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.authenticated;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.demo.domain.usecase.UserUsecase;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import redis.clients.jedis.Jedis;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTests {

  @Autowired
  private MockMvc mockMvc;

  private Jedis jedis;

  @Autowired
  UserController controller;

  @MockBean
  UserUsecase userUsecase;

  @BeforeEach
  void clearRedisData() {
    jedis = new Jedis("localhost", 6379);
    jedis.flushAll();
  }

  @Test
  @DisplayName("OK")
  @WithMockUser(username = "admin", roles = "ADMIN")
  void getUsersTest() throws Exception {
    mockMvc.perform(
        get("/users"))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(authenticated());

    Set<String> redisResults = jedis.keys("*");
    assertTrue(redisResults.size() > 0);

    System.out.println(redisResults);
  }

  @Test
  @DisplayName("Unauthorized")
  void getUsersUnauthorized() throws Exception {
    mockMvc.perform(
        get("/users"))
        .andDo(print())
        .andExpect(status().isUnauthorized())
        .andExpect(unauthenticated());
  }

}