# live-commerce

Live commerce platform

## Gateway

#### Why we need api gateway

- load balancing
- Blue Green Deployment
- Canary Deployment
- A/B testing

![Copy of Copy of Untitled Diagram drawio](https://github.com/jrhe123/live-commerce/assets/17329299/25d85c5f-f0df-4b61-bfb4-cd1a2d367356)

#### Let's connect it with nacos

- use http in local
- use lb for docker services

```
live-gateway.yaml

spring:
  cloud:
    gateway:
      discovery:
        locator:
          enabled: true
      routes:
      - id: live-api
        # uri: lb://live-api
        uri: http://localhost:8081
        predicates:
        - Path=/live/api/**

dubbo:
  application:
    name: ${spring.application.name}
    qos-enable: false
  registry:
    address: nacos://127.0.0.1:8848?namespace=live-test&&username=nacos&&password=nacos

logging:
  level:
    org.springframework.cloud.gateway: INFO
    reactor.netty.http.client: INFO

gateway:
  notCheckUrlList:
    - /live/api/userLogin/
```

```
live-api.yaml

dubbo:
  application:
    name: live-api
    qos-enable: false
  registry:
    address: nacos://127.0.0.1:8848?namespace=live-test&&username=nacos&&password=nacos

server:
  servlet:
    context-path: /live/api

```
