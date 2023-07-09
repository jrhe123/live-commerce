# live-commerce

Live commerce platform

## RPC

```
  [client]                  [server]

     |                         |
                network
[client stub]   <----->    [server stub]
```

#### Protocol

- gRPC(Http)
- Dubbo(Tcp)

#### Serialize

- Hessian
- Json
- Xml
- Protobuf

## RPC vs. Http

- custom protocol, e.g, no request header
- smaller size
- long connection (same in http2)
