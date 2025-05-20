package com.voteSphere.listener;

import com.voteSphere.config.AppConfig;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@WebListener
public class EssentialFolderInitializer implements ServletContextListener {

    private static final Logger logger = LogManager.getLogger(DatabaseInitializer.class);

    String REQUIRED_DIRECTORIES_IN_COMMA_SEPERATED_STRING = AppConfig.get("REQUIRED_FOLDER_INITIALIZATION_BEFORE_STARTUP");


    String[] REQUIRED_DIRECTORIES = REQUIRED_DIRECTORIES_IN_COMMA_SEPERATED_STRING.split(",");


    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("Checking required directories...");

        for (String dirPath : REQUIRED_DIRECTORIES) {
            if (dirPath == null || dirPath.isEmpty()) continue;

            Path path;
            if (dirPath.startsWith("~/")) {
                path = Paths.get(System.getProperty("user.home"), dirPath.substring(2));
            } else if (!Paths.get(dirPath).isAbsolute()) {
                path = Paths.get(System.getProperty("user.home"), dirPath);
            } else {
                path = Paths.get(dirPath);
            }

            if (!Files.exists(path)) {
                try {
                    Files.createDirectories(path);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                logger.info("Created directory: {}", path.toAbsolutePath());
            } else {
                logger.debug("Directory already exists: {}", path.toAbsolutePath());
            }

            // Verify write permissions
            if (!Files.isWritable(path)) {
                logger.warn("Directory not writable: {}", path.toAbsolutePath());
            }

        }
    }




    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("Context destroyed");

    }
}
