import { ChatInputCommandInteraction, SlashCommandBuilder } from "discord.js"

export default {
  data: new SlashCommandBuilder().setName("ping").setDescription("Reply with pong"),
  async execute(interaction: ChatInputCommandInteraction) {
    await interaction.reply(`Hello ${interaction.user.username}, teto is here!`);
  }
}
