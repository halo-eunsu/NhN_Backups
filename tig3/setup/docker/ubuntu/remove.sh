#!/bin/sh
. ./setup/lib/functions.sh

if ! command_exists docker; then
  echo No Docker. Nothing to remove.
  exit 0
fi

sudo systemctl disable docker
sudo gpasswd --delete ${USER} docker
sudo groupdel -f docker

# 우분투 기본 꾸러미로 깔았을 때
(installed docker.io || installed containerd || installed runc) \
  && sudo apt-get purge --autoremove --yes docker.io containerd runc
# 우분투 새 꾸러미로 깔았을 때
(installed docker-ce || installed docker-ce-cli || containerd.io) \
  && sudo apt-get purge --autoremove --yes docker-ce docker-ce-cli containerd.io

URL=https://download.docker.com/linux/ubuntu
CODENAME=bionic

sudo rm -rf /var/lib/docker
sudo add-apt-repository --remove "deb [arch=amd64] ${URL} ${CODENAME} stable"
sudo rm -f /etc/apt/sources.list.d/docker.list
sudo apt-get update
