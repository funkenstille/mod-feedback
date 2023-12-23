package de.funkenstille.modfeedback.util;

import net.dv8tion.jda.api.entities.User;

public class UserUtils {

	@SuppressWarnings("deprecation")
	public final static String getUsername(User user) {
		if (user.getDiscriminator().equals("0000")) {
			return "@" + user.getName();
		} else {
			return user.getAsTag();
		}
	}

	public final static String getMemberAvatar(User user) {
		if (user.getAvatar() != null) {
			return user.getAvatar().getUrl();
		}
		long img = (user.getIdLong() >> 22) % 6;
		return String.format("https://cdn.discordapp.com/embed/avatars/%d.png", img);
	}
}
