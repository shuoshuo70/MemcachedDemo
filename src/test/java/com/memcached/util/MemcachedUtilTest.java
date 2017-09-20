package com.memcached.util;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by shuoshuo on 2017/9/20.
 */
public class MemCachedUtilTest {
    @Test
    public void test() {
        boolean res = MemCachedUtil.set("hello", "world", 60);
        Assert.assertTrue(res);
        String value = (String) MemCachedUtil.get("hello");
        Assert.assertEquals(value, "world");

        MemCachedUtil.add("hello", "world1", 60);
        value = (String) MemCachedUtil.get("hello");
        Assert.assertEquals(value, "world1");

        MemCachedUtil.replace("hello", "world1", 60);
        value = (String) MemCachedUtil.get("hello");
        Assert.assertEquals(value, "world1");
    }
}
