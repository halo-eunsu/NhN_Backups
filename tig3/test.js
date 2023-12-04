msg.data = msg.topic.split("/");
if ((msg.payload.deviceInfo !== undefined) && (msg.payload.deviceInfo.devEui !== undefined)) {
    msg.deviceId = msg.payload.deviceInfo.devEui
    return msg;

}

if(msg.data[2] == "device" && msg.data[5] == "up"){

    if (tenantName && tenantName.includes("경남")) {
        // "경남"을 포함하는 경우, 분류 작업을 수행합니다.
        return [msg, null]; // 두 번째 출력을 통해 분류됨을 나타냅니다.
    } else {
        // "경남"을 포함하지 않는 경우, 기본 출력으로 메시지를 전송합니다.
        return [null, msg]; // 첫 번째 출력을 통해 기본 출력으로 메시지를 전송합니다.
    }

}