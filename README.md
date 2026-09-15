# YUKI-URL : URL Shortener Projects

> YUKI-URL is a URL shortener designed to transform long URLs into compact, easy-to-share links.

## Table of Contents

- [About the Project](#about-the-project)
- [Built With](#built-with)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
   - [Prerequisites](#prerequisites)
   - [Running with Docker (Recommended)](#running-with-docker-recommended)
   - [Running Manually](#running-manually)
   - [Using the Nix Flake (NixOS / Nix users)](#using-the-nix-flake-nixos--nix-users)
- [Environment Variables](#environment-variables)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)

## About The Project

YUKI-URL uses a Base62 encoder/decoder to generate compact URLs. The combination of Generated Auto-increment IDs and
Base62 encoding allows YUKI-URL to generate short, unique, and efficient URLs. YUKI-URL also provides three separate
interfaces: a Public API, a Web Interface, and a Discord Bot, each running on its
own port.

## Built With

### Backend

[![Java OpenJDK][Java]][java-link]
[![Maven Package Manager][Maven]][maven-link]
[![Java Springboot][Springboot]][springboot-link]
[![Redis In-Memory Database][Redis]][redis-link]
[![Postgre SQL][Postgresql]][postgre-link]
[![Discord JS][Discord]][discordjs-link]
[![TS][Typescript]][typescript-link]
[![Hono-Bun][Hono]][hono-link]

### Front-End

[![TS][Typescript]][typescript-link]
[![React Vite][Vite]][vite-link]
[![React JS][React]][react-link]

### Other (Discord Bot, etc.)

[![Docker container][Docker]][docker-link]
[![Nix Flake][NixOS]][nix-link]

## Project Structure

This project is organized as a monorepo with three independent services, each with its own stack:

```
yuki-url/
├── web/     # React + Vite (frontend)
├── api/     # Spring Boot (public API)
├── bot/     # Hono + Bun + discord.js (Discord bot)
├── redis/   # Redis config (redis.conf, users.acl)
└── docker-compose.yml
```

| Service | Stack             | Purpose               |
|---------|--------------------|------------------------|
| `web`   | React, Vite, Bun   | Web interface          |
| `api`   | Spring Boot, Java  | Public REST API        |
| `bot`   | Hono, Bun, discord.js | Discord bot         |

## Getting Started

### Prerequisites

- **Docker & Docker Compose** — recommended way to run the full stack
- **Bun** (only needed if running `web`/`bot` outside Docker)
  ```sh
  curl -fsSL https://bun.com/install | bash

  # Ensure the version is 1.3.13
  bun --version
  ```
- **JDK 21 & Maven** (only needed if running `api` outside Docker)

### Running with Docker (Recommended)

1. Clone the repo
   ```sh
   git clone https://github.com/<your-username>/yuki-url.git
   cd yuki-url
   ```
2. Create file users.acl for Redis
   ```sh
   cp redis/users.acl.example redis/users.acl
   
   # Read the instruction inside config file
   nano/nvim/vim redis/users.acl
   ```
   
3. Copy and fill in environment files
   ```sh
   cp .env.example .env
   cp bot/.env.example bot/.env
   ```
4. Start all services
   ```sh
   docker compose up --build
   ```
5. Services will be available at:
   - Web: `http://localhost:3000`
   - API: `http://localhost:${API_PORT}`
   - Bot: connects to Discord directly, no exposed port

To stop everything:
```sh
docker compose down
```

### Running Manually

Each service can also be run independently for local development.

**Web**
```sh
cd web
bun install
bun run dev
```

**API**
```sh
cd api
mvn spring-boot:run
```

**Bot**
```sh
cd bot
bun install
bun run start
```

> Note: when running manually, make sure `postgres` and `redis` are still available (either via `docker compose up postgres redis` or a local install), and that `api`/`bot` `.env` values point to the correct hosts (e.g. `localhost` instead of Docker service names).

### Using the Nix Flake (NixOS / Nix users)

If you're on NixOS or have Nix installed, a `flake.nix` is provided for a reproducible dev shell (useful for [Running Manually](#running-manually) — not required for the Docker workflow).

```sh
nix develop
```

The shell exposes a helper function, `load_dotenv`, to inject a `.env` file's variables into your current shell session:

```sh
load_dotenv .env          # loads root .env (for api-related vars)
load_dotenv bot/.env      # loads bot .env
```

> **Important:** environment variables loaded this way only exist in the shell session where you ran `load_dotenv`. If you run `mvn spring-boot:run` or `bun run start` **manually** (outside Docker), you must run `load_dotenv` first in that same terminal, or the service won't see `DATABASE_URL`, `DISCORD_TOKEN`, etc.
>
> This step is **not needed when using Docker Compose** — Compose reads `.env` (and each service's `env_file`) on its own, regardless of your OS or shell. `load_dotenv` is purely a convenience for local/manual development inside the Nix shell.

## Environment Variables

Each service reads its own configuration:

- **`api`** — `API_PORT`, `DATABASE_URL`, `DATABASE_USERNAME`, `DATABASE_PASSWORD`, `REDIS_HOST`, `REDIS_PORT`, `REDIS_USERNAME`, `REDIS_PASSWORD`
- **`bot`** — Discord token, application ID, and API URL (see `bot/.env.example`)
- **`web`** — `VITE_API_URL` (baked in at build time, see `web/.env.example`)

> `.env` files are gitignored and never committed. See each service's `.env.example` for the required keys.

## Usage

With the stack running via Docker Compose, open the web interface at `http://localhost:3000` to shorten a URL, or interact with the Discord bot in your server.

## Contributing

Contributions are not required for this project. This is a personal project created primarily for learning and
experimentation. If you find the project interesting, feel free to fork or clone the repository and explore it yourself.

## License

Distributed under the MIT License. See `LICENSE` for more information.

[//]: # (MARKDOWN WEB AND IMG SHIELD LINKS)

[//]: # (WEB LINKS)

[springboot-link]: https://spring.io/projects/spring-boot

[java-link]: https://openjdk.org/

[postgre-link]: https://www.postgresql.org/

[redis-link]: https://redis.io/

[maven-link]: https://maven.apache.org/

[hono-link]: https://hono.dev/

[vite-link]: https://vite.dev/

[react-link]: https://react.dev/

[typescript-link]: https://www.typescriptlang.org/

[discordjs-link]: https://discord.js.org/

[nix-link]: https://nix.dev/

[docker-link]: https://www.docker.com/

[//]: # (IMG SHIELD LINKS)

[Springboot]: https://img.shields.io/badge/springboot-000000?style=for-the-badge&logo=springboot&logoColor=green

[Java]: https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white

[Postgresql]: https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white

[Redis]: https://img.shields.io/badge/redis-%23DD0031.svg?style=for-the-badge&logo=redis&logoColor=white

[Maven]: https://img.shields.io/badge/apachemaven-%23C71A36.svg?style=for-the-badge&logo=apachemaven&logoColor=white

[Hono]: https://img.shields.io/badge/hono-%23E36002.svg?style=for-the-badge&logo=hono&logoColor=white

[Vite]: https://img.shields.io/badge/vite-%23646CFF.svg?style=for-the-badge&logo=vite&logoColor=white

[React]: https://img.shields.io/badge/react-%2320232a.svg?style=for-the-badge&logo=react&logoColor=%2361DAFB

[Discord]: https://img.shields.io/badge/Discord-%235865F2.svg?style=for-the-badge&logo=discord&logoColor=white

[NixOS]: https://img.shields.io/badge/NIX-%235277C3.svg?style=for-the-badge&logo=NixOS&logoColor=white

[Typescript]: https://img.shields.io/badge/typescript-%23007ACC.svg?style=for-the-badge&logo=typescript&logoColor=white

[Docker]: https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white