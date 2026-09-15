{
  description = "url shortener flake";

  inputs = {
    nixpkgs.url = "github:nixos/nixpkgs/nixos-26.05";
  };

  outputs = {nixpkgs, ...}:
  let
    system = "x86_64-linux";
    pkgs = import nixpkgs {inherit system; };
  in {
    devShells.${system}.default = pkgs.mkShell {
      packages = with pkgs; [
        # insert desired packages
      ];

      shellHook = ''
        # Shell function for load .env from target
        load_dotenv() {
          TARGET_ENV="$1"

          sed -i 's/\r$//' "$TARGET_ENV"

          echo "load .env file from $TARGET_ENV"
          if [ -f "$TARGET_ENV" ]; then
            echo "Found .env at $TARGET_ENV"
            set -a
            source "$TARGET_ENV"
            set +a
            echo ".env file successfully loaded from $TARGET_ENV !"
          else
            echo "Could not load .env file. File not found"
          fi
        }
      '';
    };
  };
}
