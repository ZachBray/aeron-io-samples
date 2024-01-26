#!/bin/sh
java -Djava.net.preferIPv4Stack=true -Daeron.ipc.mtu.length=8k -Daeron.cluster.log.channel=aeron:udp?term-length=256k "$@" -jar /home/aeron/jar/standby-uber.jar
