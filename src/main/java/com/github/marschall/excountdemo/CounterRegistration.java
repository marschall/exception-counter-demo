package com.github.marschall.excountdemo;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.github.marschall.excount.ExceptionCounter;

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
