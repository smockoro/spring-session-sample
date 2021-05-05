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
package com.example.demo.domain.usecase;

import com.example.demo.domain.model.User;
import com.example.demo.domain.model.helper.FakeUserHelper;
import com.example.demo.domain.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyEmitter;

@RequiredArgsConstructor
@Slf4j
@Service
public class FakeUserInteractor implements FakeUserUsecase {

  private final UserRepository userRepository;
  private final FakeUserHelper fakeUserHelper;

  @Async
  @Override
  public void createFakeUser(ResponseBodyEmitter emitter, Long fakeUserNum) {
    log.info("create fake user start");

    Long batchSize = 10L;

    for (int i = 0; i < fakeUserNum; i++) {
      User user = fakeUserHelper.create();
      userRepository.registerOne(user);
    }

    emitter.complete();
    log.info("create fake user end");
  }
}
