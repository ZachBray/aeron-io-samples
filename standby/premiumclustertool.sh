#!/bin/sh
aerondir=$(ls /dev/shm/ | grep aeron | head -1)
java -cp /home/aeron/jar/standby-uber.jar -Daeron.dir=/dev/shm/$aerondir io.aeron.cluster.PremiumClusterTool $@
