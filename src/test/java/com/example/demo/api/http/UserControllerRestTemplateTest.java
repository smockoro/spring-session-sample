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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Objects;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import redis.clients.jedis.Jedis;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UserControllerRestTemplateTest {

  private Jedis jedis;
  private TestRestTemplate testRestTemplate;
  private TestRestTemplate testRestTemplateWithAuth;
  private String testUrl = "http://localhost:8080/users";

  @BeforeEach
  public void clearRedisData() {
    testRestTemplate = new TestRestTemplate();
    testRestTemplateWithAuth = new TestRestTemplate(
        "admin", "password");

    jedis = new Jedis("localhost", 6379);
    jedis.flushAll();
  }

  @Test
  public void testRedisIsEmpty() {
    Set<String> result = jedis.keys("*");
    assertEquals(0, result.size());
  }

  @Test
  public void testUnauthenticatedCantAccess() {
    ResponseEntity<String> result = testRestTemplate.getForEntity(testUrl, String.class);
    assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());

    assertNull(result.getHeaders().get("Set-Cookie"));

    Set<String> redisResult = jedis.keys("*");
    assertTrue(redisResult.size() <= 0);
  }

  @Test
  public void testRedisControlsSession() {
    ResponseEntity<String> result = testRestTemplateWithAuth.getForEntity(testUrl, String.class);
    assertEquals(HttpStatus.OK, result.getStatusCode());

    Set<String> redisResult = jedis.keys("*");
    assertTrue(redisResult.size() > 0);

    String sessionCookie = Objects.requireNonNull(result.getHeaders().get("Set-Cookie"))
        .get(0).split(";")[0];
    HttpHeaders headers = new HttpHeaders();
    headers.add("Cookie", sessionCookie);
    HttpEntity<String> httpEntity = new HttpEntity<>(headers);

    result = testRestTemplate.exchange(testUrl, HttpMethod.GET, httpEntity, String.class);
    assertEquals(HttpStatus.OK, result.getStatusCode());

    jedis.flushAll();

    result = testRestTemplate.exchange(testUrl, HttpMethod.GET, httpEntity, String.class);
    assertEquals(HttpStatus.UNAUTHORIZED, result.getStatusCode());
  }
}
