package de.funkenstille.modfeedback.util;

import de.funkenstille.modfeedback.main.Constants;
import net.dv8tion.jda.api.entities.User;

public class UserProfileManager {

	@SuppressWarnings("deprecation")
	public final static String getUsername(User user) {
		if (user.getDiscriminator().equals("0000")) {
			return "@" + user.getName();
		} else {
			return user.getAsTag();
		}
	}

	public final static String getAvatarUrl(User user) {
		final String avatarUrl = user.getAvatarUrl();
		if (avatarUrl == null) {
			return Constants.discordIcon;
		} else {
			return avatarUrl;
		}
	}
}
