package org.example.live.im.router.provider.cluster;

import java.util.List;

import org.apache.dubbo.rpc.Invocation;
import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.Result;
import org.apache.dubbo.rpc.RpcContext;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.rpc.cluster.Directory;
import org.apache.dubbo.rpc.cluster.LoadBalance;
import org.apache.dubbo.rpc.cluster.support.AbstractClusterInvoker;

import com.alibaba.cloud.commons.lang.StringUtils;

public class ImRouterClusterInvoker<T> extends AbstractClusterInvoker<T> {

    public ImRouterClusterInvoker(Directory<T> directory) {
        super(directory);
    }

    @Override
    protected Result doInvoke(
    		Invocation invocation, List list, LoadBalance loadbalance
    		) throws RpcException {
        
    	// parent method
    	checkWhetherDestroyed();
    	
    	
    	// 找回之前设置的ip
    	// check IP address (target IP !!!)
        String ip = (String) RpcContext.getContext().get("ip");
        
        System.out.println("!!! 找回ip: " + ip);
        
        if (StringUtils.isEmpty(ip)) {
            throw new RuntimeException("ip can not be null!");
        }
        
        
        // !!! IMPORTANT !!!
        //获取到指定的rpc服务提供者的所有地址信息
        // 找到nacos 注册中心上一系列的 ip 地址
        List<Invoker<T>> invokers = list(invocation);
        Invoker<T> matchInvoker = invokers.stream().filter(invoker -> {
            //拿到我们服务提供者的暴露地址（ip: 端口的格式）
        	//找到相同的IP address
            String serverIp = invoker.getUrl().getHost() + ":" + invoker.getUrl().getPort();
            
            System.out.println("!!! serverIp: " + serverIp);
            
            return serverIp.equals(ip);
        }).findFirst().orElse(null);
        
        
        // Netty (im 机器) 已经过期 
        if (matchInvoker == null) {
        	// ip addr expired
            throw new RuntimeException("ip is invalid");
        }
        
        
        // !!! 找到matched 的invoker !!!
        return matchInvoker.invoke(invocation);
    }
}
