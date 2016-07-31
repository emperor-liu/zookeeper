/**
 * Project Name zookeeper
 * File Name ZookeeperPoolStore.java
 * Package Name com.lljqiu.zookeeper.common.pool
 * Create Time 2016年7月29日
 * Auther：liujie@lljqiu.com
 * Copyright © 2005, 2016, https://www.lljqiu.com. All rights reserved.
 */
package com.lljqiu.zookeeper.common.pool;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;

/** 
 * ClassName: ZookeeperPoolStore.java <br>
 * Description: <br>
 * Auther：liujie@lljqiu.com <br>
 * Create Time: 2016年7月29日<br>
 */
public class ZookeeperPoolStoreTest {

    public static void main(String[] args)
            throws IOException, KeeperException, InterruptedException {
        ZKPoolStore pool = new ZKPoolStore(null);
        pool.createNode("/watherNode/liujie", "test");
        System.out.println("当前节点状态："+pool.getNodeStatus("/liujie"));
        System.out.println("当前节点信息："+pool.getNodeList("/liujie", false));
        pool.setData("/liujie", "test".getBytes(), 0);
        System.out.println(new String(pool.getData("/liujie"), "UTF-8"));
        pool.deleteNode("/liujie/liujie",0);
    }
}
