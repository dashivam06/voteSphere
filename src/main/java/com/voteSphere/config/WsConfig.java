//package com.voteSphere.config;
//import com.voteSphere.controller.ElectionResultServlet;
//
//import jakarta.servlet.ServletContextEvent;
//import jakarta.servlet.ServletContextListener;
//import jakarta.servlet.annotation.WebListener;
//import jakarta.websocket.DeploymentException;
//import jakarta.websocket.server.ServerContainer;
//
//@WebListener
//public class WsConfig implements ServletContextListener {
//    @Override
//    public void contextInitialized(ServletContextEvent sce) {
//        ServerContainer container = (ServerContainer) sce.getServletContext()
//            .getAttribute("javax.websocket.server.ServerContainer");
//        try {
//            container.addEndpoint(ElectionResultServlet.class);
//        } catch (DeploymentException e) {
//            throw new RuntimeException(e);
//        }
//    }
//}