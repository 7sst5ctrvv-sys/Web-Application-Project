package de.mensa.controller;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import de.mensa.dao.DataStore;

@WebListener
public class AppInitListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("=== Mensa-Platform startet ===");
        DataStore.getInstance();
        System.out.println("=== Datenbank bereit ===");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        System.out.println("=== Mensa-Platform gestoppt ===");
    }
}
