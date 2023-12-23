package de.funkenstille.modfeedback.main;

import java.io.IOException;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
	public static AppProperties appProperties = new AppProperties();
	private final static Logger logger = LogManager.getLogger(Main.class);

	public static void main(String[] args) throws IOException {
		appProperties.loadProperties();
		String token = appProperties.getToken();
		List<Long> guilds = appProperties.getGuilds();
		new ModFeedbackBot(token, guilds);
	}

	public final static Logger getLogger() {
		return logger;
	}
}
