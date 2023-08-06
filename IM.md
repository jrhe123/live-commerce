# live-commerce

Live commerce platform

## IM - instant messaging

- live-im-provider
- live-im-msg-provider
- live-im-core-server
- live-im-router-provider

![How it works](https://github.com/jrhe123/live-commerce/assets/17329299/3394283e-a719-4c0a-ac36-3f92ea620a5d)

#### Let's build the IM server (🦄)

- netty
- RocketMQ

#### Steps:

1. NettyImServerStarter

- org.example.live.im.core.server.starter.NettyImServerStarter
- org.example.live.im.core.server.handler.ImServerCoreHandler
- org.example.live.im.core.server.handler.impl.ImHandlerFactoryImpl
  - org.example.live.im.core.server.handler.impl.BizImMsgHandler
  - org.example.live.im.core.server.handler.impl.HeartBeatImMsgHandler
  - org.example.live.im.core.server.handler.impl.LoginMsgHandler
  - org.example.live.im.core.server.handler.impl.LogoutMsgHandler

2. ImMsg

- org.example.live.im.core.server.common.ImMsg
- org.example.live.im.core.server.common.ImMsgEncoder
- org.example.live.im.core.server.common.ImMsgDecoder

3. ImClientApplication

- com.example.live.im.core.server.test.imclient.ImClientApplication
  - com.example.live.im.core.server.test.imclient.handler.ImClientHandler (init test client)
  - com.example.live.im.core.server.test.imclient.handler.ClientHandler (server 返回信息 handler)

4. MQ: ImMsgConsumer

- org.example.live.msg.provider.consumer.ImMsgConsumer
- org.example.live.msg.provider.consumer.handler.impl.MessageHandlerImpl (onMsgReceive 消化消息)

5. Router for IM

- A 发信息给 B
- A -> im-core-server -> msg-provider(MQ 持久化) -> im-core-server -> 通知到 B
- 怎么知道 B 在哪台 im server???
- 引入 router 就是一个 dubbo 的 rpc 层
  - ImCoreServerApplication: enable dubbo
  - 利用 im-router-provider 转发回对应的 im-core-server (即 dubbo rpc)
  - org.example.live.im.router.provider.service.impl.ImRouterServiceImpl
    - 绑定 ip, 找到正确的 im-core-server
  - 实现 cluster (找到 nacos 注册中心上一系列的 ip 地址)
    - org.example.live.im.router.provider.cluster.ImRouterCluster
    - org.example.live.im.router.provider.cluster.ImRouterClusterInvoker<T>

#### Test case 1

- start these modules first (others)

  - live-im-provider (token rpc)
  - live-msg-provider (rocketMQ) application 初始化时候启动 MQ
    - org.example.live.framework.mq.starter.producer.RocketMQProducerConfig

- 测试 im 连接 MQ 流程 (biz message)
  - modules
  - live-im-core-server:test (im-client) !!! send BIZ message !!!
  - live-im-core-server (im-server) !!! send message to MQ producer !!!
  - live-msg-provider (MQ consumer) !!! pull it from broker !!!

#### Test case 2

- 测试发送消息, 根据 router(ip), 能否找回正确的 im-core-server
  - ImRouterProviderApplication

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
