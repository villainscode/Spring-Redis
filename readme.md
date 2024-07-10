# Spring Redis 샘플 소스
> 본 내용은 한빛미디어에서 출간한 책(개발자 기술 면접 노트)의 일부 내용을 보강하기 위해서 만든 Collection Framework 가이드 입니다.<br>
> 책의 분량상 코드나 이론등의 내용을 다 담아내지 못하였기에 문서로 추가적인 설명을 진행합니다.<br>
> 설명에서는 타이핑을 줄이기 위해서 존대를 생략했으니 양해 바랍니다.<br>
> 만약 내용에 문제가 있거나 오/탈자가 있을 경우 villainscode@gmail.com으로 메일 부탁드립니다.
>
>
> - Instagram - [https://www.instagram.com/codevillains](https://www.instagram.com/codevillains)
> - email - [villainscode@gmail.com](mailto:villainscode@gmail.com)
> - Yes24 - https://www.yes24.com/Product/Goods/125554439
> - KyoboBooks - https://product.kyobobook.co.kr/detail/S000212738756

## 책 소개
### [연봉의 앞자리를 바꾸는] 개발자 기술면접 노트
- 2024.03.25, 한빛미디어, 이남희(codevillain) 저

<img src="https://github.com/villainscode/collections/blob/main/image/book_600.png?raw=true" width="400">

> **신입 및 주니어 개발직군의 취업 및 이직을 위한 가이드**
>
> 서류 작성 방법부터 , 유망한 회사를 찾는 방법, 코딩 테스트와 기술 면접을 준비하기 위해 알아야 할 개념들을 어떤 방식으로 접근해야 하는지 설명했습니다.
>
> 특히 면접에서 면접관이 던지는 질문의 의도와 해당 질문에 올바르게 답변하기 위한 실질적인 대처방법에 대해서 기술하였습니다.
> 아울러 기술면접을 넘어 인성면접에서 가져야할 마음가짐과 리더십 원칙, 정답이 없는 질문에 대처하기 위한 사례들을 소개하였습니다.
>
> 취업 및 이직의 상황에서 본인이 준비하고 있는 방향이 맞는지 점검하고 커리어 패스를 어떻게 설계해야 하는지 실천 가능한 방법을 제시함으로서 도움을 주고자 하였습니다.


## 프로젝트 설정 
1. 빈 github project repository 생성  
2. 로컬 clone 후 Springboot gradle 프로젝트 생성 (Spring-Redis) 
3. root(Spring-Redis)에서 new > module...선택 
4. Java project 선택 후 프로젝트 네임 입력, Gradle, Groovy 선택 후 Create 클릭
5. root의 src 폴더는 필요 없으므로 삭제
6. 이와 같은 형태로 여러 프로젝트 하위에 멀티 모듈 형태로 생성할 수 있으며, 연관관계를 따로 맺지 않고 개별 프로젝트로 사용함 

![project생성](./image/redis-create.png)

## 프로젝트 구조 
<img src="./image/redis-project.png" width="550"  />

> redis-sample : Spring startup시 간단한 메시지 출력 예제 
> 
> redis-crud : redis를 이용한 CRUD 활용 예제
> 
> redis-pub/sub : message publish/subscribe 예제 
> 

root의 build.gradle은 아래와 같다. 하위 프로젝트들은 root의 설정에 의존성을 갖는다.
```groovy
plugins {
    id 'java'
    id 'org.springframework.boot' version '3.3.1'
    id 'io.spring.dependency-management' version '1.1.5'
}

group = 'com.villainscode'
version = '0.0.1-SNAPSHOT'

java {
    sourceCompatibility = '17'
}

configurations {
    compileOnly {
        extendsFrom annotationProcessor
    }
}

// 하위 프로젝트 모듈의 공통 플러그인 및 의존성 정의
subprojects {
    apply plugin: 'java-library'
    apply plugin: 'idea'
    apply plugin: 'org.springframework.boot'
    apply plugin: 'io.spring.dependency-management'
    apply plugin: 'java'
    compileJava.options.encoding = 'UTF-8'
    sourceCompatibility = '17'

    task wrapper(type: Wrapper) {
        gradleVersion = '8.1.1'
    }

    repositories {
        mavenCentral()
    }

    dependencies {
        compileOnly 'org.projectlombok:lombok'
        developmentOnly 'org.springframework.boot:spring-boot-devtools'
        annotationProcessor 'org.springframework.boot:spring-boot-configuration-processor'
        annotationProcessor 'org.projectlombok:lombok'
        testImplementation 'org.springframework.boot:spring-boot-starter-test'
        implementation 'org.springframework.boot:spring-boot-starter-data-redis'
    }


    tasks.named('test') {
        useJUnitPlatform()
    }
}

```
root의 settings.gradle을 아래와 같다. 하위 프로젝트 생성 후 gradle refresh 하면 자동으로 추가된다.
```groovy
rootProject.name = 'Spring-Redis'
include 'redis-sample'
include 'redis-crud'
include 'redis-pub'
include 'redis-sub'
```
각 하위 프로젝트는 해당 프로젝트에서 필요한 모듈의 dependencies들을 각 build.gradle 에 추가해준 후 사용하면 된다.



