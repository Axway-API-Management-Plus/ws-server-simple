package io.github.axway.apimplus.websocket.server;

import java.util.concurrent.atomic.AtomicInteger;

import jakarta.websocket.CloseReason;
import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@ServerEndpoint(value="/echo")
public class Endpoint {
	
	private static final Logger log = LogManager.getLogger(Endpoint.class);
	private static final AtomicInteger sessionCount = new AtomicInteger(0);
	
	@OnOpen
	public void onOpen(Session session) {
		int count = Endpoint.sessionCount.incrementAndGet();
		log.info("New connection created (sessions={})", count);
	}

	@OnMessage
	public String onMessage(String message, Session session) {
		log.info("Echo: {}", message);
		return message;
	}
	
	@OnClose
	public void onClose(Session session, CloseReason closeReason) {
		int count = Endpoint.sessionCount.decrementAndGet();
		log.info("Session closed: {} (sessions={})", closeReason.getReasonPhrase(), count);
	}
}
