package com.memcached.util;


/**
 * Created by shuoshuo on 2017/9/19.
 */

import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;

public class MemcachedUtil {
    //memcached客户端
    private static MemCachedClient client = new MemCachedClient();

    /**
     * 初始化连接池
     */


    static {
        //获取连接池的实例
        SockIOPool pool = SockIOPool.getInstance();
        String[] servers = {"127.0.0.1:11211"};
        Integer[] weights = {3};
        //设置服务器信息
        pool.setServers(servers);
        pool.setWeights(weights);
        //设置初始最小、最大、初始连接数和最大空闲时间
        pool.setMinConn(10);
        pool.setMaxConn(1000);
        pool.setInitConn(10);
        pool.setMaxIdle(1000*60*60);
        //设置守护进程的睡眠时间
        pool.setMaintSleep(60);
        //设置TCP参数，连接超时
        pool.setNagle(false);
        pool.setSocketTO(60);
        pool.setSocketConnectTO(0);

        //初始化并启动连接池
        pool.initialize();

    }
}

