package de.funkenstille.modfeedback.commands;

import java.util.EnumSet;

import de.funkenstille.emotes.Emotes;
import de.funkenstille.modfeedback.main.CommandContext;
import de.funkenstille.modfeedback.main.Main;
import de.funkenstille.modfeedback.main.VerifiedGuildCommandCondition;
import de.mineking.discordutils.commands.ApplicationCommand;
import de.mineking.discordutils.commands.ApplicationCommandMethod;
import de.mineking.discordutils.commands.condition.IRegistrationCondition;
import de.mineking.discordutils.commands.condition.Scope;
import de.mineking.discordutils.events.Listener;
import de.mineking.discordutils.events.handlers.ModalHandler;
import net.dv8tion.jda.api.entities.Message.MentionType;
import net.dv8tion.jda.api.entities.channel.concrete.ThreadChannel;
import net.dv8tion.jda.api.entities.channel.unions.IThreadContainerUnion;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;

@ApplicationCommand(name = "feedback", description = "Poste anonymes Feedback im Kanal", scope = Scope.GUILD)
public class SubmitFeedbackCommand {
	public final IRegistrationCondition<CommandContext> registrationCondition = new VerifiedGuildCommandCondition();

	@ApplicationCommandMethod
	public void performCommand(CommandContext context, SlashCommandInteractionEvent event) {
		Long channelId = event.getChannel().getIdLong();
		switch (event.getChannel().getType()) {
		case GUILD_PRIVATE_THREAD:
		case GUILD_PUBLIC_THREAD:
		case GUILD_NEWS_THREAD:
			ThreadChannel threadChannel = event.getChannel().asThreadChannel();
			IThreadContainerUnion threadParent = threadChannel.getParentChannel();
			channelId = threadParent.getIdLong();
			System.out.println(threadParent.getType());
			switch (threadParent.getType()) {
			case FORUM:
				if (!Main.appProperties.getFeedbackForum().contains(channelId)) {
					event.replyFormat("%s Du befindest dich **außerhalb eines Feedback-Forums**!", Emotes.private_o)
							.setEphemeral(true).queue();
					return;
				}
				break;
			default:
				if (!Main.appProperties.getFeedbackChannel().contains(channelId)) {
					event.replyFormat("%s Du befindest dich **außerhalb eines Feedback-Kanals**!", Emotes.private_o)
							.setEphemeral(true).queue();
					return;
				}
				break;
			}
			break;
		default:
			if (!Main.appProperties.getFeedbackChannel().contains(channelId)) {
				event.replyFormat("%s Du befindest dich **außerhalb eines Feedback-Kanals**!", Emotes.private_o)
						.setEphemeral(true).queue();
				return;
			}
			break;
		}

		TextInput feedback = TextInput.create("feedback", "💡〢Dein Feedback", TextInputStyle.PARAGRAPH)
				.setPlaceholder("► Was möchtest du anonym posten?").setMaxLength(2000).setRequired(true).build();

		Modal modal = Modal.create("post-feedback", "Verfasse das Feedback...").addActionRow(feedback).build();

		event.replyModal(modal).queue();
	}

	@Listener(type = ModalHandler.class, filter = "post-feedback")
	public void handleFeedbackModal(ModalInteractionEvent event) {
		String feedback = event.getValue("feedback").getAsString();
		event.getChannel().sendMessage(feedback).setAllowedMentions(EnumSet.noneOf(MentionType.class)).queue((s) -> {
			event.reply("Die Nachricht wurde erfolgreich gesendet!").setEphemeral(true).queue();
		}, (e) -> {
			event.reply("Es gab einen Fehler beim versenden des Feedback!").setEphemeral(true).queue();
			Main.getLogger().error("Error on sending feedback {}", e);
		});
	}
}
