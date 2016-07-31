/**
 * Project Name zookeeper
 * File Name WatcherHelper.java
 * Package Name com.lljqiu.zookeeper
 * Create Time 2016年7月31日
 * Auther：liujie@lljqiu.com
 * Copyright © 2005, 2016, https://www.lljqiu.com. All rights reserved.
 */
package com.lljqiu.zookeeper.watcher;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import com.lljqiu.zookeeper.common.pool.ZookeeperPoolStore;

/** 
 * ClassName: WatcherHelper.java <br>
 * Description: <br>
 * Auther：liujie@lljqiu.com <br>
 * Create Time: 2016年7月31日<br>
 */
public class WatcherHelper implements Watcher {
    static ZookeeperPoolStore pool;
    static WatcherHelper wh ;
   

    /* (non-Javadoc)
     * @see org.apache.zookeeper.Watcher#process(org.apache.zookeeper.WatchedEvent)
     */
    public void process(WatchedEvent event) {
        // watcher名称
        System.out.println("watcher=" + this.getClass().getName());
        // 路径
        System.out.println("path=" + event.getPath());
        // 时间类型
        System.out.println("eventType=" + event.getType().name());
        try {
            /*WatcherHelper we1 = new WatcherHelper();
            we1.setZk(zk);*/
            pool.getData(event.getPath(), wh);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }


    }

    public static void main(String[] args)
            throws IOException, KeeperException, InterruptedException {
        /*wh = new WatcherHelper();
        pool = new ZookeeperPoolStore(wh);
        pool.createPool(wh);
        //pool.createNode("/liujie", "liujie");
        pool.getData("/liujie", true);*/
    }

}
