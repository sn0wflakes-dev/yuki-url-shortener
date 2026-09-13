import { Client, Events } from "discord.js";
import path from "path";
import { ModuleRegistry } from "../config/module-registry";
import { log } from "../application/log";

export async function registerCommandHandler(client: Client): Promise<void> {
  const registry = new ModuleRegistry(path.join(__dirname, "../commands"));
  client.on(Events.InteractionCreate, async (interaction) => {
    if (!interaction.isChatInputCommand()) return;
    
    const commands = await registry.getCommands();
    const command = commands.get(interaction.commandName);
    
    if (!command) {
      await interaction.reply({ content: `Command ${interaction.commandName} is not found`, ephemeral: true });
      return;
    }

    try {
     await command.execute(interaction); 
    } catch (error) {
      log.error(`[CommandHandler] : Error while executing command ${interaction.commandName}`, error);
      const reply = interaction.replied || interaction.deferred 
      ? interaction.followUp.bind(interaction)
      : interaction.reply.bind(interaction);
      await reply({content: "Error while executing command", ephemeral: true});
    }
  });
}
