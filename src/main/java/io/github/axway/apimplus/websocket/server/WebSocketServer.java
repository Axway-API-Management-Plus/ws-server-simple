package io.github.axway.apimplus.websocket.server;

import java.io.Closeable;
import java.io.IOException;
import java.util.Objects;

import jakarta.websocket.DeploymentException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.glassfish.tyrus.server.Server;
import org.glassfish.tyrus.spi.ServerContainerFactory;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.ParameterException;

public class WebSocketServer implements Closeable {

	private static final Logger log = LogManager.getLogger(WebSocketServer.class);

	private final Config config;
	private Server server = null;

	public static void main(String[] args) {
		int rc = 0;
		Config cfg = parseArgs(args);

		try (WebSocketServer server = new WebSocketServer(cfg)) {
			server.start();

			try {
				while (true) {
					Thread.sleep(500);
				}
			} catch (InterruptedException e) {
				log.info("Server terminated");
			}
		} catch (Exception e) {
			log.error("error occurred", e);
			rc = 2;
		}

		System.exit(rc);

	}

	private static Config parseArgs(String[] args) {
		Config cfg = new Config();

		// Parse command line
		try {
			JCommander jc = JCommander.newBuilder().addObject(cfg).programName("ws-server-simple").build();
			jc.parse(args);
			if (cfg.isHelp()) {
				printHelpAndExit(jc);
			}
		} catch (ParameterException e) {
			log.error(e.getLocalizedMessage());
			System.exit(1);
		}

		return cfg;
	}

	private static void printHelpAndExit(JCommander jc) {
		jc.usage();
		System.exit(1);
	}

	WebSocketServer(Config config) {
		this.config = Objects.requireNonNull(config);
	}

	void start() throws DeploymentException {
		Server server = new Server(this.config.getHost(), this.config.getPort(), "/", null, Endpoint.class);
		server.start();
		log.info("WebSocket server started: {}:{}", this.config.getHost(), this.config.getPort());
	}

	@Override
	public void close() throws IOException {
		if (server != null) {
			server.stop();
			server = null;
		}
		log.info("WebSocket server stopped");
	}
}
