# 개별 서비스 설정과 점검

[[_TOC_]]

## INFLUXDB

- influxdb의 admin 사용자 이름과 암호를 바꾸고 싶다면
  1. `etc/influxdb/initdb.d/init.iql`의 `create user ...`를 고친다.
  2. `.env` 파일에서 `INFLUXDB_ADMIN_USER`와 `INFLUXDB_ADMIN_PASSWORD` 변수 값도 같게 고친다.
  3. `.env` 파일에서 `INFLUXDB_HTTP_AUTH_ENABLED=true`
- 동작확인

```console
TIG ~/tig$ influx # influxdb 쉘에 연결되는지 확인하기
Connected to http://localhost:8086 version 1.8.3
InfluxDB shell version: 1.8.3
> auth # 앞서 INFLUXDB_HTTP_AUTH_ENABLED=true를 설정했다면 admin 사용자 인증 필요
> show users
user      admin
----      -----
futureict true
> exit
```

## TELEGRAF

- 기본 설정은 `etc/telegraf/telegraf.conf`
- 추가 설정 폴더는 `etc/telegraf/telegraf.d`
- 추가 설정 폴더를 (이를테면 `etc/telegraf/spcc.d`로) 바꾸고 싶다면
  - `.env` 파일에서 `TELEGRAF_CONFIG_DIRECTORY=/etc/telegraf/spcc.d` (맨 앞 `/`를 빠뜨리지 않기)

## MOSQUITTO

- 기본 설정은 `etc/mosquitto/mosquitto.conf`
  - 기본 보안 설정은 `etc.mosquitto/conf.d/00_auth.conf`
  - 암호 파일은 `etc/mosquitto/passwd` (`mosquitto_passwd`로 바꿀 수 있다.)
  - 추가 설정은 `etc/mosquitto/conf.d` 폴더 아래에 있는 파일을 편집하거나 새로 파일을 만들어 보탠다.

## GRAFANA

- 동작 확인은 `http://<니 서버 주소>:3000`
- 기본 설정은 `etc/grafana/grafana.ini`
- 데이터는 `var/lib/grafana/grafana.db`
- 플러그인은 `var/lib/grafana/plugins`
- influxDB Data Source를 만들 때 HTTP URL은 `http://influxdb:8086`
  - influxdb의 관리자 이름과 암호는 `.env` 파일에 환경 변수로 정의
