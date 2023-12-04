# History

## Mosquitto

Moquitto (1.6.x 판) 서버가 SIGUSR2를 받으면 subscription tree를 (또는 topic
tree를) log로 출력한다. ~~서버를 시험하려고 만든 기능이라서 언제 없어질
지 모른다고 한다.~~ 2.0.x 판에 이르러서도 여전히 tree를 보여주지만 signal에
곧장 반응하지 않아서 그다지 쓸모가 없다. 하지만 MQTT 서버가 topic tree의 현재를
요청에 따라 보여주는 기능을 갖추고 있는 것이 좋은 아이디어라고 생각해서 이를
기록으로 남긴다. 아래처럼 topic_tree란 이름의 shell script로 만들어 쓰려고도
하였다. (설명에서 mosquitto container 이름이 mosquitto라고 가정한다.)

```sh
#!/usr/bin/env sh

services=$*
if [ $# -eq 0 ] ; then
  services=mosquitto
fi

for service in $services
do
  docker kill --signal=SIGUSR2 $service
done
```

더불어 docker로 실행하는 container에 아래와 같이 docker kill 명령으로 곧장
signal을 날릴 수 있다는 것도 알아두자.

```sh
docker kill --signal=SIGHUP mosquitto
```

다만 container 속에서 process id가 1인 process만 이런 방식으로 signal을 받을
수 있다. 같은 container에서 돌아가는 다른 process에 signal을 보내려면 container
속 ps 명령으로 process id를 알아내어 kill로 signal을 날리는 수 밖에 없다. 이를
테면 mosquitto container 속 mosquitto_sub의 process id는 아래와 같이 얻을
수 있다.

```sh
$ docker exec mosquitto mosquitto_sub -h mosquitto -t topic &
[1] 27522
$ docker exec mosquitto ps
PID   USER     TIME  COMMAND
    1 1000      0:04 /usr/sbin/mosquitto -c /mosquitto/config/mosquitto.conf
   72 1000      0:00 mosquitto_sub -h mosquitto -t topic -C 2 -u futureict -P futureict_1101
   77 1000      0:00 ps
```

[Docker container 속 프로세스에 signal 보내는 법][1]를 논의를 보면 docker
inspect로 container 내부에서 돌아가는 process id를 알아내는 방법도 있다.
그 다음에는 docker exec kill로 내부 process에 signal을 직접 보낸다.

```sh
$ docker exec mosquitto kill -9  72
[1]  + 27522 exit 137   docker exec mosquitto mosquitto_sub -h mosquitto -t topic
```

## 참고 문헌

[1]: https://stackoverflow.com/questions/25687131/how-to-send-signal-to-program-run-in-a-docker-container
