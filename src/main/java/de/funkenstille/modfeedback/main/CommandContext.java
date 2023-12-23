package de.funkenstille.modfeedback.main;

import org.jetbrains.annotations.NotNull;

import de.mineking.discordutils.commands.context.ICommandContext;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.GenericCommandInteractionEvent;

public class CommandContext implements ICommandContext {
	private final GenericCommandInteractionEvent event;
	private final Guild guild;
	private final User user;
	private final Member member;
	private final JDA jda;
	private final ModFeedbackBot modFeedbackBot;

	public CommandContext(@NotNull GenericCommandInteractionEvent event, ModFeedbackBot modFeedbackBot) {
		this.modFeedbackBot = modFeedbackBot;
		this.event = event;
		this.guild = event.getGuild();
		this.user = event.getUser();
		this.member = event.getMember();
		this.jda = event.getJDA();
	}

	public void reply(String input) {
		// TODO
		event.reply("Die Funktion steht bereits auf der To-Do-Liste!").setEphemeral(true).queue();
	}

	public String get(String input) {
		// TODO
		return null;
	}

	@NotNull
	@Override
	public GenericCommandInteractionEvent getEvent() {
		return event;
	}

	public ModFeedbackBot getModFeedbackBot() {
		return modFeedbackBot;
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

	public JDA getJda() {
		return jda;
	}
}
