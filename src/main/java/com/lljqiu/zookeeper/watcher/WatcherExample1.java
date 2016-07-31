/**
 * Project Name zookeeper
 * File Name WatcherExample1.java
 * Package Name com.lljqiu.zookeeper.watcher
 * Create Time 2016年7月31日
 * Auther：liujie@lljqiu.com
 * Copyright © 2005, 2016, https://www.lljqiu.com. All rights reserved.
 */
package com.lljqiu.zookeeper.watcher;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

/** 
 * ClassName: WatcherExample1.java <br>
 * Description: <br>
 * Auther：liujie@lljqiu.com <br>
 * Create Time: 2016年7月31日<br>
 */
public class WatcherExample1 implements Watcher {
    private ZooKeeper zk = null;

    public ZooKeeper getZk() {
        return zk;
    }

    public void setZk(ZooKeeper zk) {
        this.zk = zk;
    }
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
            WatcherExample1 we1 = new WatcherExample1();
            we1.setZk(zk);
            zk.getData(event.getPath(), we1, null);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
