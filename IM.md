# live-commerce

Live commerce platform

## IM - instant messaging

#### Let's build the IM server (🦄)

- netty
- RocketMQ

#### Test case

- modules
  - live-im-core-server:test (im-client) !!! send BIZ message !!!
  - live-im-core-server (im-server) !!! send message to MQ producer !!!
  - live-msg-provider (MQ consumer) !!! pull it from broker !!!

#### How to use junit to test Netty

- https://blog.51cto.com/u_16175439/6671294

#### TCP vs WebSocket

- TCP (native) has better performace than WebSocket ~ 10times faster
- App (preferred TCP)
- Web App (WebSocket)

#### Online

![Copy of Copy of Copy of Untitled Diagram drawio](https://github.com/jrhe123/live-commerce/assets/17329299/96a28ca9-8921-4ec7-a219-921f091e5966)

![Copy of Copy of Copy of Untitled Diagram drawio (2)](https://github.com/jrhe123/live-commerce/assets/17329299/be02c3e8-3b6f-44ec-9be8-fe658cd4ba1e)

#### Offline

![Copy of Copy of Untitled Diagram drawio (4)](https://github.com/jrhe123/live-commerce/assets/17329299/2ba21d98-34c8-437d-8574-507ca2769023)

#### Continue reading.. (🦊)

- Why not use GoLang to create IM server?
