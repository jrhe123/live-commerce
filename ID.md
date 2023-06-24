# live-commerce

Live commerce platform

## ID generator

#### Why we use custom generator? (🦄)

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
    - no sorting
    - not good with mysql search
- redis id: incr / increby
  - pros
    - no DB dependency
    - number id sorting
    - performance
  - cons
    - not support non-continous id
    - store in redis, risk of loss
- MySQL id: auto_increment
  - pros
    - sorting
  - cons
    - DB dependency
    - high cost, low performace
- Twitter Snowflake (ZooKepper to generate global ID)
  - pros
    - performace
    - low latency
    - sorting
  - cons
    - standalone deploy
    - machine clock dependency
- UidGenerator (Baidu)
- Leaf (Meitun)
