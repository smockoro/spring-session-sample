package com.example.demo.listener;

import javax.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.web.session.HttpSessionCreatedEvent;
import org.springframework.security.web.session.HttpSessionDestroyedEvent;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HttpSessionLoggingAnnotationListener {

  @EventListener
  public void sessionCreated(HttpSessionCreatedEvent httpSessionEvent) {
    HttpSession session = httpSessionEvent.getSession();
    log.info("created session id: {}", session.getId());
  }

  @EventListener
  public void sessionDestroyed(HttpSessionDestroyedEvent httpSessionEvent) {
    HttpSession session = httpSessionEvent.getSession();
    log.info("destroyed session id: {}", session.getId());
  }
}
