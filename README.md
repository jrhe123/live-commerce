# live-commerce

Live commerce platform

## Stack

- DB: MySQL
- Cache: Caffeine, Redis
- Message Queue: Apache RocketMQ
- Container: Docker
- Others: SpringBoot, MyBatisPlus, Dubbo, ShardingJDBC, Nacos, Netty

## Dubbo

#### Why we use Dubbo?

- RPC
- Dynamic setup
- Load balancing: router/group/version && A/B test
- Dobbo-Admin ui
- Fast connect to Sentinel & Nacos

#### Subsitutes

- Spring Cloud
  - Eureka, Ribbon, Feign, Hystrix, Zuul, Archaius
- Spring Cloud Alibaba
  - Spring Cloud Gateway, Nacos, Dubbo, RocketMQ, Sentinel, Seata

```
------------------------------------------------------------------
  Framework                 Service     Plugins     Performance
------------------------------------------------------------------
|                         |           |           |              |
| Dubbo                   |    👍     |    👌      |     👍       |
|                         |           |           |              |
|-----------------------------------------------------------------
|                         |           |           |              |
| Spring Cloud            |    👍     |    👍      |     👌       |
|                         |           |           |              |
------------------------------------------------------------------
|                         |           |           |              |
| Spring Cloud Alibaba    |    👍     |    👍      |     👍       |
|                         |           |           |              |
------------------------------------------------------------------
```
