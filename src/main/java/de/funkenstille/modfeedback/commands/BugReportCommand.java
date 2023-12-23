package de.funkenstille.modfeedback.commands;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import club.minnced.discord.webhook.send.WebhookMessageBuilder;
import de.funkenstille.emotes.Emotes;
import de.funkenstille.modfeedback.main.CommandContext;
import de.funkenstille.modfeedback.main.Constants;
import de.funkenstille.modfeedback.main.Main;
import de.funkenstille.modfeedback.util.UserProfileManager;
import de.mineking.discordutils.commands.ApplicationCommand;
import de.mineking.discordutils.commands.ApplicationCommandMethod;
import de.mineking.discordutils.events.Listener;
import de.mineking.discordutils.events.handlers.ModalHandler;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;

@ApplicationCommand(name = "bug_report", description = "Melde Bugs an das Developer-Team")
public class BugReportCommand {

	@ApplicationCommandMethod
	public void performCommand(CommandContext context, SlashCommandInteractionEvent event) {

		TextInput description = TextInput.create("description", "🔎〢Beschreibung", TextInputStyle.PARAGRAPH)
				.setPlaceholder(
						"► Bitte versuche ungenaue Beschreibungen, wie \"es glitcht\" oder \"es geht nicht\" zu vermeiden.")
				.setMinLength(5).setMaxLength(500).setRequired(true).build();

		TextInput reproduce = TextInput
				.create("reproduce", "👣〢Schritte, um den Fehler zu reproduzieren", TextInputStyle.PARAGRAPH)
				.setPlaceholder("► Liste die Schritte auf, um das Problem aufzurufen.").setMinLength(5)
				.setMaxLength(500).setRequired(true).build();

		TextInput expected = TextInput.create("expected", "📈〢Erwartetes Ergebniss", TextInputStyle.PARAGRAPH)
				.setPlaceholder("► Was sollte ohne den Bug passieren?").setMaxLength(250).setRequired(true).build();

		TextInput actual = TextInput.create("actual", "📉〢Tatsächliches Ergebniss", TextInputStyle.PARAGRAPH)
				.setPlaceholder("► Was ist das tatsächliche Ergebniss?").setMaxLength(250).setRequired(true).build();

		Modal modal = Modal.create("bug-report", "Melde einen Bug!").addActionRow(description).addActionRow(reproduce)
				.addActionRow(expected).addActionRow(actual).build();

		event.replyModal(modal).queue();
	}

	@Listener(type = ModalHandler.class, filter = "bug-report")
	public void handleFeedbackModal(ModalInteractionEvent event) {

		User user = event.getUser();

		String description = event.getValue("description").getAsString();
		String reproduce = event.getValue("reproduce").getAsString();
		String expected = event.getValue("expected").getAsString();
		String actual = event.getValue("actual").getAsString();

		MessageEmbed embed = new EmbedBuilder()
				.setAuthor(String.format("%s • %s", UserProfileManager.getUsername(user), user.getId()), null,
						UserProfileManager.getAvatarUrl(user))
				.setTitle(Emotes.bug_o + "〣Ein neuer Bug wurde gemeldet!")
				.addField(Emotes.list_o + "〢Beschreibung", description, false)
				.addField(Emotes.rules_o + "〢Wege den Bug zu reproduzieren", reproduce, false)
				.addField(Emotes.check_circle_o + "〢Erwartet", expected, false)
				.addField(Emotes.cancel_circle_o + "〢Tatsächliches Resultat", actual, false).setColor(Constants.orange)
				.setFooter("© funkenstille, 2023", Constants.ownerAvatar).build();

		WebhookMessageBuilder builder = new WebhookMessageBuilder();
		builder.setUsername("Bug-Reports (Mod-System)");
		builder.addEmbeds(WebhookEmbedBuilder.fromJDA(embed).build());
		try (WebhookClient client = WebhookClient.withUrl(Main.appProperties.getBugreportWebhookUrl())) {
			client.send(builder.build());
			event.replyFormat("%s Dein **Bug** wurde **erfolgreich eingereicht**!", Emotes.bug_o).setEphemeral(true)
					.queue();
		}
	}
}
