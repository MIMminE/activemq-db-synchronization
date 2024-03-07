**ActiveMQ 데이터베이스 동기화 **

**< 활용 기술 & 컨셉 >**
- ActiveMQ artemis Topic 기반의 데이터베이스 테이블 동기화 목적
- MariaDB와 ActiveMQ 서버 -> Docker 컨테이너 기동
- mvn 프로파일 활용한 dev, test, prod 환경 분리
- 멀티스레드로 데이터베이스 row-level 트랜잭션별 처리
- jobs 설정 파일 형식을 응용한 테이블별 개별 설정 지원(스레드 수, 인터벌 타임...)
- Github Action 기반의 테스트, CI 활용
