import { Hono } from 'hono'
import { discordBot, startBot } from './app'
import { log } from './application/log';
import { serve } from 'bun';
import { env } from './application/env';
const bot = discordBot();
const app = new Hono();

async function startup() {
  startBot(bot).catch((error) => {
    log.error(`Failed to starting bot`, error);
    process.exit(1);
  })

  serve({
    port: env.APPLICATION_PORT,
    fetch: app.fetch
  });

  log.info(`Web Server Running on PORT ${env.APPLICATION_PORT}`);
}

startup();
