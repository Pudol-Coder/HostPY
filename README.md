# HostPY

Minecraft 서버에서 **Python 스크립트를 실행하고, Minecraft/Skript와 Python 사이의 신호를 연결하는 브리지 플러그인**입니다.

## 주요 기능

### 🐍 Python 스크립트 실행

- 서버에서 `.py` 파일을 직접 실행
- 실행 중인 Python 프로세스 관리
- 실행 중인 스크립트 종료
- 현재 실행 중인 스크립트 목록 확인
- `/hostpy` 명령어 탭 자동완성 지원
- 서버 종료 시 실행 중인 Python 프로세스 정리

### 🔄 양방향 신호 통신

파일 기반 신호 시스템을 통해 Minecraft와 Python 사이에서 데이터를 주고받을 수 있습니다.

```text
Minecraft / Skript
        │
        ▼
signals/to-python/
        │
        ▼
     Python
        │
        ▼
signals/to-server/
        │
        ▼
Minecraft / Skript
```

- Minecraft → Python 신호 전송
- Python → Minecraft 신호 전송
- UTF-8 문자열 지원
- Python → Minecraft 방향은 파일 생성 감시 방식으로 자동 감지
- 신호 처리 후 해당 신호 파일 자동 삭제
- UUID 기반 파일명으로 신호 충돌 방지

## 명령어

| 명령어 | 기능 |
|---|---|
| `/hostpy start <파일명>` | Python 스크립트 실행 |
| `/hostpy stop <파일명>` | 실행 중인 Python 스크립트 종료 |
| `/hostpy list` | 현재 실행 중인 스크립트 목록 확인 |
| `/hostpy` | 도움말 표시 |

`<파일명>`은 `.py` 확장자를 생략할 수 있습니다.

예:

```text
/hostpy start bot
/hostpy stop bot
```

## Python 스크립트

스크립트는 다음 위치에 저장됩니다.

```text
plugins/HostPY/scripts/
```

예:

```text
plugins/HostPY/
└─ scripts/
   ├─ bot.py
   ├─ server.py
   └─ example.py
```

HostPY는 기본적으로 시스템의 `python` 실행 명령을 사용하여 스크립트를 실행합니다.

## 신호 시스템

HostPY 데이터 폴더의 신호 디렉터리는 다음과 같이 구성됩니다.

```text
plugins/HostPY/
└─ signals/
   ├─ to-python/
   └─ to-server/
```

### Minecraft → Python

Minecraft 또는 Skript에서 신호를 보내면 `to-python`에 신호 파일이 생성됩니다.

```text
plugins/HostPY/signals/to-python/
└─ signal-xxxxxxxx-xxxx-xxxx-xxxx-xxxxxxxxxxxx.txt
```

Python 프로그램은 해당 폴더를 감시하여 신호를 읽을 수 있습니다.

### Python → Minecraft

Python에서 `to-server`에 파일을 생성하면 HostPY가 이를 감지하고 Minecraft 이벤트로 전달합니다.

```text
plugins/HostPY/signals/to-server/
└─ bot.py
```

신호 내용은 UTF-8 문자열 그대로 전달됩니다.

## Skript 연동

Skript가 설치되어 있는 경우 HostPY가 자동으로 Skript 연동 기능을 활성화합니다.

### Minecraft → Python

```skript
send hostpy signal "hello"
```

문자열을 Python 방향으로 전달할 수 있습니다.

### Python → Minecraft

```skript
on hostpy signal received:
    broadcast "받은 신호: %event-string%"
```

Python에서 전달된 신호를 Skript 이벤트로 받을 수 있습니다.

`%event-string%`에는 전달된 신호 내용이 들어갑니다.

## Bukkit 이벤트

다른 플러그인에서도 Python → Minecraft 신호를 이벤트로 처리할 수 있습니다.

```java
HostPYSignalEvent
```

이벤트에서 제공되는 정보:

- `getSignal()` — 전달된 신호 내용
- `getSourceScript()` — 신호 파일 이름

## 권한

```text
hostpy.admin
```

`/hostpy` 명령어 사용에 필요한 권한입니다.

기본값은 OP입니다.

## 요구 사항

- Minecraft Paper 1.21.x 계열
- Java 21
- Python
- Skript 연동 기능 사용 시 Skript 설치 필요

Skript가 설치되어 있지 않아도 Python 실행 및 기본 신호 기능은 사용할 수 있습니다.

## 라이선스

프로젝트의 라이선스 정책에 따릅니다.
