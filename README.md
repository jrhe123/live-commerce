# live-commerce

Live commerce platform

## User flow

```
--------------------------------------------[wallet]---------(send / receive 💰)----------------------
|                                                                                                    |
|                                                                                                    |
|                                                                    login ----------                |
|                                                                      ^            |                |
|                                                                      |            |                |
|                                                                     "NO"          |         --- Donate ---
|                                                                      |            |        |              |
👤 -----> live platform -----> live show -----> select live -----> [logged in]   enter show - |               | -----> exit
           home page             list              show                            room       |              |          |
               ^                                                                                ---  Chat  ---          |
               |                                                                                                        |
               |                                                                                                        |
                ---------------------------------------------------------------------------------------------------------

```

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
- Spring Cloud Alibaba (we used in this project)
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

```
-----------------------------------------------------------------------------
  Framework                 Spring Cloud Netflix     Spring Cloud Alibaba
-----------------------------------------------------------------------------
|                         |                         |                       |
| Gateway                 |         Zuul            |        Gateway        |
|                         |                         |                       |
|----------------------------------------------------------------------------
|                         |                         |                       |
| Service Registry        | Eureka,Consul,ZooKeeper |         Nacos         |
|                         |                         |                       |
-----------------------------------------------------------------------------
|                         |                         |                       |
| Configuration           |   Spring Cloud Config   |      Nacos, Apollo    |
|                         |                         |                       |
-----------------------------------------------------------------------------
|                         |                         |                       |
| Web Service             |         Feign           |         Dubbo         |
|                         |                         |                       |
-----------------------------------------------------------------------------
|                         |                         |                       |
| Fuse                    |        Hystrix          |        Sentinel       |
|                         |                         |                       |
-----------------------------------------------------------------------------
|                         |                         |                       |
| Tracing                 |      Sleuth,Zipkin      |       Skywalking      |
|                         |                         |                       |
-----------------------------------------------------------------------------
```
