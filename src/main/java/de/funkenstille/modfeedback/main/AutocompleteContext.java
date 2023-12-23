package de.funkenstille.modfeedback.main;

import org.jetbrains.annotations.NotNull;

import de.mineking.discordutils.commands.context.IAutocompleteContext;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.CommandAutoCompleteInteractionEvent;

public class AutocompleteContext implements IAutocompleteContext {
	private final CommandAutoCompleteInteractionEvent event;
	private final Guild guild;
	private final User user;
	private final Member member;

	public AutocompleteContext(@NotNull CommandAutoCompleteInteractionEvent event) {
		this.event = event;
		this.guild = event.getGuild();
		this.user = event.getUser();
		this.member = event.getMember();
	}

	@NotNull
	@Override
	public CommandAutoCompleteInteractionEvent getEvent() {
		return event;
	}

	public Guild getGuild() {
		return guild;
	}

	public Member getMember() {
		return member;
	}

	public User getUser() {
		return user;
	}

}
