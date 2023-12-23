package de.funkenstille.modfeedback.main;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

public class AppProperties {
	private Properties props = new Properties();

	public void loadProperties() throws IOException {
		props.load(new FileInputStream("./app.properties"));
	}

	public final String getToken() {
		return props.getProperty("token");
	}

	public final Long getDeveloperId() {
		return Long.valueOf(props.getProperty("developer"));
	}

	public final List<Long> getGuilds() {
		return Arrays.stream(props.getProperty("guild").split(",")).map(String::trim).map(Long::parseLong)
				.collect(Collectors.toList());
	}

	public final List<Long> getFeedbackForum() {
		return Arrays.stream(props.getProperty("feedbackForum").split(",")).map(String::trim).map(Long::parseLong)
				.collect(Collectors.toList());
	}

	public final List<Long> getFeedbackChannel() {
		return Arrays.stream(props.getProperty("feedbackChannel").split(",")).map(String::trim).map(Long::parseLong)
				.collect(Collectors.toList());
	}

	public final String getBugreportWebhookUrl() {
		return props.getProperty("bugreportWebhookUrl");
	}
}
