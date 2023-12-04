# 5팀 Branch

## [mini teamproject] iot gateway
MQTT 프로토콜을 이해하기 위한 IoT Gateway 만들기
<br><br>
<b>팀원</b> : 김성환, 박미정, 우혜승, 정은수, 하준영
<b>github</b> : https://github.com/codethestudent/mini-teamproject-iot-gateway-1.git
<br><br>
## 클래스 구성
- MqttInNode
  * 목적 : 
  * 주요 기능 : 
  * 메서드 예시 :
    
- MqttOutNode
  * 목적 : 
  * 주요 기능 : 
  * 메서드 예시 :
    
- FunctionNode
  * 목적 : 
  * 주요 기능 : 
  * 메서드 예시 :

- LogsInputOutputNode
  * 목적 : 로그를 json형식으로 출력하기위한 노드이다.
  * 주요 기능 : 각 입출력노드들의 데이터를 전송받아 로그를 json형태 파일로 출력한다.
  * 메서드 예시 : createJsonLog(JsonMessage jsonMessage), writeDataToFile(File file, List<String> data) 

- JsonMessage
  * 목적 : 각 노드들의 제이슨 메세지 오브젝트를 담아서 전송한다.
  * 주요 기능 : 노드들의 메세지 오브젝트와 메세지의 히스토리를 기록하여 전송한다.
  * 메서드 예시 : getJsonObject(), getNodeName()
    
- SystemOption
  * 목적 : 입력, 출력, 동작 상태 등을 설정하는 class
  * 주요 기능 : command line argument를 받아서 설정을 지정하거나 JSON file을 통해 설정을 지정하도록 한다.
  * 메서드 예시 : getApplicationName()
