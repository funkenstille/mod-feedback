package de.funkenstille.modfeedback.main;

import de.mineking.discordutils.commands.Cache;
import de.mineking.discordutils.commands.CommandManager;
import de.mineking.discordutils.commands.condition.IRegistrationCondition;
import net.dv8tion.jda.api.entities.Guild;

public class VerifiedGuildCommandCondition implements IRegistrationCondition<CommandContext> {
	@Override
	public boolean shouldRegister(CommandManager<CommandContext, ?> manager, Guild guild, Cache data) {
		return Main.appProperties.getGuilds().contains(guild.getIdLong());
	}
}
