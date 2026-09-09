# YUKI-URL : URL Shortener Projects

> YUKI-URL is a URL shortener designed to transform long URLs into compact, easy-to-share links.

## Table of Contents

- [About the Project](#about-the-project)
- [Built With](#built-with)
- [Getting Started](#getting-started)
- [Usage](#usage)
- [Contributing](#contributing)
- [License](#license)

## About The Project

YUKI-URL uses a Base62 encoder/decoder to generate compact URLs with a fixed length of 7 characters. Each shortened URL
is associated with a unique Snowflake ID, inspired by Twitter/X's Snowflake algorithm, but with a modified bit layout
tailored to the project's requirements.

The combination of Snowflake IDs and Base62 encoding allows YUKI-URL to generate short, unique, and efficient URLs while
maintaining a consistent output length.

YUKI-URL also provides three separate interfaces: a Public API, a Web Interface, and a Discord Bot, each running on its
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


## Getting Started

Follow these instructions to set up your project locally.

### Prerequisites

There are several SDK you need to install

**Bun JS**
```bash
curl -fsSL https://bun.com/install | bash

# Ensure the version is 1.3.13
bun --version
```

### Installation

1. Clone the repo
   ```sh
   git clone https://github.com
   ```
2. Install packages
   ```sh
   npm install
   ```

## Usage

Provide instructions and examples on how to use your project.

```sh
npm run start
```

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