#!/bin/sh
. ./setup/lib/functions.sh 

dc=/usr/local/bin/docker-compose
if command_exists docker-compose && [ -e ${dc} ]; then
  sudo rm -f ${dc}
fi

if command_exists docker-compose && command_exists pip; then
  sudo=sudo
  if [ -n "$VIRTUAL_ENV" ]; then
    sudo=
  fi
  $sudo pip uninstall docker-compose
fi
