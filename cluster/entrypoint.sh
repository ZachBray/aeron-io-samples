#!/bin/sh
java -Djava.net.preferIPv4Stack=true -Daeron.ipc.mtu.length=8k -Daeron.cluster.log.channel=aeron:udp?term-length=256k -Daeron.term.buffer.length=256k -Daeron.archive.segment.file.length=256k "$@" -jar /home/aeron/jar/cluster-uber.jar
