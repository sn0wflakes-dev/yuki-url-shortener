import { ChatInputCommandInteraction, Collection,  SharedSlashCommand } from "discord.js";
import { importAll } from "./module-loader";
import { log } from "../application/log";

export interface Command {
  data: SharedSlashCommand;
  execute: (interaction: ChatInputCommandInteraction) => Promise<void>;
}

export class ModuleRegistry {
  private commandCache: Collection<string, Command> | null = null;
  
  constructor(private readonly baseDir: string) {};

  public async getCommands(): Promise<Collection<string, Command>> {
    if (this.commandCache) return this.commandCache;

    const commands = new Collection<string, Command>();
    const modules = await importAll(this.baseDir);

    for (const {file, mod} of modules) {
      const command = mod.default ?? mod;
      if (!command.data || !command.execute) {
        log.warn(`[ModuleRegistry] : Skipped, there is no data/execute on ${file}`);
        continue;
      }
      commands.set(command.data.name, {data: command.data, execute: command.execute});
    }

    this.commandCache = commands;
    return commands;
  }

  public async getCommandData(): Promise<SharedSlashCommand[]> {
    const commands = await this.getCommands()
    return commands.map((cmd) => cmd.data);
  }
}

