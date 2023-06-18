# live-commerce

Live commerce platform

## CRUD

#### Single user search

```
[Search]

   |

[Redis] (Fst / Kryo, "🔑" use string)

   |

[MySQL] (ShardingJDBC)
```

#### Batch users search (Provided two api)

```
[Search]                        [Search]

   |                               |

[Redis] (Redis.multiGet)        [MySQL]

   |

[MySQL] (ShardingJDBC group them at the end)

```

#### Update user

- Question: which approach you're doing in your app?
  - 1. delete Redis, then update MySQL
  - 2. update MySQL, then delete Redis
  - 3. update MySQL, then delete Redis twice (🦄)
    - delay the second redis deletion
      - event driven
      - binlog driven

#### Register user

- 🔑 generation algorithm
