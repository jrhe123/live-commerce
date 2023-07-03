# live-commerce

Live commerce platform

## Stress Test

- reliability: 99.9% required (99.99% ideally)
- throughput
  - QPS/TPS
  - concurrency: 1000 thread/sec
  - throughput: 500 request/sec (🦄)

#### Run your JMeter

- cd /Users/jiaronghe/Desktop/projects/jmeter/bin
- sh jmeter.sh

#### Test Server: 2 core, 2 gb

- 5 mins & JMeter
- 80 ms - maximum response time
- no docker cpu, ram limit
- result: 1500 QPS (throughput)

```
data:
  redis:
    port: 6379
    host: 127.0.0.1
    password: 123456
    lettuce:
      // stress test
      pool:
        min-idle: 10
        max-active: 100
        max-idle: 10
```

```
#dubbo:
application:
  name: ${spring.application.name}
registry:
  address: nacos://127.0.0.1:8848?namespace=live-test&&username=nacos&&password=nacos
protocol:
  name: dubbo
  port: 9091
  threadpool: fixed
  dispatcher: execution
  threads: 500
  accepts: 500
```

```
Jvm config:

-XX:MetaspaceSize=128m
-XX:MaxMetaspaceSize=128m
-Xms1024m
-Xmx1024m
-Xmn512m
-Xss256k
```
