#!/bin/bash

# Docker Compose 실행 스크립트

# 서비스 디렉토리와 docker-compose.yml 파일 경로 설정
SERVICES=("db" "kafka" "redis-cluster" "prometheus-grafana" "zipkin")

# 도커 컴포즈 실행 함수
start_compose() {
    for service in "${SERVICES[@]}"; do
        echo "Starting $service..."
        docker-compose -f "Dockerfiles/$service/docker-compose.yml" up -d
    done
}

# 도커 컴포즈 정지 함수
stop_compose() {
    for service in "${SERVICES[@]}"; do
        echo "Stopping $service..."
        docker-compose -f "Dockerfiles/$service/docker-compose.yml" down
    done
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
        echo "사용법: $0 {start|stop|restart}"
        exit 1
esac

