#!/bin/bash
##########################################################################################
#                                       BOOT SERVER                                      #
##########################################################################################
scriptdir=`dirname $0`
APP_HOME=$scriptdir
APP_CONFIG="conf/application.properties"
APP_PORT="9200"
BOOT_OPTS="--server.port=$APP_PORT"
JAVA_OPTS="-Dfile.encoding=UTF-8 -Xmx1024M -Xms64M -Xss256K -XX:MaxPermSize=256M -XX:+UseParallelGC "
JAVA_HOME_PATH="/home/wxpay/jdk1.8.0_121"

CL_ERR='\e[0;31m'
CL_INFO='\e[0;32m'
CL_WARN='\e[0;33m'
reset='\e[0m'

error() { echo -e "${CL_ERR}ERROR | $1${reset}"; }
info() { echo -e "${CL_INFO}INFO | $1${reset}"; }
warn() { echo -e "${CL_WARN}WARN | $1${reset}"; }

PID_FILE="BOOT_PID"


if [[ -n "$APP_CONFIG" ]]; then
    BOOT_OPTS="$BOOT_OPTS --spring.conf.location=$APP_CONFIG"
fi

if [[ -n "$JAVA_HOME" && -x "$JAVA_HOME/bin/java" ]]; then
    JAVACMD="$JAVA_HOME/bin/java"
	JAVA_HOME_PATH="$JAVA_HOME"
else
    if [[ -n "$JAVA_HOME_PATH" && -x "$JAVA_HOME_PATH/bin/java" ]]; then
	    JAVACMD="$JAVA_HOME_PATH/bin/java"
	else
      echo "JAVA_HOME not found in your enviroment"
	  exit 1
	fi
fi

SEP=":"
CLASSPATH=""
if [[ -z "$CLASSPATH" ]]; then
    for ext in "$APP_HOME"/lib/* ; do
        if [[ -z "$CLASSPATH" ]]; then
            CLASSPATH="$ext"
        else
            CLASSPATH="${CLASSPATH}${SEP}${ext}"
        fi
    done
    for ext in "$APP_HOME"/modules/*; do
        if [[ -z "$CLASSPATH" ]]; then
            CLASSPATH="$ext"
         else
             CLASSPATH="${CLASSPATH}${SEP}${ext}"
         fi
    done
fi

checkPort(){
    if [[ $# -gt 0 && $1 -gt 0 ]]; then
	   port_pid=`lsof -i:$1 | grep -v "PID" | awk '{print $2}'`
	   if [[ $port_pid -gt 0 ]] ;then
		   return 600
	   fi
	else
	   return 500
	fi
}

run() {
    APP_MAINCLASS="com.teamclub.App"
	if [[ $1 -eq 1 ]]; then
	   $JAVACMD $JAVA_OPTS -classpath $CLASSPATH $APP_MAINCLASS $BOOT_OPTS
	else
       pid=`nohup $JAVACMD $JAVA_OPTS -classpath $CLASSPATH $APP_MAINCLASS $BOOT_OPTS >/dev/null 2>&1 & echo $!`
	fi
}

debug(){
   start 1
}

start() {
    port_pid=`lsof -i:$APP_PORT | grep -v "PID" | awk '{print $2}'`
    if [[ $port_pid -gt 0 ]] ;then
	   warn "Port $APP_PORT is used for $port_pid"
	   exit 0
    fi
     
    if [ -f $PID_FILE ]; then 
	    pid=`cat $PID_FILE`
	    if [ -z "`ps axf | grep -w ${pid} | grep -v grep`" ]; then
            run $1
		else
		    warn "$APP_MAINCLASS already started! (pid=$pid)"
			exit 0
		fi
	else
	    run $1
	fi
	
	if [ -z $pid ]; then
        error "Failed starting"
        exit 3
    else
        echo $pid > $PID_FILE
        info "(pid=$pid) [OK]"
        exit 0
    fi
}

stop() {
    if [ -f $PID_FILE ]; then
        pid=`cat $PID_FILE`
        if [ -z "`ps axf | grep -w ${pid} | grep -v grep`" ]; then
            error "Not running (process dead but pidfile exists)"
            exit 1
        else
            pid=`cat $PID_FILE`
            kill -9 $pid 2> /dev/null
			rm -f $PID_FILE
            info "Stopped [$pid]"
            exit 0
        fi
    else
        error "Not running (pid not found)"
        exit 3
    fi
}
restart() {
    if [ -f $PID_FILE ]; then
        pid=`cat $PID_FILE`
        if [ -z "`ps axf | grep -w ${pid} | grep -v grep`" ]; then
            error "Not running (process dead but pidfile exists)"
			start 0
            exit 0
        else
            pid=`cat $PID_FILE`
            kill -9 $pid 2> /dev/null
			rm -f $PID_FILE
            info "Stopped [$pid]"
			start 0
            exit 0
        fi
    else
        error "Not running (pid not found)"
        exit 3
    fi
}

status() {
    if [ -f $PID_FILE ]; then
        pid=`cat $PID_FILE`
        if [ -z "`ps axf | grep -w ${pid} | grep -v grep`" ]; then
            error "Not running (process dead but pidfile exists)"
            exit 1
        else
            info "Running [$pid]"
            exit 0
        fi
    else
        error "Not running"
        exit 3
    fi
}

sysinfo() {
   info "System Information:"
   info "****************************"
   info `head -n 1 /etc/issue`
   info `uname -a`
   info
   info "JAVA_HOME=$JAVA_HOME_PATH"
   info `$JAVACMD -version`
   info
   info "APP_HOME=$APP_HOME"
   info "APP_MAINCLASS=$APP_MAINCLASS"
   info "****************************"
}

case "$1" in
   'start')
      start 0
      ;;
   'stop')
     stop
     ;;
   'restart')
     restart
     ;;
   'status')
     status
     ;;
   'info')
     sysinfo
     ;;
   'debug')
     start 1
	 ;;
  *)
     echo "Usage: $0 {start|stop|restart|status|info}"
     exit 1
esac
