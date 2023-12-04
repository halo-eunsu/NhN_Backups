#!/bin/bash

# Useful functions for testing
uuid() {
    cat /proc/sys/kernel/random/uuid
}

rand() {
    local floor=$1; local celing=$2
    local range=$(( $celing - $floor + 1 ))
    echo $(( $RANDOM % $range + $floor ))
}

repeat() {
    to=$1
    shift
    for i in `seq 1 $to`
    do
        $@
    done
}

time_stamp() {
    date +%s
    sleep $1
}

times() {
    repeat $1 "time_stamp $2"
}

host=mosquitto
# protocol_version=mqttv5
# protocol_version=mqttv311
# protocol_version=mqttv31
# options="--protocol-version $protocol_version --debug"

options="--debug"

topic="센서/온도"
status="${topic}/상태"

n_messages=10
delay=2
timeout=$(( n_messages * delay ))

pub() {
    docker exec -i mosquitto \
	    mosquitto_pub --host $host --topic $topic $*
}

sub() {
    docker exec -i mosquitto	\
	    mosquitto_sub --host $host --topic $topic \
         $*
}

sub_json () {
    docker exec -i mosquitto	\
	    mosquitto_sub --host $host --topic $topic \
         -v -F %j   \
         $*
}


# Idioms for retained messages
set(){
    pub --retain $* 
}

get() {
    sub --retained-only -C 1 $* 
}

reset() {
    set --null-message $*
}

# Idioms for persistent messages
clean_session() { 
    sub --id C4 $* 
}
clean_start()   {
    sub --id C5 \
        --protocol-version mqttv5 \
        --property CONNECT session-expiry-interval 0 \
        $*
}
subscriber() {
    sub --id S5 \
        --property CONNECT session-expiry-interval $timeout \
        $*
}
