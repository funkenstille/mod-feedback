package de.funkenstille.modfeedback.main;

import java.util.List;

import org.apache.logging.log4j.Logger;

import de.funkenstille.modfeedback.commands.BugReportCommand;
import de.funkenstille.modfeedback.commands.SubmitFeedbackCommand;
import de.mineking.discordutils.DiscordUtils;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;

public class ModFeedbackBot {
	private final String version = "1.1.0";
	public final JDA jda;
	public final DiscordUtils<ModFeedbackBot> discordUtils;
	Logger logger = Main.getLogger();

	public ModFeedbackBot(String token, List<Long> guilds) {

		logger.info("Bot Version: {}", version);
		logger.info("Bot wird gestartet...");
		if (token == null || token.isEmpty()) {
			logger.error("BITTE EIN TOKEN ANGEBEN (app.properties im Bot-Ordner)");
			System.exit(421);
		}
		jda = JDABuilder.createDefault(token).setActivity(Activity.listening("dem Stuhlback der Mods!"))
				.setStatus(OnlineStatus.ONLINE).build();
		discordUtils = DiscordUtils.create(jda, this)
				.useCommandManager(event -> new CommandContext(event, this), AutocompleteContext::new,
						config -> config.registerCommand(BugReportCommand.class)
								.registerCommand(SubmitFeedbackCommand.class).updateCommands())
				.useEventManager(null).useUIManager(null).build();

		if (guilds.isEmpty()) {
			logger.warn(
					"Keine Server-Commands erstellt, da kein Server angegeben wurde (app.properties im Bot-Ordner)");
		}

	}
}
