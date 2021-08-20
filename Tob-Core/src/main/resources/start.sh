#!/bin/sh

#指定JDK目录&AppName
export JAVA_HOME=/Library/Java/JavaVirtualMachines/jdk1.8.0_111.jdk/Contents/Home/
APP_NAME=iristar-dataflow.jar
echo $JAVA_HOME
echo $APP_NAME

#nohup命令后台启动jar包并写入日志
nohup  java -jar $APP_NAME >>logs/start.log 2>>logs/startError.log &

#sleep等待15秒后，判断包含AppName的线程是否存在
sleep 15

if test $(pgrep -f $APP_NAME|wc -l) -eq 0
then
   echo "Start Failed"
else
   echo "Start Successed"
fi