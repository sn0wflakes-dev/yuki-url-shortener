import { Client, Events, GatewayIntentBits } from "discord.js";
import { log } from "./application/log";
import { registerCommand } from "./config/commands-register";
import { env } from "./application/env";
import { registerCommandHandler } from "./handler/command-handler";

export function discordBot(): Client {
  const client = new Client({
    intents: [
      GatewayIntentBits.Guilds,
      GatewayIntentBits.GuildMessages,
      GatewayIntentBits.MessageContent
    ]
  });

  client.once(Events.ClientReady, (c) => {
    log.info(`[BotInstance] : logged in as ${c.user.displayName}`);
  });

  registerCommandHandler(client);

  return client;
}

export async function startBot(client: Client): Promise<void> {
  await registerCommand();
  await client.login(env.DISCORD_TOKEN);
}
