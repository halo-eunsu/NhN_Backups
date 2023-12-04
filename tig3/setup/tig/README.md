[[_TOC_]]

 ./etc 폴더는 [docker-compose.yml](../../docker-compose.yml)에 적힌 서비스들이 적어도 돌아갈 수 있도록 만들어 둔 설정이다. 여러 서비스 스택을 쌓아 올려 일을 하다 보면 어딘가 꼬여서 밑바닥부터 다시 시작해야 하는 일이 생긴다. 그럴 때마다 volume mount 구조에 맞게 폴더를 짜고 설정 파일을 새로 만들어 손보는 일을 되풀이하자면 지루하고 번거롭다. 이 폴더를 복붙해서 쓰도록 하자. 기본 설정이 잘 동작하는지 때맞춰 시험하고 고쳐 놓도록 하자.

 물론 여태 고생해서 만들어둔 [etc](../../etc) 설정을 주의 깊게 다루고 git push 해두는 것이 가장 중요하다.

 아래는 서비스 별로 설정 파일을 만들거나 기본 환경을 리셋하는 방법이다. 어렵지 않지만, 잊으면 이 또한 귀찮은 일이라서 아래와 같이 적어둔다. 

# 설정 파일 만드는 법
## InfluxDB
### influxdb.conf
docker-compose(1.27.2)에서 내부에서 실행되는 프로세스의 표준 입출력을 host의
표준 입출력과 연결하기 위하여 pseudo tty를 할당하면 stderr로 갈 메시지들
마저 stdout으로 나간다. 그럴 경우 아래와 같이 기본 설정 파일을 자동으로
생성하기 위해서 stdout의 출력을 리다이렉션하는 경우 stderr 곧 에러 메시지나
로그 메시지가 뜻하지 않게 설정 파일에 들어가서 오류가 난다. 그나마 run
명령에서는 아래와 같이 pseudo tty를 할당하지 않도록 -T 옵션으로 골치 거리를
막을 수 있는데 exec는 -T를 줘도 해결이 안되는 버그가 있다. ( 1.19.x 판에서는
exec도 -T가 먹힐 거라고 한다. ) 

```sh
tig run --rm -T influxdb influxd config > influxdb.conf 
```

### DB 리셋

influxdb 컨테이너 속 /init-influxdb.sh를 돌리면 db를 리셋할 수 있다. 다만 리셋을 하려면 var/lib/influxdb/meta 폴더가 없거나 없애야 한다. 어떤 이유에서든 meta 폴더가 없다는 것은 db가 셋업 안되어 있다는 말이다. 리셋/셋업을 입맛에 맞게 고치고 싶다면 etc/influxdb/initdb.d 폴더에 *.sh이나 *.iql 스크립트를 만들어 넣는다. 

```sh
tig run --rm influxdb /init-influxdb.sh
```
## Telegraf
### telegraf.conf

```sh
tig run --rm -T telegraf telegraf config > telegraf.conf 
```
