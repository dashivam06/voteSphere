package com.voteSphere.util;


import org.apache.logging.log4j.core.*;
import org.apache.logging.log4j.core.appender.AbstractAppender;
import org.apache.logging.log4j.core.appender.AppenderLoggingException;
import org.apache.logging.log4j.core.config.plugins.*;
import org.apache.logging.log4j.core.layout.PatternLayout;
import org.apache.logging.log4j.core.LogEvent;

import java.io.IOException;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Plugin(name = "TelegramAppender", category = Core.CATEGORY_NAME, elementType = Appender.ELEMENT_TYPE, printObject = true)
public class TelegramAppender extends AbstractAppender {

    private final String botToken;
    private final String chatId;

    protected TelegramAppender(String name, Filter filter, Layout<? extends Serializable> layout,
                               boolean ignoreExceptions, String botToken, String chatId) {
        super(name, filter, layout, ignoreExceptions);
        this.botToken = botToken;
        this.chatId = chatId;
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

    private void sendMessage(String message) throws IOException {
        String encodedMessage = URLEncoder.encode(message, StandardCharsets.UTF_8);
        String urlString = "https://api.telegram.org/bot" + botToken + "/sendMessage?chat_id=" + chatId + "&text=" + encodedMessage;

        URL url = new URL(urlString);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.getInputStream().close(); // Fire and forget
    }
}
