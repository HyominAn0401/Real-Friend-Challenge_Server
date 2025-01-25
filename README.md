# Real-Friend-Challenge
👩‍🏫 [찐친고사] 친구들과 함께 우정을 테스트하는 퀴즈 플랫폼

<br/>

**찐친고사**는 친구들과 함께 우정을 테스트하는 퀴즈 플랫폼입니다. 연말을 마무리하고 새해를 맞이하며, 서로를 얼마나 잘 알고 있는지 확인할 수 있는 재미있는 경험을 제공합니다.


**기획 의도**:
- 친구들과의 관계를 더욱 돈독하게 만들 수 있는 인터랙티브한 퀴즈 콘텐츠 제공
- 개인화된 질문을 활용한 맞춤형 퀴즈 생성 기능 추가
- 퀴즈 결과를 바탕으로 점수 기반 랭킹 및 모의고사 성적표 제공
- 카카오톡을 활용한 간편한 공유 기능을 통해 접근성 극대화

### Team Member

| 안효민 | 김지수 | 최은소 | 박채연 | 김소연 |
| :---: | :---: | :---: | :---: | :---: |
| Back-end | Back-end | PL, 디자인, FE, Infra | 디자인, Infra | Infra |
| <img src="https://avatars.githubusercontent.com/u/98948416?v=4" alt="안효민 프로필" width="180" height="180"> | <img src="https://avatars.githubusercontent.com/u/86948824?v=4" alt="김지수 프로필" width="180" height="180"> | <img src="https://avatars.githubusercontent.com/u/93801149?v=4" alt="최은소 프로필" width="180" height="180"> | <img src="https://avatars.githubusercontent.com/u/61193581?v=4" alt="박채연 프로필" width="180" height="180"> | <img src="https://avatars.githubusercontent.com/u/61140818?v=4" alt="김소연 프로필" width="180" height="180"> |
| [HyominAn0401](https://github.com/HyominAn0401) | [Jixoo-IT](https://github.com/Jixoo-IT) | [esc-beep](https://github.com/esc-beep) | [Yeon-chae](https://github.com/Yeon-chae) | [thdusqpf](https://github.com/thdusqpf) |


### 개발 기간
2024.12.01 - 2025.01.14

### 활용 기술 및 도구

- **Frontend**: React, TypeScript, React Router, Axios
- **Backend**: JAVA, Springboot, RDS, MySQL
- **Infra**: EC2, ASG, CodeDeploy, github actions, ALB, Route53
- **Collaboration**: GitHub, Notion, Discord
---

<br/>

## 실행 화면 

|                                                                 퀴즈 생성                                                                  |                                                                 문제 풀이                                                                 | 문제 생성 |
|:--------------------------------------------------------------------------------------------------------------------------------------:|:-------------------------------------------------------------------------------------------------------------------------------------:| :---: |
| <img src="https://github.com/nunsongCookie/CookieSong-Backend/blob/main/src/main/resources/images/make_quiz.jpg?raw=true" width="300"> |<img src="https://github.com/nunsongCookie/CookieSong-Backend/blob/main/src/main/resources/images/start_solve.jpg?raw=true" width=300> | <img src="https://github.com/nunsongCookie/CookieSong-Backend/blob/main/src/main/resources/images/make_question.png?raw=true" width=300>
| 정오표 | 등수 조회 | 카카오톡 공유 |
| <img src="https://github.com/nunsongCookie/CookieSong-Backend/blob/main/src/main/resources/images/grade.jpg?raw=true" width=300> | <img src="https://github.com/nunsongCookie/CookieSong-Backend/blob/main/src/main/resources/images/rank.jpg?raw=true" width=300> | <img src="https://github.com/nunsongCookie/CookieSong-Backend/blob/main/src/main/resources/images/share_kakaotalk.jpg?raw=true" width=300> |

<br/>

## Back-end 주요 기능
⛓️ JAVA, Springboot, RDS, MySQL
<br></br>

### Sharekey - UUID 적용으로 URL 공유 보안 강화

#### 1. 개요
기존에는 `quizId`를 사용해 퀴즈를 공유하였으나, 이는 보안성이 낮고 URL을 통해 데이터가 유추될 가능성이 있음을 테스트 배포 후 확인하였다. 이를 해결하기 위해 **UUID 기반의 `shareKey`** 를 도입하여 공유 링크의 보안성을 강화하였다.

- **기존 방식**: 퀴즈 공유 시, `quizId`가 URL에 포함되었음  
  ex) `https://examready2025.site/quiz/1`
- **수정 후**: `quizId` 대신 UUID로 생성된 `shareKey`를 활용  
  ex) `https://examready2025.site/quiz/aa12d1c9-c12b-4a82-82a0-10f8a0939c48`

<br/>

#### 2. Entity 수정
기존 `Quiz` 엔티티에 `shareKey` 필드를 추가하고, UUID를 생성하여 기본값으로 설정하였다.
- @PrePersist: 새로운 퀴즈 생성 시, shareKey가 UUID 형식으로 자동으로 생성되도록 함
```java
@Entity
@Getter
@NoArgsConstructor
@Table(name = "quiz")
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 36)
    private String shareKey; // 난수 필드 추가

    @PrePersist
    private void initializeShareKey() {
        if (this.shareKey == null) {
            this.shareKey = UUID.randomUUID().toString();
        }
    }
}
```

