#!/bin/sh

. ./setup/lib/functions.sh

if [ -z "${TIG_ENV}" ]; then
  echo "Error: \". bin/begin_tig\" first."
  return 1
fi

CONFIG=etc
DATA=var
TIG=tig

if [ ! -d "${CONFIG}" ]; then
  echo "Error: no ${CONFIG} folder."
  return 1 
fi

mkdir_quietly ${DATA}/lib
services="chirpstack chirpstack-gateway-bridge grafana influxdb postgresql redis node-red mosquitto"
for service in $services
do
  mkdir_quietly ${DATA}/lib/${service}
done

# Configure more for InfluxDB
INFLUXDB_DATA=${DATA}/lib/influxdb
#touch "${INFLUXDB_DATA}/.influx_history"
if [ ! -d "${INFLUXDB_DATA}/meta" ]; then
  # db에 아무런 기본 설정이 안되어 있으므로 /init-influxdb.sh를 돌려서 db 초기
  # 설정을 한다. 여기에 뭔가 보태고 싶다면 etc/influxdb/initdb.d 폴더에 *.sh이나
  # *.iql을 스크립트를 만들어서 넣으면 된다. 
  ${TIG} run --rm influxdb /init-influxdb.sh
fi

# Configure more for mosquitto
mkdir_quietly ${DATA}/log/mosquitto

sudo chown -R ${TIG_USER_ID}:${TIG_GROUP_ID} ${CONFIG} ${DATA}

# Configure more for node-red
mkdir_quietly ${DATA}/lib/node-red/data

# Configure more for postgres
mkdir_quietly ${DATA}/lib/postgres/data

# Configure more for redis
mkdir_quietly ${DATA}/lib/redis/data

# 참고
# MQTT 입력 플러그인 설정]
#+{https://github.com/influxdata/telegraf/blob/master/plugins/inputs/mqtt_consumer/README.md}

# [InfluxDB 출력 플러그인 설정]
#+{https://github.com/influxdata/telegraf/blob/master/plugins/outputs/influxdb/README.md}

# [JSON 입력 데이터 양식 설정]
#+{https://docs.influxdata.com/telegraf/v1.12/data_formats/input/json/}
