pwdexport JAVA_HOME=/opt/jdk1.8.0_05
export LD_LIBRARY_PATH=$LD_LIBRARY_PATH:/opt/iristar/taidagongyu/hk:/opt/iristar/taidagongyu/alg
cd /opt/iristar/taidagongyu
nohup sh bin/OAServer > /dev/null 2> /dev/null &