<br/>

#### 3. Controller 수정

`quizId` 대신 `shareKey`를 활용하여 퀴즈 데이터를 처리하도록 수정하였다.

### 공유 URL 생성 API
- **요청**: `POST /api/quizzes/share`
- **응답**: `shareKey`를 포함한 URL 반환

```java
@PostMapping("/api/quizzes/share")
public ResponseEntity<ShareResponseDto> shareQuiz(@RequestBody ShareRequestDto requestDto) {
    ShareResponseDto responseDto = shareService.generateShareUrl(requestDto);
    return ResponseEntity.status(HttpStatus.OK).body(responseDto);
}
```

```json
{
    "shareUrl": "https://examready2025.site/quiz/aa12d1c9-c12b-4a82-82a0-10f8a0939c48"
}
```
<br/>

#### 4. Service 수정

기존 quizId 기반 조회 로직을 shareKey 기반으로 변경하였다.

```java
public ResultDto getResults(String shareKey) {
    Quiz quiz = quizRepository.findByShareKey(shareKey)
            .orElseThrow(() -> new IllegalArgumentException("해당 공유 키로 퀴즈를 찾을 수 없습니다."));

    List<Response> responses = responseRepository.findByQuizIdAndIsCompletedTrue(quiz.getId());
    // 이후 로직은 동일
}
```
<br/>

## 🔑 UUID 도입 효과

- **보안 강화**: `quizId` 대신 난수 `shareKey`를 사용하여 데이터 유추 방지
- **직관적 공유**: URL에 민감한 정보 대신 UUID 포함으로 간편한 공유
- **확장성**: 향후 다양한 공유 기능 추가에 용이

<br/>

## 프로젝트 개요

### 1️⃣ 기획 의도
- 친구들과의 관계를 더욱 돈독하게 만들 수 있는 인터랙티브한 퀴즈 콘텐츠 제공
- 개인화된 질문을 활용한 맞춤형 퀴즈 생성 기능 추가
- 퀴즈 결과를 바탕으로 점수 기반 랭킹 및 모의고사 성적표 제공
- 카카오톡을 활용한 간편한 공유 기능을 통해 접근성 극대화

<br/>

### 2️⃣ 기능 소개
1. **우정 테스트**
   - 3지선다 객관식 퀴즈로 친구들과의 친밀도를 확인
   - "OO님의 올해 가장 비싼 소비는?" 같은 개인화된 질문 제공

2. **모의고사 생성**
   - 정해진 5~10개의 질문 유형을 바탕으로 나만의 답변을 작성하여 퀴즈 생성
   - 랜덤 선택지가 아닌, 사용자가 직접 입력한 정답을 포함한 3지선다 객관식 문제 생성

3. **결과 공개**
   - 점수 기반 랭킹 및 모의고사 성적표 제공

4. **카카오톡 공유 기능**
   - 생성된 퀴즈를 카카오톡으로 친구들에게 공유하여 쉽게 풀도록 유도
   - 퀴즈 결과도 간편하게 공유 가능


<br/>

### 3️⃣ 기대효과
#### 📱 [기술적 기대효과]
1. **백엔드 개발 능력 향상**
   - DB연동, REST API 설계, 비즈니스 로직 설계 등 심화 기술력 및 문제 해결 능력 향상

2. **실제 서비스와의 유사성**
   - 실서비스와 유사한 기능 개발해 사용자 경험을 고려한 설계 및 개발 능력 향상
   - 프로젝트 홍보 및 테스트 과정에서 다수 사용자 유입 상황 대응 능력 강화


#### 🧩 [사회적 기대효과]
1. **우정 확인 및 추억 공유**
   - 친구들과의 관계를 점검하고, 퀴즈를 통해 서로에 대해 더 많이 알 수 있음
   - 새해를 맞아 우정을 되돌아보는 기회를 제공

2. **손쉬운 참여 및 공유**
   - 카카오톡을 활용한 공유 기능으로 접근성을 높이고, 누구나 쉽게 참여 가능
   - 퀴즈 생성과 결과 확인이 직관적으로 이루어짐

3. **커뮤니티 활성화**
   - 퀴즈 결과를 공유하면서 새로운 대화 소재로 활용 가능
   - 연말 모임 및 친목 활동에서 활용할 수 있는 재미있는 콘텐츠 제공

<br/>

### 4️⃣ 프로젝트 수행결과
- 유저 약 400명 이용
- 사용자 피드백을 통한 UI 개선

<br/>

### 5️⃣ 프로젝트 구조

#### ERD
![쿠키송ERD.png](https://github.com/nunsongCookie/.github/blob/main/assets/%EC%BF%A0%ED%82%A4%EC%86%A1ERD.png?raw=true)

#### 아키텍처
![쿠키송아키텍처.png](https://raw.githubusercontent.com/nunsongCookie/.github/refs/heads/main/assets/%EC%BF%A0%ED%82%A4%EC%86%A1%EC%95%84%ED%82%A4%ED%85%8D%EC%B2%98.png)
