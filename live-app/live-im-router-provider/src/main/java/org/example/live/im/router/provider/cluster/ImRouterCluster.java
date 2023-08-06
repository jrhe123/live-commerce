package org.example.live.im.router.provider.cluster;

import org.apache.dubbo.rpc.Invoker;
import org.apache.dubbo.rpc.RpcException;
import org.apache.dubbo.rpc.cluster.Cluster;
import org.apache.dubbo.rpc.cluster.Directory;

/**
 * 
 * router 自定义 cluster
 * 
 * @author jiaronghe
 *
 */
public class ImRouterCluster implements Cluster {

    @Override
    public <T> Invoker<T> join(
    		Directory<T> directory, boolean buildFilterChain) 
    				throws RpcException {
    	/**
    	 * 
    	 * based on context to find the matched netty server (ip addr)
    	 * 
    	 */
    	// 自定义cluster invoker
        return new ImRouterClusterInvoker<>(directory);
    }
}