package com.github.marschall.excount;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class CounterRegistration implements ServletContextListener {

  @Override
  public void contextInitialized(ServletContextEvent sce) {
    ExceptionCounter.register();
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {
    ExceptionCounter.unregister();
  }

}
