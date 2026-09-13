import { REST, Routes } from "discord.js";
import { ModuleRegistry } from "./module-registry";
import path from "path";
import { env } from "../application/env";
import { log } from "../application/log";

export async function registerCommand(): Promise<void> {
  const commands = new ModuleRegistry(path.join(__dirname, "../commands"));
  const rest = new REST().setToken(env.DISCORD_TOKEN);

  try {
    const command = await commands.getCommandData();
    await rest.put(
      Routes.applicationGuildCommands(
        env.DISCORD_APPLICATION_ID,
        env.DISCORD_GUILD_ID),
      {body: command}
    );
    log.info(`Command registered Successfully to Discord`);
  } catch (error) {
    log.error(`Failed to registering command to discord, error ${error}`);
    process.exit(1);
  }
}
