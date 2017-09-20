package com.memcached.util;


/**
 * Created by shuoshuo on 2017/9/19.
 */

import com.whalin.MemCached.MemCachedClient;
import com.whalin.MemCached.SockIOPool;

import java.util.Map;

public class MemCachedUtil {
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
        pool.setFailover(true);
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

    private MemCachedUtil() {

    }

    public static boolean add(String key, Object value) {
        try {
            return client.add(key, value);
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }

    }

    public static boolean add(String key, Object value, Integer expire) {
        return client.add(key, value, expire);
    }

    public static boolean set(String key, Object value) {
        return client.set(key, value);
    }

    public static boolean set(String key, Object value, Integer expire) {
        return client.set(key, value, expire);
    }

    public static boolean replace(String key, Object value) {
        return client.replace(key, value);
    }

    public static boolean replace(String key, Object value, Integer expire) {
        return client.replace(key, value, expire);
    }

    public static Object get(String key) {
        try {
            return client.get(key);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Map<String, Object> getMulti(String[] keys) {
        try {
            return client.getMulti(keys);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        MemCachedUtil.add("kwq1", "kwq1,kwq");
        MemCachedUtil.add("kwq2", "kwq2,kwq");
        MemCachedUtil.add("kwq3", "kwq3,kwq");
        Map<String, Object> map = MemCachedUtil.getMulti(new String[] { "kwq1",
                "kwq2", "kwq3" });
        System.out.println(map);

        // Object obj = FlightsCacheAPI.getFlights("PEK", "PVG", "2011-06-03");
        // System.out.println(obj);
    }
}

