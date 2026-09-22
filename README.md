# HostPY

Minecraft 서버 안에서 Python 프로젝트를 실행하고, Minecraft/Skript와 Python 사이에서 신호를 주고받기 위한 가벼운 브리지 플러그인입니다.

## 목적

HostPY 자체는 Discord 봇이나 외부 서비스가 아닙니다.

- Minecraft 서버에서 Python 파일 실행
- Minecraft/Skript -> Python 신호 전송
- Python -> Minecraft/Skript 신호 전송
- 신호 내용 자체는 HostPY가 임의로 해석하지 않고 그대로 전달
- Discord, 데이터베이스, API 등의 실제 기능은 Python 프로젝트에서 직접 구현

## 명령어

```text
/hostpy start <파일명>
/hostpy stop <파일명>
/hostpy list
```

Python 파일은 `plugins/HostPY/scripts/`에 둡니다.

## 신호 폴더

```text
plugins/HostPY/signals/
├─ to-python/   Minecraft/Skript -> Python
└─ to-server/   Python -> Minecraft/Skript
```

파일 기반 통신을 사용하므로 Python 쪽에서는 해당 폴더를 polling 또는 watchdog 등으로 감시할 수 있습니다.

## Skript

Python으로 신호를 보낼 때:

```skript
send hostpy signal "hello"
```

Python에서 보낸 신호를 받을 때:

```skript
on hostpy signal received:
    broadcast "받은 신호: %event-string%"
```

## 빌드

프로젝트 루트(`pom.xml`이 있는 폴더)에서:

```bash
mvn clean package
```

빌드 결과:

```text
target/HostPY-1.2.0.jar
```
