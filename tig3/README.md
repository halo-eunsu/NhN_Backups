# TIG 서비스 스택의 설치, 설정, 사용 설명서

[[_TOC_]]

## 데모

~~<http://52.79.184.14:3000/> (사용자/암호 : demo/demo@aws)~~

## 언어

- 아래 둘 가운데 하나로 설정

### C.UTF-8

한글을 포함하여 여러 문자를 UTF-8 표준에 맞게 처리할 수 있는 최소 설정

```sh
export LC_ALL=C.UTF-8
echo export LC_ALL=C.UTF-8 >> ~/.profile
```

### ko_KR.UTF-8

- 소프트웨어에 따라 한글 읽고 쓰기가 제대로 안될 때
- 날짜, 시간, 주소 따위 데이터를 한국 현지화(Localization 또는 줄여서 l10n)해야 할 때

#### Ubuntu

```sh
sudo apt-get install locales \
  && sudo locale-gen ko_KR.UTF-8 \
  && export LC_ALL=ko_KR.UTF-8 \
  && echo export LC_ALL=ko_KR.UTF-8 >> ~/.profile
```

#### Debian

```sh
sudo dpkg-reconfigure locales
```

- ko_KR.UTF-8 UTF-8
- [참고 - 데비안한글설정문서](https://wiki.kldp.org/wiki.php/%B5%A5%BA%F1%BE%C8%C7%D1%B1%DB%BC%B3%C1%A4%B9%AE%BC%AD#s-1.1)

## Git

```sh
# 원본을 고치고 싶은 경우에만 필요한 설정
# GitLab 계정은 `https://gitlab.com/xtra72`
git config --global user.name "xtra72"
git config --global user.email "xtra72@gmail.com"

# `~/.ssh/id_ed25519.pub`를 `https://gitlab.comx/xtra72`에 올린다.
# GitLab 계정에 인증키 올리는 [방법](https://docs.gitlab.com/ee/ssh/README.html#generate-an-ssh-key-pair)
# (xtra72는 `git@gitlab.com:xtra72/tig2.git`의 관리자)
ssh-keygen -t ed25519 -C "서버 인증용 SSH 키 생성"

# master 한 가지에서 최신판만 복제
git clone --branch master --depth 1 --single-branch https://github.com/xtra72/tig2.git

# 원본을 함께 개발할 목적으로 서버를 설치하는 경우에는:
# git clone git@gitlab.com:xtra72/tig2.git
```

## Docker

```sh
# docker와 docker-compose 설치
# sudo 없이 docker 쓸 수 있게 설정
cd tig && sh setup/setup.sh
sudo reboot

# 설치된 docker와 docker-compose 판 번호 출력
# hello-world:latest 이미지 받고 `Hello from Docker!`
# 맨 밑 줄에 `Nothing more to do.`
cd tig && sh setup/setup.sh
```

## 설치

```console
$ cd tig
~/tig$ source bin/begin_tig
TIG ~/tig$ sh setup/configure.sh # 설정 적용
TIG ~/tig$ up `services` # 모든 서비스를 설치하고 배치
```

- 참고: [docker-compose.yml](docker-compose.yml)
- [서비스 별 설정](docs/SERVICES.md)

## TIG 환경

### 세션

```console
~/tig$
~/tig$ . bin/begin_tig  # 세션 열기( bash에서는 . 대신에 source )
TIG ~/tig$              # TIG 표시
TIG ~/tig$ end_tig      # 세션 닫기
~/tig$
```

- login 하자 마자 바로 열기

```console
TIG ~/tig$ echo "cd $TIG_ENV && . bin/begin_tig" >> ~/.profile
```

### 열거

```console
# 설정된 모든 서비스 열거
TIG ~/tig$ services # = `tig config --services`
```

### 배치

```console
TIG ~/tig$ up mosquitto # mosquitto 서비스만 띄우기
TIG ~/tig$ up influxdb telegraf grafana # influxdb telegraf grafana 서비스를 차례대로 띄우기
influxdb
telegraf
grafana
```

### 관리

TIG 서비스는 docker-compose로 서비스를 관리하지만 docker-compose 명령을 바로 쓰지 않고 [tig](bin/tig) 명령을 쓴다. `tig`는 `. bin/begin_tig` 하지 않고 docker-compose를 곧바로 쓰는 실수를 막으려고 만든 안전 장치다.
[사용법](https://docs.docker.com/compose/reference/overview/)은 같다. 자주 쓰는 명령을 간추려 둔다.

```
사용법:
  tig [자주 쓰는 명령과 선택] [서비스...]
  tig -h|--help

서비스:
  influxdb
  telegraf
  grafana
  node-red
  mosquitto

자주 쓰는 명령과 선택:
  config    # docker-compose.yml의 서비스 설정을 보여주거나 점검한다.
  up [-d]   # 서비스를 만들고 시작한다. -d는 detach 모드 곧 background로.
  start     # 서비스를 background로 시작한다.
  restart   # 서비스를 다시 시작한다.
  stop      # 서비스를 background로 중단한다.
  pause     # 서비스를 잠시 재운다.
  unpause   # 서비스를 다시 깨운다.
  down      # 서비스를 중단하고 컨테이너, 네트워크, 이미지, 볼륨을 지운다.
  top       # 돌고 있는 프로세스를 보여준다.
  ps        # 돌고 있는 프로세스 상태를 보여준다.
  images    # 이미지를 나열한다.
```

### 로그 관리

```
사용법:
  logs [서비스...]    # 새 로그를 10줄씩 보여준다.
  clear-logs [서비스] # 지정한 서비스 로그를 지운다.
  clear-all-logs      # 모든 서비스 로그를 지운다.
```

### 서비스에서 명령을 처리

docker-compose exec를 간편하게 쓰도록 만든 쉘 스크립트 명령어들이다.
`services` 명령으로 열거된 모든 서비스 이름에 대응하는 간편 명령어가 있다.

```
사용법:
  [서비스] # 서비스 쉘
  [서비스] [서비스 쉘에서 처리할 명령과 선택]

예제:
    # grafana 서비스 내부에 있는 설정 파일을 less 뷰어로 본다.
    grafana less conf/defaults.ini

    # telegraf 기본 설정 파일 만든다.
    telegraf telegraf config > telegraf.conf

    # influxdb 서비스의 쉘을 불러 쓴다.
    influxdb

    # influxdb influx, influxdb 쉘을 불러 쓴다.
    influx

    # mosquitto 내부에서 실행되는 모든 process를 열거한다.
    mosquitto ps

    # mosquitto 경우에는 컨테이너 속 명령어를 간편하게 돌릴 수 있도록 아래와 같은 간편 명령어들이 있다.
    mosquitto_ctrl
    mosquitto_passwd
    mosquitto_pub
    mosquitto_rr
    mosquitto_sub
```

### 그 밖의 간편 스크립트

```
running_services # `services`로 열거한 container 가운데 running 상태인 것을 차례대로 찍는다.
status mosquitto # mosquitto란 이름의 container 상태를 찍는다.
pull  # 새 판으로 덮어쓰고 서비스 다시 시작 - influxdb는 놔두지만 grafana 패널을 덮어쓰니 조심
upgrade # 서비스 이미지를 업그레이드
```
