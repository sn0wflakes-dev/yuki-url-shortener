import { ChatInputCommandInteraction, MessageFlags, SlashCommandBuilder } from "discord.js"
import { shortenUrl } from "../services/shorten-url-service";
import { log } from "../application/log";

export default {
  data: new SlashCommandBuilder().setName("shorten-url").setDescription("Shorten Long URL")
  .addStringOption((option) =>
    option
      .setName("long-url")
      .setDescription("Input https://example.com/your/long/url here")
      .setRequired(true)
  ).addStringOption((option) =>
      option
        .setName("alias")
        .setDescription("Input your URL Alias i.e. /ShortUrl")
        .setMinLength(5)
  ),
  async execute(interaction: ChatInputCommandInteraction) {
    const longUrl = interaction.options.getString("long-url", true);
    const alias = interaction.options.getString("alias");

    try {
     const result = await shortenUrl({
       longUrl: longUrl,
       alias: alias
     });

     await interaction.reply({
       content: `Here your shortened url : ${result.data.url}`,
       flags: MessageFlags.Ephemeral,
     });

    } catch (error) {
      log.error(`[ShortenURLCommand] : Error while excecuting command`, error);
      await interaction.reply(`Failed to shorteing your URL, please try again later`);
    }
  }
}
