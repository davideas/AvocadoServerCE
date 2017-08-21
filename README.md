[![API](https://img.shields.io/badge/SpringBoot2-M3-green.svg?style=flat)](https://github.com/spring-projects/spring-boot/wiki)
[![Licence](https://img.shields.io/badge/Licence-Apache2-blue.svg)](http://www.apache.org/licenses/LICENSE-2.0)

# Avocado Server CE (Community Edition)
###### An opinionated setup for a modern RESTful Server
My architect experience and passion for Java into this project to experiment the new modern technologies.
Instead of configuring everything from scratch we can just import or take inspiration from the features of this project.

> Computer Science is experimentation, automation and engineering (...).

So those Apps are also a playground to experiment new frameworks and new features.

# Requirements
- Java 8
- Gradle 4.x
- Maven 3.x
- MySql 5.7.x
- Tomcat 8.5.x
- Suggested IDE: _IntelliJ IDEA™_

# Features
**Note:** _All features are under deep analysis with continuous development; Features will be added step by step._

- [Clean Architecture](https://8thlight.com/blog/uncle-bob/2012/08/13/the-clean-architecture.html) by Uncle Bob
- API (Key generation or Token generation with JWT) - **Not yet started**
- [Spring Boot 2](https://github.com/spring-projects/spring-boot/wiki) & [Spring 5](https://github.com/spring-projects/spring-framework)
- RESTful web services with Spring - **Not fully done**
- CORS filter - **Not started**
- Logging filter and query stats with Spring AOP
- Exception handler advice
- Logging via [slf4j](https://www.slf4j.org/) API + [Log4j2](https://logging.apache.org/log4j/2.x/) with gzip and rolling strategy
- [MyBatis 3](http://www.mybatis.org/mybatis-3) SQL Mapping Framework for Java + Integration with Spring Boot
- [FlyWay](https://flywaydb.org/) Database schema migrations made easy across all environments
- [jBCrypt](http://www.mindrot.org/projects/jBCrypt) One-Way Encryption
- [Firebase](https://github.com/firebase/quickstart-android) Messaging with Notification manager! - **Not started**
- [JUnit 5](http://junit.org/junit5/) Jupiter - **Not fully done**
- [GrabVer](https://github.com/davideas/grabver) Gradle Automatic Build Versioning - **Not yet applied**
- SQL scripts for demo database schema
- Environment configuration files (local-acc-prod)
- Integration with IDE: Deployment and JUnit tests can be run via command line and via IDE!
- Maven & Gradle build tools (see [Gradle vs Maven: Feature Comparison Chart](https://gradle.org/maven-vs-gradle/))


# Contributions
Everybody is welcome to improve existing solutions, to experiment basic, new and specific features and frameworks.

# License

    Copyright 2017 Davide Steduto

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.