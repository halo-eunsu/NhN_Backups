# MQTT 프로토콜

## 기본 용어
### Broker Publisher Subscriber
### Client Server
### Topic Message
### Session
### QoS
- 흔히 transfer 또는 sent를 '전달'이라 옮겨 쓰고 delivery를 '배달'이라 옮겨 쓴다.
- 소식, 정보, 데이터, 메시지를 '전달한다'고 하지 '배달한다'고 쓰면 어색하다.
- 편지나 소포는 '배달한다'고 하고 '책임지고 물건을 몫몫이 날라다 준다'는 뜻이니까 '배달'이 delivery의 뜻에 더 가깝기는 하다.
- MQTT에서 QoS의 service란 메시지의 delivery. 따라서 QoS란 delivery quality를 가리킨다.
- 곧이 옮겨쓰면 '배달 품질'이 되는데 아무래도 데이터 통신에 어울리는 표현은 아니지 싶다.
- 송달 - (법) 전달 보증?

## 세션 처리

Persistent Session과 Clean Session 가운데 하나를 골라 
클라이언트가 온라인 상태가 아니라도 브로커가 메시지를 저장할 수 있으려면
두 가지 조건을 만족해야 한다.

1. 클라이언트가 Persistent session으로 연결되어야 한다.
    - 곧 Clean session 플래그 값이 false
    - Client ID를 지정하지 않으면
        + 브로커가 자동으로 Client ID를 생성
        + Clean session 값이 false면 브로커가 연결 거부
2. Topic Subscription 할 때 QoS가 0보다 커야한다.

## 토픽

- MQTT 프로토콜에서 Topic 나무의 구조를 설계하는 일은 애플리케이션 서비스의 구조와 성능을 좌우한다. [토픽 나무 설계 지침](TOPIC.md)
