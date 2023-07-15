1. cd /Users/jiaronghe/Desktop/projects/sts4/live-commerce/live-app/live-user-provider
2. mvn clean
3. mvn install

4. mvn -version
5. mvn clean
6. mvn clean install
7. mvn dependency:tree

- brew install socat # 安装 socat

# unix socket -> tcp port

- nohup socat TCP-LISTEN:2375,range=127.0.0.1/32,reuseaddr,fork UNIX-CLIENT:/var/run/docker.sock &> /dev/null &

- socat TCP-LISTEN:2375,range=127.0.0.1/32,reuseaddr,fork UNIX-CLIENT:/var/run/docker.sock

- socat TCP-LISTEN:2376,reuseaddr,fork,bind=127.0.0.1 UNIX-CLIENT:/var/run/docker.sock

# set env variable

- export DOCKER_HOST=tcp://127.0.0.1:2375

set DOCKER_HOST=tcp://0.0.0.0:2375& mvn clean install

https://hub.docker.com/r/alpine/socat
docker pull alpine/socat

docker run -d --restart=always \
 -p 127.0.0.1:2376:2375 \
 -v /var/run/docker.sock:/var/run/docker.sock \
 alpine/socat \
 tcp-listen:2375,fork,reuseaddr unix-connect:/var/run/docker.sock

mvn clean package
mvn docker:bulid
mvn docker:push -Dmaven.test.skip=true -Dv=1 -f pom.xml

{
"hosts": [ "unix:///var/run/docker.sock","tcp://0.0.0.0:2376"],
"log-driver": "journald",
"signature-verification": false,
}

$ socat -d TCP-LISTEN:2376,range=127.0.0.1/32,reuseaddr,fork UNIX:/var/run/docker.sock

$ curl localhost:2376/version
{"Version":"1.11.2","ApiVersion":"1.23","GitCommit":"56888bf","GoVersion":"go1.5.4","Os":"linux","Arch":"amd64","KernelVersion":"4.4.12-moby","BuildTime":"2016-06-06T23:57:32.306881674+00:00"}

- k8s:
  https://www.xjx100.cn/news/61048.html?action=onClick

docker-machine env

brew install docker-machine

env | grep DOCKER

- k8s:
  https://www.jianshu.com/p/bd8169b5176f

- run the image
- your server: 175.178.130.183
- your private hub registry: registry.baidubce.com

docker run -d -v /tmp/logs/live-user-provider:/tmp/logs/live-user-provider -p 9091:9090 --name live-user-provider-02 --add-host 'live.nacos.com:175.178.130.183' --add-host 'cloud.db:175.178.130.183' --add-host 'live.rmq.com:175.178.130.183' -e TZ=America/Toronto registry.baidubce.com/live-test/live-user-provider-docker:1.0.2

================

- docker-maven-plugin fabric8
  https://dandelioncloud.cn/article/details/1527085260529745921/

<!-- MAC chip -->

- Change the build plugin
- jib-maven-plugin
