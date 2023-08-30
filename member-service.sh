#!/bin/bash


start_compose() {
   echo "Starting member-service..."
   docker-compose -f "member/docker-compose.yml" up -d
}

stop_compose() {
   echo "Stopping $service..."
   docker-compose -f "member/docker-compose.yml" down
}

# 스크립트 실행 옵션 처리
case "$1" in
    start)
        start_compose
        ;;
    stop)
        stop_compose
        ;;
    restart)
        stop_compose
        start_compose
        ;;
    *)
        echo "usage: $0 {start|stop|restart}"
        exit 1
esac

