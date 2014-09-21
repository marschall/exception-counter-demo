package com.github.marschall.excountdemo;

import static java.util.concurrent.TimeUnit.MILLISECONDS;

import java.lang.invoke.MethodHandles;
import java.util.Random;
import java.util.concurrent.ScheduledFuture;
import java.util.logging.Logger;

import javax.annotation.Resource;
import javax.enterprise.concurrent.ManagedScheduledExecutorService;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class ExceptionGenerator implements ServletContextListener {
  
  private static final Logger LOG = Logger.getLogger(MethodHandles.lookup().lookupClass().getName());
  
  @Resource
  private ManagedScheduledExecutorService executor;
  
  private volatile ScheduledFuture<?> future;

  private volatile Random random;
  
  private volatile boolean stopped = false;

  @Override
  public void contextInitialized(ServletContextEvent sce) {
    this.random = new Random();
    this.scheduleException();
  }

  @Override
  public void contextDestroyed(ServletContextEvent sce) {
    this.stopped = true;
    ScheduledFuture<?> f = this.future;
    if (f != null) {
      f.cancel(true);
    }
  }
  
  private void scheduleException() {
    // between 500 and 1000 milliseconds
    long milliseconds = 500L + this.random.nextInt(500);
    
    this.future = executor.schedule(() -> {
        if (!this.stopped) {
          this.future = null;
          incrementExceptionCountByThrow();
          this.scheduleException();
        }},
        milliseconds, MILLISECONDS);
  }
  
  private static void incrementExceptionCountByThrow() {
    try {
      throwException();
      LOG.severe("exception not thrown");
    } catch (RuntimeException e) {
      LOG.finer("exception generated");
    }
  }
  
  private static void throwException() {
    throw new RuntimeException();
  }

}
