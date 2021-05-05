package com.example.demo.listener;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HttpSessionLoggingListener implements HttpSessionListener {

  @Override
  public void sessionCreated(HttpSessionEvent httpSessionEvent) {
    HttpSession session = httpSessionEvent.getSession();
    log.info("created session id: {}", session.getId());
  }

  @Override
  public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
    HttpSession session = httpSessionEvent.getSession();
    log.info("destroyed session id: {}", session.getId());
  }
}
