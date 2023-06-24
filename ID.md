# live-commerce

Live commerce platform

## ID generator

#### Why we use custom generator? (🦄)

```
                           (check if reach threadshold) --------Yes---------
                                        ^                                   |
                                        |                                   |
                                        |                                   |
                                  -------------                             |
                                 |             |     (sync id to RAM)       |
[client] -----Dubbo request------|    [RAM]    | <----------------------- MySQL
                                 |     id      |
                                  -------------
                                  Dobbo provider
```

- Long-term maintainence
- Dubbo & RAM & MySQL

#### Other solutions

- uuid
  - pros
    - local
    - easy
    - performance
    - no re-useable risk
  - cons
    - too long
    - no sequential
    - not good with mysql search
- redis id: incr / increby
  - pros
    - no DB dependency
    - number id sequential
    - performance
  - cons
    - not support non-continous id
    - store in redis, risk of loss
- MySQL id: auto_increment
  - pros
    - sequential
  - cons
    - DB dependency
    - high cost, low performace
- Twitter Snowflake (ZooKepper to generate global ID)
  - pros
    - performace
    - low latency
    - sequential
  - cons
    - standalone deploy
    - machine clock dependency
- UidGenerator (Baidu)
- Leaf (Meitun)
