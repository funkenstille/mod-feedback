package de.funkenstille.modfeedback.main;

import de.funkenstille.emotes.Emotes;
import de.mineking.discordutils.commands.CommandManager;
import de.mineking.discordutils.commands.condition.ICommandPermission;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;

public enum CommandPermission implements ICommandPermission<CommandContext> {
	/**
	 * Diese Befehle können nur von Teammitgliedern ausgeführt werden, sind aber
	 * Standardmäßig für alle Servermitglieder sichtbar. Es wird überprüft, ob der
	 * Nutzer, der den Befehl verwendet Bot-Developer ist. Wenn der Nutzer nicht die
	 * nötigen Rechte hat, wird ihm eine Fehlermeldung angezeigt.
	 */
	DEVELOPER {
		@Override
		public boolean isPermitted(CommandManager<CommandContext, ?> manager, CommandContext context, Member member) {
			return member.getIdLong() == Main.appProperties.getDeveloperId();
		}

		@Override
		public void handleUnpermitted(CommandManager<CommandContext, ?> manager, CommandContext context) {
			context.getEvent()
					.replyFormat("%s Dieser Befehl kann nur von einem Developer ausgeführt werden", Emotes.warning_r)
					.setEphemeral(true).queue();
		}
	},
	/**
	 * Diese Befehle sind nur für Server-Mitglieder mit der Berechtigung
	 * `ADMINISTRATOR` und sind standartmäßig nur für diese sichtbar. Wenn eine
	 * Command-Überschreibung vorgenommen wurde und der Nutzer nicht die nötigen
	 * Rechte hat, wird ihm eine Fehlermeldung angezeigt.
	 */
	ADMINISTRATOR {
		@Override
		public DefaultMemberPermissions requiredPermissions() {
			return DefaultMemberPermissions.enabledFor(Permission.ADMINISTRATOR);
		}

		@Override
		public boolean isPermitted(CommandManager<CommandContext, ?> manager, CommandContext context, Member member) {
			return member.hasPermission(Permission.ADMINISTRATOR);
		}

		@Override
		public void handleUnpermitted(CommandManager<CommandContext, ?> manager, CommandContext context) {
			context.getEvent()
					.replyFormat("%s Dieser Befehl kann nur mit der Berechtigung `ADMINISTRATOR` ausgeführt werden",
							Emotes.warning_r)
					.setEphemeral(true).queue();
		}
	}
}
