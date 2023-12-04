#!/bin/sh

# from ./setup/setup.sh

. ./setup/lib/functions.sh 

if ! command_exists docker; then
  get_docker=.get-docker.sh
  curl -fsSL https://get.docker.com -o ${get_docker} 
  sudo sh ${get_docker} && rm -f ${get_docker}
fi

if command_exists docker; then
  echo $(which docker): $(docker --version)
fi
