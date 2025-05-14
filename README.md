# 일정 관리 앱

# 📚 프로젝트 소개
Spring Boot를 기반으로 일정 관리 시스템을 개발하면서 기본적인 CRUD 기능 구현을 학습하기 위한 프로젝트 

# ⏰ 프로젝트 기간

- 2025.05.07 ~ 2025.05.14

# 💻 개발 환경
- JDK : Java - 17.0.1
- Framework : Spring Boot - 3.4.5
- IDE : IntelliJ IDEA
- Build : Gradle
- Database : MySQL
- DB 연동방식 : JDBC

# 📘 Schedule API 명세서

| 기능           | Method  | URL        | request                                                         | response                                                                                                                                       | 상태코드 |
|--------------|---------|------------|-----------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------|------|
| **일정등록**     | `POST`  | /schedules | {"name": "제목","password": "비밀번호","contents": "내용",}             | { "id": 1, "name": "제목", "password": "비밀번호", "contents": "내용", "createdAt": "2025-05-12T18:43:36", "updatedAt": "2025-05-12T18:43:36"  }       | 201 Created
| **일정 전체 조회** | `GET`   | /schedules |                                                                 | { "id": 1, "name": "제목", "password": "비밀번호", "contents": "내용", "createdAt": "2025-05-12T18:43:36", "updatedAt": "2025-05-12T18:43:36"  }       | 200 OK
| **일정 단건 조회** | `GET`   | /schedules/{id} |                                                                 | { "id": 1, "name": "제목", "password": "비밀번호", "contents": "내용", "createdAt": "2025-05-12T18:43:36", "updatedAt": "2025-05-12T18:43:36"  }       | 200 OK
| **일정 수정**    | `PATCH` | /schedules/{id} | { "name" : "수정 제목", "contents" : "수정 내용", "password" : "비밀번호" } | { "id": 1, "name": "수정 제목", "password": "비밀번호", "contents": "수정 내용", "createdAt": "2025-05-12T18:43:36", "updatedAt": "2025-06-12T09:43:36"  } | 200 OK
| **일정 삭제**    | `DELETE` |/schedules/{id} | { "password" : "1234" } |  | 200 OK

# 📘 ERD
![ERD](./images/ERD.png)

## 1.일정 등록
### 1-1. Request
- Method : POST
- URL : /schedules
```json
{
    "name" : "제목",
    "contents" : "내용",
    "password" : "12312",
    "authorId" : 2
}
```
- 1-2. Response(201 Created)

```json
{
    "id": 61,
    "name": "제목",
    "password": "12312",
    "contents": "내용",
    "createdAt": "2025-05-13T21:10:10.646622",
    "updatedAt": "2025-05-13T21:10:10.646639",
    "author": {
        "id": 2,
        "email": "dmsdnr222@naver.com",
        "name": "안은욱12",
        "createdAt": "2025-05-12T17:24:03",
        "updatedAt": "2025-05-12T17:24:03"
    }
}
```

## 2.일정 전체 조회
### 2-1. Response (200 OK)
- Method : GET
- URL : /schedules
```json
[
    {
        "id": 61,
        "name": "제목",
        "password": "12312",
        "contents": "내용",
        "createdAt": "2025-05-13T21:10:11",
        "updatedAt": "2025-05-13T21:10:11",
        "author": {
            "id": 2,
            "email": "dmsdnr222@naver.com",
            "name": "안은욱12",
            "createdAt": "2025-05-12T17:24:03",
            "updatedAt": "2025-05-12T17:24:03"
        }
    },
    {
        "id": 60,
        "name": "제목",
        "password": "12312",
        "contents": "내용",
        "createdAt": "2025-05-13T21:09:28",
        "updatedAt": "2025-05-13T21:09:28",
        "author": {
            "id": 2,
            "email": "dmsdnr222@naver.com",
            "name": "안은욱12",
            "createdAt": "2025-05-12T17:24:03",
            "updatedAt": "2025-05-12T17:24:03"
        }
    },
```

## 3. 일정 단건 조회 (200 OK)
### 3-1. Response
- Method : GET
- URL : /schedules/id
```
{
    "id": 59,
    "name": "제목",
    "password": "비밀번호",
    "contents": "내용",
    "createdAt": "2025-05-13T21:00:05",
    "updatedAt": "2025-05-13T21:00:05",
    "author": {
        "id": 2,
        "email": "dmsdnr222@naver.com",
        "name": "안은욱12",
        "createdAt": "2025-05-12T17:24:03",
        "updatedAt": "2025-05-12T17:24:03"
    }
}
```

## 4. 일정 수정
### 4-1. Request
- Method : PATCH
- URL : /schedules/id
```
{
    "name" : "수정제목",
    "contents" : "수정내용",
    "password" : "비밀번호"
}
```

### 4-2. Response (200 OK)
```
{
    "id": 62,
    "name": "수정제목",
    "password": "비밀번호",
    "contents": "수정내용",
    "createdAt": "2025-05-14T10:32:55",
    "updatedAt": "2025-05-14T10:33:33",
    "author": {
        "id": 2,
        "email": "dmsdnr222@naver.com",
        "name": "안은욱12",
        "createdAt": "2025-05-12T17:24:03",
        "updatedAt": "2025-05-12T17:24:03"
    }
}
```

## 5. 일정 삭제
### 5-1. Request
```
{
    "password" : "비밀번호"
}
```

### 5-2. Response (200 OK)
```
{
    
}
```