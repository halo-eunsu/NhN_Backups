#!/bin/sh

# from ./setup/setup.sh

# docker 설치전 작업
. ./settings/lib/functions.sh

sudo apt-get update
if command_exists docker; then
  sh ./setup/docker/ubuntu/remove.sh
  sh ./setup/docker-compose/remove.sh
fi

if ! command_exists docker; then
# 먼저 깔려 있어야 할 패키지들
# software-properties-common을 빼면 Ubuntu에 보통 깔려있는
# 꾸러미들이 겹치지만 꼭 필요한 것이 무엇인지는 알아야하고
# 이미 깔렸으면 넘어가기 때문에 점검을 위해서 필요
sudo apt-get install \
  apt-transport-https \
  ca-certificates \
  curl \
  gnupg-agent \
  software-properties-common

URL=https://download.docker.com/linux/ubuntu
CODENAME=bionic
ME=${USER}
dockerdir=${HOME}/.docker

curl -fsSL ${URL}/gpg | sudo apt-key add -
sudo apt-key fingerprint 0EBFCD88
sudo add-apt-repository "deb [arch=amd64] ${URL} ${CODENAME} stable"
sudo apt-get update

sudo apt-get install docker-ce docker-ce-cli containerd.io
fi
