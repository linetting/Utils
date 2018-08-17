#! /bin/bash

process_name="xms-auth"

PID=$(ps -ef|grep $process_name |grep -v 'grep' |awk '{print $2}')

if [ $? -eq 0 ]; then
    echo "process id : $PID"
    kill ${PID}
    echo "kill $PID success"
else 
    echo "process $process_name not exist"
fi

cd /usr/local/tomcat_xms/auth

java -jar service-xms-authentication-0.0.1-SNAPSHOT.jar > nohup.out 2>&1 &

sleep 5

echo "$process_name start success"

exit 0
