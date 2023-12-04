#!/bin/sh

# from ./setup/setup.sh

. ./setup/lib/functions.sh 

if ! command_exists docker; then
  echo No docker here. Nothing more to do.
  exit 0
fi

if ! command_exists docker-compose && command_exists pip; then
  sudo=sudo
  if [ -n "$VIRTUAL_ENV" ]; then
    # Don't have to sudo under python venv 
    sudo=
  fi
  $sudo pip install --upgrade pip
  $sudo pip install docker-compose
fi

if ! command_exists docker-compose; then
  # Install the latest copy directly
  latest=https://github.com/docker/compose/releases/download/1.26.2
  dc=/usr/local/bin/docker-compose

  sudo curl -L "$latest/docker-compose-$(uname -s)-$(uname -m)" -o ${dc}
  sudo chmod +x ${dc} 
fi

if command_exists docker-compose; then
  # Show the installed
  echo $(which docker-compose): $(docker-compose --version)
  exit 0
fi
