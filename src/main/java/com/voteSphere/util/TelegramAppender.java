package com.voteSphere.util;


import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.appender.AppenderLoggingException;
import org.apache.logging.log4j.core.config.plugins.*;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.apache.logging.log4j.core.LogEvent;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.random.RandomGenerator;

@Plugin(name = "TelegramAppender", category = Core.CATEGORY_NAME, elementType = Appender.ELEMENT_TYPE, printObject = true)
public class TelegramAppender extends AbstractAppender {

    private final String botToken;
    private final String chatId;
    private final ExecutorService executor ;


    protected TelegramAppender(String name, Filter filter, Layout<? extends Serializable> layout,
                               boolean ignoreExceptions, String botToken, String chatId) {
        super(name, filter, layout, ignoreExceptions);
        this.botToken = botToken;
        this.chatId = chatId;
        this.executor = Executors.newSingleThreadExecutor();

    }

    @PluginFactory
    public static TelegramAppender createAppender(
            @PluginAttribute("name") String name,
            @PluginAttribute("botToken") String botToken,
            @PluginAttribute("chatId") String chatId,
            @PluginElement("Layout") Layout<? extends Serializable> layout,
            @PluginElement("Filter") final Filter filter) {


        if (name == null) {
            LOGGER.error("No name provided for TelegramAppender");
            return null;
        }
        if (botToken == null) {
            LOGGER.error("No botToken provided for TelegramAppender");
            return null;
        }
        if (chatId == null) {
            LOGGER.error("No chatId provided for TelegramAppender");
            return null;
        }
        if (layout == null) {
            layout = PatternLayout.createDefaultLayout();
        }
        return new TelegramAppender(name, filter, layout, true, botToken, chatId);
    }

    @Override
    public void append(LogEvent event) {
        try {
            String message = new String(getLayout().toByteArray(event), StandardCharsets.UTF_8);
            sendMessage(message);
        } catch (Exception ex) {
            if (!ignoreExceptions()) {
                throw new AppenderLoggingException("Failed to send log to Telegram", ex);
            }
        }
    }


    private void sendMessage(String message) {
        executor.submit(() -> {
            try {
                String encodedMessage = URLEncoder.encode(message, StandardCharsets.UTF_8);
                String urlString = "https://api.telegram.org/bot" + botToken +
                        "/sendMessage?chat_id=" + chatId +
                        "&text=" + encodedMessage;

                HttpURLConnection conn = (HttpURLConnection) new URL(urlString).openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(3000);
                conn.setReadTimeout(3000);

                try (InputStream is = conn.getInputStream()) {
                    // do nothing
                }
            } catch (IOException e) {
                LOGGER.warn("TelegramAppender failed to send message", e);
            }
        });
    }


    @Override
    public void stop() {
        super.stop();
        executor.shutdown();
        try {
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                executor.shutdownNow();
            }
        } catch (InterruptedException e) {
            executor.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }

}
