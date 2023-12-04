# 일거리

[[_TOC_]]


## 공통

- [ ] README.md에 docker 로그 파일 지우는 설명
- [ ] grafana에서 fill(null) > fill(linear), null > connectected 등등으로 처리하는 부분 설명
- [ ] TODO 파일 내용 정리
- [ ] docker swarm을 쓰게 된다면 매 서비스 라이프싸이클을 docker service 명령을
관리하도록 구성할 필요가 있다. 그러한 경우 서비스 생성도 docker service
create로 만들어 쓰는 것이 더 나을 수도 있다. docker-compose는 서비스를 만들고
개발할 때 쓰고, 실제 서비스를 배치하고 운영하는 것은 docker swarm 같은
orchestrator를 쓰는 것이라고들 하는데 둘 사이에 구별이 명확하지 않다.
내 생각에는 어쩌다보니 이런 저런 것을 두서없이 만들었는데 나름 특성을
반영한답시고 뒤늦게 그 용처를 억지로 끼워 맞췄다는 생각이 든다. 여튼 이 기능을
사용하여 mosquitto 서비스를 만든다면:

```sh
docker service create \
  --hostname mosquitto \
  --name mosquitto \
  --mount type=volume,source=data,destination=/mosquitto/data \
  --mount type=volume,source=log,destination=/mosquitto/log \
  --mount type=bind,source=$(pwd),destination=/mosquitto/config \
  --user=`id -u`:`id -g` \
  eclipse-mosquitto:latest $
```

- [X] 각 서비스의 설정은 etc 아래에 미리 만들어 두고 volume mount로 쓰는 것이 더 간결하겠다. 이에 따라 서비스 별로 설정 폴더, 데이터 폴더를 일일이 만들고 권한을 바꾸는 따위 번잡한 것들을 싹 정리해 버리는 것이 낫지 싶다. 이렇게 복잡해야 할 까닭이 없다.
- [ ] 각 서비스의 쉘에 접속하였을 때 사용자@호스트 이름 따위 곧 프롬프트가 깔끔하게 떨어지도록 호스트와 사용자 이름을 설정 (docker-compose.yml 파일에 지정 가능)
- [ ] host storage를 직접 마운트하지 않고 docker의 volume을 만들어 속도를 개선하고 권한 설정 문제를 해결할 수 있을 지도. host storage를 곧장 마운트하면 편리한 경우도 많지만 불규칙하게 때때로 lag 걸려 성능을 떨어 뜨린다. 하지만 docker volume을 실제 써보면서 실험해 본적이 없어서 이런 버벅거림이 정말 해결될 수 있을런지는 모름
- [X] 컨테이너에 마운트해서 쓸 service 별 설정 파일이나 데이터베이스 폴더를 ./etc 아래에 모아서 정리
- [X] bin/begin_tig
  . python virtual env처럼 (de)activate 하는 것과 동일한 일을 하는 도구가 필요
  . python virtual env의 (de)activate를 베껴서 setup을 하거나 bin 폴더에 넣어둔 간편 스트립트들이 정상 동작하는데 필요한 환경 변수 따위를 설정하거나 PATH를 걸어주는 일 따위를 하는 스크립트
  . setup/configure.sh을 비롯하여 모든 간편스트립트는 bin/begin_tig를 한 다음에서 환경에서 정상 동작
- [X] TIG 스택 서비스에서 호스트의 폴더를 마운트하여 데이터 저장 공간으로 쓰면서 일어나는 권한 충돌 문제를 풀고자 호스트의 그룹 + 사용자 ID를 컨테이너(서비스) 인자로 건네주는 번잡함을 풀기 위해 처음에는 Makefile을 사용하였으나 관리가 더 복잡하고 불편하여 쉘 스크립트를 만들었다.
- [X] 서비스별로 간편하게 exec할 수 있도록  쉘 스크립트를 만들었다. 이를테면 influxdb 서비스에는 influx라는 스크립트를 만든다.
- [X] 위의 간편 스크립트를 모두 bin 폴더로 넣어서 정리.

### setup/configure.sh

- [X] 설정 파일을 만들 때 docker run 대신에 docker-compose run을 쓰는 게 맞을 듯 (마운트할 폴더의 사용자 권한 설정에 주의)
- [X] 이미 폴더나 파일이 있는 경우는? Do nothing~~? Backup? Just alarm?~~

## Grafana

- [ ] [Docker Secrets](https://grafana.com/docs/grafana/latest/installation/configure-docker/#configure-grafana-with-docker-secrets)을 써서 관리자 암호 따위의 설정을 할 수도 있다. 해야하나?
- [X] grafana.ini 등의 설정 파일이 있는 폴더를 host 폴더와 volume mapping (참고: [기본 경로](https://grafana.com/docs/grafana/latest/installation/configure-docker/#default-paths)를 환경변수로도 지정할 수 있다.)
- [X] ~~grafana의 접속 포트를 http나 https 기본 포트로 변경할 수 있도록 grafana.ini를 수정~~
  1. docker-compose.yml에서 host port의 설정을 80으로 바꾸는 것만으로 충분하고 이게 더 나은 방법
  2. 또한 80 포트를 다른 Web app에서 쓸 수 있도록 비워 두는 것이 더 나을 듯.

## Telegraf

- [X] cpu, mem, proc, disk 등 host의 관제에 필요한 데이터를 받도록 [설정](https://github.com/influxdata/telegraf/blob/master/docs/FAQ.md#q-how-can-i-monitor-the-docker-engine-host-from-within-a-container) 변경
- [X] etc/telegraf/telegraf.d에서 데이터 수집에 필요한 입출력 플러그인 별로 conf 파일을 분리 ~~inputs.mqtt_consumer 항목이 (서버 별로) 여럿일 수 있나? 그게 의미가 있나?~~

## Influxdb

- [ ] Retention policy, 오랜 데이터를 아키이빙 처리 또는 장기 보존하는 방법?
- [ ] 전송된 데이터의 time stamp와 db에서 들어갈 때 time stamp를 따로 받아서 보존하고, 이 둘의 차이로 네트워크 송수신 지연 상황을 관제하는 방법
