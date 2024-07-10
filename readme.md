Spring Redis 샘플 소스

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



