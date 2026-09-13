import { Glob } from "bun";

export async function importAll(dir: string) : Promise<Array<{file: string, mod: any}>> {
  const entries = new Glob("**/*.{ts/js}");
  const modules: Array<{file: string, mod: any}> = [];

  for await (const relativePath of entries.scan({cwd: dir, absolute: true})) {
    if (relativePath.startsWith(".d.ts")) {
      continue;
    }

    const mod = await import(relativePath);
    modules.push({file: relativePath, mod});
  }

  return modules;
}
