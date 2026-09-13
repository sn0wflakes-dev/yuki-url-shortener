import * as dotenv from "dotenv";
import { z } from "zod";
import { log } from "./log";

dotenv.config();

const envSchema = z.object({
  APPLICATION_PORT: z.string().default('3001').transform((val) => Number(val)),
  DISCORD_TOKEN: z.string(),
  DISCORD_APPLICATION_ID: z.string(),
  DISCORD_PUBLIC_KEY: z.string(),
  DISCORD_GUILD_ID: z.string(),
  API_BASE_URL: z.string(),
});

const parsedEnv = envSchema.safeParse(process.env);

if (!parsedEnv.success) {
  log.error("Invalid or missing environment variable");
  process.exit(1);
};

export const env = Object.freeze(parsedEnv.data);
