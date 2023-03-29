package io.github.axway.apimplus.websocket.server;

import com.beust.jcommander.Parameter;

class Config {
	@Parameter(names = { "-h", "--help" }, description = "Show help screen.", help = true, order = 1)
	private boolean help = false;

	@Parameter(names = "--host", description = "Hostname of the WebSocket server", order = 2)
	private String host = "localhost";
	
	@Parameter(names = "--port", description = "Port of the WebSocket server", order=3)
	private int port = 8025;

	public boolean isHelp() {
		return this.help;
	}
	
	public String getHost() {
		return this.host;
	}
	
	public int getPort() {
		return this.port;
	}
}
