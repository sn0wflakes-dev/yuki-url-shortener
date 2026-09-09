{
  description = "personal flakes template - change this to proj desc";

  inputs = {
    nixpkgs.url = "github:nixos/nixpkgs/nixos-26.05";
  };

  outputs = {nixpkgs, ...}: let
    system = "x86_64-linux";
    pkgs = import nixpkgs {inherit system; };
  in {
    devShells.${system}.default = pkgs.mkShell {
      packages = with pkgs; [
        # insert desired packages
      ];

      shellHook = ''
        # insert custom command
      '';
    };
  };
}
