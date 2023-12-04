#!/bin/sh
. ./setup/lib/functions.sh

if uname -a | grep Ubuntu; then
  # 우분트 18.04 LTS에서 docker 새 판을 깔았을 때 소켓 통신이 자주 끊기고
  # 버벅대는 일이 잦았다. 배포본에 포함된 docker 꾸러미를 쓸 때는 없던 문제다.
  sh ./setup/docker/ubuntu/install.sh
  # 새 판을 깐다면 아래 줄을 쓴다.
  # sh ./setup/docker/ubuntu/install_latest.sh
fi

if ! uname -a | grep Ubuntu; then
  sh ./setup/docker/install.sh
fi

. ./setup/docker/configure.sh
sh ./setup/docker-compose/install.sh
