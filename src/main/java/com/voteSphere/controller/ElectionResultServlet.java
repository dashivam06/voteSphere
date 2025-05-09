package com.voteSphere.controller;

import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.voteSphere.model.ElectionResult;
import com.voteSphere.service.ElectionService;

import jakarta.websocket.CloseReason;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnError;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;

@ServerEndpoint("/election-results/{election_id}")
public class ElectionResultServlet {
    private static final Logger logger = LogManager.getLogger(ElectionResultServlet.class);
    private static final Set<Session> sessions = Collections.synchronizedSet(new HashSet<>());
    private static final ObjectMapper objectMapper = new ObjectMapper(); // Thread-safe after configuration

    @OnOpen
    public void onOpen(Session session, @PathParam("election_id") String electionIdStr) {
        try {
            int electionId = Integer.parseInt(electionIdStr);
            logger.info("WebSocket opened for election: {}", electionId);

            session.getUserProperties().put("election_id", electionId);
            sessions.add(session);

            sendCurrentResults(session, electionId);

        } catch (NumberFormatException e) {
            logger.error("Invalid election ID: {}", electionIdStr);
            closeSession(session, "Invalid election ID");
        } catch (Exception e) {
            logger.error("Connection error", e);
            closeSession(session, "Connection error");
        }
    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
        logger.info("Connection closed for session: {}", session.getId());
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        sessions.remove(session);
        logger.error("WebSocket error for session: {}", session.getId(), throwable);
    }

    public static void notifyVoteAdded(int electionId) {
        try {
            List<ElectionResult> results = ElectionService.getElectionResults(electionId);
            broadcastResults(electionId, results);
            logger.info("Broadcasted vote update for election {}", electionId);
        } catch (Exception e) {
            logger.error("Failed to broadcast vote update", e);
        }
    }

    private static void broadcastResults(int electionId, List<ElectionResult> results) {
        String json;
        try {
            json = resultsToJson(results);
        } catch (JsonProcessingException e) {
            logger.error("Failed to serialize election results", e);
            return;
        }

        synchronized (sessions) {
            sessions.stream()
                    .filter(Session::isOpen)
                    .filter(s -> electionId == (Integer) s.getUserProperties().get("election_id"))
                    .forEach(session -> {
                        try {
                            session.getBasicRemote().sendText(json);
                        } catch (IOException e) {
                            logger.error("Failed to send update to session: {}", session.getId(), e);
                        }
                    });
        }
    }

    private void sendCurrentResults(Session session, int electionId) throws IOException {
        List<ElectionResult> results = ElectionService.getElectionResults(electionId);
        String json = resultsToJson(results);
        session.getBasicRemote().sendText(json);
    }

    private void closeSession(Session session, String reason) {
        try {
            session.close(new CloseReason(CloseReason.CloseCodes.CANNOT_ACCEPT, reason));
        } catch (IOException e) {
            logger.error("Failed to close session: {}", session.getId(), e);
        }
    }

    public static String resultsToJson(List<ElectionResult> results) throws JsonProcessingException {
        return objectMapper.writeValueAsString(results);
    }
}