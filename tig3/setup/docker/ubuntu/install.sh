#!/bin/sh

# from ./setup/setup.sh

. ./setup/lib/functions.sh

if command_exists docker; then
  docker --version
  exit 0
fi

sudo apt-get update
sudo apt-get install --yes docker.io containerd runc
