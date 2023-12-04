#!/bin/sh

# from ./setup/setup.sh

. ./setup/lib/functions.sh

if ! command_exists docker; then
  echo No docker here. Nothing to configure.
  exit 1
fi

if docker run hello-world; then
  echo Nothing more to do.
  exit 0
fi

sudo groupadd -f docker
sudo usermod -aG docker ${USER}

sudo systemctl enable docker

dockerdir=~/.docker
if [ -d ${dockerdir} ] ; then \
  sudo chown "${USER}":"${USER}" ${dockerdir} -R ;\
  sudo chmod g+rwx "${dockerdir}" -R ;\
fi
