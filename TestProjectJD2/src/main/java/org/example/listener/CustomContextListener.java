package org.example.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class CustomContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        System.out.println("Context is Up!!!!!!!!!!!!");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        System.out.println("Context is DOWN!!!!!!!!!!");
    }
}
